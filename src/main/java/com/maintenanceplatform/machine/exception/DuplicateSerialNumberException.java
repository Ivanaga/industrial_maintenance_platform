package com.maintenanceplatform.machine.exception;

public class DuplicateSerialNumberException extends RuntimeException 
{

    public DuplicateSerialNumberException(String serialNumber) 
    {
        super("Machine with serial number already exists: " + serialNumber);
    }
}