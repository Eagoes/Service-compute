package com.service.client.controller;

import java.util.List;
import static java.lang.Math.min;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.service.client.model.Guide;
import com.service.client.service.GuideService;

@Controller
public class GuideController {
	@Autowired
	GuideService guideService;
	
	@RequestMapping(value={"/guide-add.html"}, method = RequestMethod.GET)
	public ModelAndView showGuideAdd() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("guide",new Guide());
		modelAndView.setViewName("guide-add");
		return modelAndView;
	}
	
	@RequestMapping(value={"/guide-add.html"}, method = RequestMethod.POST)
	public ModelAndView createGuide(@Valid Guide guide,BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		guideService.saveGuide(guide);
		List<Guide> guidelist = guideService.findAll();
		modelAndView.addObject("guidelist",guidelist);
		modelAndView.setViewName("guide-list");
		return modelAndView;
		
	}
	
	@RequestMapping(value={"/guide-list.html"}, method = RequestMethod.GET)
	public ModelAndView listGuide() {
		ModelAndView modelAndView = new ModelAndView();
		List<Guide> guidelist = guideService.findAll();
		modelAndView.addObject("guidelist",guidelist);
		modelAndView.setViewName("guide-list");
		return modelAndView;
	}
	
	@RequestMapping(value = "findGuideByAttrId",method = RequestMethod.GET)
	public @ResponseBody Guide findGuideByAttrId(@RequestParam("attractionId") Integer attractionId) {		
		Guide guide = guideService.findGuideByAttractionId(attractionId.intValue());
		return guide;
	}

	@RequestMapping(value = "findLastGuideByAttrId",method = RequestMethod.GET)
	public @ResponseBody List<Guide> findLastGuideByAttrId(@RequestParam("attractionId") long attractionId) {
		List<Guide> guideList = guideService.findGuideByAttractionIdInOrder(attractionId);
		int min_n = min(guideList.size(), 3);
		if (min_n == 0) {
			return null;
		} else {
			return guideList.subList(0, min_n);
		}
	}

	@RequestMapping(value = "findGuideById",method = RequestMethod.GET)
	public @ResponseBody Guide findGuideById(@RequestParam("id") long id) {
		return guideService.findGuideById(id);
	}
}
