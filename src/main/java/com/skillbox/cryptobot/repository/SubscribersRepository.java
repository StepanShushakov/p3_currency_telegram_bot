package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.entity.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscribersRepository extends JpaRepository<Subscribers, Long> {

    @Query(value = """
select
    *
from
    subscribers
where
    price >= :currentPrice
""", nativeQuery = true)
    List<Subscribers> findAllByPriceGreaterThan(@Param("currentPrice") double currentPrice);
}
