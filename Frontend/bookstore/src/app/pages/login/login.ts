import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class LoginComponent {
  name = '';
  password = '';
  error = '';
  loading = false;

  constructor(private auth: AuthService, private router: Router) {}

  close() {
    this.router.navigate(['/']);
  }

  submit() {
    this.error = '';
    if (!this.name || !this.password) {
      this.error = 'Ingrese nombre y contraseña';
      return;
    }
    this.loading = true;
    this.auth
      .login(this.name, this.password)
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: () => {
          const u = this.auth.user;
          if (u?.roles?.includes('ROLE_ADMIN')) {
            this.router.navigate(['/profile', 'admin']);
            return;
          }
          this.router.navigate(['/']);
        },
        error: (err) => {
          console.error('Login error', err);
          if (err?.status === 401) {
            this.error = 'Login o contraseña incorrectos';
          } else {
            this.error = err?.error?.message ?? 'Error al iniciar sesión';
          }
        },
      });
  }
}
