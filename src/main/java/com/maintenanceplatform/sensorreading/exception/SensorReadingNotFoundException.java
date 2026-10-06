package com.maintenanceplatform.sensorreading.exception;

public class SensorReadingNotFoundException extends RuntimeException 
{

    public SensorReadingNotFoundException(Long sensorId) 
    {
        super("No sensor readings found for sensor with id " + sensorId);
    }
}