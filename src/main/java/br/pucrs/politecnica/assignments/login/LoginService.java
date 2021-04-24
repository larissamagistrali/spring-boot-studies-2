package br.pucrs.politecnica.assignments.login;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private LoginRepository loginRepository;
	
	public Login getLogin(Principal principal) {
		try {
			Optional<Login> l = this.loginRepository.findByEmail(principal.getName());
			if(l.isPresent()) return l.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean firstAcessForm(Principal principal) {
		try{
			Optional<Login> l = this.loginRepository.findByEmail(principal.getName());
			if(l.isPresent() && l.get().isFirstTime()) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public void changePassword(String password, String confirmation, Principal principal) throws Exception {
		if (password.equals(confirmation)) {
			Optional<Login> l = this.loginRepository.findByEmail(principal.getName());
			final String encryptedPassword = bCryptPasswordEncoder.encode(password);
			
			l.get().setPassword(encryptedPassword);
			l.get().setFirstTime(false);
			
			loginRepository.save(l.get());
			return;
		}
		throw new Exception();
	}

}