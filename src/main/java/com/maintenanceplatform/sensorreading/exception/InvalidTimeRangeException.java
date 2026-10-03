package com.maintenanceplatform.sensorreading.exception;

import java.time.Instant;

public class InvalidTimeRangeException extends RuntimeException 
{
    public InvalidTimeRangeException(Instant from, Instant to) 
    {
        super("Invalid time range: 'from' must be before or equal to 'to'. " + "from=" + from + ", to=" + to);
    }
}