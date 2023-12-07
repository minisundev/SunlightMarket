package com.raincloud.sunlightmarket.review.toseller.repository;

import com.raincloud.sunlightmarket.review.toseller.entity.ReviewToSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewToSellerRepository extends JpaRepository<ReviewToSeller, Long> {

}
