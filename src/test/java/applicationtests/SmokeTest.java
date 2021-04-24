package applicationtests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.pucrs.politecnica.assignments.AssignmentsApplication;
import br.pucrs.politecnica.assignments.admin.AdminController;
import br.pucrs.politecnica.assignments.discipline.DisciplineController;
import br.pucrs.politecnica.assignments.login.LoginController;
import br.pucrs.politecnica.assignments.menu.AdmWelcomeController;
@SpringBootTest(classes = AssignmentsApplication.class)
@ActiveProfiles("test")
public class SmokeTest {

	@Autowired
	private AdminController admController;

	@Autowired
	private LoginController loginController;
	
	
	@Autowired
	private AdmWelcomeController admWelcomeController;
	
	
	@Autowired
	private DisciplineController disciplineController;
	
	@Test
	public void contexLoads() throws Exception {
		assertThat(admController).isNotNull();
		assertThat(loginController).isNotNull();
		assertThat(admWelcomeController).isNotNull();
		assertThat(disciplineController).isNotNull();
	}
}