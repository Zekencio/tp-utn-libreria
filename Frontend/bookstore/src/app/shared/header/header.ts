import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
})
export class HeaderComponent {
  menuOpen = false;
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

  constructor(private router: Router) {}
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
}
