package com.service.client.controller;

import java.util.List;
import static java.lang.Math.min;

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
import com.service.client.service.CarRentalService;

@Controller
public class CarrentalController {
	@Autowired
	CarRentalService carRentalService;

	@RequestMapping(value = { "/carrental-add.html" }, method = RequestMethod.GET)
	public ModelAndView addCarrental() {
		ModelAndView modelAndView = new ModelAndView();
		CarRental carRental = new CarRental();
//		carRentalService.saveCarRental(carRental);
		modelAndView.addObject("carRental", carRental);
		modelAndView.setViewName("carrental-add");
		return modelAndView;
	}

	@RequestMapping(value = { "/carrental-add.html" }, method = RequestMethod.POST)
	public ModelAndView addAirLineToDB(@Valid CarRental carRental, BindingResult bindingResult) {
		carRentalService.saveCarRental(carRental);
		ModelAndView modelAndView = new ModelAndView();
		List<CarRental> carlist = carRentalService.findAll();
		modelAndView.addObject("carlist", carlist);
		modelAndView.setViewName("carrental-list");
		return modelAndView;
	}

	@RequestMapping(value = { "/carrental-list.html" }, method = RequestMethod.GET)
	public ModelAndView listCarrental() {
		ModelAndView modelAndView = new ModelAndView();
		List<CarRental> carlist = carRentalService.findAll();
		modelAndView.addObject("carlist", carlist);
		modelAndView.setViewName("carrental-list");
		return modelAndView;
	}
	
	@RequestMapping(value = "findCarByCity",method = RequestMethod.GET)
	public @ResponseBody CarRental findCarByCity(@RequestParam("rentalLoc") String rentalLoc,@RequestParam("returnLoc") String returnLoc) {		
		CarRental carRental = carRentalService.findCarByCity(rentalLoc,returnLoc);
		return carRental;
	}
	
	@RequestMapping(value = "findBestCarByCity",method = RequestMethod.GET)
	public @ResponseBody CarRental findBestCarByCity(@RequestParam("rentalLoc") String rentalLoc,@RequestParam("returnLoc") String returnLoc) {		
		CarRental carRental = carRentalService.findBestCarByCity(rentalLoc,returnLoc);
		return carRental;
	}

	@RequestMapping(value = "findLastCarByCity", method = RequestMethod.GET)
	public @ResponseBody List<CarRental> findLastCarByCity(@RequestParam("rentalLoc") String rentalLoc,@RequestParam("returnLoc") String returnLoc) {
		List<CarRental> carRentalList= carRentalService.findCarByCityInOrder(rentalLoc,returnLoc);
		int min_n = min(carRentalList.size(), 3);
		if (min_n == 0) {
			return null;
		} else {
			return carRentalList.subList(0, min_n);
		}
	}

	@RequestMapping(value = "findCarById",method = RequestMethod.GET)
	public @ResponseBody CarRental findCarById(@RequestParam("id") long id) {
		CarRental carRental = carRentalService.findCarById(id);
		return carRental;
	}


	public CarrentalController() {
		// TODO Auto-generated constructor stub
	}

}
