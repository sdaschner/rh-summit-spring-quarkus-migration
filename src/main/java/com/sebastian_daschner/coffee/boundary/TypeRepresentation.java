package com.sebastian_daschner.coffee.boundary;

import org.springframework.hateoas.RepresentationModel;

public class TypeRepresentation extends RepresentationModel<TypeRepresentation> {

    private final String type;

    public TypeRepresentation(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
