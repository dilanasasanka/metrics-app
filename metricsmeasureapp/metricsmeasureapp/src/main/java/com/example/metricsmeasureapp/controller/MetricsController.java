package com.example.metricsmeasureapp.controller;

import com.example.metricsmeasureapp.dto.MetricsSummaryDTO;
import com.example.metricsmeasureapp.dto.MetricsResponseDTO;
import com.example.metricsmeasureapp.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MetricsController {

    @Autowired
    private MetricsService service;

    @GetMapping("/metrics")
    public MetricsResponseDTO getMetrics(@RequestParam("startDate") String startDate,
                                         @RequestParam("endDate") String endDate,
                                         @RequestParam("applicationName") String applicationName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start;
        LocalDateTime end;

        try {
            LocalDate startDateParsed = LocalDate.parse(startDate, formatter);
            start = startDateParsed.atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid start date format. Please use 'yyyy-MM-dd'");
        }

        try {
            LocalDate endDateParsed = LocalDate.parse(endDate, formatter);
            end = endDateParsed.atStartOfDay().plusDays(1).minusSeconds(1);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid end date format. Please use 'yyyy-MM-dd'");
        }

        return service.calculateMetrics(start, end, applicationName);
    }

    @GetMapping("/metrics/summary")
    public MetricsSummaryDTO getMetricsSummary(@RequestParam("startDate") String startDate,
                                               @RequestParam("endDate") String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start;
        LocalDateTime end;

        try {
            LocalDate startDateParsed = LocalDate.parse(startDate, formatter);
            start = startDateParsed.atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid start date format. Please use 'yyyy-MM-dd'");
        }

        try {
            LocalDate endDateParsed = LocalDate.parse(endDate, formatter);
            end = endDateParsed.atStartOfDay().plusDays(1).minusSeconds(1);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid end date format. Please use 'yyyy-MM-dd'");
        }

        return service.getMetricsSummary(start, end);
    }
}
