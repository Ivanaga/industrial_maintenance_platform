package com.maintenanceplatform.sensorreading;

import com.maintenanceplatform.sensor.Sensor;
import com.maintenanceplatform.sensor.SensorRepository;
import com.maintenanceplatform.sensor.exception.SensorNotFoundException;
import org.springframework.stereotype.Service;
import com.maintenanceplatform.sensorreading.exception.InvalidTimeRangeException;
import com.maintenanceplatform.sensorreading.exception.SensorReadingNotFoundException;

import java.time.Instant;
import java.util.List;

@Service
public class SensorReadingService 
{

    private final SensorReadingRepository sensorReadingRepository;
    private final SensorRepository sensorRepository;

    public SensorReadingService(SensorReadingRepository sensorReadingRepository, SensorRepository sensorRepository)
    {
        this.sensorReadingRepository = sensorReadingRepository;
        this.sensorRepository = sensorRepository;
    }

    public SensorReading createReading(Long sensorId, double value, Instant timestamp) 
    {
        Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() -> new SensorNotFoundException(sensorId));

        SensorReading reading = new SensorReading(sensor, value, timestamp);

        return sensorReadingRepository.save(reading);
    }

    public List<SensorReading> getAllReadings() 
    {
        return sensorReadingRepository.findAll();
    }

    public List<SensorReading> getReadingsBySensorId(Long sensorId) 
    {

        if(!sensorRepository.existsById(sensorId)) 
        {
            throw new SensorNotFoundException(sensorId);
        }

        return sensorReadingRepository.findBySensorIdOrderByTimestampAsc(sensorId);
    }

    public List<SensorReading> getReadingsBySensorId(Long sensorId, Instant from, Instant to) 
    {
        if (!sensorRepository.existsById(sensorId)) 
        {
            throw new SensorNotFoundException(sensorId);
        }

        if (from != null && to != null && from.isAfter(to)) 
        {
            throw new InvalidTimeRangeException(from, to);
        }

        if (from != null && to != null) 
        {
            return sensorReadingRepository.findBySensorIdAndTimestampBetweenOrderByTimestampAsc(sensorId, from, to);
        }

        return sensorReadingRepository.findBySensorIdOrderByTimestampAsc(sensorId);
    }
    public SensorReading getLatestReadingBySensorId(Long sensorId) 
    {
        if (!sensorRepository.existsById(sensorId)) 
        {
            throw new SensorNotFoundException(sensorId);
        }
        
        SensorReading reading = sensorReadingRepository.findTopBySensorIdOrderByTimestampDesc(sensorId).orElseThrow(() -> new SensorReadingNotFoundException(sensorId));

        return reading;
    }
}