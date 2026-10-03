package com.maintenanceplatform.sensorreading;

import com.maintenanceplatform.sensor.SensorRepository;
import com.maintenanceplatform.sensor.exception.SensorNotFoundException;
import com.maintenanceplatform.sensorreading.exception.InvalidTimeRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SensorReadingServiceTest 
{
    private SensorReadingRepository sensorReadingRepository;
    private SensorRepository sensorRepository;
    private SensorReadingService sensorReadingService;

    @BeforeEach
    void setUp() 
    {
        sensorReadingRepository = mock(SensorReadingRepository.class);
        sensorRepository = mock(SensorRepository.class);

        sensorReadingService = new SensorReadingService(sensorReadingRepository, sensorRepository);
    }

    // Test for getReadingsBySensorId()
    @Test
    void shouldReturnReadingsForSensor() 
    {
        Long sensorId = 1L;

        when(sensorRepository.existsById(sensorId)).thenReturn(true);

        when(sensorReadingRepository.findBySensorIdOrderByTimestampAsc(sensorId)).thenReturn(List.of());

        List<SensorReading> result = sensorReadingService.getReadingsBySensorId(sensorId, null, null);

        assertEquals(0, result.size());

        verify(sensorRepository).existsById(sensorId);
        verify(sensorReadingRepository).findBySensorIdOrderByTimestampAsc(sensorId);
    }

    // Test getReadingsBySensorId using timerange
    @Test
    void shouldUseTimeRangeWhenFromAndToAreProvided() 
    {
        Long sensorId = 1L;

        Instant from = Instant.parse("2026-10-03T09:00:00Z");
        Instant to = Instant.parse("2026-10-03T10:00:00Z");

        when(sensorRepository.existsById(sensorId)).thenReturn(true);

        when(sensorReadingRepository.findBySensorIdAndTimestampBetweenOrderByTimestampAsc(sensorId, from, to)).thenReturn(List.of());

        sensorReadingService.getReadingsBySensorId(sensorId, from, to);

        verify(sensorReadingRepository).findBySensorIdAndTimestampBetweenOrderByTimestampAsc(sensorId, from, to);
    }

    // Test for throwing an exception for invalid time range
    @Test
    void shouldThrowWhenTimeRangeIsInvalid() 
    {
        Long sensorId = 1L;

        Instant from = Instant.parse("2026-10-03T12:00:00Z");
        Instant to = Instant.parse("2026-10-03T10:00:00Z");

        when(sensorRepository.existsById(sensorId)).thenReturn(true);

        assertThrows(InvalidTimeRangeException.class, () -> sensorReadingService.getReadingsBySensorId(sensorId, from, to));

        verify(sensorReadingRepository, never()).findBySensorIdAndTimestampBetweenOrderByTimestampAsc(anyLong(), any(), any());
    }

    // Test for throwing SensorNotFoundException
    @Test
    void shouldThrowWhenSensorDoesNotExist() 
    {
        when(sensorRepository.existsById(999L)).thenReturn(false);

        assertThrows(SensorNotFoundException.class, () -> sensorReadingService.getReadingsBySensorId(999L, null, null));

        verify(sensorReadingRepository, never()).findBySensorIdOrderByTimestampAsc(anyLong());
    }
}