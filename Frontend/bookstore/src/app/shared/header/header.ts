import { Component, ElementRef, HostListener, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

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

  constructor(private router: Router, public auth: AuthService) {}
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
    this.router.navigate(['/profile']);
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
}
