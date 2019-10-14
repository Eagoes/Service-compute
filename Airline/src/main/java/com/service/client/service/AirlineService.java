package com.service.client.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.client.model.Airline;
import com.service.client.repository.*;

import static java.lang.Math.min;

@Service("airlineService")
public class AirlineService {
	private AirlineRepository airlineRepository;
	
	@Autowired
	public AirlineService(AirlineRepository airlineReporsitory) {
		this.airlineRepository = airlineReporsitory;
	}

	public Airline saveAirline(Airline airline) {
		return airlineRepository.save(airline);
	}
	
	public void delete(Long id) {
		airlineRepository.deleteById(id);
	}
	
	public Airline getMinPriceAirline(String departureTime,String departure,String destination) {
		List<Airline> airlinesList =  airlineRepository.findByDepartureTimeAndDepartureAndDestinationOrderByAirlinePriceAsc(departureTime,departure,destination);
		if(airlinesList.size()==0)
			return null;
		else
			return airlinesList.get(0);
	}

	public List<Airline> getLastNMinPriceAirline(String departureTime,String departure,String destination, int n) {
		List<Airline> airlinesList =  airlineRepository.findByDepartureTimeAndDepartureAndDestinationOrderByAirlinePriceAsc(departureTime,departure,destination);
		if(airlinesList.size()==0)
			return null;
		else {
			int min_n = min(airlinesList.size(), n);
			List<Airline> returnList = airlinesList.subList(0, min_n);
			return returnList;
		}
	}
	
	public Airline getMaxPriceAirline(String departureTime,String departure,String destination) {
		List<Airline> airlinesList =  airlineRepository.findByDepartureTimeAndDepartureAndDestinationOrderByAirlinePriceDesc(departureTime,departure,destination);
		if(airlinesList.size()==0)
			return null;
		else
			return airlinesList.get(0);
	}

	public Airline getAirline(long id) {
		return airlineRepository.findById(id);
	}
	
	public void update(Airline airline) {
		airline.setStatus(1);
		airlineRepository.save(airline);
	}
	
	public List<Airline> findAll() {
		return airlineRepository.findAll();
	}
	
	public AirlineService() {
		// TODO Auto-generated constructor stub
	}
	
	 
}
