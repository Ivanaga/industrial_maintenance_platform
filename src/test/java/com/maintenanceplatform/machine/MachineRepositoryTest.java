package com.maintenanceplatform.machine;


// Imports for integration tests
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;


// Java priimitives
import java.time.LocalDate;


// Test primitives
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MachineRepositoryTest 
{

    @Autowired
    private MachineRepository machineRepository;

    // Integration test for searching in DB for existing machine using Id as key
    @Test
    void shouldFindMachineBySerialNumber() 
    {
        Machine machine = new Machine("Pump 01", "PUMP-TEST-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        machineRepository.save(machine);

        boolean exists = machineRepository.existsBySerialNumber("PUMP-TEST-001");

        assertTrue(exists);
    }

    // existsBySerialNumber() with invalid Id
    @Test
    void shouldReturnFalseWhenSerialNumberDoesNotExist() 
    {
        boolean exists = machineRepository.existsBySerialNumber("DOES-NOT-EXIST");

        assertFalse(exists);
    }
}