package com.maintenanceplatform.machine.exception;

public class MachineNotFoundException extends RuntimeException 
{
    public MachineNotFoundException(Long id) 
    {
        super("Machine not found with id: " + id);
    }
}