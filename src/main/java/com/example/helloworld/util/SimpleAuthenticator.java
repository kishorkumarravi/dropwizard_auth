package com.example.helloworld.util;

import com.example.helloworld.domain.User;
import com.google.common.base.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {

	
	
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
    	System.out.println("Authentication...");
        if ("secret".equals(credentials.getPassword())) {
            return Optional.of(new User(credentials.getUsername()));
        }
        return null;
    }


}
	

