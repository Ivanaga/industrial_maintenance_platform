package com.maintenanceplatform.common.exception;

import com.maintenanceplatform.machine.exception.DuplicateSerialNumberException;
import com.maintenanceplatform.machine.exception.MachineNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}