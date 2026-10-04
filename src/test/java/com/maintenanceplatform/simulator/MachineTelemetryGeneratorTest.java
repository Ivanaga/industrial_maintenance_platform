package com.maintenanceplatform.simulator;

import com.maintenanceplatform.sensor.SensorType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MachineTelemetryGeneratorTest 
{
    @Test
    void shouldGenerateReadingForEverySensor() 
    {
        SensorSimulation temperature = new SensorSimulation(1L, SensorType.TEMPERATURE, 70.0);

        SensorSimulation vibration = new SensorSimulation(2L, SensorType.VIBRATION, 1.5);

        MachineSimulation machine = new MachineSimulation(1L, List.of(temperature, vibration));

        machine.setLoadControlMode(LoadControlMode.MANUAL);

        machine.setLoadLevel(LoadLevel.NORMAL);

        TelemetryGenerator telemetryGenerator = new TelemetryGenerator();

        MachineTelemetryGenerator machineTelemetryGenerator = new MachineTelemetryGenerator(telemetryGenerator);

        Map<Long, Double> readings = machineTelemetryGenerator.generateTick(machine);

        assertEquals(2, readings.size());

        assertTrue(readings.containsKey(1L));
        assertTrue(readings.containsKey(2L));
    }
}