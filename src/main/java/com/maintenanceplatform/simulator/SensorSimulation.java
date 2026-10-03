package com.maintenanceplatform.simulator;

import com.maintenanceplatform.sensor.SensorType;

// Stores the state of a simulated sensor.
// currentValue is used to generate the next time-series value
// based on the previous value instead of pure random generation.
public class SensorSimulation 
{
    private final Long sensorId;
    private final SensorType type;
    private final double baseline;

    private double currentValue;

    public SensorSimulation(Long sensorId, SensorType type, double baseline) 
    {
        this.sensorId = sensorId;
        this.type = type;
        this.baseline = baseline;
        this.currentValue = baseline;
    }

    public Long getSensorId() 
    {
        return sensorId;
    }

    public SensorType getType() 
    {
        return type;
    }

    public double getBaseline() 
    {
        return baseline;
    }

    public double getCurrentValue() 
    {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) 
    {
        this.currentValue = currentValue;
    }
}