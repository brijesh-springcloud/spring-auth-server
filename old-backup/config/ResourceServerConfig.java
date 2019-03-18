package com.project.auth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/*
 * This Java Class will work as Resource Server...
 * Resources like URLs, users, etc... all those will be configured here
 * You can have various types of way to provide this using inMemory, Database, LDAP, etc...
 * 
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends WebSecurityConfigurerAdapter 
{
	private static final Logger logger = LoggerFactory.getLogger( ResourceServerConfig.class );
	private static final String logPrefix = "<<<<< ResourceServerConfig >>>>> ";

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		logger.info( logPrefix + " Configure HTTPSecurity where you will say what are the URLs can be access "
				               + "with and without authencation & authorization..." );
		http.requestMatchers()
			.antMatchers( "/login", "/oauth/authorize" )
			.and()
			.authorizeRequests().anyRequest().authenticated()
			.and()
			.formLogin()
			.permitAll();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.parentAuthenticationManager( authenticationManager )
			.inMemoryAuthentication()
			.withUser( "pramukh" )
			.password( "swami" )
			.roles( "ADMIN" );
	}
}

