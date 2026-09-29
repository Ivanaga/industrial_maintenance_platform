package com.maintenanceplatform.machine;

import com.maintenanceplatform.machine.exception.InvalidMachineStatusTransitionException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MachineTest 
{

    @Test
    void shouldChangeStatusWhenTransitionIsValid() 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        machine.changeStatus(MachineStatus.UNDER_MAINTENANCE);

        assertEquals(MachineStatus.UNDER_MAINTENANCE, machine.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenTransitionIsInvalid() 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.DECOMMISSIONED);

        assertThrows(InvalidMachineStatusTransitionException.class, () -> machine.changeStatus(MachineStatus.OPERATIONAL));
    }
}