import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SellerProfileDTOFull, SellerProfileService } from '../../services/seller-profile.service';
import { AuthService, UserDTO } from '../../services/auth.service';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-profile-seller',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  template: `
    <header class="profile-title"><h2>Perfil del Vendedor</h2></header>
    <div class="profile-details">
      @if (seller) {
      <div class="profile-email-row">
        <p class="seller-detail-item"><strong>Nombre de empresa:</strong> {{ seller.name }}</p>
        <button class="edit-email" title="Editar" (click)="openEditModal()">
          <i class="ri-edit-line"></i>
        </button>
      </div>
      <div class="profile-email-row">
        <p class="seller-detail-item"><strong>Direccion:</strong> {{ seller.address }}</p>
        <button class="edit-email" title="Editar" (click)="openEditModal()">
          <i class="ri-edit-line"></i>
        </button>
      </div>
      <p class="seller-detail-item">
        <strong>Inventario:</strong> {{ seller.inventory?.length || 0 }} items
      </p>
      }
    </div>

    <!-- Edit seller modal -->
    @if (showEditModal) {
    <div class="modal" (click)="closeEditModal()">
      <div class="modal-content" (click)="$event.stopPropagation()">
        <button class="modal-close" aria-label="Cerrar" (click)="closeEditModal()">×</button>
        <h3>Editar datos del vendedor</h3>
        <div class="modal-field">
          <label for="editSellerName">Nombre de empresa</label>
          <input id="editSellerName" [(ngModel)]="editName" [disabled]="isSavingSeller" />
          @if (editName && editName.trim().length > 0 && editName.trim().length < 6) {
          <div class="field-hint">Nombre debe tener al menos 6 символов.</div>
          }
        </div>
        <div class="modal-field">
          <label for="editSellerAddress">Dirección</label>
          <input id="editSellerAddress" [(ngModel)]="editAddress" [disabled]="isSavingSeller" />
          @if (editAddress && editAddress.trim().length > 0 && editAddress.trim().length < 6) {
          <div class="field-hint">Dirección debe tener al menos 6 символов.</div>
          }
        </div>
        <div class="modal-actions">
          <button
            class="btn-primary"
            (click)="submitEdit()"
            [disabled]="
              isSavingSeller ||
              !editName.trim() ||
              !editAddress.trim() ||
              editName.trim().length < 6 ||
              editAddress.trim().length < 6
            "
          >
            Guardar
          </button>
          <button class="btn-secondary" (click)="closeEditModal()" [disabled]="isSavingSeller">
            Cancelar
          </button>
        </div>
        @if (sellerUpdateError) {
        <div class="error">{{ sellerUpdateError }}</div>
        }
      </div>
    </div>
    }
  `,
})
export class ProfileSellerComponent {
  get seller(): SellerProfileDTOFull | null {
    const u = this.auth.user;
    return u && u.sellerProfile ? (u.sellerProfile as SellerProfileDTOFull) : null;
  }
  showEditModal = false;
  editName = '';
  editAddress = '';
  isSavingSeller = false;
  sellerUpdateError: string | null = null;

  constructor(private sellerService: SellerProfileService, private auth: AuthService) {}

  openEditModal(): void {
    const s = this.seller;
    if (s) {
      this.editName = s.name || '';
      this.editAddress = s.address || '';
    }
    this.sellerUpdateError = null;
    this.showEditModal = true;
  }

  closeEditModal(): void {
    if (this.isSavingSeller) return;
    this.showEditModal = false;
  }

  submitEdit(): void {
    if (!this.editName || this.editName.trim().length < 6) {
      this.sellerUpdateError = 'Nombre de empresa debe tener al menos 6 символов.';
      return;
    }
    if (!this.editAddress || this.editAddress.trim().length < 6) {
      this.sellerUpdateError = 'Dirección debe tener al menos 6 символов.';
      return;
    }
    this.isSavingSeller = true;
    this.sellerUpdateError = null;
    this.sellerService
      .updateSellerProfile({ name: this.editName.trim(), address: this.editAddress.trim() })
      .pipe(finalize(() => (this.isSavingSeller = false)))
      .subscribe({
        next: (res: SellerProfileDTOFull) => {
          const current = this.auth.user;
          if (current) {
            const updated: UserDTO = { ...current, sellerProfile: res };
            this.auth.setCurrentUser(updated);
          }
          this.showEditModal = false;
        },
        error: (_err) => {
          this.sellerUpdateError = 'Error al actualizar datos del vendedor';
        },
      });
  }
}
