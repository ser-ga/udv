package ru.udvybor.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import ru.udvybor.web.rest.DocumentRestService;

public class RestApplication extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public RestApplication(){
        register(RequestContextFilter.class);
        register(DocumentRestService.class);
        register(JacksonFeature.class);
    }
}
