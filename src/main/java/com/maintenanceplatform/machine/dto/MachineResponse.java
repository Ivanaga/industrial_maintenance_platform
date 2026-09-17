package com.maintenanceplatform.machine.dto;

import com.maintenanceplatform.machine.Machine;
import com.maintenanceplatform.machine.MachineStatus;

import java.time.LocalDate;

public record MachineResponse(
        Long id,
        String name,
        String serialNumber,
        String manufacturer,
        String model,
        LocalDate installationDate,
        String location,
        MachineStatus status
)
{

    public static MachineResponse from(Machine machine) 
    {
        return new MachineResponse(
                machine.getId(),
                machine.getName(),
                machine.getSerialNumber(),
                machine.getManufacturer(),
                machine.getModel(),
                machine.getInstallationDate(),
                machine.getLocation(),
                machine.getStatus()
        );
    }
}