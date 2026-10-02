package com.maintenanceplatform.sensor.dto;

import com.maintenanceplatform.sensor.Sensor;
import com.maintenanceplatform.sensor.SensorType;

public record SensorResponse(Long id, String name, SensorType type, String unit, Long machineId, boolean active) 
{
    public static SensorResponse from(Sensor sensor) 
    {
        return new SensorResponse(
                sensor.getId(),
                sensor.getName(),
                sensor.getType(),
                sensor.getUnit(),
                sensor.getMachine().getId(),
                sensor.isActive());
    }
}