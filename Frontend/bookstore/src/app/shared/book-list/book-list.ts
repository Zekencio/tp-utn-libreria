import { Component, OnInit, ChangeDetectorRef, NgZone, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookService, BookDTO } from '../../services/book.service';
import { RouterModule } from '@angular/router';
import { CartService } from '../../services/cart.service';

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
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadBooks();
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
    if(bookId){
      this.cart.addToCart(bookId, 1);
    }
  }

  loadBooks(): void {
    this.loading = true;
    this.bookService.getAll().subscribe({
      next: (b) => {
        this.zone.run(() => {
          this.books = b || [];
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
                this.books = b2 || [];
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
