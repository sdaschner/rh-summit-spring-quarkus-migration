package com.sebastian_daschner.coffee.boundary;

import com.sebastian_daschner.coffee.entity.CoffeeType;
import com.sebastian_daschner.coffee.entity.Order;
import com.sebastian_daschner.coffee.entity.Origin;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.UUID;

@Controller
public class OrderCoffeeController {

    private final CoffeeShop coffeeShop;

    public OrderCoffeeController(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @GetMapping("order.html")
    public String index(Model model) {
        Set<CoffeeType> types = coffeeShop.getCoffeeTypes();
        model.addAttribute("types", types);
        return "order";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, value = "order.html")
    public String submit(@RequestParam(value = "type", defaultValue = "") String type,
                         @RequestParam(value = "origin", defaultValue = "") String originName,
                         Model model) {
        CoffeeType coffeeType = CoffeeType.fromString(type);
        Origin origin = new Origin(originName);
        Order order = new Order(UUID.randomUUID(), coffeeType, origin);

        if (!orderIsValid(order)) {
            Set<CoffeeType> types = coffeeShop.getCoffeeTypes();
            model.addAttribute("failed", true);
            model.addAttribute("types", types);
            return "order";
        }

        coffeeShop.createOrder(order);

        return "redirect:/index.html";
    }

    private boolean orderIsValid(Order order) {
        if (order.getType() == null || order.getOrigin() == null)
            return false;
        Origin origin = coffeeShop.getOrigin(order.getOrigin().getName());
        return origin != null;
    }

}
