package br.pucrs.politecnica.assignments.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.csrf().disable()
				.authorizeRequests()
					.antMatchers("/login", "/sign-up", "/classes/new" , "/classesList")
					.permitAll()
				.anyRequest()
					.authenticated()
				.and()
				.formLogin()
				.defaultSuccessUrl("/home", true)
				.loginPage("/login")
					.permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/login")
				.permitAll();
				
				
					
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService)
		.passwordEncoder(bCryptPasswordEncoder());
	}

}
