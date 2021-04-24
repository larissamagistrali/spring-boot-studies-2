package br.pucrs.politecnica.assignments.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.pucrs.politecnica.assignments.login.Login;

@Service
public class LogService {
	
	@Autowired
	private LogRepository logRepository;
	
	public Log createLog(Login login) {
		Log log = new Log();
		log.setLogin(login);
		log.setCreatedBy(login.getCreatedBy());
		log.setCreatedTime(login.getCreatedTime());
		return logRepository.save(log);
	}

}
