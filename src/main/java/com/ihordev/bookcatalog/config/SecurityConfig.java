package com.ihordev.bookcatalog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
		auth
		.inMemoryAuthentication()
		.withUser("user").password("password").roles("USER").and()
		.withUser("admin").password("password").roles("USER", "ADMIN");
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
			.formLogin()
				.and()
			.authorizeRequests()
			
				.antMatchers("/authors/create").access("hasRole('ADMIN')")
				.antMatchers(HttpMethod.GET, "/authors/{\\d+}/update").access("hasRole('ADMIN')")
				.antMatchers(HttpMethod.POST,
							 "/authors/update",
						 	 "/authors/delete").access("hasRole('ADMIN')")
				
		        .antMatchers("/books/create").access("hasRole('ADMIN')")
		        .antMatchers(HttpMethod.GET, "/books/{\\d+}/update").access("hasRole('ADMIN')")
		        .antMatchers(HttpMethod.POST,
		        			 "/books/update",
		        			 "/books/delete").access("hasRole('ADMIN')")
		        
		        .anyRequest().authenticated();
    }
	
}
