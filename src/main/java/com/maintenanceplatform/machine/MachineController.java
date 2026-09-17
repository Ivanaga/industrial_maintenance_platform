package com.maintenanceplatform.machine;

import com.maintenanceplatform.machine.dto.CreateMachineRequest;
import com.maintenanceplatform.machine.dto.MachineResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
public class MachineController 
{

    private final MachineService machineService;

    public MachineController(MachineService machineService) 
    {
        this.machineService = machineService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MachineResponse createMachine(@Valid @RequestBody CreateMachineRequest request) 
    {
        Machine machine = new Machine(
        request.name(),
        request.serialNumber(),
        request.manufacturer(),
        request.model(),
        request.installationDate(),
        request.location(),
        request.status()
        );

        return MachineResponse.from(machineService.createMachine(machine));
    }

    @GetMapping("/{id}")
    public MachineResponse getMachineById(@PathVariable Long id) 
    {
        return MachineResponse.from(machineService.getMachineById(id));
    }

    @GetMapping
    public List<MachineResponse> getAllMachines() 
    {
        return machineService.getAllMachines().stream().map(MachineResponse::from).toList();
    }
}