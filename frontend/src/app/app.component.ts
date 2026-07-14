import { Component } from '@angular/core';
import { WorldListComponent } from './components/world-list/world-list.component';

@Component({
  selector: 'app-root',
  imports: [WorldListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
