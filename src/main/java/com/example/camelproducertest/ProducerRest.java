package com.example.camelproducertest;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class ProducerRest extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.xml);

        rest("/student")
                .produces("application/xml")
                .get("/get/{fname}/{lname}")
                .to("direct:student");
                //.route().transform().simple("Witam ${header.name} !!!")
                //.endRest();

        from("direct:student")
                .process(new Processor() {
                    final AtomicLong counter = new AtomicLong();

                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String fname = exchange.getIn().getHeader("fname", String.class);
                        String lname = exchange.getIn().getHeader("lname", String.class);
                        exchange.getIn().setBody(new Student(counter.incrementAndGet(), fname, lname));
                    }
                })
                .log("${body}");


    }
}
