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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView showUsrResult(@RequestParam("departureAirlineId") long departureAirlineId,
									  @RequestParam("returnAirlineId") long returnAirlineId,
									  @RequestParam("hotelId") long hotelId,
									  @RequestParam("carRentalId") long carRentalId,
									  @RequestParam("attractionId") long attractionId,
									  @RequestParam("guideId") long guideId) {
		ModelAndView modelAndView = new ModelAndView();
		List<AgentService> agentServices = agentServiceService.findAll();
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

		Airline finalRetAirline = null;
		Airline finalDepAirline = null;
		Attraction finalAttraction = null;
		Guide finalGuide = null;
		Hotel finalHotel = null;
		CarRental finalCarRental = null;
		float cost = 0;

		for (AgentService as : agentServices) {
			if (as.getServiceType().equals("Airline")) {
				String planOption = "getAirline";
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?id={departureAirlineId}";

				Map<String, Long> departureMap = new HashMap<String, Long>();
				departureMap.put("departureAirlineId", new Long(departureAirlineId));
				finalDepAirline = restTemplate.getForObject(serviceURL, Airline.class, departureMap);

				serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?id={returnAirlineId}";
				Map<String, Long> returnMap = new HashMap<String, Long>();
				returnMap.put("returnAirlineId", new Long(returnAirlineId));
				finalRetAirline = restTemplate.getForObject(serviceURL, Airline.class, returnMap);
			}

			if (as.getServiceType().equals("Hotel")) {
				String planOption = "findHotelById";
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?id={hotelId}";
//				System.out.println(serviceURL);
				Map<String, Long> map = new HashMap<String, Long>();
				map.put("hotelId", new Long(hotelId));
				finalHotel = restTemplate.getForObject(serviceURL, Hotel.class, map);
			}

			if (as.getServiceType().equals("Attraction")) {
				String planOption = "getAttraction";
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?id={attractionId}";
				Map<String, Long> map = new HashMap<String, Long>();
				map.put("attractionId", new Long(attractionId));
				finalAttraction = restTemplate.getForObject(serviceURL, Attraction.class, map);
			}

			if (as.getServiceType().equals("CarRental")) {
				String planOption = "findCarById";
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?id={carRentalId}";
				Map<String, Long> map = new HashMap<String, Long>();
				map.put("carRentalId", new Long(carRentalId));
				finalCarRental = restTemplate.getForObject(serviceURL, CarRental.class, map);
			}

			if (as.getServiceType().equals("Guide")) {
				String planOption = "findGuideById";
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?id={guideId}";
				Map<String, Long> map = new HashMap<String, Long>();
				map.put("guideId", new Long(guideId));
				finalGuide = restTemplate.getForObject(serviceURL, Guide.class, map);
			}
		}

		if(finalDepAirline!=null) {
			modelAndView.addObject("departure_airline_message",
					"Departure City：" + finalDepAirline.getDeparture() + "<br/>" +
							"Departure Date：" + finalDepAirline.getDepartureTime() + "<br/>" +
							"Destination：" + finalDepAirline.getDestination() + "<br/>" +
							"Airline Price：" + finalDepAirline.getAirlinePrice());
			cost = cost + finalDepAirline.getAirlinePrice();
		}
		else
		{
			System.out.println("departure airline id:" + departureAirlineId + " is null");
			modelAndView.addObject("departure_airline_message","");
		}

		if(finalRetAirline!=null) {
			System.out.println("print return airline");
			modelAndView.addObject("return_airline_message",
					"Return City：" + finalRetAirline.getDeparture() + "<br/>" +
							"Return Date：" + finalRetAirline.getDepartureTime() + "<br/>" +
							"Destination：" + finalRetAirline.getDestination() + "<br/>" +
							"Price：" + finalRetAirline.getAirlinePrice());
			cost = cost + finalRetAirline.getAirlinePrice();
		}
		else {
			System.out.println("return  airline id:" + returnAirlineId + " is null");
			modelAndView.addObject("return_airline_message","");
		}

		if(finalHotel!=null) {
			modelAndView.addObject("hotel_message",
					"Hotel Name：" + finalHotel.getName() + "<br/>" +
							"Hotel Address：" + finalHotel.getAddress() + "<br/>" +
							"Hotel Level：" + finalHotel.getHotelStar() + "<br/>" +
							"Hotel Price：" + finalHotel.getHotelPrice());
			cost = cost + finalHotel.getHotelPrice();
		}
		else {
			modelAndView.addObject("hotel_message", "");
		}

		if(finalCarRental!=null) {
			modelAndView.addObject("car_message",
					"Car Brand：" + finalCarRental.getBrand() + "<br/>" +
							"Car Type：" + finalCarRental.getCarType() + "<br/>" +
							"Car Plate：" + finalCarRental.getCarPlate() + "<br/>" +
							"Rental Location：" + finalCarRental.getRentalLoc() + "<br/>" +
							"Return Location：" + finalCarRental.getReturnLoc() + "<br/>" +
							"Rental Price：" + finalCarRental.getCarPrice());
			cost = cost + finalCarRental.getCarPrice();
		}
		else {
			modelAndView.addObject("car_message", "");
		}

		if(finalAttraction!=null) {
			modelAndView.addObject("attraction_message",
					"Attraction Name：" + finalAttraction.getName() + "<br/>" +
							"Attraction Phone：" + finalAttraction.getPhone() + "<br/>" +
							"Attraction Address：" + finalAttraction.getAttractionAddress() + "<br/>" +
							"Attraction Level：" + finalAttraction.getAttractionStar() + "<br/>" +
							"Attraction Price：" + finalAttraction.getAttractionPrice());
			cost = cost + finalAttraction.getAttractionPrice();
		}
		else {
			modelAndView.addObject("attraction_message", "");
		}

		if(finalGuide!=null) {
			modelAndView.addObject("guide_message",
					"Guide Name：" + finalGuide.getName() + "<br/>" +
							"Guide Phone：" + finalGuide.getPhone() + "<br/>" +
							"Guide Price：" + finalGuide.getPrice());
			cost = cost + finalGuide.getPrice();
		}
		else {
			modelAndView.addObject("guide_message", "");
		}


		modelAndView.addObject("cost","Cost：" + cost);
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
