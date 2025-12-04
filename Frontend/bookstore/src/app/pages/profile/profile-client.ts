import { Component, WritableSignal, signal, effect, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Subscription } from 'rxjs';
import { SellerProfileService, SellerProfileDTOFull } from '../../services/seller-profile.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './profile-client.html',
})
export class ProfileComponent implements OnDestroy {
  showSellerModal = false;
  isRegistering = false;
  sellerName: string = '';
  sellerAddress: string = '';
  showEmailModal = false;
  emailModalStep: number = 1;
  emailCurrentPassword: string = '';
  newEmail: string = '';
  isUpdatingEmail = false;
  emailUpdateError: string | null = null;
  sellerProfile: WritableSignal<SellerProfileDTOFull | null> = signal(null);

  private roleLabels: Record<string, string> = {
    ROLE_CLIENT: 'Cliente',
    ROLE_ADMIN: 'Administrador',
    ROLE_SELLER: 'Vendedor',
  };

  private qpSub: Subscription | null = null;

  constructor(
    public auth: AuthService,
    private sellerProfileService: SellerProfileService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    effect(() => {
      const u = this.auth.user;

      if (u) {
        this.newEmail = u.email || u.name || '';
      } else {
        this.newEmail = '';
      }

      if (u?.roles?.includes('ROLE_ADMIN')) {
        this.router.navigate(['/profile', 'admin']);
        return;
      }

      if (u?.roles?.includes('ROLE_SELLER')) {
        this.loadSellerProfileIfNeeded();
      } else {
        this.sellerProfile.set(null);
      }
    });

    this.qpSub = this.route.queryParams.subscribe((qp) => {
      if (qp && qp['openSellerModal']) {
        this.showSellerModal = true;
        try {
          this.router.navigate([], {
            relativeTo: this.route,
            queryParams: { openSellerModal: null },
            replaceUrl: true,
          });
        } catch (e) {}
      }
    });
  }

  ngOnDestroy(): void {
    this.qpSub?.unsubscribe();
  }

  openEmailModal(): void {
    this.emailModalStep = 1;
    this.emailCurrentPassword = '';
    this.emailUpdateError = null;
    const u = this.auth.userSignal();
    this.newEmail = u?.email || u?.name || '';
    this.showEmailModal = true;
  }

  closeEmailModal(): void {
    if (this.isUpdatingEmail) return;
    this.showEmailModal = false;
  }

  submitEmailStep1(): void {
    if (!this.emailCurrentPassword) {
      this.emailUpdateError = 'Ingrese la contraseña actual';
      return;
    }
    this.emailUpdateError = null;
    this.emailModalStep = 2;
  }

  submitEmailStep2(): void {
    if (!this.newEmail || !this.emailCurrentPassword) {
      this.emailUpdateError = 'Ingrese email y contraseña actuales.';
      return;
    }
    const emailTrim = this.newEmail.trim();
    if (emailTrim.length < 8) {
      this.emailUpdateError = 'Email debe tener al menos 8 caracteres.';
      return;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(emailTrim)) {
      this.emailUpdateError = 'Ingrese un correo electrónico válido.';
      return;
    }
    this.isUpdatingEmail = true;
    this.emailUpdateError = null;
    this.auth
      .updateUser({ name: emailTrim, currentPassword: this.emailCurrentPassword })
      .subscribe({
        next: () => {
          try {
            localStorage.removeItem('basicAuth');
            localStorage.removeItem('jwtToken');
          } catch (e) {}
          this.isUpdatingEmail = false;
          this.showEmailModal = false;
          this.emailCurrentPassword = '';
        },
        error: (err) => {
          this.isUpdatingEmail = false;
          if (err?.status === 401) {
            this.emailUpdateError = 'Contraseña actual incorrecta.';
            this.emailModalStep = 1;
          } else if (err?.status === 406) {
            this.emailUpdateError = 'Ya existe un usuario con ese email.';
          } else {
            this.emailUpdateError = 'Error al actualizar email. Inténtelo más tarde.';
          }
        },
      });
  }

  roleLabel(role?: string): string {
    if (!role) return '';
    if (this.roleLabels[role]) return this.roleLabels[role];
    const cleaned = role.replace(/^ROLE_/, '').toLowerCase();
    return cleaned.charAt(0).toUpperCase() + cleaned.slice(1);
  }

  rolesLabel(roles?: string[] | null): string {
    if (!roles || roles.length === 0) return '—';
    return roles.map((r) => this.roleLabel(r)).join(', ');
  }

  hasSellerRole(): boolean {
    return !!this.auth.userSignal()?.roles?.includes('ROLE_SELLER');
  }

  onSellClick(): void {
    const tokenPresent = (() => {
      try {
        return !!localStorage.getItem('jwtToken');
      } catch (e) {
        return false;
      }
    })();

    if (!this.auth.userSignal() || !tokenPresent) {
      this.router.navigate(['/login'], { queryParams: { returnUrl: '/profile' } });
      return;
    }

    if (this.hasSellerRole()) {
      this.router.navigate(['/profile', 'seller']);
    } else {
      this.showSellerModal = true;
    }
  }

  submitSellerRegistration(): void {
    if (!this.sellerName || this.sellerName.trim().length < 6) {
      this.emailUpdateError = 'El nombre de la empresa debe tener al menos 6 caracteres.';
      return;
    }
    if (!this.sellerAddress || this.sellerAddress.trim().length < 6) {
      this.emailUpdateError = 'La dirección debe tener al menos 6 caracteres.';
      return;
    }
    this.emailUpdateError = null;
    this.isRegistering = true;
    const start = Date.now();
    const minMs = 2500;

    this.sellerProfileService
      .createSellerProfile({ name: this.sellerName.trim(), address: this.sellerAddress.trim() })
      .subscribe({
        next: () => {
          const finish = () => {
            this.auth.me().subscribe((user) => {
              this.isRegistering = false;
              this.showSellerModal = false;
              this.sellerName = '';
              this.sellerAddress = '';
              if (user?.roles?.includes('ROLE_SELLER')) {
                this.router.navigate(['/profile', 'seller']);
              }
            });
          };
          const elapsed = Date.now() - start;
          const wait = Math.max(0, minMs - elapsed);
          setTimeout(finish, wait);
        },
        error: () => {
          const finishErr = () => {
            this.isRegistering = false;
          };
          const elapsed = Date.now() - start;
          const wait = Math.max(0, minMs - elapsed);
          setTimeout(finishErr, wait);
        },
      });
  }

  onModalBackdropClick(): void {
    if (this.isRegistering) return;
  }

  onModalCloseClick(): void {
    if (this.isRegistering) return;
    this.showSellerModal = false;
  }

  loadSellerProfileIfNeeded(): void {
    if (!this.hasSellerRole()) return;
    this.sellerProfileService.getMySellerProfile().subscribe({
      next: (p) => this.sellerProfile.set(p),
      error: () => this.sellerProfile.set(null),
    });
  }
}
