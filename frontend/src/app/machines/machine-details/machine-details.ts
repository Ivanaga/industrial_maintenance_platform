import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Sensor } from '../../models/sensor';
import { SensorService } from '../../services/sensor';

import { Machine } from '../../models/machine';
import { MachineService } from '../../services/machine';

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

  sensors = signal<Sensor[]>([]);

  machine = signal<Machine | null>(null);

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
      next: (sensors) => { this.sensors.set(sensors);},
      error: (error) => {console.error('Failed to load sensors', error);}
    });
  }
}