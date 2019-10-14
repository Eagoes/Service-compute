package com.service.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.client.model.*;

@Repository("airlineRepository")
public interface AirlineRepository extends JpaRepository<Airline, Long> {
	void deleteById(long id);

	public Airline findById(long id);

	public List<Airline> findByDepartureTimeAndDepartureAndDestinationOrderByAirlinePriceAsc(String departureTime,String departure,String destination);
	
	public List<Airline> findByDepartureTimeAndDepartureAndDestinationOrderByAirlinePriceDesc(String departureTime,String departure,String destination);
	
}
