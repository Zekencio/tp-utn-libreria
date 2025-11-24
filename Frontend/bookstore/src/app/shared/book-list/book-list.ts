import { Component, OnInit, ChangeDetectorRef, NgZone, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookService, BookDTO } from '../../services/book.service';
import { RouterModule, Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { AuthService } from '../../services/auth.service';
import { ProfileToggleService } from '../../pages/profile/profile-toggle.service';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './book-list.html',
  styleUrls: ['./book-list.css'],
})
export class BookListComponent implements OnInit {
  books: BookDTO[] = [];
  loading = false;
  errorMessage: string | null = null;
  selectedBook: BookDTO | null = null;

  constructor(
    private bookService: BookService,
    private cart: CartService,
    private zone: NgZone,
    private cd: ChangeDetectorRef,
    private router: Router,
    private auth: AuthService,
    private profileToggle: ProfileToggleService
  ) {}

  ngOnInit(): void {
    this.loadBooks();
  }

  /**
   * Return array with in-stock books first, then out-of-stock ones (stable order preserved).
   */
  private sortBooks(books: BookDTO[]): BookDTO[] {
    if (!books || books.length === 0) return books;
    const inStock: BookDTO[] = [];
    const outStock: BookDTO[] = [];
    for (const b of books) {
      if (b && b.stock != null && b.stock <= 0) outStock.push(b);
      else inStock.push(b);
    }
    return [...inStock, ...outStock];
  }

  openDetails(book: BookDTO): void {
    this.selectedBook = book;
  }

  closeDetails(): void {
    this.selectedBook = null;
  }

  @HostListener('document:keydown', ['$event'])
  handleEscape(event: Event) {
    const ke = event as KeyboardEvent;
    if (ke.key === 'Escape' && this.selectedBook) {
      this.closeDetails();
    }
  }

  addToCart(bookId: number|undefined){
    if (!bookId) return;
    const book = this.books.find(x => x.id === bookId);
    if (book && (book.stock === 0 || book.stock <= 0)) {
      // prevent adding out-of-stock item (silent)
      return;
    }
    // if current active role is seller, ask to switch to client before adding
    try {
      const active = this.auth.getActiveRole();
      if (active === 'ROLE_SELLER') {
        const payload = {
          target: 'client' as const,
          title: 'Cambiar a perfil de cliente',
          message: 'Estás en modo Vendedor. Para comprar debes cambiar a perfil de cliente. ¿Deseas continuar?',
          onConfirm: () => {
            try {
              this.auth.setActiveRole('ROLE_CLIENT');
            } catch (e) {}
            try {
              this.cart.addToCart(bookId, 1).subscribe({
                next: () => {},
                error: (err) => console.error('Error agregando al carrito', err)
              });
            } catch (e) {}
            try { this.router.navigate(['/']); } catch (e) {}
          },
        };

        // show global confirm dialog without navigating away from products page
        this.profileToggle.requestToggle(payload);
        return;
      }
    } catch (e) {}
    this.cart.addToCart(bookId, 1).subscribe({
      next: () => {
        // successfully added, cart service updates subjects
      },
      error: (err) => {
        console.error('Error agregando al carrito', err);
      }
    });
  }

  loadBooks(): void {
    this.loading = true;
    this.bookService.getAll().subscribe({
      next: (b) => {
        this.zone.run(() => {
          this.books = this.sortBooks(b || []);
          this.loading = false;
          this.errorMessage = null;
          try {
            this.cd.detectChanges();
          } catch (e) {}
        });
      },
      error: (err: any) => {
        console.error('Failed to load books (authenticated). Retrying public fetch', err);
        const status = err?.status;
        if (status === 401 || status === 403) {
          this.bookService.getAllPublic().subscribe({
            next: (b2) => {
              this.zone.run(() => {
                this.books = this.sortBooks(b2 || []);
                this.loading = false;
                this.errorMessage = null;
                try {
                  this.cd.detectChanges();
                } catch (e) {}
              });
            },
            error: (err2) => {
              console.error('Public fetch also failed', err2);
              this.zone.run(() => {
                this.loading = false;
                this.errorMessage = 'Error al cargar libros. Ver consola para más detalles.';
                try {
                  this.cd.detectChanges();
                } catch (e) {}
              });
            },
          });
          return;
        }
        this.zone.run(() => {
          this.loading = false;
          this.errorMessage = 'Error al cargar libros. Ver consola para más detalles.';
          try {
            this.cd.detectChanges();
          } catch (e) {}
        });
      },
    });
  }
}
