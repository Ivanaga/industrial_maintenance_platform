package com.maintenanceplatform.machine.dto;

import com.maintenanceplatform.machine.MachineStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeMachineStatusRequest(@NotNull MachineStatus status) 
{
}