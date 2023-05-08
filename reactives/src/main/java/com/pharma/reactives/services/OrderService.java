package com.pharma.reactives.services;

import com.pharma.reactives.models.CartItem;
import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Order;
import com.pharma.reactives.models.Person;
import com.pharma.reactives.repositories.CartItemRepository;
import com.pharma.reactives.repositories.MedicineRepository;
import com.pharma.reactives.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    @Autowired
    public OrderService(OrderRepository orderRepository, CartItemRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public List<Order> orderList(Person user) {
        return orderRepository.findByUser(user);
    }

    @Transactional
    public void checkout(List<CartItem> cartItems, Order deliveryInfo, Person user, Timestamp placed_at){

        for (CartItem cartItem:
             cartItems) {
            // user info
            Order order = new Order();
            order.setUser(user);
            order.setDelivery_address(deliveryInfo.getDelivery_address());
            order.setCcNumber(passwordEncoder.encode(deliveryInfo.getCcNumber()));
            order.setCcExpiration(deliveryInfo.getCcExpiration());
            order.setCcCVV(deliveryInfo.getCcCVV());
            order.setPlaced_at(placed_at);
            // product info
            Medicine medicine = cartItem.getMedicine();
            order.setMedicine(medicine);
            order.setQuantity(cartItem.getQuantity());
            order.setPrice(cartItem.getSubTotal());
            orderRepository.save(order);
        }

        cartRepository.deleteAll(cartItems);
    }
}
