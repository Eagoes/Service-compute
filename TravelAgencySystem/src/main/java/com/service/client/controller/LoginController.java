package com.service.client.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.service.client.model.Airline;
import com.service.client.model.Attraction;
import com.service.client.model.CarRental;
import com.service.client.model.Guide;
import com.service.client.model.Hotel;
import com.service.client.model.User;
import com.service.client.model.Userrequest;
import com.service.client.service.AirlineService;
import com.service.client.service.AttractionService;
import com.service.client.service.CarRentalService;
import com.service.client.service.GuideService;
import com.service.client.service.HotelService;
import com.service.client.service.TuserService;
import com.service.client.service.UserService;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private TuserService tuserService;
    
    @Autowired
    private AirlineService airlineService;
    
    @Autowired
    private CarRentalService carRentalService;
    
    @Autowired
    private HotelService hotelService;
    
    @Autowired
    private GuideService guideService;
    
    @Autowired
    private AttractionService attractionService;

	    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	    public ModelAndView login(){
	        //从数据库取出数据  
	        List<User> users = userService.findAll();  
	        ModelAndView mav = new ModelAndView();  
	        mav.setViewName("login_lcy");  
	        mav.addObject("User", new User());  
	        mav.addObject("Userlist", users);  
	        return mav;  
	    }
    
//	  @RequestMapping(value={"/user-list.html"}, method = RequestMethod.GET)
//	  public ModelAndView ulist(){
//	      //从数据库取出数据  
//	      List<User> users = userService.findAll();  
//	      ModelAndView mav = new ModelAndView();  
//	      mav.setViewName("user-list");  
//	      mav.addObject("user", new T_user());  
//	      mav.addObject("userlist", users); 
//	      System.out.println("Now you are in user-list.html, list size = " + Integer.toString(users.size()));
//	      return mav;  
//	  }
	  
//		@RequestMapping(value={"/addairline"}, method = RequestMethod.GET)
//		public ModelAndView AirlineAddHtml() {
//			ModelAndView modelAndView = new ModelAndView();
////			airlineService.saveAirline(airline);
////			modelAndView.addObject("successMessage", attributeValue);
//			modelAndView.setViewName("user-add");
//			modelAndView.addObject("t_user", new T_user());  
//			return modelAndView;
//		}
	  
//	  @RequestMapping(value={"/adduser"}, method = RequestMethod.GET)
//	  public ModelAndView tuserlist(){
//	      //从数据库取出数据  
//	      List<User> users = userService.findAll();  
//	      ModelAndView mav = new ModelAndView();  
//	      mav.setViewName("user-add");  
//	      mav.addObject("user", new User());  
//	      mav.addObject("userlist", users); 
//	      System.out.println("Now you are in login methos=get");
//	      return mav;  
//	  }
  
//	  @RequestMapping(value={"/adduser"}, method = RequestMethod.POST)
//	  public ModelAndView createTUser(@Valid T_user t_user, BindingResult bindingResult) {
//	      System.out.println("1234");
//		  ModelAndView modelAndView = new ModelAndView();
////	      User userExists = tuserService.findUserByEmail(user.getEmail());
////	      if (userExists != null) {
////	          bindingResult
////	                  .rejectValue("email", "error.user",
////	                          "There is already a user registered with the email provided");
////	      }
////	      if (bindingResult.hasErrors()) {
////	          modelAndView.setViewName("registration");
////	      } else {
////	    	  tuserService.save(user);
////	          modelAndView.addObject("successMessage", "User has been registered successfully");
////	          modelAndView.addObject("user", new User());
////	          modelAndView.setViewName("registration");
////	
////	      }
//	      System.out.println(t_user.getEmail());
//	      tuserService.save(t_user);
//	      modelAndView.setViewName("user-add");;
//	      return modelAndView;
//	  }
    
//    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
//    public ModelAndView login(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login_lcy");
//        return modelAndView;
//    }

    @RequestMapping(value={"/list"}, method = RequestMethod.GET)
    public ModelAndView userlist(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("test");
        return modelAndView;
    }
    
    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        System.out.println(user.getName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login_lcy");
        }
        return modelAndView;
    }
//    
//    @RequestMapping(value="/usr-query.html", method = RequestMethod.GET)
//    public ModelAndView showUsrQuery(){
//        ModelAndView modelAndView = new ModelAndView();
//        Userrequest userrequest = new Userrequest();
//        modelAndView.addObject("userrequest", userrequest);
//        modelAndView.setViewName("usr-query");
//        return modelAndView;
//    }
//
//    @SuppressWarnings("unused")
//	@RequestMapping(value = "/usr-query.html", method = RequestMethod.POST)
//    public ModelAndView postUsrQuery(@Valid Userrequest userrequest, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//        
//        System.out.println(userrequest.getAim());
//        System.out.println(Integer.toString(userrequest.getCost()));
//        System.out.println(userrequest.getDeparture_time());
//        System.out.println(userrequest.getDepature_point());
//        System.out.println(userrequest.getReturn_time());
//        
//        String departureTime = userrequest.getDeparture_time();
//        String departure_point = userrequest.getDepature_point();
//        String return_time = userrequest.getReturn_time();
//        String destination = userrequest.getAim();
//        int expected_cost = userrequest.getCost();
//        Airline departure_airline = airlineService.getMinPriceAirline(departureTime, departure_point, destination);
//        Airline return_airline = airlineService.getMinPriceAirline(return_time, destination, departure_point);
//        CarRental carRental = carRentalService.findCarByCity(destination, destination);
//        Hotel hotel = hotelService.findHotelByCity(destination);
//        Attraction attraction = attractionService.findAttractionByCity(destination);
//        Guide guide;
//        if(attraction==null)
//        	guide=null;
//        else
//        	guide = guideService.findGuideByAttractionId(attraction.getId());
//        float cost = 0;
//        
////        modelAndView.addObject("departure_airline", departure_airline);
////        modelAndView.addObject("return_airline", return_airline);
////        modelAndView.addObject("carRental", carRental);
////        modelAndView.addObject("hotel", hotel);
//        
//        if(departure_airline!=null) {
//        	modelAndView.addObject("departure_airline_message", 
//            		"起飞城市：" + departure_airline.getDeparture() + "<br/>" + 
//            		"起飞日期：" + departure_airline.getDepartureTime() + "<br/>" + 
//            		"目的地：" + departure_airline.getDestination() + "<br/>" +
//            		"机票价格：" + departure_airline.getAirlinePrice());
//        	cost = cost + departure_airline.getAirlinePrice();
//        }
//        else
//        {
//        	modelAndView.addObject("departure_airline_message","");
//        }       	
//    
//        if(return_airline!=null) {
//        	modelAndView.addObject("return_airline_message", 
//            		"返航城市：" + return_airline.getDeparture() + "<br/>" + 
//            		"返航日期：" + return_airline.getDepartureTime() + "<br/>" + 
//            		"目的地：" + return_airline.getDestination() + "<br/>" +
//            		"机票价格：" + return_airline.getAirlinePrice());
//        	cost = cost + return_airline.getAirlinePrice();
//        }
//        else {
//        	modelAndView.addObject("return_airline_message","");
//        }
//        
//        if(hotel!=null) {
//            modelAndView.addObject("hotel_message", 
//            		"宾馆名：" + hotel.getName() + "<br/>" + 
//            		"宾馆地址：" + hotel.getAddress() + "<br/>" + 
//            		"宾馆星级：" + hotel.getHotelStar() + "<br/>" +
//            		"宾馆价格：" + hotel.getHotelPrice() + "<br/>" +
//            		"预定时间：" + hotel.getHotelPrice());
//            cost = cost + hotel.getHotelPrice();
//        }
//        else {
//        	modelAndView.addObject("hotel_message", "");
//        }
//
//        if(carRental!=null) {
//            modelAndView.addObject("car_message", 
//            		"汽车品牌：" + carRental.getBrand() + "<br/>" + 
//            		"汽车车型：" + carRental.getCarType() + "<br/>" + 
//            		"汽车车牌：" + carRental.getCarPlate() + "<br/>" + 
//            		"租车地点：" + carRental.getRentalLoc() + "<br/>" +
//            		"还车地点：" + carRental.getReturnLoc() + "<br/>" +
//            		"还车地点：" + carRental.getReturnLoc() + "<br/>" +
//            		"租金：" + carRental.getCarPrice());
//            cost = cost + carRental.getCarPrice();
//        }
//        else {
//        	modelAndView.addObject("car_message", "");
//        }
//       
//        if(attraction!=null) {
//            modelAndView.addObject("attraction_message", 
//            		"景点名：" + attraction.getName() + "<br/>" + 
//            		"景点电话：" + attraction.getPhone() + "<br/>" + 
//            		"景点地址：" + attraction.getAttractionAddress() + "<br/>" +
//            		"景点星级：" + attraction.getAttractionStar() + "<br/>" +
//            		"门票价格：" + attraction.getAttractionPrice());
//            cost = cost + attraction.getAttractionPrice();
//        }
//        else {
//        	modelAndView.addObject("attraction_message", "");
//        }
//
//        if(guide!=null) {
//            modelAndView.addObject("guide_message", 
//            		"导游名：" + guide.getName() + "<br/>" + 
//            		"导游电话：" + guide.getPhone() + "<br/>" + 
//            		"导游价格：" + guide.getPrice());
//            cost = cost + guide.getPrice();
//        }
//        else {
//        	modelAndView.addObject("guide_message", "");
//        }
//
//        
//        modelAndView.addObject("cost","预计花销：" + cost); 
//        		
//        
//        modelAndView.setViewName("usr-result");
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/usr-result.html", method = RequestMethod.GET)
//    public ModelAndView showUsrResult(@Valid Userrequest userrequest, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//        System.out.println(userrequest.getAim());
//        System.out.println(Integer.toString(userrequest.getCost()));
//        System.out.println(userrequest.getDeparture_time());
//        System.out.println(userrequest.getDepature_point());
//        System.out.println(userrequest.getReturn_time());
//        modelAndView.setViewName("usr-query");
//        return modelAndView;
//    }
//    
//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public String test() {
//        String url = "http://10.77.30.180:8084/findBestAttractionByCity?city={city}";
//        Map<String,String> map = new HashMap<String,String>();
//        map.put("city","beijing");
//        RestTemplate restTemplate = new RestTemplate();
//        
//        Attraction attraction = restTemplate.getForObject(url, Attraction.class, map);
//        if (attraction==null) {
//			return "can't find attraction";
//		}
//        return attraction.getName();
//    }
//    

//    @RequestMapping(value="/home", method = RequestMethod.GET)
//    public ModelAndView home(){
//        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByEmail(auth.getName());
//        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
//        modelAndView.addObject("memberMessage","Content Available Only for Users with Member Role");
//        modelAndView.setViewName("home");
//        return modelAndView;
//    }
    
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public ModelAndView dispatch(ModelMap model, HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView();
//    	String path = request.getContextPath() ;
//        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
          Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext()
                  .getAuthentication().getAuthorities());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", user.getName());

          if (roles.contains("USER")) {
//        	  modelAndView.addObject("memberMessage","Content Available Only for Users with User Role");
              modelAndView.setViewName("index-1");
          }
          else {
//        	  modelAndView.addObject("memberMessage","Content Available Only for Users with Root Role");
        	  modelAndView.setViewName("index-2");
          }
          return modelAndView;
    }
}
