import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SensorReading } from '../models/sensor-reading';

@Injectable(
{
  providedIn: 'root'
})
export class SensorReadingService
{
    private readonly http = inject(HttpClient);

    private readonly apiUrl = 'http://localhost:8080/api/sensor-readings';

    getLatestReadingBySensorId(sensorId: number): Observable<SensorReading> 
    {
        return this.http.get<SensorReading>(`${this.apiUrl}/sensor/${sensorId}/latest`);
    }
}
