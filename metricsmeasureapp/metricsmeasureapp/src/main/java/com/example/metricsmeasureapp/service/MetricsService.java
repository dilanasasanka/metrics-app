package com.example.metricsmeasureapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.metricsmeasureapp.dto.MetricsResponse;
import com.example.metricsmeasureapp.dto.MetricsResponseDTO;
import com.example.metricsmeasureapp.dto.MetricsSummaryDTO;
import com.example.metricsmeasureapp.entity.Metrics;
import com.example.metricsmeasureapp.repository.MetricsRepository;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MetricsService {

    @Autowired
    private MetricsRepository repository;

    private RestTemplate restTemplate = new RestTemplate();

    private final String studentManagementUrl = "http://localhost:8081/actuator/metrics";
    private final String employmentManagementUrl = "http://localhost:8082/actuator/metrics";

    @Scheduled(fixedRate = 30000)
    public void collectMetrics() {
        System.out.println("Saving to database");
        collectMetricsFromApplication("StudentManagement", studentManagementUrl);
        collectMetricsFromApplication("EmploymentManagement", employmentManagementUrl);
    }

    private void collectMetricsFromApplication(String applicationName, String baseUrl) {
        System.out.println("Collecting metrics");
        double cpuUsage = fetchMetric(baseUrl + "/system.cpu.usage");
        long memoryUsed = fetchMetric(baseUrl + "/jvm.memory.used").longValue();

        Metrics metrics = new Metrics();
        metrics.setApplicationName(applicationName);
        metrics.setCpuUsage(cpuUsage);
        metrics.setMemoryUsed(memoryUsed);
        metrics.setTimestamp(LocalDateTime.now());

        repository.save(metrics);
    }

    private Double fetchMetric(String url) {
        MetricsResponse response = restTemplate.getForObject(url, MetricsResponse.class);
        return response.getMeasurements().get(0).getValue();
    }

    public MetricsResponseDTO calculateMetrics(LocalDateTime startDate, LocalDateTime endDate, String applicationName) {
        List<Metrics> metrics = repository.findMetricsBetweenDatesAndApplication(startDate, endDate, applicationName);

        if (metrics.isEmpty()) {
            System.out.println("No metrics found");
            return new MetricsResponseDTO();
        }

        double totalCpuUsage = 0;
        long totalRamUsed = 0;
        long ramTotal = getTotalRAM();
        double totalEnergyConsumption = 0;
        double totalCarbonFootprint = 0;

        double cpuPowerDraw = 50;
        double ramPowerDraw = 10;
        double emissionFactor = 0.5;
        double totalSystemPower = 200;

        for (Metrics metric : metrics) {
            totalCpuUsage += metric.getCpuUsage();
            totalRamUsed += metric.getMemoryUsed();
        }

        int count = metrics.size();
        double averageCpuUsage = totalCpuUsage / count;
        long averageRamUsed = totalRamUsed / count;

        double energyConsumptionCpu = averageCpuUsage * cpuPowerDraw * (30 / 3600.0);
        double energyConsumptionRam = (averageRamUsed / (double) ramTotal) * ramPowerDraw * (30 / 3600.0);
        totalEnergyConsumption = energyConsumptionCpu + energyConsumptionRam;
        totalCarbonFootprint = totalEnergyConsumption * emissionFactor;

        double totalSystemEnergyConsumption = totalSystemPower * (30 / 3600.0);
        double energyConsumptionPercentage = (totalEnergyConsumption / totalSystemEnergyConsumption) * 100;
        double carbonFootprintPercentage = (totalCarbonFootprint / (totalSystemEnergyConsumption * emissionFactor)) * 100;

        MetricsResponseDTO response = new MetricsResponseDTO();
        response.setCpuUsage(roundToTwoDecimalPlaces(averageCpuUsage * 100));
        response.setRamUsed(averageRamUsed);
        response.setRamTotal(ramTotal);
        response.setVramUsage(0);
        response.setEnergyConsumption(roundToTwoDecimalPlaces(totalEnergyConsumption));
        response.setCarbonFootprint(roundToTwoDecimalPlaces(totalCarbonFootprint));
        response.setCarbonFootprintPrecentage(roundToTwoDecimalPlaces(carbonFootprintPercentage));

        return response;
    }

    public MetricsSummaryDTO getMetricsSummary(LocalDateTime startDate, LocalDateTime endDate) {
        List<Metrics> studentMetrics = repository.findMetricsBetweenDatesAndApplication(startDate, endDate, "StudentManagement");
        List<Metrics> employmentMetrics = repository.findMetricsBetweenDatesAndApplication(startDate, endDate, "EmploymentManagement");
        
        Metrics firstMetric = repository.findFirstMetric();
        LocalDateTime firstDate = (firstMetric != null) ? firstMetric.getTimestamp() : startDate;

        double totalEnergyConsumed = 0;
        double totalEmissionsProduced = 0;

        double cpuPowerDraw = 50;
        double ramPowerDraw = 10;
        double emissionFactor = 0.5;
        double totalSystemPower = 200;

        totalEnergyConsumed += calculateEnergyConsumption(studentMetrics, cpuPowerDraw, ramPowerDraw);
        totalEnergyConsumed += calculateEnergyConsumption(employmentMetrics, cpuPowerDraw, ramPowerDraw);

        totalEmissionsProduced = totalEnergyConsumed * emissionFactor;

        Duration duration = Duration.between(firstDate, endDate);
        double cumulativeDuration = duration.toDays(); // Calculating duration in days

        MetricsSummaryDTO summary = new MetricsSummaryDTO();
        summary.setEnergyConsumed(roundToTwoDecimalPlaces(totalEnergyConsumed));
        summary.setEmissionsProduced(roundToTwoDecimalPlaces(totalEmissionsProduced));
        summary.setCumulativeDuration(cumulativeDuration);

        return summary;
    }

    private double calculateEnergyConsumption(List<Metrics> metrics, double cpuPowerDraw, double ramPowerDraw) {
        if (metrics.isEmpty()) {
            return 0;
        }

        double totalCpuUsage = 0;
        long totalRamUsed = 0;
        long ramTotal = getTotalRAM();

        for (Metrics metric : metrics) {
            totalCpuUsage += metric.getCpuUsage();
            totalRamUsed += metric.getMemoryUsed();
        }

        int count = metrics.size();
        double averageCpuUsage = totalCpuUsage / count;
        long averageRamUsed = totalRamUsed / count;

        double energyConsumptionCpu = averageCpuUsage * cpuPowerDraw * (30 / 3600.0);
        double energyConsumptionRam = (averageRamUsed / (double) ramTotal) * ramPowerDraw * (30 / 3600.0);

        return energyConsumptionCpu + energyConsumptionRam;
    }

    private long getTotalRAM() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsBean = (com.sun.management.OperatingSystemMXBean) osBean;
            return sunOsBean.getTotalPhysicalMemorySize();
        }
        return 0;
    }

    private double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
