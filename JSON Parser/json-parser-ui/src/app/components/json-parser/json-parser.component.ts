import { Component } from '@angular/core';
import { JsonParserService } from '../../services/json-parser.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-json-parser',
  imports: [FormsModule, CommonModule],
  templateUrl: './json-parser.component.html',
  styleUrl: './json-parser.component.css'
})
export class JsonParserComponent {
  
  public jsonInput: string = '';
  public result: any = null;

  public constructor(private jsonParserService: JsonParserService) {}

  public validateJson() {
    this.jsonParserService.validateJson(this.jsonInput).subscribe(
      response => this.result = response,
      error => this.result = error.error
    );
  }

  public parseJson() {
    this.jsonParserService.parseJson(this.jsonInput).subscribe(
      response => this.result = response,
      error => this.result = error.error
    );
  }

  public copyResultToClipboard(): void {
    const resultString = JSON.stringify(this.result, null, 2); // Format the JSON result
    navigator.clipboard
      .writeText(resultString)
      // .then(() => alert('Copied to clipboard!'))
      .catch(err => console.error('Failed to copy: ', err));
  }

}
