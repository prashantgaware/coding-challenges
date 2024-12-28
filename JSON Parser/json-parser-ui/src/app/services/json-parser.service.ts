import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class JsonParserService {
  private apiUrl = 'http://localhost:5171/api/jsonparser';

  constructor(private http: HttpClient) {}

  validateJson(json: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/validate`, json, {
      headers: { 'Content-Type': 'application/json' },
    });
  }

  parseJson(json: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/parse`, json, {
      headers: { 'Content-Type': 'application/json' },
    });
  }
}
