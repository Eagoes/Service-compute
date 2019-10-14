package com.service.client.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
	@RequestMapping(value = "/usr-query.html", method = RequestMethod.POST)
    public ModelAndView postUsrQuery(@Valid Userrequest userrequest, BindingResult bindingResult) {
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
			// find cheapest airline
//			System.out.println(as.getServiceName());
//			System.out.println(as.getServiceType());
			if (as.getServiceType().equals("airline")) {
				String planOption = "getMinPriceAirline";
				if (userrequest.getOption() == 1) 
					planOption = "getMaxPriceAirline"; // TODO:replaced by Zhoufang's interface name
				
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?departureTime={departureTime}&departure={departure}&destination={destination}";				
				Map<String, String> map = new HashMap<String, String>();
				map.put("departureTime", userrequest.getDeparture_time());
				map.put("departure", userrequest.getDepature_point());
				map.put("destination", userrequest.getAim());
//				System.out.println(serviceURL);
//				System.out.println(userrequest.getDeparture_time());
//				System.out.println(userrequest.getDepature_point());
//				System.out.println(userrequest.getAim());
				if (finalDepAirline == null) {
					finalDepAirline = restTemplate.getForObject(serviceURL, Airline.class, map);
				}
				if (finalDepAirline==null) {
					System.out.println("finalDepAirline is null");
				}
				System.out.println(finalDepAirline.getArrivalTime());
				
				Airline tmpAirline = restTemplate.getForObject(serviceURL, Airline.class, map); 
				
				if (tmpAirline != null) {
					if (tmpAirline.getAirlinePrice() < finalDepAirline.getAirlinePrice()) {
						finalDepAirline = tmpAirline;
					}
				}	
				
				Map<String, String> returnmap = new HashMap<String, String>();
				returnmap.put("departureTime", userrequest.getReturn_time());
				returnmap.put("departure", userrequest.getAim());
				returnmap.put("destination", userrequest.getDepature_point());
				System.out.println(serviceURL);
				System.out.println(userrequest.getReturn_time());
				System.out.println(userrequest.getAim());
				System.out.println(userrequest.getDepature_point());
				if (finalRetAirline == null) {
					finalRetAirline = restTemplate.getForObject(serviceURL, Airline.class, returnmap);
				}
				if (finalRetAirline != null) {
					System.out.println("find return airline");
				}
				
				Airline tmpreutrnAirline = restTemplate.getForObject(serviceURL, Airline.class, returnmap); 
				
				if (tmpreutrnAirline != null) {
					if (tmpreutrnAirline.getAirlinePrice() < finalRetAirline.getAirlinePrice()) {
						finalRetAirline = tmpreutrnAirline;
					}
				
				}
			}
//			
//			if (as.getServiceType().equals("Airline")) {
//				String planOption = "getMinPriceAirline";
//				if (userrequest.getOption() == 1) 
//					planOption = "getMaxPriceAirline"; // TODO:replaced by Zhoufang's interface name
//				
//				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?returnTime={returnTime}&departure={departure}&destination={destination}";
//				System.out.println(serviceURL);
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("returnTime", userrequest.getReturn_time());
//				map.put("departure", userrequest.getAim());
//				map.put("destination", userrequest.getDepature_point());
//				System.out.println(serviceURL);
//				if (finalRetAirline == null) {
//					finalRetAirline = restTemplate.getForObject(serviceURL, Airline.class, map);
//				}
//				
//				Airline tmpAirline = restTemplate.getForObject(serviceURL, Airline.class, map); 
//				
//				if (tmpAirline != null) {
//					if (tmpAirline.getAirlinePrice() < finalRetAirline.getAirlinePrice()) {
//						finalRetAirline = tmpAirline;
//					}
//				}	
//			}
			if (as.getServiceType().equals("Hotel")) {
				String planOption = "findHotelByCity";
				if (userrequest.getOption() == 1) 
					planOption = "findBestHotelByCity"; // TODO:replaced by Zhoufang's interface name
				
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?city={city}";
//				System.out.println(serviceURL);
				Map<String, String> map = new HashMap<String, String>();
				map.put("city", userrequest.getAim());
//				System.out.println(serviceURL);
				if (finalHotel == null) {
					finalHotel = restTemplate.getForObject(serviceURL, Hotel.class, map);
				}
//				restTemplate = new RestTemplate();
				Hotel tmpHotel = restTemplate.getForObject(serviceURL, Hotel.class, map); 
				
				if (tmpHotel != null) {
					if (tmpHotel.getHotelPrice() < finalHotel.getHotelPrice()) {
						finalHotel = tmpHotel;
					}
				}
			}
			
			if (as.getServiceType().equals("Attraction")) {
				String planOption = "findAttractionByCity";
				if (userrequest.getOption() == 1)
					planOption = "findBestAttractionByCity"; // TODO:replaced by Zhoufang's interface name
				
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?city={city}";
//				System.out.println(serviceURL);
				Map<String, String> map = new HashMap<String, String>();
				map.put("city", userrequest.getAim());
//				System.out.println(serviceURL);
				if (finalAttraction == null) {
					finalAttraction = restTemplate.getForObject(serviceURL, Attraction.class, map);
				}
				
				Attraction tmpAttraction = restTemplate.getForObject(serviceURL, Attraction.class, map); 
				if (tmpAttraction != null) {
					if (tmpAttraction.getAttractionPrice() < finalAttraction.getAttractionPrice()) {
						finalAttraction = tmpAttraction;
					}
				}
			}
			
			if (as.getServiceType().equals("CarRental")) {
				String planOption = "findCarByCity";
				if (userrequest.getOption() == 1) 
					planOption = "findBestCarByCity"; // TODO:replaced by Zhoufang's interface name
				
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?rentalLoc={rentalLoc}&returnLoc={returnLoc}";
//				System.out.println(serviceURL);
				Map<String, String> map = new HashMap<String, String>();
				map.put("rentalLoc", userrequest.getAim());
				map.put("returnLoc", userrequest.getAim());
//				System.out.println(serviceURL);
				if (finalCarRental == null) {
					finalCarRental = restTemplate.getForObject(serviceURL, CarRental.class, map);
				}
				
				CarRental tmpCarRental = restTemplate.getForObject(serviceURL, CarRental.class, map); 
				
				if (tmpCarRental != null) {
					if (tmpCarRental.getCarPrice() < finalCarRental.getCarPrice()) {
						finalCarRental = tmpCarRental;
					}
				}
			}
			
			if (as.getServiceType().equals("Guide") && finalAttraction != null) {
				String planOption = "findGuideByAttrId";
				if (userrequest.getOption() == 1) 
					planOption = "findGuideByAttrId"; // TODO:replaced by Zhoufang's interface name
				
				String serviceURL = "http://" + as.getServiceIP() + ":8080/" + as.getServiceName() + "/" + planOption + "?attractionId={attractionId}";
				
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put("attractionId", new Integer(finalAttraction.getId()));
//				System.out.println(serviceURL);
				if (finalGuide == null) {
					finalGuide = restTemplate.getForObject(serviceURL, Guide.class, map);
				}
				
				Guide tmpGuide = restTemplate.getForObject(serviceURL, Guide.class, map); 
				if (tmpGuide != null) {
					if (tmpGuide.getPrice() < finalGuide.getPrice()) {
						finalGuide = tmpGuide;
					}
				}
			}
		}
		
		if(finalDepAirline!=null) {
        	modelAndView.addObject("departure_airline_message", 
            		"Forward Departure" + finalDepAirline.getDeparture() + "<br/>" + 
            		"Forward Departure Time" + finalDepAirline.getDepartureTime() + "<br/>" + 
            		"Forward Destination" + finalDepAirline.getDestination() + "<br/>" +
            		"Forward Price" + finalDepAirline.getAirlinePrice());
        	cost = cost + finalDepAirline.getAirlinePrice();
        }
        else
        {
        	modelAndView.addObject("departure_airline_message","");
        }       	
    
        if(finalRetAirline!=null) {
        	System.out.println("print return airline");
        	modelAndView.addObject("return_airline_message", 
            		"Backward Departure" + finalRetAirline.getDeparture() + "<br/>" + 
            		"Backward Departure Time" + finalRetAirline.getDepartureTime() + "<br/>" + 
            		"Backward Destination" + finalRetAirline.getDestination() + "<br/>" +
            		"Backward Price" + finalRetAirline.getAirlinePrice());
        	cost = cost + finalRetAirline.getAirlinePrice();
        }
        else {
        	modelAndView.addObject("return_airline_message","");
        }
        
        if(finalHotel!=null) {
            modelAndView.addObject("hotel_message", 
            		"Hotel Name" + finalHotel.getName() + "<br/>" + 
            		"Hotel Address" + finalHotel.getAddress() + "<br/>" + 
            		"Hotel Star" + finalHotel.getHotelStar() + "<br/>" +
            		"Hotel Price" + finalHotel.getHotelPrice() + "<br/>");
            cost = cost + finalHotel.getHotelPrice();
        }
        else {
        	modelAndView.addObject("hotel_message", "");
        }

        if(finalCarRental!=null) {
            modelAndView.addObject("car_message", 
            		"Car Brand" + finalCarRental.getBrand() + "<br/>" + 
            		"Car Type" + finalCarRental.getCarType() + "<br/>" + 
            		"Car Plate" + finalCarRental.getCarPlate() + "<br/>" + 
            		"Rent Location" + finalCarRental.getRentalLoc() + "<br/>" +
            		"Return Location" + finalCarRental.getReturnLoc() + "<br/>" +
            		"Rental Price" + finalCarRental.getCarPrice());
            cost = cost + finalCarRental.getCarPrice();
        }
        else {
        	modelAndView.addObject("car_message", "");
        }
       
        if(finalAttraction!=null) {
            modelAndView.addObject("attraction_message", 
            		"Attraction Name" + finalAttraction.getName() + "<br/>" + 
            		"Attraction Office Phone" + finalAttraction.getPhone() + "<br/>" + 
            		"Attraction Address" + finalAttraction.getAttractionAddress() + "<br/>" +
            		"Attraction Start" + finalAttraction.getAttractionStar() + "<br/>" +
            		"Attraction Price" + finalAttraction.getAttractionPrice());
            cost = cost + finalAttraction.getAttractionPrice();
        }
        else {
        	modelAndView.addObject("attraction_message", "");
        }

        if(finalGuide!=null) {
            modelAndView.addObject("guide_message", 
            		"Guide Name" + finalGuide.getName() + "<br/>" + 
            		"Guide Phone" + finalGuide.getPhone() + "<br/>" + 
            		"Guide Price" + finalGuide.getPrice());
            cost = cost + finalGuide.getPrice();
        }
        else {
        	modelAndView.addObject("guide_message", "");
        }

        
        modelAndView.addObject("cost","棰勮鑺遍攢锛�" + cost);
        modelAndView.setViewName("usr-result");
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
