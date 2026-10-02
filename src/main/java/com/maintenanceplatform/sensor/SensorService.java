package com.maintenanceplatform.sensor;

import com.maintenanceplatform.machine.Machine;
import com.maintenanceplatform.machine.MachineRepository;
import com.maintenanceplatform.machine.exception.MachineNotFoundException;
import org.springframework.stereotype.Service;
import com.maintenanceplatform.sensor.exception.SensorNotFoundException;

import java.util.List;

@Service
public class SensorService 
{

    private final SensorRepository sensorRepository;
    private final MachineRepository machineRepository;

    public SensorService(SensorRepository sensorRepository, MachineRepository machineRepository) 
    {
        this.sensorRepository = sensorRepository;
        this.machineRepository = machineRepository;
    }

    public Sensor createSensor(String name, SensorType type, String unit, Long machineId, boolean active) 
    {
        Machine machine = machineRepository.findById(machineId).orElseThrow(() -> new MachineNotFoundException(machineId));

        Sensor sensor = new Sensor(name, type, unit, machine, active);

        return sensorRepository.save(sensor);
    }

    public List<Sensor> getAllSensors() 
    {
        return sensorRepository.findAll();
    }

    public Sensor getSensorById(Long id) 
    {
        return sensorRepository.findById(id).orElseThrow(() -> new SensorNotFoundException(id));
    }
}