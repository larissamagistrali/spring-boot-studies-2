package applicationtests;



import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.UserRole;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        Login login = new Login();
        login.setCreatedBy("teste");
        login.setCreatedTime(LocalDateTime.now());
        login.setEmail("teste@teste.com");
        login.setFirstTime(false);
        login.setId(1L);
        login.setName("Teste");
        login.setPassword("teste");
        login.setUserRole(UserRole.ADMIN);
        
        return new InMemoryUserDetailsManager(Arrays.asList(
                login
        ));
    }
}
