package com.maintenanceplatform.machine;

import java.util.Set;

public enum MachineStatus {

    OPERATIONAL,
    UNDER_MAINTENANCE,
    OUT_OF_SERVICE,
    DECOMMISSIONED;

    public boolean canTransitionTo(MachineStatus newStatus) 
    {
        return switch (this) 
        {
            case OPERATIONAL -> Set.of(UNDER_MAINTENANCE, OUT_OF_SERVICE, DECOMMISSIONED).contains(newStatus);

            case UNDER_MAINTENANCE -> Set.of(OPERATIONAL, OUT_OF_SERVICE, DECOMMISSIONED).contains(newStatus);

            case OUT_OF_SERVICE -> Set.of(OPERATIONAL, UNDER_MAINTENANCE, DECOMMISSIONED).contains(newStatus);

            case DECOMMISSIONED -> false;
        };
    }
}