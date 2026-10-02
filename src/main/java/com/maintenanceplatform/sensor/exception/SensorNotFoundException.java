package com.maintenanceplatform.sensor.exception;

public class SensorNotFoundException extends RuntimeException 
{
    public SensorNotFoundException(Long id) 
    {
        super("Sensor not found with id: " + id);
    }
}