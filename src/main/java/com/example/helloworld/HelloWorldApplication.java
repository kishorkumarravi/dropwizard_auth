package com.example.helloworld;


import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;

import com.example.helloworld.resources.HelloWorldResource;

import org.eclipse.jetty.security.authentication.BasicAuthenticator;

import com.example.helloworld.domain.UserDetails;
import com.example.helloworld.health.TemplateHealthCheck;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) {
    	

    	
    	environment.jersey().register(AuthFactory.binder(
				new BasicAuthFactory<UserDetails>(
						new BasicAuthenticator(configuration.getUserName(),
								configuration.getPassword()),
						"SECURITY REALM",
						UserDetails.class)));
    	
    	
    	//Create new HelloWorld Resource & add it to application's Jersey environment
    	final HelloWorldResource resource = new HelloWorldResource(
    	        configuration.getTemplate(),
    	        configuration.getDefaultName()
    	    );
    	environment.jersey().register(resource);
    	
    	// Adding template HealthCheck to environment
    	final TemplateHealthCheck healthCheck =
    	        new TemplateHealthCheck(configuration.getTemplate());
    	    environment.healthChecks().register("template", healthCheck);
    	    environment.jersey().register(resource);
    }

}