package com.example.metricsmeasureapp.dto;

import java.util.List;

public class MetricsResponse {

    private List<Measurement> measurements;

    public List<Measurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<Measurement> measurements) {
		this.measurements = measurements;
	}

	public static class Measurement {
        public Double getValue() {
			return value;
		}

		public void setValue(Double value) {
			this.value = value;
		}

		private Double value;
	
    }
}
