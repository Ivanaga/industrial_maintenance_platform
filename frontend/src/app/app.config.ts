import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';

import { provideEchartsCore } from 'ngx-echarts';
import { TitleComponent } from 'echarts/components';

import * as echarts from 'echarts/core';
import { LineChart } from 'echarts/charts';
import {
  GridComponent,
  TooltipComponent,
  DataZoomComponent
} from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';

echarts.use(
[
  LineChart,
  GridComponent,
  TooltipComponent,
  DataZoomComponent,
  TitleComponent,
  CanvasRenderer
]);
export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(),
    provideEchartsCore({ echarts })
  ]
};
