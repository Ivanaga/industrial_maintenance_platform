// Web API integration tests
package com.maintenanceplatform.machine;


// Mock + autoconfigure classes import + exceptions
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.maintenanceplatform.machine.exception.MachineNotFoundException;
import com.maintenanceplatform.machine.exception.DuplicateSerialNumberException;


// Java primitives
import java.time.LocalDate;

// Mock + configure methods import
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(MachineController.class)
class MachineControllerTest 
{

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MachineService machineService;

    // Test for post request + response from serv
    @Test
    void shouldCreateMachine() throws Exception 
    {
        Machine machine = new Machine("Pump 01", "PUMP-001", "Siemens", "X1", LocalDate.of(2024, 5, 12), "Hall A", MachineStatus.OPERATIONAL);

        when(machineService.createMachine(any(Machine.class))).thenReturn(machine);

        mockMvc.perform(post("/api/machines").contentType("application/json").content(
            """
            {
                "name": "Pump 01",
                "serialNumber": "PUMP-001",
                "manufacturer": "Siemens",
                "model": "X1",
                "installationDate": "2024-05-12",
                "location": "Hall A",
                "status": "OPERATIONAL"
            }
            """)
        ).andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Pump 01")).andExpect(jsonPath("$.serialNumber").value("PUMP-001")).andExpect(jsonPath("$.status").value("OPERATIONAL"));
    }

    // Testing error response from server 400 when trying to post machine with empty name
    @Test
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception 
    {
        mockMvc.perform(post("/api/machines").contentType("application/json").content("""
        {
        "name": "",
        "serialNumber": "PUMP-001",
        "manufacturer": "Siemens",
        "model": "X1",
        "installationDate": "2024-05-12",
        "location": "Hall A",
        "status": "OPERATIONAL"
        }
        """)).andExpect(status().isBadRequest());
    }

    // Testing 404 response!!
    @Test
    void shouldReturnNotFoundWhenMachineDoesNotExist() throws Exception 
    {
        when(machineService.getMachineById(999L)).thenThrow(new MachineNotFoundException(999L));

        mockMvc.perform(get("/api/machines/999")).andExpect(status().isNotFound()).andExpect(jsonPath("$.error").value("Machine not found with id: 999"));
    }

    // Testing 409 when trying to create a machine with same serial number
    @Test
    void shouldReturnConflictWhenSerialNumberAlreadyExists() throws Exception 
    {
        when(machineService.createMachine(any(Machine.class))).thenThrow(new DuplicateSerialNumberException("PUMP-001"));

        mockMvc.perform(post("/api/machines").contentType("application/json").content("""
        {
        "name": "Pump 01",
        "serialNumber": "PUMP-001",
        "manufacturer": "Siemens",
        "model": "X1",
        "installationDate": "2024-05-12",
        "location": "Hall A",
        "status": "OPERATIONAL"
        }
        """)).andExpect(status().isConflict()).andExpect(jsonPath("$.error").value("Machine with serial number already exists: PUMP-001"));
    }
}