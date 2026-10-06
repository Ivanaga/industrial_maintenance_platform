import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Sensor } from '../../models/sensor';
import { SensorService } from '../../services/sensor';

import { Machine } from '../../models/machine';
import { MachineService } from '../../services/machine';

import { SensorReading } from '../../models/sensor-reading';
import { SensorReadingService } from '../../services/sensor-reading';

@Component(
{
  selector: 'app-machine-details',
  imports: [],
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
      },
      error: (error) => {console.error('Failed to load sensors', error);}
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