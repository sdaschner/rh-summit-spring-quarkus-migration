package com.sebastian_daschner.coffee.boundary;

import com.sebastian_daschner.coffee.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    private final CoffeeShop coffeeShop;

    @Value("${coffee.greeting}")
    String greeting;

    public IndexController(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @GetMapping({"/", "index.html"})
    public String index(Model model) {
        List<Order> orders = coffeeShop.getOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("greeting", greeting);
        return "index";
    }

}
