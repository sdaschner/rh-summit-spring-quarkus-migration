package com.sebastian_daschner.coffee.boundary;

import com.sebastian_daschner.coffee.entity.CoffeeType;
import com.sebastian_daschner.coffee.entity.Origin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.sebastian_daschner.coffee.control.Strings.capitalize;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TypesResource {

    private final CoffeeShop coffeeShop;

    public TypesResource(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @GetMapping("types")
    public List<TypeRepresentation> getCoffeeTypes() {
        return coffeeShop.getCoffeeTypes().stream()
                .map(t -> new TypeRepresentation(capitalize(t.name())))
                .map(r -> r.add(linkTo(methodOn(TypesResource.class).getTypeOrigins(r.getType().toLowerCase())).withRel("origins")))
                .collect(Collectors.toList());
    }

    @GetMapping("types/{type}/origins")
    public List<String> getTypeOrigins(@PathVariable String type) {
        CoffeeType coffeeType = CoffeeType.fromString(type);
        return coffeeShop.getOrigins(coffeeType).stream()
                .map(Origin::getName)
                .collect(Collectors.toList());
    }

}
