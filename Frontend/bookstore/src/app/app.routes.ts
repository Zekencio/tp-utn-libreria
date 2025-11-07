import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';
import { RegisterComponent } from './pages/register/register';
import { LoginComponent } from './pages/login/login';
import { ProfileComponent } from './pages/profile/profile-client';
import { ProfileWrapperComponent } from './pages/profile/profile-wrapper';
import { ProfileSellerComponent } from './pages/profile/profile-seller';
import { SellerGuard } from './pages/profile/seller.guard';
import { SellerResolver } from './pages/profile/seller.resolver';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'profile',
    component: ProfileWrapperComponent,
    children: [
      { path: 'client', component: ProfileComponent },
      {
        path: 'seller',
        component: ProfileSellerComponent,
        canActivate: [SellerGuard],
        resolve: { sellerProfile: SellerResolver },
      },
      { path: '', redirectTo: 'client', pathMatch: 'full' },
    ],
  },
];
