import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
})
export class RegisterComponent {
  name = '';
  password = '';
  error = '';
  nameError = false;
  loading = false;

  constructor(private auth: AuthService, private router: Router, private cdr: ChangeDetectorRef) {}

  close() {
    this.router.navigate(['/']);
  }

  submit() {
    this.error = '';
    if (!this.name || !this.password) {
      this.error = 'Engrese nombre y contraseña';
      return;
    }
    this.loading = true;
    this.auth.register(this.name, this.password).subscribe({
      next: (user) => {
        this.loading = false;
        this.router.navigate(['/']);
        try {
          this.cdr.detectChanges();
        } catch (e) {}
      },
      error: (err) => {
        console.error('Registration error', err);
        this.loading = false;
        if (err && err.status === 406) {
          this.error = 'El usuario ya existe';
          this.nameError = true;
          try {
            this.cdr.detectChanges();
          } catch (e) {}
          return;
        }
        this.nameError = false;
        const serverMessage =
          err?.error?.message || (typeof err?.error === 'string' ? err.error : null);
        this.error = serverMessage || 'Error al registrar el usuario';
        try {
          this.cdr.detectChanges();
        } catch (e) {}
      },
    });
  }
}
