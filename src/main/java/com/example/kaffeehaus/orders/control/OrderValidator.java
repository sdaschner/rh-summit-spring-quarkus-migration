package com.example.kaffeehaus.orders.control;

import com.example.kaffeehaus.orders.boundary.CoffeeShop;
import com.example.kaffeehaus.orders.entity.CoffeeType;
import com.example.kaffeehaus.orders.entity.Origin;
import com.example.kaffeehaus.orders.entity.ValidOrder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@ApplicationScoped
public class OrderValidator implements ConstraintValidator<ValidOrder, JsonObject> {

    @Inject
    CoffeeShop coffeeShop;

    public void initialize(ValidOrder constraint) {
        // nothing to do
    }

    public boolean isValid(JsonObject json, ConstraintValidatorContext context) {

        final String type = json.getString("type", null);
        final String origin = json.getString("origin", null);

        if (type == null || origin == null)
            return false;

        final CoffeeType coffeeType = coffeeShop.getCoffeeTypes().stream()
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findAny().orElse(null);

        final Origin coffeeOrigin = coffeeShop.getOrigin(origin);

        if (coffeeOrigin == null || coffeeType == null)
            return false;

        return coffeeOrigin.getCoffeeTypes().contains(coffeeType);
    }

}
