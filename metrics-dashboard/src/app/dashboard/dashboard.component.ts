import { Component, OnInit } from '@angular/core';
import { buildStyles } from '@plcoder/ng-circular-progressbar';
import { MetricsService } from '../metrics.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  startDate: string = '';
  endDate: string = '';
  selectedApplication: string = 'StudentManagement';
  metrics: any;
  carKMs: number = 0;
  householdEnergy: number = 0;
  tvHours: number = 0;

  globalMetrics = {
    energyConsumed: 0,
    emissionsProduced: 0,
    cumulativeDuration: 0
  };

  constructor(private metricsService: MetricsService) {}

  ngOnInit(): void {
    this.metrics = {
      cpuUsage: 0,
      ramUsed: 0,
      ramTotal: 1,
      vramUsage: 0,
      energyConsumption: 0,
      carbonFootprint: 0,
      carbonFootprintPrecentage: 0,
      ramUsagePercentage: 0,
    };
    this.calculateAdditionalValues(this.metrics.carbonFootprint);
  }

  fetchMetrics() {
    this.metricsService.getMetrics(this.startDate, this.endDate, this.selectedApplication).subscribe(data => {
      if (data) {
        this.metrics = data;
        this.metrics.ramUsagePercentage = this.roundToTwoDecimals((this.metrics.ramUsed / this.metrics.ramTotal) * 100);
        // this.calculateAdditionalValues(this.metrics.carbonFootprint);
      }
    });

    this.metricsService.getSummaryMetrics(this.startDate, this.endDate).subscribe(data => {
      if (data) {
        this.globalMetrics = data;
        this.calculateAdditionalValues(this.globalMetrics.emissionsProduced);
      }
    });
  }
  
  calculateAdditionalValues(emissionsProduced: number) {
    this.carKMs = parseFloat((emissionsProduced  / (0.409 * 1.6)  ).toFixed(2));
    this.householdEnergy = parseFloat((emissionsProduced / 160.58 ).toFixed(3));
    this.tvHours = parseFloat((emissionsProduced / 0.097).toFixed(2));
  }

  roundToTwoDecimals(value: number): number {
    return Math.round(value * 100) / 100;
  }

  

  getStyles(value: number) {
    let color = '#f50f0f';
    if (value < 75) {
      color = '#53AE75';
    } else if (value < 90) {
      color = '#FFA500';
    }

    return buildStyles({
      rotation: 0,
      textSize: '15px',
      pathTransitionDuration: 0.5,
      pathColor: color,
      textColor: '#333',
      trailColor: '#d6d6d6',
      backgroundColor: '#e0e0e0',
      strokeLinecap: 'butt'
    });
  }
}
