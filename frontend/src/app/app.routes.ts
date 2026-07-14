import { Routes } from '@angular/router';
import { WorldListComponent } from './components/world-list/world-list.component';
import { WorldDetailComponent } from './components/world-detail/world-detail.component';
import { QuestPlayComponent } from './components/quest-play/quest-play.component';

export const routes: Routes = [
  { path: '', redirectTo: 'worlds', pathMatch: 'full' },
  { path: 'worlds', component: WorldListComponent },
  { path: 'worlds/:id', component: WorldDetailComponent },
  { path: 'worlds/:worldId/quests/:questId', component: QuestPlayComponent },
  { path: '**', redirectTo: 'worlds' }
];
