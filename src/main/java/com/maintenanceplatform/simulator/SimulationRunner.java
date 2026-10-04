package com.maintenanceplatform.simulator;

import com.maintenanceplatform.sensor.SensorType;

import java.util.List;
import java.util.Map;

public class SimulationRunner 
{
    public static void main(String[] args) throws InterruptedException 
    {
        SensorBaselineFactory baselineFactory = new SensorBaselineFactory();

        SensorSimulation temperature = new SensorSimulation(1L, SensorType.TEMPERATURE, baselineFactory.getBaseline(SensorType.TEMPERATURE));

        SensorSimulation vibration = new SensorSimulation(2L, SensorType.VIBRATION, baselineFactory.getBaseline(SensorType.VIBRATION));

        MachineSimulation machine = new MachineSimulation(1L, List.of(temperature, vibration));

        TelemetryGenerator telemetryGenerator = new TelemetryGenerator();

        MachineTelemetryGenerator machineTelemetryGenerator = new MachineTelemetryGenerator(telemetryGenerator);

        for (int i = 0; i < 100; i++) 
        {
            Map<Long, Double> readings = machineTelemetryGenerator.generateTick(machine);

            System.out.println("Tick: " + i + " | Load: " + machine.getLoadLevel() + " | Health: " + machine.getHealthState() + " | Degradation: " + machine.getDegradationLevel() + " | Readings: " + readings);

            Thread.sleep(500);
        }
    }
}