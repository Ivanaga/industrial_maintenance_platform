// MOCK TESTS

package com.maintenanceplatform.machine;

import com.maintenanceplatform.machine.exception.DuplicateSerialNumberException;
import com.maintenanceplatform.machine.exception.MachineNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MachineServiceTest 
{

    private MachineRepository machineRepository;
    private MachineService machineService;

    @BeforeEach
    void setUp() 
    {
        machineRepository = Mockito.mock(MachineRepository.class);
        machineService = new MachineService(machineRepository);
    }

    // Test for createMachine() with unique ID
    @Test
    void shouldCreateMachineWhenSerialNumberIsUnique() 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        when(machineRepository.existsBySerialNumber("PUMP-001")).thenReturn(false);

        when(machineRepository.save(machine)).thenReturn(machine);

        Machine result = machineService.createMachine(machine);

        assertEquals(machine, result);

        verify(machineRepository).existsBySerialNumber("PUMP-001");

        verify(machineRepository).save(machine);
    }

    // Test for createMachine with already existing ID
    @Test
    void shouldThrowExceptionWhenSerialNumberAlreadyExists() 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        when(machineRepository.existsBySerialNumber("PUMP-001")).thenReturn(true);

        assertThrows(DuplicateSerialNumberException.class, () -> machineService.createMachine(machine));

        verify(machineRepository).existsBySerialNumber("PUMP-001");

        verify(machineRepository, never()).save(any());
    }

    // Test for getMachineById() if machine exists
    @Test
    void shouldReturnMachineWhenMachineExists() 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));

        Machine result = machineService.getMachineById(1L);

        assertEquals(machine, result);
    }
    // Test for getMachineById() when Machine does not exist
    @Test
    void shouldThrowExceptionWhenMachineDoesNotExist() 
    {
        when(machineRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(MachineNotFoundException.class, () -> machineService.getMachineById(999L));
    }

    // Test changeMachineStatus() valid transition
    @Test
    void shouldChangeMachineStatus()
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);
        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));
        when(machineRepository.save(machine)).thenReturn(machine);
        Machine result = machineService.changeMachineStatus(1L, MachineStatus.UNDER_MAINTENANCE);

        assertEquals(MachineStatus.UNDER_MAINTENANCE, result.getStatus());

        verify(machineRepository).findById(1L);
        verify(machineRepository).save(machine);
    }

    // Test for updateMachine() when Id does exist
    @Test
    void shouldUpdateMachineDetails() 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));

        when(machineRepository.save(machine)).thenReturn(machine);

        Machine result = machineService.updateMachine(1L, "Pump 01 Updated", "ABB", "X2", LocalDate.of(2025, 1, 10), "Hall B");

        assertEquals("Pump 01 Updated", result.getName());
        assertEquals("ABB", result.getManufacturer());
        assertEquals("X2", result.getModel());
        assertEquals(LocalDate.of(2025, 1, 10), result.getInstallationDate());
        assertEquals("Hall B", result.getLocation());

        verify(machineRepository).findById(1L);
        verify(machineRepository).save(machine);
     }
     // Test for updateMachine() when Id does not exist
     @Test
     void shouldNotUpdateMachineWhenMachineDoesNotExist() 
     {
        when(machineRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(MachineNotFoundException.class, () -> machineService.updateMachine(999L, "Pump 01 Updated", "ABB", "X2", LocalDate.of(2025, 1, 10), "Hall B"));

        verify(machineRepository).findById(999L);
        verify(machineRepository, never()).save(any());
     }
}