package com.maintenanceplatform.simulator;

import java.util.List;


// Generates one telemetry tick for all sensors of a simulated machine.
public class MachineSimulation 
{
    private final Long machineId;
    private OperatingMode operatingMode;
    private final List<SensorSimulation> sensors;
    private double degradationLevel;
    private int highLoadTicksRemaining;

    public MachineSimulation(Long machineId, List<SensorSimulation> sensors)
    {
        this.machineId = machineId;
        this.sensors = sensors;
        this.operatingMode = OperatingMode.NORMAL;
        this.degradationLevel = 0.0;
        this.highLoadTicksRemaining = 0;
    }

    public Long getMachineId() 
    {
        return machineId;
    }

    public OperatingMode getOperatingMode() 
    {
        return operatingMode;
    }

    public double getDegradationLevel() 
    {
        return degradationLevel;
    }

    public void setOperatingMode(OperatingMode operatingMode) 
    {
        this.operatingMode = operatingMode;
    }

    public List<SensorSimulation> getSensors() 
    {
        return sensors;
    }
    public void advanceState() 
    {
        increaseDegradation();

        if (degradationLevel >= 0.95) 
        {
            operatingMode = OperatingMode.FAILURE;
            return;
        }

        if (degradationLevel >= 0.60) 
        {
            operatingMode = OperatingMode.DEGRADING;
            return;
        }

        if (operatingMode == OperatingMode.HIGH_LOAD) 
        {
            highLoadTicksRemaining--;

            if (highLoadTicksRemaining <= 0) 
            {
                operatingMode = OperatingMode.NORMAL;
            }

            return;
        }

        maybeEnterHighLoad();
    }

    private void increaseDegradation() 
    {
        if (operatingMode == OperatingMode.HIGH_LOAD) 
        {
            degradationLevel += 0.01;
        } 
        else 
        {
            degradationLevel += 0.002;
        }

        degradationLevel = Math.min(degradationLevel, 1.0);
    }

    private void maybeEnterHighLoad() 
    {
        double probability = Math.random();

        if (probability < 0.05) 
        {
            operatingMode = OperatingMode.HIGH_LOAD;
            highLoadTicksRemaining = 5;
        }
    }

    public void forceHighLoad(int durationTicks) 
    {
        if (operatingMode == OperatingMode.FAILURE) 
        {
            return;
        }

        operatingMode = OperatingMode.HIGH_LOAD;
        highLoadTicksRemaining = durationTicks;
    }

    public void forceNormalMode() 
    {
        if (operatingMode == OperatingMode.FAILURE) 
        {
            return;
        }

        operatingMode = OperatingMode.NORMAL;
        highLoadTicksRemaining = 0;
    }
}