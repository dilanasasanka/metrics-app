package com.example.metricsmeasureapp.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.metricsmeasureapp.entity.Metrics;

@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Long> {
	
	@Query("SELECT m FROM Metrics m ORDER BY m.timestamp ASC LIMIT 1")
    Metrics findFirstMetric();
	
	@Query("SELECT m FROM Metrics m WHERE m.timestamp BETWEEN :startDate AND :endDate AND m.applicationName = :applicationName")
    List<Metrics> findMetricsBetweenDatesAndApplication(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("applicationName") String applicationName
    );
}
