package br.pucrs.politecnica.assignments.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.UserRole;

@SpringBootTest
@ActiveProfiles("test")
class LogServiceTest {

    @MockBean 
    private LogRepository logRepository;

    @Autowired
    private LogService logService;

    @Test
    void shouldCreatedLog() {
        Login login = new Login();
        login.setEmail("teste@teste.com");
        login.setName("Teste");
        login.setPassword("admin123");
        login.setUserRole(UserRole.ADMIN);
        login.setCreatedBy("teste");
        login.setCreatedTime(LocalDateTime.now());
        login.setFirstTime(true);

        Log log = new Log();
        log.setLogin(login);
        log.setCreatedBy(login.getCreatedBy());
        log.setCreatedTime(login.getCreatedTime());

        given(this.logRepository.save(Mockito.any())).willReturn(log);
        assertEquals(log, logService.createLog(login));
    }

}