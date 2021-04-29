package com.example.camelproducertest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class ProducerRest extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json);

        rest("/student")
                .produces("application/json")
                .get("/hello/{name}")
                .route().transform().simple("Witam ${header.name} !!!")
                .endRest();

    }
}
