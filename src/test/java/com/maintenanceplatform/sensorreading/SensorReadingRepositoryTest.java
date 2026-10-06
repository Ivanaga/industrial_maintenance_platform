package com.maintenanceplatform.sensorreading;

import com.maintenanceplatform.machine.Machine;
import com.maintenanceplatform.machine.MachineRepository;
import com.maintenanceplatform.machine.MachineStatus;
import com.maintenanceplatform.sensor.Sensor;
import com.maintenanceplatform.sensor.SensorRepository;
import com.maintenanceplatform.sensor.SensorType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


// Only deploy things that are related to test repository slice
@DataJpaTest
// With this command we say Spring not to use embedded DB for testing use the real one
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SensorReadingRepositoryTest 
{
    // Injects a matching Spring bean from the ApplicationContext.
    // When Spring starts, it creates and manages the required bean instances.
    // @Autowired tells Spring to retrieve the matching existing bean and inject it here.
    // We can use it because @DataJPATest creates Spring Test Context with all needed elements 
    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorReadingRepository sensorReadingRepository;

    // Testing saving and extracting sensor readings to and from DB
    @Test
    void shouldFindReadingsBySensorOrderedByTimestamp() 
    {
        Machine machine = machineRepository.save(new Machine("Pump 01", "PUMP-READING-TEST-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL));

        Sensor sensor = sensorRepository.save(new Sensor("Temperature Sensor", SensorType.TEMPERATURE, "C", machine, true));

        SensorReading laterReading = new SensorReading(sensor, 72.0, Instant.parse("2026-10-03T10:00:00Z"));

        SensorReading earlierReading = new SensorReading(sensor, 70.0, Instant.parse("2026-10-03T09:00:00Z"));

        sensorReadingRepository.save(laterReading);
        sensorReadingRepository.save(earlierReading);

        List<SensorReading> result = sensorReadingRepository.findBySensorIdOrderByTimestampAsc(sensor.getId());

        assertEquals(2, result.size());
        assertEquals(70.0, result.get(0).getValue());
        assertEquals(72.0, result.get(1).getValue());
    }

    // Testing automatically generated function if it really returns only readings within timestamp
    @Test
    void shouldFindReadingsWithinTimeRange() 
    {
        Machine machine = machineRepository.save(new Machine("Pump 02", "PUMP-READING-TEST-002", "Siemens", "X2", LocalDate.of(2024, 6, 10), "Hall B", MachineStatus.OPERATIONAL));

        Sensor sensor = sensorRepository.save(new Sensor("Temperature Sensor", SensorType.TEMPERATURE, "C", machine, true));

        SensorReading reading1 = new SensorReading(sensor, 68.0, Instant.parse("2026-10-03T08:00:00Z"));

        SensorReading reading2 = new SensorReading(sensor, 70.0, Instant.parse("2026-10-03T09:00:00Z"));

        SensorReading reading3 = new SensorReading(sensor, 72.0, Instant.parse("2026-10-03T10:00:00Z"));

        sensorReadingRepository.save(reading1);
        sensorReadingRepository.save(reading2);
        sensorReadingRepository.save(reading3);

        Instant from = Instant.parse("2026-10-03T08:30:00Z");
        Instant to = Instant.parse("2026-10-03T09:30:00Z");

        List<SensorReading> result = sensorReadingRepository.findBySensorIdAndTimestampBetweenOrderByTimestampAsc(sensor.getId(), from, to);

        assertEquals(1, result.size());
        assertEquals(70.0, result.get(0).getValue());
    }

    // Testing new api endpoint for frontend
    @Test
    void shouldFindLatestReadingBySensorId() 
    {
        Machine machine = machineRepository.save(new Machine("Pump 03", "PUMP-READING-TEST-003", "Siemens", "X3", LocalDate.of(2024, 7, 10), "Hall C", MachineStatus.OPERATIONAL));

        Sensor sensor = sensorRepository.save(new Sensor("Temperature Sensor", SensorType.TEMPERATURE, "C", machine, true));

        SensorReading oldest = sensorReadingRepository.save(new SensorReading(sensor, 68.0, Instant.parse("2026-10-06T10:00:00Z")));

        SensorReading latest = sensorReadingRepository.save(new SensorReading(sensor, 71.5, Instant.parse("2026-10-06T12:00:00Z")));

        SensorReading middle = sensorReadingRepository.save(new SensorReading(sensor, 69.5, Instant.parse("2026-10-06T11:00:00Z")));

        Optional<SensorReading> result = sensorReadingRepository.findTopBySensorIdOrderByTimestampDesc(sensor.getId());

        assertTrue(result.isPresent());
        assertEquals(latest.getId(), result.get().getId());
        assertEquals(71.5, result.get().getValue())        ;
        assertEquals(Instant.parse("2026-10-06T12:00:00Z"), result.get().getTimestamp());
    }

    @Test        
    void shouldReturnEmptyWhenSensorHasNoReadings() 
    {
        Machine machine = machineRepository.save(new Machine("Pump 04", "PUMP-READING-TEST-004", "Siemens", "X4", LocalDate.of(2024, 7, 10), "Hall C", MachineStatus.OPERATIONAL));

        Sensor sensor = sensorRepository.save(new Sensor("Temperature Sensor", SensorType.TEMPERATURE, "C", machine, true));

        Optional<SensorReading> result = sensorReadingRepository.findTopBySensorIdOrderByTimestampDesc(sensor.getId());

        assertTrue(result.isEmpty());
    }
}