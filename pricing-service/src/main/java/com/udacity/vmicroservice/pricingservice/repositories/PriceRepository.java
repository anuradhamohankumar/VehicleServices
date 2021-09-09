package com.udacity.vmicroservice.pricingservice.repositories;

import com.udacity.vmicroservice.pricingservice.entity.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Long> {
}
