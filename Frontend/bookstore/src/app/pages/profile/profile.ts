import { Component, Signal, WritableSignal, signal } from '@angular/core';
import { CommonModule, NgIf } from '@angular/common';
import { Subscription } from 'rxjs';
import { RouterModule } from '@angular/router';
import { AuthService, UserDTO } from '../../services/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterModule, NgIf],
  templateUrl: './profile.html',
  styleUrls: ['./profile.css'],
})
export class ProfileComponent {
  currentUser$: Observable<UserDTO | null>;
  user!: WritableSignal<UserDTO | null>;
  private userSub?: Subscription;

  private roleLabels: Record<string, string> = {
    ROLE_CLIENT: 'Cliente',
    ROLE_ADMIN: 'Administrador',
    ROLE_SELLER: 'Vendedor',
    ROLE_USER: 'Usuario',
  };

  constructor(public auth: AuthService) {
    this.currentUser$ = this.auth.currentUser$;
    this.user = signal<UserDTO | null>(null);
    this.userSub = this.currentUser$.subscribe((u) => this.user.set(u));
  }

  ngOnDestroy(): void {
    this.userSub?.unsubscribe();
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
}
