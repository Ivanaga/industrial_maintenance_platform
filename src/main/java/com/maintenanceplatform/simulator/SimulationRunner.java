package com.maintenanceplatform.simulator;

import com.maintenanceplatform.sensor.SensorType;

import java.util.List;
import java.util.Map;
import java.time.Instant;

import java.io.IOException;

public class SimulationRunner 
{
    public static void main(String[] args) throws IOException, InterruptedException
    {

        TelemetryIngestionClient ingestionClient = new TelemetryIngestionClient("http://localhost:8080");

        SensorBaselineFactory baselineFactory = new SensorBaselineFactory();

        SensorSimulation temperature = new SensorSimulation(8L, SensorType.TEMPERATURE, baselineFactory.getBaseline(SensorType.TEMPERATURE));

        SensorSimulation vibration = new SensorSimulation(9L, SensorType.VIBRATION, baselineFactory.getBaseline(SensorType.VIBRATION));

        SensorSimulation current = new SensorSimulation(10L, SensorType.CURRENT, baselineFactory.getBaseline(SensorType.CURRENT));

        SensorSimulation load = new SensorSimulation(11L, SensorType.LOAD, baselineFactory.getBaseline(SensorType.LOAD));

        MachineSimulation machine = new MachineSimulation(13L, List.of(temperature, vibration, current, load));

        TelemetryGenerator telemetryGenerator = new TelemetryGenerator();

        MachineTelemetryGenerator machineTelemetryGenerator = new MachineTelemetryGenerator(telemetryGenerator);

        for (int i = 0; i < 100; i++) 
        {
            Map<Long, Double> readings = machineTelemetryGenerator.generateTick(machine);

            for (Map.Entry<Long, Double> entry : readings.entrySet()) 
            {
                ingestionClient.sendReading(entry.getKey(), entry.getValue(), Instant.now());
            }

            System.out.println("Tick: " + i + " | Load: " + machine.getLoadLevel() + " | Health: " + machine.getHealthState() + " | Degradation: " + machine.getDegradationLevel() + " | Readings: " + readings);

            Thread.sleep(500);
        }
    }
}