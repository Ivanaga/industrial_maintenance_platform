package com.maintenanceplatform.simulator;

import java.util.HashMap;
import java.util.Map;

public class MachineTelemetryGenerator 
{
    private final TelemetryGenerator telemetryGenerator;

    public MachineTelemetryGenerator(TelemetryGenerator telemetryGenerator) 
    {
        this.telemetryGenerator = telemetryGenerator;
    }

    // Advances the machine state and generates one telemetry reading
    // for every simulated sensor.
    public Map<Long, Double> generateTick(MachineSimulation machine) 
    {
        machine.updateLoadLevel();
        machine.updateDegradation();
        Map<Long, Double> readings = new HashMap<>();

        for (SensorSimulation sensor : machine.getSensors()) 
        {
            double value = telemetryGenerator.generateNextValue(sensor, machine.getLoadLevel(), machine.getHealthState());

            readings.put(sensor.getSensorId(), value);
        }

        return readings;
    }
}