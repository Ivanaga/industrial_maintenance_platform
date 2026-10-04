package com.maintenanceplatform.simulator;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


// Generates one telemetry tick for all sensors of a simulated machine.
public class MachineSimulation 
{
    private final Long machineId;
    private LoadLevel loadLevel;
    private final List<SensorSimulation> sensors;
    private double degradationLevel;
    private LoadControlMode loadControlMode;

    public MachineSimulation(Long machineId, List<SensorSimulation> sensors)
    {
        this.machineId = machineId;
        this.sensors = sensors;
        this.loadLevel = LoadLevel.NORMAL;
        this.degradationLevel = 0.0;
        this.loadControlMode = LoadControlMode.AUTOMATIC;
    }

    public Long getMachineId() 
    {
        return machineId;
    }

    public double getDegradationLevel() 
    {
        return degradationLevel;
    }

    public LoadLevel getLoadLevel() 
    {
        return loadLevel;
    }

    public LoadControlMode getLoadControlMode() 
    {
        return loadControlMode;
    }

    public void setLoadControlMode(LoadControlMode loadControlMode) 
    {
        this.loadControlMode = loadControlMode;
    }

    public void setLoadLevel(LoadLevel loadLevel) 
    {
        this.loadLevel = loadLevel;
    }

    public List<SensorSimulation> getSensors() 
    {
        return sensors;
    }

    public HealthState getHealthState() 
    {
        if (degradationLevel >= 0.95) 
        {
            return HealthState.FAILURE;
        }

        if (degradationLevel >= 0.60) 
        {
            return HealthState.DEGRADING;
        }

        return HealthState.HEALTHY;
    }

    public void updateLoadLevel() 
    {
        if (loadControlMode == LoadControlMode.MANUAL) 
        {
            return;
        }

        updateAutomaticLoad();
    }

    private void updateAutomaticLoad() 
    {
        double random = Math.random();

        switch (loadLevel) 
        {
            case LOW -> 
            {
                if (random < 0.25) 
                {
                    loadLevel = LoadLevel.NORMAL;
                }
            }

            case NORMAL -> 
            {
                if (random < 0.10) 
                {
                    loadLevel = LoadLevel.LOW;
                } 
                else if (random < 0.25) 
                {
                    loadLevel = LoadLevel.MEDIUM;
                }
            }

            case MEDIUM -> 
            {
                if (random < 0.15) 
                {
                    loadLevel = LoadLevel.NORMAL;
                } 
                else if (random < 0.30) 
                {
                    loadLevel = LoadLevel.HIGH;
                }
            }

            case HIGH -> 
            {
                if (random < 0.20) 
                    {
                    loadLevel = LoadLevel.MEDIUM;
                } 
                else if (random < 0.25) 
                {
                    loadLevel = LoadLevel.OVERLOAD;
                }
            }

            case OVERLOAD -> 
            {
                if (random < 0.40) 
                {
                    loadLevel = LoadLevel.HIGH;
                }
            }
        }
    }

    // Increases machine degradation based on current load
    // with a small random wear component.
    public void updateDegradation() 
    {
        double baseWear = switch (loadLevel) 
        {
            case LOW -> 0.0005;
            case NORMAL -> 0.0010;
            case MEDIUM -> 0.0015;
            case HIGH -> 0.0030;
            case OVERLOAD -> 0.0070;
        };

        double randomWear = ThreadLocalRandom.current().nextDouble(0.0, 0.0005);

        degradationLevel += baseWear + randomWear;

        degradationLevel = Math.min(degradationLevel, 1.0);
    }
}