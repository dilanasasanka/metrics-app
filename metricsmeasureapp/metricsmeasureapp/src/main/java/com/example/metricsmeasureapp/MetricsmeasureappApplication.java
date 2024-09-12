package com.example.metricsmeasureapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MetricsmeasureappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetricsmeasureappApplication.class, args);
	}
}
