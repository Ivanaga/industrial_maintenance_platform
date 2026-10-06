import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Sensor } from '../../models/sensor';
import { SensorService } from '../../services/sensor';

import { Machine } from '../../models/machine';
import { MachineService } from '../../services/machine';

import { SensorReading } from '../../models/sensor-reading';
import { SensorReadingService } from '../../services/sensor-reading';

import { SensorHistoryChart } from '../../sensor-readings/sensor-history-chart/sensor-history-chart';

@Component(
{
  selector: 'app-machine-details',
  imports: [SensorHistoryChart],
  templateUrl: './machine-details.html',
  styleUrl: './machine-details.scss'
})
export class MachineDetails implements OnInit 
{
  private readonly route = inject(ActivatedRoute);
  private readonly machineService = inject(MachineService);
  private readonly sensorService = inject(SensorService);
  private readonly sensorReadingService = inject(SensorReadingService);

  sensors = signal<Sensor[]>([]);

  machine = signal<Machine | null>(null);

  selectedSensor = signal<Sensor | null>(null);

  sensorHistory = signal<SensorReading[]>([]);

  latestReadings = signal<Map<number, SensorReading>>(new Map());

  ngOnInit(): void 
  {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.machineService.getMachineById(id).subscribe(
    {
      next: (machine) => { this.machine.set(machine);},
      error: (error) => { console.error('Failed to load machine', error);}
    });
    this.sensorService.getSensorsByMachineId(id).subscribe(
    {
      next: (sensors) => 
      { 
        this.sensors.set(sensors);
        for (const sensor of sensors) 
        {
          this.loadLatestReading(sensor.id);
        }
      
      const firstSensor = sensors[0];

      if (firstSensor) 
      {
        this.sensorReadingService.getReadingsBySensorId(firstSensor.id).subscribe(
          {
            next: (readings) => 
            {
              console.log('Sensor history:', readings);
            },
            error: (error) =>
            {
              console.error('Failed to load sensor history', error);
            }
          });
      }
      },
      error: (error) => {console.error('Failed to load sensors', error);}
    });
  }

  selectSensor(sensor: Sensor): void 
  {
    this.selectedSensor.set(sensor);

    this.sensorReadingService.getReadingsBySensorId(sensor.id).subscribe(
      {
        next: (readings) => { this.sensorHistory.set(readings);},
        error: (error) => { console.error(`Failed to load history for sensor ${sensor.id}`, error);}
      });
  }

  private loadLatestReading(sensorId: number): void 
  {
    this.sensorReadingService.getLatestReadingBySensorId(sensorId).subscribe(
      {
        next: (reading) => 
          {
            this.latestReadings.update((readings) => 
            {
              const updated = new Map(readings);

              updated.set(sensorId, reading);

              return updated;
            });
        },
        error: (error) => 
        {
          console.error(`Failed to load latest reading for sensor ${sensorId}`, error);
        }
      });
  }
}