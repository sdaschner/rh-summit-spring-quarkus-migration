package com.sebastian_daschner.coffee.boundary;

import com.sebastian_daschner.coffee.control.OrderRepository;
import com.sebastian_daschner.coffee.control.OriginRepository;
import com.sebastian_daschner.coffee.entity.CoffeeType;
import com.sebastian_daschner.coffee.entity.Order;
import com.sebastian_daschner.coffee.entity.Origin;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class CoffeeShop {

    private final OrderRepository orderRepository;
    private final OriginRepository originRepository;

    public CoffeeShop(OrderRepository orderRepository, OriginRepository originRepository) {
        this.orderRepository = orderRepository;
        this.originRepository = originRepository;
    }

    public Set<CoffeeType> getCoffeeTypes() {
        return EnumSet.of(CoffeeType.ESPRESSO, CoffeeType.MELANGE, CoffeeType.FILTER);
    }

    public Set<Origin> getOrigins(CoffeeType type) {
        return originRepository.findByCoffeeTypes(type);
    }

    public Origin getOrigin(String name) {
        return originRepository.findById(name).orElse(null);
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrder(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public void updateOrder(UUID id, Order order) {
        Order managedOrder = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Could not find order"));
        managedOrder.setType(order.getType());
        managedOrder.setOrigin(order.getOrigin());
    }

}
