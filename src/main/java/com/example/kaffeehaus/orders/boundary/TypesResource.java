package com.example.kaffeehaus.orders.boundary;

import com.example.kaffeehaus.orders.control.EntityBuilder;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("types")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TypesResource {

    @Inject
    CoffeeShop coffeeShop;

    @Inject
    EntityBuilder entityBuilder;

    @Context
    ResourceContext resourceContext;

    @Context
    HttpServletRequest request;

    @GET
    public JsonArray getCoffeeTypes() {
        return coffeeShop.getCoffeeTypes().stream()
                .map(t -> entityBuilder.buildType(t, request))
                .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add).build();
    }

    @Path("{type}/origins")
    public OriginsResource originsResource() {
        return resourceContext.getResource(OriginsResource.class);
    }

}
