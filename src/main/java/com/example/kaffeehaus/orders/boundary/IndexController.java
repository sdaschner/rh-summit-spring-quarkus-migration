package com.example.kaffeehaus.orders.boundary;

import com.example.kaffeehaus.orders.entity.Order;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateExtension;
import io.quarkus.qute.TemplateInstance;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class IndexController {

    @Inject
    CoffeeShop coffeeShop;

    @Location("index.html")
    Template index;

    @ConfigProperty(name = "kaffeehaus.greeting")
    String greeting;

    @GET
    public Response root() {
        return Response.seeOther(URI.create("/index.html")).build();
    }

    @GET
    @Path("index.html")
    public TemplateInstance index() {
        List<Order> orders = coffeeShop.getOrders();
        return index.data("orders", orders, "greeting", greeting);
    }

    @TemplateExtension(namespace = "instant")
    static String format(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd MMM uuuu 'at' HH:mm:ss"));
    }

}
