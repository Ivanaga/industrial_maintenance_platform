import { Component, inject, OnInit, signal } from '@angular/core';

import { Machine } from './models/machine';
import { MachineService } from './services/machine';

@Component({
  selector: 'app-root',
  imports: [],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit 
{
  private readonly machineService = inject(MachineService);

  machines = signal<Machine[]>([]);

  ngOnInit(): void 
  {
    this.machineService.getMachines().subscribe(
    {
      next: (machines) => {this.machines.set(machines);},
      error: (error) => {console.error('Failed to load machines', error);}
    });
  }
}