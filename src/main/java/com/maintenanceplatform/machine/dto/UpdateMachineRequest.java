package com.maintenanceplatform.machine.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UpdateMachineRequest(@NotBlank String name, 
String manufacturer,
String model,
LocalDate installationDate,
String location
) 
{
}