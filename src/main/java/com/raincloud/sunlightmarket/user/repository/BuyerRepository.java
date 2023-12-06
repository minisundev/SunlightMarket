package com.raincloud.sunlightmarket.user.repository;

import com.raincloud.sunlightmarket.user.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer,Long> {

}
