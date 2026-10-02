package com.maintenanceplatform.sensor;

import com.maintenanceplatform.sensor.dto.CreateSensorRequest;
import com.maintenanceplatform.sensor.dto.SensorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorController 
{

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) 
    {
        this.sensorService = sensorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorResponse createSensor(@Valid @RequestBody CreateSensorRequest request) 
    {
        Sensor sensor = sensorService.createSensor(request.name(), request.type(), request.unit(), request.machineId(), request.active());

        return SensorResponse.from(sensor);
    }

    @GetMapping
    public List<SensorResponse> getAllSensors() 
    {
        return sensorService.getAllSensors().stream().map(SensorResponse::from).toList();
    }
    @GetMapping("/{id}")
    public SensorResponse getSensorById(@PathVariable Long id) 
    {
        return SensorResponse.from(sensorService.getSensorById(id));
    }
}