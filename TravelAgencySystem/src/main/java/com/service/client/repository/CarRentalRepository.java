package com.service.client.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.client.model.*;

@Repository("carRentalRepository")
public interface CarRentalRepository extends JpaRepository<CarRental, Long> {

	public List<CarRental> findByRentalLocAndReturnLocOrderByCarPriceAsc(String rentalLoc,String returnLoc);
}
