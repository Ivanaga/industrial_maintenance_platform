package com.maintenanceplatform.sensorreading;

import com.maintenanceplatform.sensorreading.dto.CreateSensorReadingRequest;
import com.maintenanceplatform.sensorreading.dto.SensorReadingResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.Instant;

@RestController
@RequestMapping("/api/sensor-readings")
public class SensorReadingController 
{
    private final SensorReadingService sensorReadingService;

    public SensorReadingController(SensorReadingService sensorReadingService) 
    {
        this.sensorReadingService = sensorReadingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorReadingResponse createReading(@Valid @RequestBody CreateSensorReadingRequest request)
    {
        SensorReading reading = sensorReadingService.createReading(request.sensorId(), request.value(), request.timestamp());

        return SensorReadingResponse.from(reading);
    }

    @GetMapping
    public List<SensorReadingResponse> getAllReadings() 
    {
        return sensorReadingService.getAllReadings().stream().map(SensorReadingResponse::from).toList();
    }

    @GetMapping("/sensor/{sensorId}")
    public List<SensorReadingResponse> getReadingsBySensor(@PathVariable Long sensorId, @RequestParam(required = false) Instant from, @RequestParam(required = false) Instant to) 
    {
        return sensorReadingService.getReadingsBySensorId(sensorId, from, to).stream().map(SensorReadingResponse::from).toList();
    }
}