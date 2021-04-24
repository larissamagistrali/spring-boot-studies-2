package applicationtests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.pucrs.politecnica.assignments.AssignmentsApplication;

import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AssignmentsApplication.class)
@ActiveProfiles("test")
public class ApplicationTest {

    @Test
    public void contextLoads() {
    }

}
