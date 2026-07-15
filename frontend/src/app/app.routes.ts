import { Routes } from '@angular/router';
import { WorldListComponent } from './components/world-list/world-list.component';
import { WorldDetailComponent } from './components/world-detail/world-detail.component';
import { QuestPlayComponent } from './components/quest-play/quest-play.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ProfileComponent } from './components/profile/profile.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { authGuard } from './services/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'worlds', pathMatch: 'full' },
  { path: 'worlds', component: WorldListComponent, canActivate: [authGuard] },
  { path: 'worlds/:id', component: WorldDetailComponent, canActivate: [authGuard] },
  { path: 'worlds/:worldId/quests/:questId', component: QuestPlayComponent, canActivate: [authGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [authGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'not-found', component: NotFoundComponent },
  { path: '**', redirectTo: 'not-found' }
];
