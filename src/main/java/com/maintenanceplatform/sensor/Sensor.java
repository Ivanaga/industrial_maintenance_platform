package com.maintenanceplatform.sensor;

import com.maintenanceplatform.machine.Machine;
import jakarta.persistence.*;

@Entity
@Table(name = "sensors")
public class Sensor 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SensorType type;

    @Column(nullable = false)
    private String unit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    @Column(nullable = false)
    private boolean active;

    protected Sensor() 
    {
    }

    public Sensor(String name, SensorType type, String unit, Machine machine, boolean active) 
    {
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.machine = machine;
        this.active = active;
    }

    public Long getId() 
    {
        return id;
    }

    public String getName() 
    {
        return name;
    }

    public SensorType getType() 
    {
        return type;
    }

    public String getUnit() 
    {
        return unit;
    }

    public Machine getMachine() 
    {
        return machine;
    }

    public boolean isActive() 
    {
        return active;
    }
}