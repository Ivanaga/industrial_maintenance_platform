import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Sensor } from '../models/sensor';

@Injectable(
{
  providedIn: 'root'
})
export class SensorService 
{
  private readonly http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8080/api/sensors';

  getSensorsByMachineId(machineId: number): Observable<Sensor[]> 
  {
    return this.http.get<Sensor[]>(`${this.apiUrl}/machine/${machineId}`);
  }
}