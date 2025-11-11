import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-profile-admin',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './profile-admin.html',
})
export class ProfileAdminComponent {
  constructor(public auth: AuthService) {}

  private roleLabels: Record<string, string> = {
    ROLE_CLIENT: 'Cliente',
    ROLE_ADMIN: 'Administrador',
    ROLE_SELLER: 'Vendedor',
  };

  roleLabel(role?: string): string {
    if (!role) return '';
    if (this.roleLabels[role]) return this.roleLabels[role];
    const cleaned = role.replace(/^ROLE_/, '').toLowerCase();
    return cleaned.charAt(0).toUpperCase() + cleaned.slice(1);
  }
}
