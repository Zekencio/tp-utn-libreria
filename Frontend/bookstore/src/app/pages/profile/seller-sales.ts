import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-seller-sales',
  standalone: true,
  imports: [CommonModule],
  template: `
    <section class="seller-sales">
      <header class="admin-header"><h2>Mis Ventas</h2></header>
      <div class="admin-container">
        <p>Ventas del vendedor.</p>
      </div>
    </section>
  `,
})
export class SellerSalesComponent {}
