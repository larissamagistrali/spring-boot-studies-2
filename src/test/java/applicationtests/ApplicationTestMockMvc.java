package applicationtests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.pucrs.politecnica.assignments.AssignmentsApplication;

@SpringBootTest(classes = AssignmentsApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApplicationTestMockMvc {
	
	@Autowired	
	private MockMvc mockmvc;
	
	@Test
	public void showLogin() throws Exception {
		this.mockmvc.perform(get("/login")).andExpect(status().isOk());
	}
}
