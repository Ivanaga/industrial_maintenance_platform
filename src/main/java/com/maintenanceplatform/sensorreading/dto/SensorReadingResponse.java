package com.maintenanceplatform.sensorreading.dto;

import com.maintenanceplatform.sensorreading.SensorReading;

import java.time.Instant;

public record SensorReadingResponse(
Long id,
Long sensorId,
double value,
Instant timestamp
) 
{
    public static SensorReadingResponse from(SensorReading reading) 
    {
        return new SensorReadingResponse(reading.getId(), reading.getSensor().getId(), reading.getValue(), reading.getTimestamp());
    }
}