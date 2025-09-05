package com.atuluttam.order.service;

//import com.atuluttam.M2_Ecom.model.Product;
//import com.atuluttam.M2_Ecom.model.User;
//import com.atuluttam.M2_Ecom.repository.ProductRepository;
//import com.atuluttam.M2_Ecom.repository.UserRepository;
import com.atuluttam.order.repository.CartItemRepository;
import com.atuluttam.order.dto.CartItemRequest;
import com.atuluttam.order.model.CartItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {


    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest request)
    {

    CartItem existingCartItem  = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
    if(existingCartItem !=null)
    {
        existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
        existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
        cartItemRepository.save(existingCartItem);
    }
    else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
    }
    return true;
    }

    public boolean deleteItemfromCart(String userId, String productId) {

      CartItem cartItem =   cartItemRepository.findByUserIdAndProductId(userId, productId);
       if(cartItem != null)
       {
           cartItemRepository.delete(cartItem);
           return true;
       }
        return false;
    }

    public List<CartItem> getCart(String userId) {

        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
         cartItemRepository.deleteByUserId(userId);
    }
}
