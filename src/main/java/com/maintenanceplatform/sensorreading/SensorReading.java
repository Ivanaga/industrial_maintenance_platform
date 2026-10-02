package com.maintenanceplatform.sensorreading;

import com.maintenanceplatform.sensor.Sensor;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "sensor_readings")
public class SensorReading 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @Column(nullable = false)
    private double value;

    @Column(nullable = false)
    private Instant timestamp;

    protected SensorReading() 
    {
    }

    public SensorReading(Sensor sensor, double value, Instant timestamp)
    {
        this.sensor = sensor;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Long getId() 
    {
        return id;
    }

    public Sensor getSensor() 
    {
        return sensor;
    }

    public double getValue() 
    {
        return value;
    }

    public Instant getTimestamp() 
    {
        return timestamp;
    }
}