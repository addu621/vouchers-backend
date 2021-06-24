package com.example.server.repositories;

import com.example.server.entities.Cart;
import com.example.server.entities.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem,Long> {
    List<CartItem> findByCartId(long cartId);
    List<CartItem> findByCartIdAndVoucherId(long cartId,long voucherId);
    List<CartItem> deleteByCartIdAndVoucherId(long cartId,long voucherId);
}
