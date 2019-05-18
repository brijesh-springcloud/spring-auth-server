package com.project.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class JWTAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Value("${check-user-scopes}")
	private Boolean checkUserScopes;
	
	@Autowired
	JwtKeyStoreConfig jwtKeyStoreConfig;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Bean
	public TokenStore tokenStore() {
		return jwtKeyStoreConfig.tokenStore();
	}
	
	@Bean
    public DefaultTokenServices tokenServices(final TokenStore tokenStore, final ClientDetailsService clientDetailsService) 
	{
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAuthenticationManager(this.authenticationManager);
        return tokenServices;
    }
	
	@Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception 
	{
        clients.jdbc( this.dataSource ).passwordEncoder( passwordEncoder );
    }

	@Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) 
	{
        oauthServer.passwordEncoder( this.passwordEncoder )
        			.tokenKeyAccess("permitAll()")
        			.checkTokenAccess("isAuthenticated()");
    }
	
	@Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) 
	{
		 endpoints.authenticationManager(this.authenticationManager)
                	.accessTokenConverter( jwtKeyStoreConfig.accessTokenConverter() )
                		.tokenStore( jwtKeyStoreConfig.tokenStore() )
		 					.userDetailsService( userDetailsService );
		 
		 // Need more understanding why this one needed.
		 if (checkUserScopes)
				endpoints.requestFactory(requestFactory());
        
    }
	
	
	
	@Bean
	public OAuth2RequestFactory requestFactory() {
		CustomOauth2RequestFactory requestFactory = new CustomOauth2RequestFactory(clientDetailsService);
		requestFactory.setCheckUserScopes(true);
		return requestFactory;
	}
}
