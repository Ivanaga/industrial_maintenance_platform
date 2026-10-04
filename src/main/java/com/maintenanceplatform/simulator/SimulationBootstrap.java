package com.maintenanceplatform.simulator;

import com.maintenanceplatform.machine.Machine;
import com.maintenanceplatform.machine.MachineRepository;
import com.maintenanceplatform.machine.MachineStatus;
import com.maintenanceplatform.sensor.Sensor;
import com.maintenanceplatform.sensor.SensorRepository;
import com.maintenanceplatform.sensor.SensorType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

// Spring creates this class as bean class in ApplicationContext
// implements CommandLineRunner means that by starting the application it will automatically execute run() func
// and dev profile means that bootstrap will be executed only from dev profile
@Component
@Profile("dev")
public class SimulationBootstrap implements CommandLineRunner 
{
    private final MachineRepository machineRepository;
    private final SensorRepository sensorRepository;

    public SimulationBootstrap(MachineRepository machineRepository, SensorRepository sensorRepository)
    {
        this.machineRepository = machineRepository;
        this.sensorRepository = sensorRepository;
    }

    @Override
    public void run(String... args) 
    {
        Machine machine = machineRepository.findBySerialNumber("SIM-MACHINE-001").orElseGet(this::createMachine);

        if (sensorRepository.findByMachineId(machine.getId()).isEmpty()) 
        {
            createSensors(machine);
        }

        printSensorIds(machine);
    }

    private Machine createMachine() 
    {
        Machine machine = new Machine("Simulation Machine 01", "SIM-MACHINE-001", "Virtual Factory", "SIM-1000", LocalDate.now(), "Simulation Lab", MachineStatus.OPERATIONAL);

        return machineRepository.save(machine);
    }

    private void createSensors(Machine machine) 
    {
        sensorRepository.save(new Sensor("Motor Temperature", SensorType.TEMPERATURE, "°C", machine, true));

        sensorRepository.save(new Sensor("Bearing Vibration", SensorType.VIBRATION, "mm/s", machine, true));

        sensorRepository.save(new Sensor("Motor Current", SensorType.CURRENT, "A", machine, true));

        sensorRepository.save(new Sensor("Machine Load", SensorType.LOAD, "%", machine, true));
    }

    private void printSensorIds(Machine machine) 
    {
        System.out.println("Simulation machine ID: " + machine.getId());

        sensorRepository.findByMachineId(machine.getId()).forEach(sensor -> System.out.println(sensor.getType() + " sensor ID: " + sensor.getId()));
    }
}