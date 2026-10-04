package com.maintenanceplatform.machine;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MachineRepository extends JpaRepository<Machine, Long> 
{
    boolean existsBySerialNumber(String serialNumber);

    Optional<Machine> findBySerialNumber(String serialNumber);
}