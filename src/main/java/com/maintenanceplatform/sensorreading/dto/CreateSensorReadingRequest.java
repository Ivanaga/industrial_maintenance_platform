package com.maintenanceplatform.sensorreading.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateSensorReadingRequest(
@NotNull Long sensorId,
double value,
@NotNull Instant timestamp) 
{
}