import { Component, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from '../../services/auth.service';
import { ProfileToggleService } from './profile-toggle.service';

@Component({
  selector: 'app-profile-wrapper',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="profile-page">
      <div class="menu-container">
        <aside class="profile-side">
          <nav class="profile-menu">
            <!-- Top: Mi perfil (links to client or seller depending on route) -->
            <a
              class="profile-menu__item"
              [routerLink]="isSellerRoute ? ['/profile', 'seller'] : ['/profile', 'client']"
              routerLinkActive="active"
              [routerLinkActiveOptions]="{ exact: true }"
              >Mi perfil</a
            >

            <!-- Client-only items -->
            @if (!isSellerRoute) {
            <a
              class="profile-menu__item"
              routerLink="/profile/client/cards"
              routerLinkActive="active"
              >Mis tarjetas</a
            >
            <a
              class="profile-menu__item"
              routerLink="/profile/client/purchases"
              routerLinkActive="active"
              >Mis compras</a
            >
            } @else {
            <!-- Seller-only items (shown when in seller area) -->
            <a
              class="profile-menu__item"
              routerLink="/profile/seller/sales"
              routerLinkActive="active"
              >Mis ventas</a
            >
            <a
              class="profile-menu__item"
              routerLink="/profile/seller/catalog"
              routerLinkActive="active"
              >Mi catalogo</a
            >
            <a
              class="profile-menu__item"
              routerLink="/profile/seller/books/new"
              routerLinkActive="active"
              >Agregar libro</a
            >
            }

            <!-- single toggle: Vender / Comprar depending on current route -->
            <a
              class="profile-menu__item"
              (click)="openToggleConfirm()"
              [class.active]="isSellerRoute"
              >{{ toggleLabel }}</a
            >
          </nav>
        </aside>
      </div>
      <section class="profile-content">
        <router-outlet></router-outlet>
      </section>
    </div>

    <!-- Confirmation modal for switching profiles -->
    @if (showConfirm) {
    <div class="modal" (click)="cancelConfirm()">
      <div class="modal-content" (click)="$event.stopPropagation()">
        <button class="modal-close" aria-label="Cerrar" (click)="cancelConfirm()">×</button>
        <h3>{{ confirmTitle }}</h3>
        <p>{{ confirmMessage }}</p>
        <div class="modal-actions">
          <button class="btn-primary" (click)="confirmSwitch()">Sí, continuar</button>
          <button class="btn-secondary" (click)="cancelConfirm()">Cancelar</button>
        </div>
      </div>
    </div>
    }
  `,
  styleUrls: ['./profile.css'],
  encapsulation: ViewEncapsulation.None,
})
export class ProfileWrapperComponent {
  currentUrl = '';
  showConfirm = false;
  confirmTitle = '';
  confirmMessage = '';
  confirmTarget: 'seller' | 'client' | null = null;

  constructor(
    private router: Router,
    public auth: AuthService,
    private profileToggle: ProfileToggleService
  ) {
    this.currentUrl = this.router.url || '';
    this.router.events.pipe(filter((e) => e instanceof NavigationEnd)).subscribe((e: any) => {
      this.currentUrl = e.urlAfterRedirects || e.url || '';
    });
    this.profileToggle.toggle$.subscribe(() => this.openToggleConfirm());
  }

  get isSellerRoute(): boolean {
    return this.currentUrl.includes('/profile/seller');
  }

  get toggleLabel(): string {
    return this.isSellerRoute ? 'Comprar' : 'Vender';
  }

  openToggleConfirm(): void {
    const user = this.auth.userSignal();
    const hasSeller = !!user?.roles?.includes('ROLE_SELLER');

    if (this.isSellerRoute) {
      this.confirmTarget = 'client';
      this.confirmTitle = 'Cambiar a perfil de cliente';
      this.confirmMessage = '¿Deseas pasar al perfil de cliente?';
      this.showConfirm = true;
      return;
    }

    if (hasSeller) {
      this.confirmTarget = 'seller';
      this.confirmTitle = 'Cambiar a perfil de vendedor';
      this.confirmMessage = '¿Deseas pasar al perfil de vendedor?';
      this.showConfirm = true;
      return;
    }

    this.router.navigate(['/profile', 'client'], { queryParams: { openSellerModal: '1' } });
  }

  confirmSwitch(): void {
    this.showConfirm = false;
    const user = this.auth.userSignal();
    const hasSeller = !!user?.roles?.includes('ROLE_SELLER');

    if (this.confirmTarget === 'client') {
      this.router.navigate(['/profile', 'client']);
    } else if (this.confirmTarget === 'seller') {
      if (hasSeller) {
        this.router.navigate(['/profile', 'seller']);
      } else {
        this.router.navigate(['/profile', 'client'], { queryParams: { openSellerModal: '1' } });
      }
    }
    this.confirmTarget = null;
  }

  cancelConfirm(): void {
    this.showConfirm = false;
    this.confirmTarget = null;
  }

  onToggle(): void {
    const user = this.auth.userSignal();
    const hasSeller = !!user?.roles?.includes('ROLE_SELLER');

    if (this.isSellerRoute) {
      this.router.navigate(['/profile', 'client']);
      return;
    }

    if (hasSeller) {
      this.router.navigate(['/profile', 'seller']);
    } else {
      this.router.navigate(['/profile', 'client'], { queryParams: { openSellerModal: '1' } });
    }
  }
}
