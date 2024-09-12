package com.example.metricsmeasureapp.dto;

public class MetricsResponseDTO {
	
    private double cpuUsage;
    private long ramUsed;
    private long ramTotal;
    private double vramUsage;
    private double energyConsumption;
    private double carbonFootprint;
    private double carbonFootprintPrecentage;

    
    public MetricsResponseDTO() {}


	public MetricsResponseDTO(double cpuUsage, long ramUsed, long ramTotal, double vramUsage, double energyConsumption,
			double carbonFootprint, double carbonFootprintPrecentage) {
		super();
		this.cpuUsage = cpuUsage;
		this.ramUsed = ramUsed;
		this.ramTotal = ramTotal;
		this.vramUsage = vramUsage;
		this.energyConsumption = energyConsumption;
		this.carbonFootprint = carbonFootprint;
		this.carbonFootprintPrecentage = carbonFootprintPrecentage;
	}


	public double getCpuUsage() {
		return cpuUsage;
	}


	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}


	public long getRamUsed() {
		return ramUsed;
	}


	public void setRamUsed(long ramUsed) {
		this.ramUsed = ramUsed;
	}


	public long getRamTotal() {
		return ramTotal;
	}


	public void setRamTotal(long ramTotal) {
		this.ramTotal = ramTotal;
	}


	public double getVramUsage() {
		return vramUsage;
	}


	public void setVramUsage(double vramUsage) {
		this.vramUsage = vramUsage;
	}


	public double getEnergyConsumption() {
		return energyConsumption;
	}


	public void setEnergyConsumption(double energyConsumption) {
		this.energyConsumption = energyConsumption;
	}


	public double getCarbonFootprint() {
		return carbonFootprint;
	}


	public void setCarbonFootprint(double carbonFootprint) {
		this.carbonFootprint = carbonFootprint;
	}


	public double getCarbonFootprintPrecentage() {
		return carbonFootprintPrecentage;
	}


	public void setCarbonFootprintPrecentage(double carbonFootprintPrecentage) {
		this.carbonFootprintPrecentage = carbonFootprintPrecentage;
	}
	
	
    
    
   
}
