package com.example.helloworld.resources;

import com.example.helloworld.api.Saying;
import com.google.common.base.Optional;

import io.dropwizard.auth.Auth;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

//Tells Jersey that this resource is accessible at the URI
@Path("/hello-world")
//Lets Jersey�s content negotiation code know that this resource produces 
// representations which are application/json
@Produces(MediaType.APPLICATION_JSON)


public class HelloWorldResource {
	
    private final String template;
    private final String defaultName;
    // An AtomicLong provides us with a cheap, 
    //thread-safe way of generating unique(ish) IDs.
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    
    
    @GET
    @Timed  // Dropwizard automatically records the duration and rate of its 
            //invocations as a Metrics Timer.
    public Saying sayHello(@Auth @QueryParam("name") Optional<String> name) {
    	System.out.println("controller");
        final String value = String.format(template, name.or(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }
}