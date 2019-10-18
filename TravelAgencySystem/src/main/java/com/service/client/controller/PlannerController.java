package com.service.client.controller;

import java.nio.charset.Charset;
import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.service.client.model.*;
import com.service.client.service.*;

@Controller
public class PlannerController {
	@Autowired
	private AgentServiceService agentServiceService;
	
	public PlannerController() {
		// TODO Auto-generated constructor stub
		
	}
	
    @RequestMapping(value="/usr-query.html", method = RequestMethod.GET)
    public ModelAndView showUsrQuery(){
        ModelAndView modelAndView = new ModelAndView();
        Userrequest userrequest = new Userrequest();
        modelAndView.addObject("userrequest", userrequest);
        modelAndView.setViewName("usr-query");
        return modelAndView;
    }


	@RequestMapping(value = "/result-list.html", method = RequestMethod.GET)
    public ModelAndView postUsrQuery(@Valid Userrequest userrequest, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		List<AgentService> agentServices = agentServiceService.findAll();
		RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

		List<Airline> depAirlineList = null;
		List<Airline> retAirlineList = null;
		List<Attraction> attractionList = null;
		List<CarRental> carRentalList = null;
		List<Hotel> hotelList = null;
		Map<Attraction, Guide> attractionGuideMap = new HashMap<>();
		List<QueryResult> resultList = new ArrayList<>();
		float cost = 0;

		for (AgentService as : agentServices) {
			if (as.getServiceType().equals("Airline")) {
				String planOption = "getLast3MinPriceAirline";

				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?departureTime={departureTime}&departure={departure}&destination={destination}";
				Map<String, String> map = new HashMap<String, String>();
				map.put("departureTime", userrequest.getDeparture_time());
				map.put("departure", userrequest.getDepature_point());
				map.put("destination", userrequest.getAim());
//				System.out.println(serviceURL);
//				System.out.println(userrequest.getDeparture_time());
//				System.out.println(userrequest.getDepature_point());
//				System.out.println(userrequest.getAim());
				depAirlineList = Arrays.asList(restTemplate.getForObject(serviceURL, Airline[].class, map));
				if (depAirlineList == null || depAirlineList.size() == 0) break;

				Map<String, String> returnmap = new HashMap<String, String>();
				returnmap.put("departureTime", userrequest.getReturn_time());
				returnmap.put("departure", userrequest.getAim());
				returnmap.put("destination", userrequest.getDepature_point());
//				System.out.println(serviceURL);
//				System.out.println(userrequest.getReturn_time());
//				System.out.println(userrequest.getAim());
//				System.out.println(userrequest.getDepature_point());
				retAirlineList = Arrays.asList(restTemplate.getForObject(serviceURL, Airline[].class, returnmap));
				if (retAirlineList == null || retAirlineList.size() == 0) break;
			}

			if (as.getServiceType().equals("Hotel")) {
				String planOption = "findLastHotelByCity";

				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?city={city}";
				Map<String, String> map = new HashMap<String, String>();
				map.put("city", userrequest.getAim());
				hotelList = Arrays.asList(restTemplate.getForObject(serviceURL, Hotel[].class, map));
				if (hotelList == null || hotelList.size() == 0) break;
			}

			if (as.getServiceType().equals("Attraction")) {
				String planOption = "findBest3AttractionByCity";

				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?city={city}";
//				System.out.println(serviceURL);
				Map<String, String> map = new HashMap<String, String>();
				map.put("city", userrequest.getAim());
				attractionList = Arrays.asList(restTemplate.getForObject(serviceURL, Attraction[].class, map));
				if (attractionList == null || attractionList.size() == 0) break;
			}

			if (as.getServiceType().equals("CarRental")) {
				String planOption = "findLastCarByCity";
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?rentalLoc={rentalLoc}&returnLoc={returnLoc}";
//				System.out.println(serviceURL);
				Map<String, String> map = new HashMap<String, String>();
				map.put("rentalLoc", userrequest.getAim());
				map.put("returnLoc", userrequest.getAim());
//				System.out.println(serviceURL);
				carRentalList = Arrays.asList(restTemplate.getForObject(serviceURL, CarRental[].class, map));
				if (carRentalList == null || carRentalList.size() == 0) break;
			}

			if (as.getServiceType().equals("Guide") && attractionList != null) {
				String planOption = "findGuideByAttrId";
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption +
						"?attractionId={attractionId}";

				for (Attraction attraction : attractionList) {
					Map<String, Long> map = new HashMap<String, Long>();
					map.put("attractionId", new Long(attraction.getId()));
					Guide guide = restTemplate.getForObject(serviceURL, Guide.class, map);
					if (guide != null) attractionGuideMap.put(attraction, guide);
				}
			}
		}

		for (Airline depAirline : depAirlineList) {
			for (Airline retAirline : retAirlineList) {
				for (Hotel hotel : hotelList) {
					for (CarRental carRental : carRentalList) {
						for (Attraction attraction : attractionList) {
							Guide guide = attractionGuideMap.get(attraction);
							if (guide == null) continue;
							QueryResult result = new QueryResult(depAirline, retAirline, attraction,
									carRental, guide, hotel);
							if (result.getCost() <= userrequest.getCost()) {
								resultList.add(result);
							}
						}
					}
				}
			}
		}

		modelAndView.addObject("resultList", resultList);
        return modelAndView;
    }
    @RequestMapping(value = "/usr-result.html", method = RequestMethod.GET)
    public ModelAndView showUsrResult(@Valid Userrequest userrequest, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(userrequest.getAim());
        System.out.println(Integer.toString(userrequest.getCost()));
        System.out.println(userrequest.getDeparture_time());
        System.out.println(userrequest.getDepature_point());
        System.out.println(userrequest.getReturn_time());
        modelAndView.setViewName("usr-query");
        return modelAndView;
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        String url = "http://10.77.30.180:8084/findBestAttractionByCity?city={city}";
        Map<String,String> map = new HashMap<String,String>();
        map.put("city","beijing");
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter(Charset.forName("UTF-8")));
        Attraction attraction = restTemplate.getForObject(url, Attraction.class, map);
        if (attraction==null) {
			return "can't find attraction";
		}
        return attraction.getName();
    }
}
