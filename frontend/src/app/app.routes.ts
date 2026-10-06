import { Routes } from '@angular/router';

import { MachineList } from './machines/machine-list/machine-list';
import { MachineDetails } from './machines/machine-details/machine-details';

export const routes: Routes = 
[
  {
    path: 'machines',
    component: MachineList
  },
  {
    path: 'machines/:id',
    component: MachineDetails
  },
  {
    path: '',
    redirectTo: 'machines',
    pathMatch: 'full'
  }
];