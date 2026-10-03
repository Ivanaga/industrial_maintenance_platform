package com.maintenanceplatform.simulator;

// Represents the current operating state of a simulated machine.
// The mode influences how sensor values behave over time.
public enum OperatingMode 
{
    NORMAL,
    HIGH_LOAD,
    DEGRADING,
    FAILURE
}