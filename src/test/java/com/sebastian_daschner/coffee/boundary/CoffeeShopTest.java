package com.sebastian_daschner.coffee.boundary;

import com.sebastian_daschner.coffee.control.OrderRepository;
import com.sebastian_daschner.coffee.control.OriginRepository;
import com.sebastian_daschner.coffee.entity.Order;
import com.sebastian_daschner.coffee.entity.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static com.sebastian_daschner.coffee.entity.CoffeeType.ESPRESSO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CoffeeShopTest {

    private CoffeeShop coffeeShop;
    private OrderRepository orderRepository;

    @Test
    void updateOrderShouldUpdateFields() {
        Order managedOrder = new Order();
        when(orderRepository.findById(any())).thenReturn(Optional.of(managedOrder));

        Order order = new Order();
        Origin colombia = new Origin("Colombia");
        order.setOrigin(colombia);
        order.setType(ESPRESSO);
        UUID uuid = UUID.randomUUID();
        coffeeShop.updateOrder(uuid, order);

        assertThat(managedOrder.getOrigin()).isEqualTo(colombia);
        assertThat(managedOrder.getType()).isEqualTo(ESPRESSO);

        verify(orderRepository).findById(uuid);
    }

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        OriginRepository originRepository = mock(OriginRepository.class);
        coffeeShop = new CoffeeShop(orderRepository, originRepository);
    }

}