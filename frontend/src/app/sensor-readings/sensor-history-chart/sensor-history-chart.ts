import { Component, computed, input } from '@angular/core';
import { NgxEchartsDirective } from 'ngx-echarts';
import { EChartsOption } from 'echarts';

import { SensorReading } from '../../models/sensor-reading';

@Component(
{
  selector: 'app-sensor-history-chart',
  imports: [NgxEchartsDirective],
  templateUrl: './sensor-history-chart.html',
  styleUrl: './sensor-history-chart.scss'
})
export class SensorHistoryChart 
{
  readings = input.required<SensorReading[]>();

  chartOptions = computed<EChartsOption>(() => 
  {
    const readings = this.readings();

    return {
      tooltip: 
      {
        trigger: 'axis'
      },

      xAxis: 
      {
        type: 'time'
      },

      yAxis: 
      {
        type: 'value'
      },

      series: 
      [
        {
          type: 'line',
          data: readings.map(reading => 
          [
            reading.timestamp,
            reading.value
          ])
        }
      ]
    };
  });
}