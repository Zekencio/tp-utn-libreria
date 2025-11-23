import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-compras-client',
  standalone: true,
  imports: [CommonModule],
  template: `
    <section class="admin-page">
      <header class="admin-header">
        <h2>Mis compras</h2>
      </header>

      <div class="admin-container">
        <p>Lista de compras del cliente.</p>
      </div>
    </section>
  `,
})
export class ComprasClientComponent {}
