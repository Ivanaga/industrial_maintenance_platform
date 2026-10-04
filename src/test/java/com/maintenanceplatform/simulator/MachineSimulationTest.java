package com.maintenanceplatform.simulator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MachineSimulationTest 
{

    // Testing manual switching to LoadControlMode.MANUAL
    @Test
    void manualLoadShouldRemainUnchanged() 
    {
        MachineSimulation machine = new MachineSimulation(1L, List.of());

        machine.setLoadControlMode(LoadControlMode.MANUAL);

        machine.setLoadLevel(LoadLevel.HIGH);

        for (int i = 0; i < 100; i++) 
        {
            machine.updateLoadLevel();
        }

        assertEquals(LoadLevel.HIGH, machine.getLoadLevel());
    }
    // Testing increasing degradation
    @Test
    void degradationShouldIncrease() 
    {
        MachineSimulation machine = new MachineSimulation(1L, List.of());

        double before = machine.getDegradationLevel();

        machine.updateDegradation();

        double after = machine.getDegradationLevel();

        assertTrue(after > before);
    }

    // Testing the case: higher load modes should increase deragation value more than normal mode
    @Test
    void highLoadShouldIncreaseDegradationMoreThanNormalLoad() 
    {
        MachineSimulation normalMachine = new MachineSimulation(1L, List.of());

        MachineSimulation highLoadMachine = new MachineSimulation(2L, List.of());

        normalMachine.setLoadControlMode(LoadControlMode.MANUAL);
        normalMachine.setLoadLevel(LoadLevel.NORMAL);

        highLoadMachine.setLoadControlMode(LoadControlMode.MANUAL);
        highLoadMachine.setLoadLevel(LoadLevel.HIGH);

        for (int i = 0; i < 100; i++) 
        {
            normalMachine.updateDegradation();
            highLoadMachine.updateDegradation();
        }

        assertTrue(highLoadMachine.getDegradationLevel() > normalMachine.getDegradationLevel());
    }

    // Testing automatic switch to degradating health state after degradation threshold
    @Test
    void shouldBecomeDegradingWhenDegradationIsHighEnough() 
    {
        MachineSimulation machine = new MachineSimulation(1L, List.of());

        machine.setLoadControlMode(LoadControlMode.MANUAL);
        machine.setLoadLevel(LoadLevel.OVERLOAD);

        while (machine.getDegradationLevel() < 0.60) 
        {
            machine.updateDegradation();
        }

        assertEquals(HealthState.DEGRADING, machine.getHealthState());
    }

    // Testing automatic switch to critical health state after critical threshold
    @Test
    void shouldBecomeFailureWhenDegradationIsCritical() 
    {
        MachineSimulation machine = new MachineSimulation(1L, List.of());

        machine.setLoadControlMode(LoadControlMode.MANUAL);
        machine.setLoadLevel(LoadLevel.OVERLOAD);

        while (machine.getDegradationLevel() < 0.95) 
        {
            machine.updateDegradation();
        }

        assertEquals(HealthState.FAILURE, machine.getHealthState());
    }
}