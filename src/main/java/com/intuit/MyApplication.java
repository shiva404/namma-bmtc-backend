package com.intuit;

import com.intuit.provider.JacksonJsonProvider;
import com.intuit.resource.LocationResource;
import com.intuit.resource.TestResource;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.web.filter.RequestContextFilter;

public class MyApplication  extends ResourceConfig {
    public MyApplication(){
        register(RequestContextFilter.class);
        register(LocationResource.class);
        register(TestResource.class);
        register(JacksonJsonProvider.class);
        register(new LoggingFilter());
    }
}
