package com.service.client.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.client.model.Hotel;
import com.service.client.repository.HotelRepository;

@Service("hotelService")
public class HotelService {
	HotelRepository hotelRepository;
	
	public HotelService(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}
	
	@Autowired
	public List<Hotel> findAll(){
		return hotelRepository.findAll();
	}
	
	public Hotel saveHotel(Hotel hotel) {
		return hotelRepository.save(hotel);
	}
	
	public Hotel findHotelByCity(String city) {
		List<Hotel> hotellist = hotelRepository.findByHotelCityOrderByHotelPriceAsc(city);
		if(hotellist.size()==0)
			return null;
		else
			return hotellist.get(0);
	}
}
