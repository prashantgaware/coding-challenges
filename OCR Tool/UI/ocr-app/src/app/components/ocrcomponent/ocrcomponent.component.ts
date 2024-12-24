import { Component } from '@angular/core';
import { OCRServiceService } from '../../services/ocrservice.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ocrcomponent',
  imports: [CommonModule],
  templateUrl: './ocrcomponent.component.html',
  styleUrl: './ocrcomponent.component.css'
})
export class OCRComponentComponent {

  public extractedText: string = '';
  public isLoading: boolean = false;

  public constructor(private ocrService: OCRServiceService) {}

  public onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      this.isLoading = true;
      this.ocrService.uploadImage(file).subscribe(
        (response) => {
          this.extractedText = response.text;
          this.isLoading = false;
        },
        (error) => {
          console.error('Error:', error);
          this.isLoading = false;
        }
      );
    }
  }
}
