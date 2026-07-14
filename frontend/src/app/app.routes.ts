import { Routes } from '@angular/router';
import { WorldListComponent } from './components/world-list/world-list.component';
import { WorldDetailComponent } from './components/world-detail/world-detail.component';

export const routes: Routes = [
  { path: '', redirectTo: 'worlds', pathMatch: 'full' },
  { path: 'worlds', component: WorldListComponent },
  { path: 'worlds/:id', component: WorldDetailComponent },
  { path: '**', redirectTo: 'worlds' }
];
