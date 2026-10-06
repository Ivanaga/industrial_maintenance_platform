import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { Machine } from '../../models/machine';
import { MachineService } from '../../services/machine';

@Component(
{
  selector: 'app-machine-list',
  imports: [RouterLink],
  templateUrl: './machine-list.html',
  styleUrl: './machine-list.scss'
})
export class MachineList implements OnInit 
{
  private readonly machineService = inject(MachineService);

  machines = signal<Machine[]>([]);

  ngOnInit(): void 
  {
    this.machineService.getMachines().subscribe(
    {
      next: (machines) => 
      {
        this.machines.set(machines);
      },
      error: (error) => 
      {
        console.error('Failed to load machines', error);
      }
    });
  }
}