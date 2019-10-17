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

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

import com.service.client.model.*;
import com.service.client.service.AttractionService;

@Controller
public class AttractionController {
	@Autowired
	AttractionService attractionService;

	@RequestMapping(value={"/attraction-add.html"}, method = RequestMethod.GET)
	public ModelAndView addAttraction() {
		ModelAndView modelAndView = new ModelAndView();
		Attraction attraction = new Attraction();
		modelAndView.addObject("attraction", attraction);
		modelAndView.setViewName("attraction-add");
		return modelAndView;
	}
	
	@RequestMapping(value={"/attraction-add.html"}, method = RequestMethod.POST)
	  public ModelAndView addAttractionToDB(@Valid Attraction attraction, BindingResult bindingResult) {
		attractionService.saveAttraction(attraction);
		ModelAndView modelAndView = new ModelAndView();
		List<Attraction> attractionlist = attractionService.findall();
		modelAndView.addObject("attractionlist",attractionlist);
		modelAndView.setViewName("attraction-list");
		return modelAndView;
	  }
	
	@RequestMapping(value={"/attraction-list.html"}, method = RequestMethod.GET)
	public ModelAndView listAttraction() {
		ModelAndView modelAndView = new ModelAndView();
		List<Attraction> attractionlist = attractionService.findall();
		modelAndView.addObject("attractionlist",attractionlist);
		modelAndView.setViewName("attraction-list");
		return modelAndView;
	}
	
	@RequestMapping(value = "findAttractionByCity",method = RequestMethod.GET)
	public @ResponseBody Attraction findAttractionByCity(@RequestParam("city") String city) {		
		Attraction attraction = attractionService.findAttractionByCity(city);
		return attraction;
	}
	
	@RequestMapping(value = "findBestAttractionByCity",method = RequestMethod.GET)
	public @ResponseBody Attraction findBestAttractionByCity(@RequestParam("city") String city) {		
		Attraction attraction = attractionService.findBestAttractionByCity(city);
		return attraction;
	}

	@RequestMapping(value = "findBest3AttractionByCity", method = RequestMethod.GET)
	public @ResponseBody List<Attraction> findBest3AttractionByCity(@RequestParam("city") String city) {
		List<Attraction> attractionList = attractionService.findAllAttractionByOrder(city);
		int min_n = min(attractionList.size(), 3);
		if (min_n == 0) {
			return null;
		}
		else {
			return attractionList.subList(0, min_n);
		}
	}

	@RequestMapping(value = "getAttraction", method = RequestMethod.GET)
	public @ResponseBody Attraction getAttraction(@RequestParam("id") long id) {
		return attractionService.getAttraction(id);
	}
	
	public AttractionController() {
		// TODO Auto-generated constructor stub
	}

}
