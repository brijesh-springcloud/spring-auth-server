package com.project.auth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

//Configuration not in use
public class AuthServerConfigurations extends WebSecurityConfigurerAdapter
										implements AuthorizationServerConfigurer 
{
	private static final Logger logger = LoggerFactory.getLogger( AuthServerConfigurations.class );
	private static final String logPrefix = "<<<<< AuthServerConfigurations >>>>> ";
	
	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		logger.info( logPrefix + " Configure HTTPSecurity where you will say what are the URLs can be access "
				+ "with and without authencation & authorization..." );
		
		http
			.requestMatchers()
			.antMatchers( "/login", "/oauth/authorize" )
			.and()
			.authorizeRequests()
				.anyRequest().authenticated()
			.and()
			.formLogin()
			.permitAll();
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		
		security.checkTokenAccess("permitAll()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory().withClient( "registeredApp" )
							.secret( passwordEncoder.encode("password") )
							.scopes( "WEB" )
							.authorizedGrantTypes( "authorization_code","password","client_credentials","implicit","refresh_token");

	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		endpoints.authenticationManager( this.authenticationManager );
	}

	
}
