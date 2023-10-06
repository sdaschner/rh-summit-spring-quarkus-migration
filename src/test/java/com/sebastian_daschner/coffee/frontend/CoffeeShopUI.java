package com.sebastian_daschner.coffee.frontend;

import com.codeborne.selenide.Configuration;
import com.sebastian_daschner.coffee.frontend.views.IndexView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

public class CoffeeShopUI {

    public void init() {
        Configuration.timeout = 2000;
        open(uriBuilder().path("index.html").build().toString());
    }

    private UriComponentsBuilder uriBuilder() {
        String host = System.getProperty("coffee-shop.test.host", "localhost");
        String port = System.getProperty("coffee-shop.test.port", "8080");
        return UriComponentsBuilder.fromUriString("http://{host}:{port}/")
                .uriVariables(Map.of("host", host, "port", port));
    }

    public IndexView index() {
        open(uriBuilder().path("index.html").build().toString());
        return new IndexView();
    }

}
