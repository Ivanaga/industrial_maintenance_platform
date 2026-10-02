package com.maintenanceplatform.sensor.dto;

import com.maintenanceplatform.sensor.SensorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSensorRequest(
@NotBlank String name,
@NotNull SensorType type,
@NotBlank String unit,
@NotNull Long machineId,
boolean active) 
{
}