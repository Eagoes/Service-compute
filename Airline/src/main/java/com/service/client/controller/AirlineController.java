package com.service.client.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.service.client.model.*;
import com.service.client.service.*;

@Controller
public class AirlineController {
	@Autowired
	private AirlineService airlineService;
	

	@RequestMapping(value={"/airline-add.html"}, method = RequestMethod.GET)
	  public ModelAndView addAirLine(){
		Airline airline = new Airline();
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("airline", airline);
        modelAndView.setViewName("airline-add");
	    return modelAndView;
	  }
	
	@RequestMapping(value={"/airline-add.html"}, method = RequestMethod.POST)
	  public ModelAndView addAirLineToDB(@Valid Airline airline, BindingResult bindingResult){
		airlineService.saveAirline(airline);
		ModelAndView modelAndView = new ModelAndView();
		List<Airline> airlinelist = airlineService.findAll();
		modelAndView.addObject("airlinelist", airlinelist);
		modelAndView.setViewName("airline-list");
		return modelAndView;
	  }
	
	@RequestMapping(value={"/airline-list.html"}, method = RequestMethod.GET)
	public ModelAndView listAirline() {
		ModelAndView modelAndView = new ModelAndView();
		List<Airline> airlinelist = airlineService.findAll();
		modelAndView.addObject("airlinelist", airlinelist);
		modelAndView.setViewName("airline-list");
		return modelAndView;
	}
	
	@RequestMapping(value = "getMinPriceAirline",method = RequestMethod.GET)
	public @ResponseBody Airline getMinPriceAirline(@RequestParam("departureTime") String departureTime,@RequestParam("departure") String departure,@RequestParam("destination") String destination) {		
		Airline airline = airlineService.getMinPriceAirline(departureTime,departure,destination);
		return airline;
	}
	
	@RequestMapping(value = "getMaxPriceAirline",method = RequestMethod.GET)
	public @ResponseBody Airline getMaxPriceAirline(@RequestParam("departureTime") String departureTime,@RequestParam("departure") String departure,@RequestParam("destination") String destination) {		
		Airline airline = airlineService.getMaxPriceAirline(departureTime,departure,destination);
		return airline;
	}
	
	@RequestMapping(value = "getLast3MinPriceAirline", method = RequestMethod.GET)
	public @ResponseBody List<Airline> getLast3MinPriceAirline(@RequestParam("departureTime") String departureTime, @RequestParam("departure") String departure, @RequestParam("destination") String destination) {
		List<Airline> airlineList = airlineService.getLastNMinPriceAirline(departureTime, departure, destination, 3);
		return airlineList;
	}

	@RequestMapping(value = "getAirline", method = RequestMethod.GET)
	public @ResponseBody Airline getAirline(@RequestParam("id") long id) {
		return airlineService.getAirline(id);
	}

	@RequestMapping(value = "getAllAirline", method = RequestMethod.GET)
    public @ResponseBody String getAllAirline() {
	    List<Airline> allAirline = airlineService.findAll();
	    System.out.println("Airline Size: ".concat(String.valueOf(allAirline.size())));
	    String returnString = new String();
        for (Airline airline: allAirline) {
            returnString += airline.toString();
        }
        System.out.println(returnString);
        return returnString;
    }
	
	public AirlineController() {
		// TODO Auto-generated constructor stub
	}

}
