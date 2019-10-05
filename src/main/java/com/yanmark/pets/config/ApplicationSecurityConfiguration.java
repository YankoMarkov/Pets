package com.yanmark.pets.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors().disable()
				.csrf()
				.csrfTokenRepository(csrfTokenRepository())
				.and()
				.authorizeRequests()
				.antMatchers("/css/**",
						"/js/**",
						"/images/**",
						"/").permitAll()
				.antMatchers("/users/login",
						"/users/register").anonymous()
				.antMatchers("/animals/**").hasAnyAuthority("ADMIN", "MODERATOR")
				.antMatchers("/home",
						"/users/profile",
						"/users/edit/**",
						"/pets/**",
						"/illnesses/**").authenticated()
				.antMatchers("/users/all",
						"/users/changeRole").hasAuthority("ROOT")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/users/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/home")
				.and()
				.exceptionHandling()
				.accessDeniedPage("/unauthorized")
				.and()
				.logout()
				.logoutSuccessUrl("/");
		
	}
	
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setSessionAttributeName("_csrf");
		return repository;
	}
}
