package com.maintenanceplatform.common.exception;

// Exceptions
import com.maintenanceplatform.machine.exception.DuplicateSerialNumberException;
import com.maintenanceplatform.machine.exception.MachineNotFoundException;
import com.maintenanceplatform.machine.exception.InvalidMachineStatusTransitionException;
import com.maintenanceplatform.sensorreading.exception.InvalidTimeRangeException;

// Spring primitives
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.maintenanceplatform.sensor.exception.SensorNotFoundException;

// Java primitives
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler 
{

    @ExceptionHandler(MachineNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleMachineNotFound(MachineNotFoundException exception) 
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(DuplicateSerialNumberException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateSerialNumber(DuplicateSerialNumberException exception) 
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(InvalidMachineStatusTransitionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidStatusTransition(InvalidMachineStatusTransitionException exception) 
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(SensorNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSensorNotFound(SensorNotFoundException exception) 
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(InvalidTimeRangeException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTimeRange(InvalidTimeRangeException exception)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
    }
}