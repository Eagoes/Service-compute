package com.service.client.model;

import java.util.function.Predicate;

public class Userrequest {

	    private String aim;

	    private String departure_time;

	    private String return_time;

	    private String depature_point;

	    private int cost;
	    
	    private int option=1;


		public String getAim() {
			return aim;
		}

		public void setAim(String aim) {
			this.aim = aim;
		}

		public String getDeparture_time() {
			return departure_time;
		}

		public void setDeparture_time(String departure_time) {
			this.departure_time = departure_time;
		}

		public String getReturn_time() {
			return return_time;
		}

		public void setReturn_time(String return_time) {
			this.return_time = return_time;
		}

		public int getOption() {
			return option;
		}

		public void setOption(int option) {
			this.option = option;
		}

		public String getDepature_point() {
			return depature_point;
		}

		public void setDepature_point(String depature_point) {
			this.depature_point = depature_point;
		}

		public int getCost() {
			return cost;
		}

		public void setCost(int cost) {
			this.cost = cost;
		}


	
}
