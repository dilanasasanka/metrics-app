package com.example.metricsmeasureapp.dto;

public class MetricsSummaryDTO {
    private double energyConsumed;
    private double emissionsProduced;
    private double cumulativeDuration;

    public double getEnergyConsumed() {
        return energyConsumed;
    }

    public void setEnergyConsumed(double energyConsumed) {
        this.energyConsumed = energyConsumed;
    }

    public double getEmissionsProduced() {
        return emissionsProduced;
    }

    public void setEmissionsProduced(double emissionsProduced) {
        this.emissionsProduced = emissionsProduced;
    }

    public double getCumulativeDuration() {
        return cumulativeDuration;
    }

    public void setCumulativeDuration(double cumulativeDuration) {
        this.cumulativeDuration = cumulativeDuration;
    }
}
