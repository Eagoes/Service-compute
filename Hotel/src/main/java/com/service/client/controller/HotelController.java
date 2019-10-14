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

import com.service.client.model.Hotel;
import com.service.client.service.HotelService;

@Controller
public class HotelController {
	
	@Autowired
	HotelService hotelService;

	@RequestMapping(value={"/hotel-add.html"}, method = RequestMethod.GET)
	public ModelAndView showHotelAdd() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("hotel",new Hotel());
		modelAndView.setViewName("hotel-add");
		return modelAndView;
	}
	
	@RequestMapping(value={"/hotel-add.html"}, method = RequestMethod.POST)
	public ModelAndView addHotel(@Valid Hotel hotel, BindingResult bind) {
		ModelAndView modelAndView = new ModelAndView();
		hotelService.saveHotel(hotel);
		List<Hotel> hotellist = hotelService.findAll();
		modelAndView.addObject("hotellist",hotellist);
		modelAndView.setViewName("hotel-list");
		return modelAndView;
	}
	
	@RequestMapping(value={"/hotel-list.html"}, method = RequestMethod.GET)
	public ModelAndView listHotel() {
		ModelAndView modelAndView = new ModelAndView();
		List<Hotel> hotellist = hotelService.findAll();
		modelAndView.addObject("hotellist",hotellist);
		modelAndView.setViewName("hotel-list");
		return modelAndView;
	}
	public HotelController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value = "findHotelByCity",method = RequestMethod.GET)
	public @ResponseBody Hotel findHotelByCity(@RequestParam("city") String city) {		
		Hotel hotel = hotelService.findHotelByCity(city);
		return hotel;
	}
	
	@RequestMapping(value = "findBestHotelByCity",method = RequestMethod.GET)
	public @ResponseBody Hotel findBestHotelByCity(@RequestParam("city") String city) {		
		Hotel hotel = hotelService.findBestHotelByCity(city);
		return hotel;
	}

	@RequestMapping(value = "findLastHotelByCity",method = RequestMethod.GET)
	public @ResponseBody List<Hotel> findLastHotelByCity(@RequestParam("city") String city) {
		List<Hotel> hotelList = hotelService.findHotelByCityInOrder(city);
		int min_n = min(hotelList.size(), 3);
		if (min_n == 0) {
			return null;
		} else {
			return hotelList.subList(0, min_n);
		}
	}

	@RequestMapping(value = "findHotelById",method = RequestMethod.GET)
	public @ResponseBody Hotel findHotelById(@RequestParam("id") long id) {
		return hotelService.findHotelById(id);
	}
}
