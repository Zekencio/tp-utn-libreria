import { Component, OnInit, ChangeDetectorRef, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ProfileTableComponent } from '../../shared/profile-table/profile-table';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog';
import { AuthService } from '../../services/auth.service';

interface UserRow {
  id?: number;
  name: string;
  roles: string;
}

@Component({
  selector: 'app-users-admin',
  standalone: true,
  imports: [CommonModule, RouterModule, ProfileTableComponent, ConfirmDialogComponent],
  templateUrl: './users-admin.html',
  styleUrls: ['../../shared/profile-table/profile-table.css'],
})
export class UsersAdminComponent implements OnInit {
  users: UserRow[] = [];
  loading = false;
  showDeleteModal = false;
  deleteTarget: UserRow | null = null;
  deleting = false;
  deleteMessage = '';
  deleteError: string | null = null;

  constructor(
    private http: HttpClient,
    private cd: ChangeDetectorRef,
    private zone: NgZone,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading = true;
    this.http.get<any[]>('/api/users').subscribe({
      next: (res) => {
        this.zone.run(() => {
          const current = this.auth.user;
          const currentId = current?.id;
          const currentName = current?.name || current?.email;
          this.users = (res || [])
            .filter((u: any) => {
              if (!u) return false;
              if (currentId != null && u.id === currentId) return false;
              const uname = u.name || u.email || '';
              if (currentName && uname === currentName) return false;
              return true;
            })
            .map((u: any) => ({
              id: u.id,
              name: u.name || u.email || '',
              roles: (u.roles || []).map((r: string) => r.replace(/^ROLE_/, '')).join(', '),
            }));
          this.loading = false;
          try {
            this.cd.detectChanges();
          } catch (e) {}
        });
      },
      error: () => {
        this.zone.run(() => (this.loading = false));
      },
    });
  }

  deleteUser(row: UserRow): void {
    this.deleteTarget = row;
    this.deleteMessage = `¿Estás seguro que deseas eliminar al usuario "${row.name}"?`;
    this.deleteError = null;
    this.showDeleteModal = true;
  }

  cancelDelete(): void {
    if (!this.showDeleteModal) return;
    this.showDeleteModal = false;
    this.deleteTarget = null;
    this.deleteError = null;
  }

  confirmDelete(): void {
    if (!this.deleteTarget || !this.deleteTarget.id) return;
    const id = this.deleteTarget.id;
    this.deleting = true;
    const token = localStorage.getItem('jwtToken');
    const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;
    this.http.delete(`/api/users/${id}`, headers ? { headers } : {}).subscribe({
      next: () => {
        setTimeout(() => {
          this.zone.run(() => {
            this.deleting = false;
            this.showDeleteModal = false;
            this.deleteTarget = null;
            this.loadUsers();
            try {
              this.cd.detectChanges();
            } catch (e) {}
          });
        }, 350);
      },
      error: (err) => {
        this.zone.run(() => {
          this.deleting = false;
          this.deleteError = err?.error?.message || 'Error al eliminar el usuario.';
          try {
            this.cd.detectChanges();
          } catch (e) {}
        });
      },
    });
  }
}
