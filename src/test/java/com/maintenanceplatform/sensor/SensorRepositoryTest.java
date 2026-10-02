package com.maintenanceplatform.sensor;

import com.maintenanceplatform.machine.Machine;
import com.maintenanceplatform.machine.MachineRepository;
import com.maintenanceplatform.machine.MachineStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SensorRepositoryTest 
{
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Test
    void shouldSaveSensorLinkedToMachine() 
    {
        Machine machine = new Machine("Pump 01", "PUMP-SENSOR-TEST-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        Machine savedMachine = machineRepository.save(machine);

        Sensor sensor = new Sensor("Motor Temperature", SensorType.TEMPERATURE, "C", savedMachine, true);

        Sensor savedSensor = sensorRepository.save(sensor);

        assertTrue(savedSensor.getId() != null);
        assertEquals(savedMachine, savedSensor.getMachine());
        assertEquals("Motor Temperature", savedSensor.getName());
    }
}