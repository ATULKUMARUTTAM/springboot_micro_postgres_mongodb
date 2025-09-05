package com.atuluttam.order.service;


import com.atuluttam.order.model.OrderItems;
import com.atuluttam.order.repository.OrderRepository;
import com.atuluttam.order.dto.OrderResponse;
import com.atuluttam.order.model.OrderStatus;
import com.atuluttam.order.dto.OrderItemDTO;
import com.atuluttam.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.atuluttam.order.model.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class OrderService {


    private final CartService cartService;

    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId)
    {


        List<CartItem> cartItems = cartService.getCart(userId);

        if(cartItems.isEmpty())
        {
            return Optional.empty();
        }


        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create Order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItems> orderItems = cartItems.stream()
                .map(item -> new OrderItems(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        // Clear the cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {

        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                              orderItem.getId(),
                                orderItem.getProductId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        ))
                        .toList(),
                order.getCreatedAt()
        );
    }
}
