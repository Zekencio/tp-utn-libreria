import { CardService } from './../../services/card.service';
import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { BookDTO} from '../../services/book.service';
import { CartService } from '../../services/cart.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CardDTO } from '../../services/card.service';

@Component({
  selector: 'app-cart',
  imports: [CommonModule, RouterModule],
  templateUrl: './cart.html',
  styleUrl: './cart.css',
})
export class CartComponent {
  cartItems : BookDTO[] = [];
  userCards: CardDTO[] = [];
  loading = false;
  errorMessage: string | null = null;

  constructor(
    private cartService: CartService,
    private cardService: CardService,
    private zone: NgZone,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void{
    this.loadCart()
  }

  loadCart(): void{
    this.loading = true
    this.cartService.getCart().subscribe({
      next: (item) =>{
        this.zone.run(() => {
          this.cartItems = item || []
          this.loading = false
          this.errorMessage = null
          try{
            this.cd.detectChanges()
          }catch(e){}
        })
      },
      error: (err: any) => {
        console.error('Failed to load cart items', err);
        this.zone.run(() => {
          this.loading = false;
          this.errorMessage = 'Error al cargar el carrito. Ver consola para más detalles.';
          try {
            this.cd.detectChanges();
          } catch (e) {}
        });
      }
    })
  }

  loadUserCards(): void{
    this.loading = true
    this.cardService.getAll().subscribe({
      next: (card) =>{
        this.zone.run(() => {
          this.userCards = card || []
          this.loading = false
          this.errorMessage = null
          try{
            this.cd.detectChanges()
          }catch(e){}
        })
      },
      error: (err: any) => {
        console.error('Failed to load user cards', err);
        this.zone.run(() => {
          this.loading = false;
          this.errorMessage = 'Error al cargar las tarjetas del usuario. Ver consola para más detalles.';
          try {
            this.cd.detectChanges();
          } catch (e) {}
        });
      }
    })
  }

  getTotalPrice(): number{
    let total = 0
    this.cartItems.forEach(item => {
      item.price ? total += item.price : total += 0
    })
    return total
  }

  substractItemFromCart(id: any): void{
    this.cartService.removeFromCart(id, 1)
  }

  addItemToCart(id: any): void{
    this.cartService.addToCart(id, 1)
  }

  // No sé si esto funciona porque no puedo probar la función de añadir/eliminar libros
  // al no poder hacer andar los perfiles de vendedor y administrador. Si alguno los
  // puede probar por mí, se los agradecería, o aun mejor, me ayudan a arreglar mi 
  // proyecto que debe estar todo descajetado a comparación de ustedes xd!!!
  
  //                                                                      - Eze
  getBookQuantity(id: any){
    let count = 0
    this.cartItems.forEach(item =>{
      item.id === id ? count++ : count += 0 
    })
  }

}
