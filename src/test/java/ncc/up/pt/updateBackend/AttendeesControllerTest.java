package ncc.up.pt.updateBackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ncc.up.pt.updateBackend.Controller.AttendeesController;
import ncc.up.pt.updateBackend.Model.Attendees;
import ncc.up.pt.updateBackend.Repository.AttendeesRepository;

@WebMvcTest(controllers = AttendeesController.class)
@AutoConfigureMockMvc
public class AttendeesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AttendeesRepository attendeesRepository;

    private Attendees testAttendee;

    @BeforeEach
    public void setUp() {
        testAttendee = new Attendees("John Doe", "john.doe@example.com", 123456789L, 123456789L);
    }

    @Test
    public void testProcessAttendee() throws Exception {
        // Mocking repository save method
        Mockito.when(attendeesRepository.save(Mockito.any(Attendees.class))).thenReturn(testAttendee);

        // Perform POST request with JSON payload
        mockMvc.perform(MockMvcRequestBuilders.post("/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAttendee)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cellphoneNumber").value("123456789"));
    }
}
