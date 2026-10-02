package com.maintenanceplatform.sensor;

import com.maintenanceplatform.machine.Machine;
import com.maintenanceplatform.machine.MachineRepository;
import com.maintenanceplatform.machine.MachineStatus;
import com.maintenanceplatform.machine.exception.MachineNotFoundException;
import com.maintenanceplatform.sensor.exception.SensorNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SensorServiceTest 
{

    private SensorRepository sensorRepository;
    private MachineRepository machineRepository;
    private SensorService sensorService;

    @BeforeEach
    void setUp() 
    {
        sensorRepository = mock(SensorRepository.class);
        machineRepository = mock(MachineRepository.class);

        sensorService = new SensorService(sensorRepository, machineRepository);
    }

    @Test
    void shouldCreateSensorWhenMachineExists() 
    {
        Machine machine = new Machine("Pump 01","PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));

        when(sensorRepository.save(any(Sensor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Sensor result = sensorService.createSensor("Motor Temperature", SensorType.TEMPERATURE, "C", 1L, true);

        assertEquals("Motor Temperature", result.getName());
        assertEquals(SensorType.TEMPERATURE, result.getType());
        assertEquals(machine, result.getMachine());

        verify(machineRepository).findById(1L);
        verify(sensorRepository).save(any(Sensor.class));
    }

    @Test
    void shouldNotCreateSensorWhenMachineDoesNotExist() 
    {
        when(machineRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(MachineNotFoundException.class,() -> sensorService.createSensor(
                        "Pressure Sensor",
                        SensorType.PRESSURE,
                        "bar",
                        999L,
                        true));

        verify(machineRepository).findById(999L);
        verify(sensorRepository, never()).save(any());
    }

    @Test
    void shouldReturnSensorWhenSensorExists() 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        Sensor sensor = new Sensor("Motor Temperature", SensorType.TEMPERATURE, "C", machine, true);

        when(sensorRepository.findById(1L)).thenReturn(Optional.of(sensor));

        Sensor result = sensorService.getSensorById(1L);

        assertEquals(sensor, result);

        verify(sensorRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenSensorDoesNotExist() 
    {
        when(sensorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(SensorNotFoundException.class, () -> sensorService.getSensorById(999L));

        verify(sensorRepository).findById(999L);
    }
}