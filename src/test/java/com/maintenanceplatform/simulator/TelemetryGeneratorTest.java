package com.maintenanceplatform.simulator;

import com.maintenanceplatform.sensor.SensorType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TelemetryGeneratorTest 
{
    // Testing that in general high loaded machines should increase temperature more than a normal loaded machines
    @Test
    void highLoadShouldIncreaseTemperatureMoreThanNormalLoad() 
    {
        TelemetryGenerator generator = new TelemetryGenerator();

        SensorSimulation normalSensor = new SensorSimulation(1L, SensorType.TEMPERATURE, 70.0);

        SensorSimulation highLoadSensor = new SensorSimulation(2L, SensorType.TEMPERATURE, 70.0);

        for (int i = 0; i < 100; i++) 
        {
            generator.generateNextValue(normalSensor, LoadLevel.NORMAL, HealthState.HEALTHY);

            generator.generateNextValue(highLoadSensor, LoadLevel.HIGH, HealthState.HEALTHY);
        }

        assertTrue(highLoadSensor.getCurrentValue() > normalSensor.getCurrentValue());
    }

    // Testing that degrading health state should increase vibration more than healthy state
    @Test
    void degradingHealthShouldIncreaseVibrationMoreThanHealthyState() 
    {
        TelemetryGenerator generator = new TelemetryGenerator();

        SensorSimulation healthySensor = new SensorSimulation(1L, SensorType.VIBRATION, 1.5);

        SensorSimulation degradingSensor = new SensorSimulation(2L, SensorType.VIBRATION, 1.5);

        for (int i = 0; i < 100; i++) 
        {
            generator.generateNextValue(healthySensor, LoadLevel.NORMAL, HealthState.HEALTHY);

            generator.generateNextValue(degradingSensor, LoadLevel.NORMAL, HealthState.DEGRADING);
        }

        assertTrue(degradingSensor.getCurrentValue() > healthySensor.getCurrentValue());
    }
}