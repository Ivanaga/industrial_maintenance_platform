package com.maintenanceplatform.machine.exception;

import com.maintenanceplatform.machine.MachineStatus;

public class InvalidMachineStatusTransitionException extends RuntimeException 
{

    public InvalidMachineStatusTransitionException(MachineStatus currentStatus, MachineStatus newStatus) 
    {
        super("Invalid machine status transition: " + currentStatus + " -> " + newStatus);
    }
}