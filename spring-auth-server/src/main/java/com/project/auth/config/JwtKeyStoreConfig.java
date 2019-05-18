package com.project.auth.config;

import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.util.Assert;

@Configuration
@EnableConfigurationProperties(AuthorizationServerProperties.class)
public class JwtKeyStoreConfig implements ApplicationContextAware 
{

	private final AuthorizationServerProperties authorization;
	private ApplicationContext context;

	@Autowired
	public JwtKeyStoreConfig(AuthorizationServerProperties authorization) {
		this.authorization = authorization;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
	
	public TokenStore tokenStore() 
	{
		return new JwtTokenStore(accessTokenConverter());
	}
	
	public JwtAccessTokenConverter accessTokenConverter() 
	{
		Assert.notNull(this.authorization.getJwt().getKeyStore(), "keyStore cannot be null");
		Assert.notNull(this.authorization.getJwt().getKeyStorePassword(), "keyStorePassword cannot be null");
		Assert.notNull(this.authorization.getJwt().getKeyAlias(), "keyAlias cannot be null");

		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		Resource keyStore = this.context.getResource(this.authorization.getJwt().getKeyStore());
		char[] keyStorePassword = this.authorization.getJwt().getKeyStorePassword().toCharArray();
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keyStore, keyStorePassword);

		String keyAlias = this.authorization.getJwt().getKeyAlias();
		char[] keyPassword = Optional.ofNullable(
				this.authorization.getJwt().getKeyPassword())
				.map(String::toCharArray).orElse(keyStorePassword);
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair(keyAlias, keyPassword));

		return converter;
	}

}
