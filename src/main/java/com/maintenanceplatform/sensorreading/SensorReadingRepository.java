package com.maintenanceplatform.sensorreading;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.Instant;
import java.util.Optional;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> 
{
    List<SensorReading> findBySensorIdOrderByTimestampAsc(Long sensorId);
    Optional<SensorReading> findTopBySensorIdOrderByTimestampDesc(Long sensorId);
    List<SensorReading> findBySensorIdAndTimestampBetweenOrderByTimestampAsc(Long sensorId, Instant from, Instant to);
}