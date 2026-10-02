package com.maintenanceplatform.sensor;

import com.maintenanceplatform.machine.Machine;
import com.maintenanceplatform.machine.MachineStatus;
import com.maintenanceplatform.sensor.exception.SensorNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorController.class)
class SensorControllerTest 
{
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SensorService sensorService;

    @Test
    void shouldCreateSensor() throws Exception 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        Sensor sensor = new Sensor("Motor Temperature", SensorType.TEMPERATURE, "C", machine, true);

        when(sensorService.createSensor(any(), any(), any(), any(), any(Boolean.class))).thenReturn(sensor);

        mockMvc.perform(post("/api/sensors").contentType("application/json")
                        .content("""
                                {
                                  "name": "Motor Temperature",
                                  "type": "TEMPERATURE",
                                  "unit": "C",
                                  "machineId": 1,
                                  "active": true
                                }
                                """)
        ).andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Motor Temperature")).andExpect(jsonPath("$.type").value("TEMPERATURE")).andExpect(jsonPath("$.unit").value("C")).andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void shouldReturnAllSensors() throws Exception 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        Sensor sensor = new Sensor("Motor Temperature", SensorType.TEMPERATURE, "C", machine, true);

        when(sensorService.getAllSensors()).thenReturn(List.of(sensor));

        mockMvc.perform(get("/api/sensors")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Motor Temperature"));
    }

    @Test
    void shouldReturnNotFoundWhenSensorDoesNotExist() throws Exception 
    {
        when(sensorService.getSensorById(999L)).thenThrow(new SensorNotFoundException(999L));

        mockMvc.perform(get("/api/sensors/999")).andExpect(status().isNotFound()).andExpect(jsonPath("$.error").value("Sensor not found with id: 999"));
    }
}