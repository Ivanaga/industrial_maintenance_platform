package com.maintenanceplatform.simulator;

import com.maintenanceplatform.sensor.SensorType;

// Provides realistic baseline values for each simulated sensor type.
public class SensorBaselineFactory 
{

    public double getBaseline(SensorType type) 
    {
        return switch (type) 
        {
            case TEMPERATURE -> 70.0;
            case VIBRATION -> 1.5;
            case PRESSURE -> 5.0;
            case RPM -> 1500.0;
            case VOLTAGE -> 230.0;
            case CURRENT -> 15.0;
            case LOAD -> 60.0;
            case ACOUSTIC -> 50.0;
        };
    }
}