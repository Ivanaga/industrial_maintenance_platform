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

  sensorName = input.required<string>();
  unit = input.required<string>();

  chartOptions = computed<EChartsOption>(() => 
  {
    const readings = this.readings();

    return {
      title: 
      {
        text: this.sensorName()
      },

      tooltip: 
      {
        trigger: 'axis',
        valueFormatter: (value) => `${value} ${this.unit()}`
      },

      xAxis: 
      {
        type: 'time',
        name: 'Time'
      },

      yAxis: 
      {
        type: 'value',
        name: this.unit()
      },

      dataZoom: 
      [
        {
          type: 'inside'
        },
        {
          type: 'slider'
        }
      ],

      series: 
      [
        {
          type: 'line',
          showSymbol: false,
          data: readings.map(reading => [reading.timestamp, reading.value])
        }
      ]
    };
  });
}