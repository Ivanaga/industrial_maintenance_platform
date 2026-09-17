package com.maintenanceplatform.machine;

//EXCEPTIONS
import com.maintenanceplatform.machine.exception.DuplicateSerialNumberException;
import com.maintenanceplatform.machine.exception.MachineNotFoundException;


//FRAMEWORK IMPORTS
import org.springframework.stereotype.Service;

// JAVA PRIMITIVES
import java.util.List;
import java.time.LocalDate;

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
            throw new DuplicateSerialNumberException(machine.getSerialNumber());
        }

        return machineRepository.save(machine);
    }

    public Machine getMachineById(Long id) 
    {
        return machineRepository.findById(id).orElseThrow(() -> new MachineNotFoundException(id));
    }

    public List<Machine> getAllMachines() 
    {
        return machineRepository.findAll();
    }

    public Machine updateMachine(
        Long id,
        String name,
        String manufacturer,
        String model,
        LocalDate installationDate,
        String location
    ) 
    {
        Machine machine = getMachineById(id);

        machine.updateDetails(name, manufacturer, model, installationDate, location);

        return machineRepository.save(machine);
    }

    public Machine changeMachineStatus(Long id, MachineStatus newStatus) 
    {
        Machine machine = getMachineById(id);

        machine.changeStatus(newStatus);

        return machineRepository.save(machine);
    }
}