package com.sebastian_daschner.coffee.frontend.views;

import com.sebastian_daschner.coffee.frontend.Order;

import java.util.List;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.util.stream.Collectors.toList;

public class IndexView {

    public void assertPageHeader(String header) {
        $("body > h1").shouldHave(exactText(header));
    }

    public List<Order> getListedOrders() {
        return $$("body > table tr").stream()
                .map(el -> el.findAll("td"))
                .filter(list -> !list.isEmpty())
                .map(list -> new Order(list.get(0).getText(), list.get(1).getText(), list.get(2).getText()))
                .collect(toList());
    }

    public OrderView followCreateOrderLink() {
        $$("a").findBy(text("Create")).click();
        return new OrderView();
    }
}
