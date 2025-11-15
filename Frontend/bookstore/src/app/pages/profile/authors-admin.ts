import { Component, OnInit, ChangeDetectorRef, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthorService, AuthorDTO } from '../../services/author.service';

@Component({
  selector: 'app-author-admin',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './authors-admin.html',
  styleUrls: ['./author-admin.css'],
})
export class AuthorsAdminComponent implements OnInit {
  authors: AuthorDTO[] = [];
  loading = false;
  showCreateModal = false;
  newAuthorName = '';
  newAuthorBirthDate: Date | null= null ;
  creating = false;
  createError: string | null = null;
  isEditMode = false;
  editingId: number | null = null;
  showDeleteModal = false;
  deleteTarget: AuthorDTO | null = null;
  deleting = false;

  constructor(
    private authorService: AuthorService,
    private cd: ChangeDetectorRef,
    private zone: NgZone
  ) {}

  ngOnInit(): void {
    this.loadAuthors();
  }

  loadAuthors(): void {
    this.loading = true;
    this.authorService.getAll().subscribe({
      next: (g) => {
        this.zone.run(() => {
          this.authors = g || [];
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

  openCreateModal(): void {
    this.isEditMode = false;
    this.editingId = null;
    this.createError = null;
    this.newAuthorName = '';
    this.newAuthorBirthDate = null;
    this.showCreateModal = true;
  }

  closeCreateModal(): void {
    if (this.creating) return;
    this.showCreateModal = false;
    this.createError = null;
  }
  openEditModal(g: AuthorDTO): void {
    this.isEditMode = true;
    this.editingId = g.id || null;
    this.createError = null;
    this.newAuthorName = g.name || '';
    this.newAuthorBirthDate = g.birthDate || null;
    this.showCreateModal = true;
  }

  submitCreateOrEdit(): void {
    const name = (this.newAuthorName || '').trim();
    if (!name) {
      this.createError = 'El nombre es requerido.';
      return;
    }
    this.creating = true;
    this.createError = null;

    const payload = { name, description: this.newAuthorBirthDate || '' };

    const obs =
      this.isEditMode && this.editingId
        ? this.authorService.update(this.editingId, payload)
        : this.authorService.create(payload);

    obs.subscribe({
      next: () => {
        this.zone.run(() => {
          this.creating = false;
          this.showCreateModal = false;
          this.loadAuthors();
          try {
            this.cd.detectChanges();
          } catch (e) {}
        });
      },
      error: (err) => {
        this.zone.run(() => {
          this.creating = false;
          this.createError = err?.error?.message || 'Error al crear/editar género.';
          try {
            this.cd.detectChanges();
          } catch (e) {}
        });
      },
    });
  }

  deleteGenre(g: AuthorDTO): void {
    this.deleteTarget = g;
    this.showDeleteModal = true;
  }

  cancelDelete(): void {
    if (!this.showDeleteModal) return;
    this.showDeleteModal = false;
    this.deleteTarget = null;
  }

  confirmDelete(): void {
    if (!this.deleteTarget) return;
    const id = this.deleteTarget.id!;
    this.deleting = true;
    this.authorService.delete(id).subscribe({
      next: () => {
        setTimeout(() => {
          this.zone.run(() => {
            this.deleting = false;
            this.showDeleteModal = false;
            this.deleteTarget = null;
            this.loadAuthors();
            try {
              this.cd.detectChanges();
            } catch (e) {}
          });
        }, 450);
      },
      error: () => {
        this.zone.run(() => {
          this.deleting = false;
          this.showDeleteModal = false;
          this.deleteTarget = null;
        });
      },
    });
  }
}
