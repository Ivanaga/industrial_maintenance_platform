package com.maintenanceplatform.sensorreading;

import com.maintenanceplatform.sensor.Sensor;
import com.maintenanceplatform.sensor.SensorType;
import com.maintenanceplatform.sensor.exception.SensorNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Same as DataJpaTest only start things that are needed to test web layer (controller layer)
@WebMvcTest(SensorReadingController.class)
class SensorReadingControllerTest 
{
    // Already existing by starting @WebMvcTest deployed web slice layer object to generate requests
    @Autowired
    private MockMvc mockMvc;

    // Creates mock object of service class (not deploying real one)
    @MockitoBean
    private SensorReadingService sensorReadingService;


    // Testing post request for creating sensorreading without deploying something except web layer
    @Test
    void shouldCreateSensorReading() throws Exception 
    {
        Sensor sensor = new Sensor("Temperature Sensor", SensorType.TEMPERATURE, "C", null, true);

        SensorReading reading = new SensorReading(sensor, 72.5, Instant.parse("2026-10-03T09:30:00Z"));

        when(sensorReadingService.createReading(1L, 72.5, Instant.parse("2026-10-03T09:30:00Z"))).thenReturn(reading);

        mockMvc.perform(post("/api/sensor-readings").contentType("application/json").content(
            """
            {
            "sensorId": 1,
            "value": 72.5,
            "timestamp": "2026-10-03T09:30:00Z"
            }
            """)
        ).andExpect(status().isCreated()).andExpect(jsonPath("$.value").value(72.5)).andExpect(jsonPath("$.timestamp").value("2026-10-03T09:30:00Z"));
    }

    // Testing get request for sensor readings
    @Test
    void shouldReturnReadingsForSensor() throws Exception 
    {
        when(sensorReadingService.getReadingsBySensorId(1L, null, null)).thenReturn(List.of());

        mockMvc.perform(get("/api/sensor-readings/sensor/1")).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    // Testing sensor not found exception
    @Test
    void shouldReturnNotFoundWhenSensorDoesNotExist() throws Exception 
    {
        when(sensorReadingService.getReadingsBySensorId(999L, null, null)).thenThrow(new SensorNotFoundException(999L));

        mockMvc.perform(get("/api/sensor-readings/sensor/999")).andExpect(status().isNotFound());
    }
}