package com.maintenanceplatform.machine.dto;

import com.maintenanceplatform.machine.MachineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateMachineRequest(
@NotBlank String name,
@NotBlank String serialNumber,
String manufacturer,
String model,
LocalDate installationDate,
String location,
@NotNull MachineStatus status
) 
{
}