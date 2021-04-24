package br.pucrs.politecnica.assignments.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.security.Principal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @MockBean
    private LoginRepository loginRepository;

    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void getLoginTest() throws Exception {
         Principal mockPrincipal = Mockito.mock(Principal.class);
         Mockito.when(mockPrincipal.getName()).thenReturn("login@teste.com");
         Login login = new Login();
         given(this.loginRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(login));
         assertEquals(login, loginService.getLogin(mockPrincipal));
    }

    @Test
    void changePasswordTest() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Login login = new Login();
        login.setPassword("12345");
        Mockito.when(mockPrincipal.getName()).thenReturn("login@teste.com");
        given(this.loginRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(login));
        this.loginService.changePassword("54321", "54321", mockPrincipal);
        final String encryptedPassword = bCryptPasswordEncoder.encode("54321");
        login.setPassword(encryptedPassword);
        assertEquals(login.getPassword(), this.loginService.getLogin(mockPrincipal).getPassword());
    }

    @Test
       void firstAcessFormTest() throws Exception {
    	Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("teste@teste.com");
        Login login = new Login();
        login.setFirstTime(true);
        
        given(this.loginRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(login));
        
        assertEquals(true, this.loginService.firstAcessForm(mockPrincipal));
    }
} 