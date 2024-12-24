import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { OCRComponentComponent } from './components/ocrcomponent/ocrcomponent.component';

@Component({
  selector: 'app-root',
  imports: [OCRComponentComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ocr-app';
}
