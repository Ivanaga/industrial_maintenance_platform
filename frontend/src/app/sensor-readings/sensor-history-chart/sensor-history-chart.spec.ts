import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SensorHistoryChart } from './sensor-history-chart';

describe('SensorHistoryChart', () => {
  let component: SensorHistoryChart;
  let fixture: ComponentFixture<SensorHistoryChart>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SensorHistoryChart],
    }).compileComponents();

    fixture = TestBed.createComponent(SensorHistoryChart);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
