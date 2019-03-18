package com.project.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.project.auth.entity.User;
import com.project.auth.repository.UserRepository;

@Service( value = "userDetailsService" )
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger( CustomUserDetailsService.class );
	private static final String logPrefix = "<<<<< CustomUserDetailsService >>>>> "	;	
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String input) 
	{
		logger.info( logPrefix + " Loading user from database for " + input );
		User user = userRepository.findByUsername(input);

		if (user == null)
			throw new BadCredentialsException("Uername/Password are invalid (#CustomUserDetailsService).");

		logger.info( logPrefix + " Loading Authorities/roles/scops from database >>>> " );
		user.getAuthorities().stream().forEach( role -> System.out.print( role ) );
		new AccountStatusUserDetailsChecker().check(user);

		return user;
	}

}
