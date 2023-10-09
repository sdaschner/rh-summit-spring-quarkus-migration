package com.example.kaffeehaus.orders.control;

import com.example.kaffeehaus.orders.boundary.OrdersResource;
import com.example.kaffeehaus.orders.boundary.TypesResource;
import com.example.kaffeehaus.orders.entity.CoffeeType;
import com.example.kaffeehaus.orders.entity.Order;
import com.example.kaffeehaus.orders.entity.Origin;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.*;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static com.example.kaffeehaus.orders.control.LinkBuilder.baseUriBuilder;
import static com.example.kaffeehaus.orders.control.StringExtensions.capitalize;

@ApplicationScoped
public class EntityBuilder {

    public JsonArray buildOrders(List<Order> orders, HttpServletRequest request) {
        return orders.stream()
                .map(o -> buildOrderTeaser(o, request))
                .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build();
    }

    private JsonObject buildOrderTeaser(Order order, HttpServletRequest request) {
        return Json.createObjectBuilder()
                .add("_self", baseUriBuilder(request)
                        .path(OrdersResource.class)
                        .path(OrdersResource.class, "getOrder")
                        .build(order.getId())
                        .toString())
                .add("type", capitalize(order.getType().name()))
                .add("origin", order.getOrigin().getName())
                .build();
    }

    public JsonObject buildOrder(Order order) {
        return Json.createObjectBuilder()
                .add("type", capitalize(order.getType().name()))
                .add("origin", order.getOrigin().getName())
                .add("created", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.from(order.getCreated().atZone(ZoneId.systemDefault()))))
                .build();
    }

    public Order buildOrder(JsonObject json) {
        final CoffeeType type = CoffeeType.fromString(json.getString("type"));
        final Origin origin = new Origin(json.getString("origin"));

        return new Order(UUID.randomUUID(), type, origin);
    }

    public JsonObject buildOrigin(HttpServletRequest request, Origin origin, CoffeeType type) {
        final URI ordersUri = baseUriBuilder(request).path(OrdersResource.class).build();
        return Json.createObjectBuilder()
                .add("origin", origin.getName())
                .add("_actions", Json.createObjectBuilder()
                        .add("order-coffee", Json.createObjectBuilder()
                                .add("method", "POST")
                                .add("href", ordersUri.toString())
                                .add("fields", Json.createArrayBuilder()
                                        .add(Json.createObjectBuilder()
                                                .add("name", "type")
                                                .add("value", capitalize(type.name())))
                                        .add(Json.createObjectBuilder()
                                                .add("name", "origin")
                                                .add("type", origin.getName()))
                                )))
                .build();
    }

    public JsonObjectBuilder buildType(CoffeeType type, HttpServletRequest request) {
        return Json.createObjectBuilder()
                .add("type", capitalize(type.name()))
                .add("_links", Json.createObjectBuilder()
                        .add("origins", baseUriBuilder(request)
                                .path(TypesResource.class)
                                .path(TypesResource.class, "originsResource")
                                .build(type).toString().toLowerCase()));
    }

}
