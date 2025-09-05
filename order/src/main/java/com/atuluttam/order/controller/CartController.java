package com.atuluttam.order.controller;

import com.atuluttam.order.model.CartItem;
import com.atuluttam.order.dto.CartItemRequest;
import com.atuluttam.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    @PostMapping
    public ResponseEntity<String> addCart(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody CartItemRequest request) {
       if(!cartService.addToCart(userId, request))
            return ResponseEntity.badRequest().body("Product Out Of Stock or User Not Found or Product Not Found");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
           @RequestHeader("X-User-Id") String userId,
          @PathVariable String productId)
    {
       boolean deleted =  cartService.deleteItemfromCart(userId, productId);
       return deleted ? ResponseEntity.noContent().build()
               : ResponseEntity.notFound().build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-User-Id") String userId)
    {
        return ResponseEntity.ok(cartService.getCart(userId));
    }


}