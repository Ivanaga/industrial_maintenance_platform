package com.maintenanceplatform.sensorreading;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.Instant;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> 
{
    List<SensorReading> findBySensorIdOrderByTimestampAsc(Long sensorId);
    List<SensorReading> findBySensorIdAndTimestampBetweenOrderByTimestampAsc(Long sensorId, Instant from, Instant to);
}