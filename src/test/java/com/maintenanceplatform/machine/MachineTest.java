// Unit tests for DTO Machine 
package com.maintenanceplatform.machine;


// Exceptions
import com.maintenanceplatform.machine.exception.InvalidMachineStatusTransitionException;


// Java primitives
import java.time.LocalDate;

// Assertions and tests primitives
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MachineTest 
{
    // Test for changeStatus()
    @Test
    void shouldChangeStatusWhenTransitionIsValid() 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        machine.changeStatus(MachineStatus.UNDER_MAINTENANCE);

        assertEquals(MachineStatus.UNDER_MAINTENANCE, machine.getStatus());
    }

    // Test for invalid status transition by changeStatus()
    @Test
    void shouldThrowExceptionWhenTransitionIsInvalid() 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.DECOMMISSIONED);

        assertThrows(InvalidMachineStatusTransitionException.class, () -> machine.changeStatus(MachineStatus.OPERATIONAL));
    }
    // Parametrized test for all combinations of valid changeStatus() transitions
    @ParameterizedTest
    @CsvSource(
    {
        "OPERATIONAL, UNDER_MAINTENANCE",
        "OPERATIONAL, OUT_OF_SERVICE",
        "OPERATIONAL, DECOMMISSIONED",
        "UNDER_MAINTENANCE, OPERATIONAL",
        "UNDER_MAINTENANCE, OUT_OF_SERVICE",
        "UNDER_MAINTENANCE, DECOMMISSIONED",
        "OUT_OF_SERVICE, OPERATIONAL",
        "OUT_OF_SERVICE, UNDER_MAINTENANCE",
        "OUT_OF_SERVICE, DECOMMISSIONED"
    })
    void shouldAllowValidStatusTransitions(MachineStatus currentStatus, MachineStatus newStatus) 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", currentStatus);

        machine.changeStatus(newStatus);

        assertEquals(newStatus, machine.getStatus());
    }
    // Parametrized test for all invalid changeStatus() transitions
    @ParameterizedTest
    @CsvSource(
    {
        "DECOMMISSIONED, OPERATIONAL",
        "DECOMMISSIONED, UNDER_MAINTENANCE",
        "DECOMMISSIONED, OUT_OF_SERVICE"
    })
    void shouldRejectInvalidStatusTransitions(MachineStatus currentStatus, MachineStatus newStatus)
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", currentStatus);

        assertThrows(InvalidMachineStatusTransitionException.class, () -> machine.changeStatus(newStatus));
    }
}