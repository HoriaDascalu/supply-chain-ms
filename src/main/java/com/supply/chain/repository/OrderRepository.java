package com.supply.chain.repository;

import com.supply.chain.model.Address;
import com.supply.chain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("select o from Order o where (o.user.username = :username or o.status = 'NEW') and o.isDeleted = false")
    List<Order> findByUsernameOrStatusNew(String username);
    @Query("select o from Order o where o.user.username = :username and o.isDeleted = false")
    List<Order> findByUsername(String username);

    List<Order> findAllByIsDeletedIsFalse();

    @Query("select max(o.orderNumber) from Order o")
    Integer getOrderNumberMaxValue();

    @Query("select distinct o.deliveryAddress from Order o where o.user.username = :username and o.isDeleted = false")
    List<Address> findAllAddressesByUsername(String username);
}
