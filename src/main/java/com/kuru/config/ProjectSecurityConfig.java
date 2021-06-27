package com.kuru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests(
				(requests) -> requests.antMatchers("/myAccount").authenticated().antMatchers("/myBalance")
						.authenticated().antMatchers("/myLoans").authenticated().antMatchers("/myCards").authenticated()
						.antMatchers("/notices").permitAll().antMatchers("/contact").permitAll());
		http.formLogin();
		http.httpBasic();

	}

	/*
	 * NOTE:This configure method is using in-memory authentication and also
	 * NoOppasswordEncoder is deprecated
	 * 
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * auth.inMemoryAuthentication().withUser("admin").password("12345").authorities
	 * ("admin").and(). withUser("user").password("12345").authorities("read").and()
	 * .passwordEncoder(NoOpPasswordEncoder.getInstance()); }
	 */

	// This method uses UserDetailsService for authentication rather than
	// InMemoryAuthentication
	// InMemoryUserDetailsManager is implementation of UserDetails Interface::other
	// interfaces->User,UserDetails and UserDetailsService
	// Calling build gives the user details for the user and user1
	// Spring Security Config always expects password encoder mapped with
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		UserDetails user = User.withUsername("admin").password("12345").authorities("admin").build();
		UserDetails user1 = User.withUsername("user").password("12345").authorities("read").build();
		userDetailsService.createUser(user);
		userDetailsService.createUser(user1);
		auth.userDetailsService(userDetailsService);

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}

/*
 * Config for all requests to be authenticated protected void
 * configure(HttpSecurity http) throws Exception {
 * http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
 * http.formLogin(); http.httpBasic(); }
 */

/*
 * Config for all requests to be allowed without authentication protected void
 * configure(HttpSecurity http) throws Exception {
 * http.authorizeRequests((requests) -> requests.anyRequest().permitAll());
 * http.formLogin(); http.httpBasic(); }
 */

/*
 * cofig all req to be denied protected void configure(HttpSecurity http) throws
 * Exception {
 * 
 * http.authorizeRequests((requests) -> requests.anyRequest().denyAll());
 * http.formLogin(); http.httpBasic(); }
 */
//=========================================================================================//
