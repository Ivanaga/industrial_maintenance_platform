package com.maintenanceplatform.machine;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "machines")
public class Machine 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    private String manufacturer;

    private String model;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MachineStatus status;

    protected Machine() 
    {
    }

    public Machine
    (
            String name,
            String serialNumber,
            String manufacturer,
            String model,
            LocalDate installationDate,
            String location,
            MachineStatus status
    ) 
    {
        this.name = name;
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.model = model;
        this.installationDate = installationDate;
        this.location = location;
        this.status = status;
    }

    public Long getId() 
    {
        return id;
    }

    public String getName() 
    {
        return name;
    }

    public String getSerialNumber() 
    {
        return serialNumber;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }

    public String getModel() 
    {
        return model;
    }

    public LocalDate getInstallationDate() 
    {
        return installationDate;
    }

    public String getLocation() 
    {
        return location;
    }

    public MachineStatus getStatus() 
    {
        return status;
    }

    public void updateDetails(
        String name,
        String manufacturer,
        String model,
        LocalDate installationDate,
        String location) 
    {
        this.name = name;
        this.manufacturer = manufacturer;
        this.model = model;
        this.installationDate = installationDate;
        this.location = location;
    }

    public void changeStatus(MachineStatus newStatus) 
    {
        if (this.status == MachineStatus.DECOMMISSIONED) 
        {
            throw new IllegalStateException("Decommissioned machine status cannot be changed");
        }

        this.status = newStatus;
    }
}