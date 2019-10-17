package com.service.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.client.model.*;
import com.service.client.repository.CarRentalRepository;

@Service("carRentalService")
public class CarRentalService {
	CarRentalRepository carRentalRepository;
	
	public CarRentalService(CarRentalRepository carRentalRepository) {
		// TODO Auto-generated constructor stub
		this.carRentalRepository = carRentalRepository;
	}
	
	public void saveCarRental(CarRental carRental) {
		carRentalRepository.save(carRental);
	}
	@Autowired
	public List<CarRental> findAll(){
		return carRentalRepository.findAll();
	}
	
	public CarRental findCarByCity(String rentalLoc,String returnLoc) {
		List<CarRental> carlist = carRentalRepository.findByRentalLocAndReturnLocOrderByCarPriceAsc(rentalLoc,returnLoc);
		if(carlist.size()==0)
			return null;
		else
			return carlist.get(0);
	}
	
	public CarRental findBestCarByCity(String rentalLoc,String returnLoc) {
		List<CarRental> carlist = carRentalRepository.findByRentalLocAndReturnLocOrderByCarPriceDesc(rentalLoc,returnLoc);
		if(carlist.size()==0)
			return null;
		else
			return carlist.get(0);
	}

	public List<CarRental> findCarByCityInOrder(String rentalLoc, String returnLoc) {
		return carRentalRepository.findByRentalLocAndReturnLocOrderByCarPriceAsc(rentalLoc,returnLoc);
	}

	public CarRental findCarById(long id) {
		return carRentalRepository.findById(id);
	}
}
