import { Component, ElementRef, HostListener, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ProfileToggleService } from '../../pages/profile/profile-toggle.service';
import { NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
})
export class HeaderComponent {
  menuOpen = false;
  @ViewChild('userMenu', { static: false }) userMenu?: ElementRef<HTMLElement>;
  cartCount = 0;
  searchQuery = '';

  isDark = false;

  ngOnInit(): void {
    const saved = localStorage.getItem('theme');
    if (saved === 'dark') {
      this.setDark(true);
    } else if (saved === 'light') {
      this.setDark(false);
    } else {
      const prefersDark =
        window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
      this.setDark(prefersDark);
    }
  }

  constructor(
    private router: Router,
    public auth: AuthService,
    private profileToggle: ProfileToggleService
  ) {}
  toggleTheme() {
    this.setDark(!this.isDark);
  }

  private setDark(value: boolean) {
    this.isDark = value;
    const body = document.body;
    if (this.isDark) {
      body.classList.add('dark-theme');
      localStorage.setItem('theme', 'dark');
    } else {
      body.classList.remove('dark-theme');
      localStorage.setItem('theme', 'light');
    }
  }

  getInitials(nameOrEmail: string | undefined | null) {
    if (!nameOrEmail) return '';
    const local = nameOrEmail.split('@')[0] || nameOrEmail;
    return local.slice(0, 2).toUpperCase();
  }

  toggleUserMenu(e: MouseEvent) {
    e.stopPropagation();
    this.menuOpen = !this.menuOpen;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(e: MouseEvent) {
    if (!this.menuOpen) return;
    try {
      const target = e.target as Node;
      if (!this.userMenu || !this.userMenu.nativeElement) {
        this.menuOpen = false;
        return;
      }
      if (!this.userMenu.nativeElement.contains(target)) {
        this.menuOpen = false;
      }
    } catch (err) {
      this.menuOpen = false;
    }
  }

  navigateTo(path: string) {
    this.menuOpen = false;
    this.router.navigate([path]);
  }

  goToProfile() {
    this.menuOpen = false;
    const user = this.auth.userSignal();
    try {
      if (user?.roles?.includes('ROLE_ADMIN')) {
        this.router.navigate(['/profile', 'admin']);
        return;
      }
      if (user?.roles?.includes('ROLE_SELLER')) {
        this.router.navigate(['/profile', 'seller']);
        return;
      }
    } catch (e) {}
    this.router.navigate(['/profile', 'client']);
  }

  get profileRoute(): any[] {
    const user = this.auth.userSignal();
    try {
      if (user?.roles?.includes('ROLE_ADMIN')) return ['/profile', 'admin'];
      if (user?.roles?.includes('ROLE_SELLER')) return ['/profile', 'seller'];
    } catch (e) {}
    return ['/profile', 'client'];
  }

  debugProfileClick(e: MouseEvent) {
    try {
      console.log('debugProfileClick - userSignal:', this.auth.userSignal());
      console.log('debugProfileClick - profileRoute:', this.profileRoute);
    } catch (err) {}
    this.menuOpen = false;
    e.preventDefault();
    try {
      this.router.navigate(this.profileRoute);
    } catch (err) {
      console.error('Navigation error', err);
      this.router.navigate(['/profile', 'client']);
    }
  }

  get isAdmin(): boolean {
    try {
      const u = this.auth.userSignal();
      if (u && u.roles) return !!u.roles.includes('ROLE_ADMIN');
      const raw = localStorage.getItem('currentUser');
      if (raw) {
        const parsed = JSON.parse(raw);
        return !!parsed?.roles?.includes('ROLE_ADMIN');
      }
    } catch (e) {}
    return false;
  }

  onAdminBooksClick(): void {
    this.menuOpen = false;
    this.router.navigate(['/profile', 'admin', 'books']);
  }

  onAdminGenresClick(): void {
    this.menuOpen = false;
    this.router.navigate(['/profile', 'admin', 'genres']);
  }

  onAdminAuthorsClick(): void {
    this.menuOpen = false;
    this.router.navigate(['/profile', 'admin', 'authors']);
  }

  onAdminClientsClick(): void {
    this.menuOpen = false;
    this.router.navigate(['/profile', 'admin', 'clients']);
  }

  onAdminSellersClick(): void {
    this.menuOpen = false;
    this.router.navigate(['/profile', 'admin', 'sellers']);
  }

  onSellClick(): void {
    this.menuOpen = false;
    const user = this.auth.userSignal();
    const tokenPresent = (() => {
      try {
        return !!localStorage.getItem('basicAuth');
      } catch (e) {
        return false;
      }
    })();

    if (!user || !tokenPresent) {
      this.router.navigate(['/login'], { queryParams: { returnUrl: '/profile' } });
      return;
    }

    if (user.roles?.includes('ROLE_SELLER')) {
      const onProfile = (this.currentUrl || this.router.url || '').includes('/profile');
      if (!onProfile) {
        this.router.navigate(['/profile']).then(() => this.profileToggle.requestToggle());
      } else {
        this.profileToggle.requestToggle();
      }
    } else {
      this.router.navigate(['/profile', 'client'], { queryParams: { openSellerModal: '1' } });
    }
  }

  onPurchasesClick(): void {
    this.menuOpen = false;
    const user = this.auth.userSignal();
    const tokenPresent = (() => {
      try {
        return !!localStorage.getItem('basicAuth');
      } catch (e) {
        return false;
      }
    })();

    if (!user || !tokenPresent) {
      this.router.navigate(['/login'], { queryParams: { returnUrl: '/profile' } });
      return;
    }

    this.router.navigate(['/profile', 'client']);
  }

  onSellerSalesClick(): void {
    this.menuOpen = false;
    this.router.navigate(['/profile', 'seller', 'sales']);
  }

  onSellerCatalogClick(): void {
    this.menuOpen = false;
    this.router.navigate(['/profile', 'seller', 'catalog']);
  }

  onAddBookClick(): void {
    this.menuOpen = false;
    this.router.navigate(['/profile', 'seller', 'books', 'new']);
  }

  getLocalPart(value?: string | null) {
    if (!value) return '';
    const idx = value.indexOf('@');
    const local = idx === -1 ? value : value.slice(0, idx);
    return local.length > 20 ? local.slice(0, 17) + '...' : local;
  }

  logout() {
    this.menuOpen = false;
    this.auth.logout();
    this.router.navigate(['/']);
  }

  currentUrl = '';

  ngAfterViewInit(): void {
    this.currentUrl = this.router.url || '';
    this.router.events.pipe(filter((e) => e instanceof NavigationEnd)).subscribe((e: any) => {
      this.currentUrl = e.urlAfterRedirects || e.url || '';
    });
  }

  get isSellerRoute(): boolean {
    return this.currentUrl.includes('/profile/seller');
  }

  get toggleLabel(): string {
    return this.isSellerRoute ? 'Comprar' : 'Vender';
  }

  onHeaderToggle(): void {
    this.menuOpen = false;
    this.profileToggle.requestToggle();
  }
}
