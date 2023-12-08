package com.raincloud.sunlightmarket.review.tobuyer.repository;

import com.raincloud.sunlightmarket.review.tobuyer.entity.ReviewToBuyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewToBuyerRepository extends JpaRepository<ReviewToBuyer, Long> {

}
