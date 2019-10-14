package com.service.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.client.model.*;

@Repository("hotelRepository")
public interface HotelRepository extends JpaRepository<Hotel, Long> {

	public List<Hotel> findByHotelCityOrderByHotelPriceAsc(String hotelCity);
}