package com.maintenanceplatform.simulator;

import com.maintenanceplatform.sensor.SensorType;

import java.util.concurrent.ThreadLocalRandom;

// Generates the next time-series sensor value using
// the previous value, random noise and an operating-mode-dependent trend.
public class TelemetryGenerator 
{
    public double generateNextValue(SensorSimulation sensor, OperatingMode operatingMode)
    {
        double currentValue = sensor.getCurrentValue();

        double noise = generateNoise(sensor.getType());
        double trend = generateTrend(sensor.getType(), operatingMode);
        double meanReversion = generateMeanReversion(sensor);

        double nextValue = currentValue + noise + trend + meanReversion;

        sensor.setCurrentValue(nextValue);

        return nextValue;
    }

    private double generateNoise(SensorType type) 
    {
        return switch (type) 
        {
            case TEMPERATURE -> randomBetween(-0.3, 0.3);
            case VIBRATION -> randomBetween(-0.05, 0.05);
            case PRESSURE -> randomBetween(-0.03, 0.03);
            case RPM -> randomBetween(-3.0, 3.0);
            case VOLTAGE -> randomBetween(-0.5, 0.5);
            case CURRENT -> randomBetween(-0.2, 0.2);
            case LOAD -> randomBetween(-0.5, 0.5);
            case ACOUSTIC -> randomBetween(-0.5, 0.5);
        };
    }

    private double generateTrend(SensorType type, OperatingMode operatingMode) 
    {
        return switch (operatingMode) 
        {
            case NORMAL -> 0.0;
            case HIGH_LOAD -> switch (type) 
            {
                case TEMPERATURE -> 0.08;
                case VIBRATION -> 0.01;
                case CURRENT -> 0.05;
                case LOAD -> 0.1;
                default -> 0.0;
            };

            case DEGRADING -> switch (type) 
            {
                case TEMPERATURE -> 0.12;
                case VIBRATION -> 0.03;
                case CURRENT -> 0.03;
                case ACOUSTIC -> 0.05;
                default -> 0.0;
            };

            case FAILURE -> switch (type) 
            {
                case TEMPERATURE -> 0.5;
                case VIBRATION -> 0.2;
                case CURRENT -> 0.15;
                case ACOUSTIC -> 0.3;
                default -> 0.0;
            };
        };
    }

    private double randomBetween(double min, double max) 
    {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    // Pulls the sensor value gradually back toward its normal baseline.
    private double generateMeanReversion(SensorSimulation sensor) 
    {
        double difference = sensor.getBaseline() - sensor.getCurrentValue();

        return difference * 0.05;
    }
}