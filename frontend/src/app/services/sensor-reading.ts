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
    getReadingsBySensorId(sensorId: number, from?: string, to?: string): Observable<SensorReading[]> 
    {
        let url = `${this.apiUrl}/sensor/${sensorId}`;

        const params = new URLSearchParams();

        if (from) 
        {
            params.set('from', from);
        }

        if (to) 
        {
            params.set('to', to);
        }

        const query = params.toString();

        if (query) 
        {
            url += `?${query}`;
        }

        return this.http.get<SensorReading[]>(url);
    }
}
