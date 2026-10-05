import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Machine } from '../models/machine';

@Injectable({providedIn: 'root'})
export class MachineService 
{
  private readonly http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8080/api/machines';

  getMachines(): Observable<Machine[]> 
  {
    return this.http.get<Machine[]>(this.apiUrl);
  }
}