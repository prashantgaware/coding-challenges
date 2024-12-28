import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { JsonParserComponent } from "./components/json-parser/json-parser.component";

@Component({
  selector: 'app-root',
  imports: [JsonParserComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'json-parser-ui';
}
