package com.maintenanceplatform.machine;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService 
{

    private final MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) 
    {
        this.machineRepository = machineRepository;
    }

    public Machine createMachine(Machine machine) 
    {
        if (machineRepository.existsBySerialNumber(machine.getSerialNumber())) 
        {
            throw new IllegalArgumentException
            (
                "Machine with serial number already exists: "
                + machine.getSerialNumber()
            );
        }

        return machineRepository.save(machine);
    }

    public Machine getMachineById(Long id) 
    {
        return machineRepository.findById(id).orElseThrow(() ->
        new IllegalArgumentException("Machine not found with id: " + id));
    }

    public List<Machine> getAllMachines() 
    {
        return machineRepository.findAll();
    }
}