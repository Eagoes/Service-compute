replace into `nouser` value("zbx");
REPLACE INTO `role` VALUES (1,'ROOT');
REPLACE INTO `role` VALUES (2,'USER');
replace into `t_user` values (100,"hello","123456","1339170","xinxi@ruc.edu.cn",1);


replace into `agentServices`(serviceID, serviceType, serviceName, serviceIP) value(0, "Airline", "Airline-1.0", "10.77.50.78");
replace into `agentServices`(serviceID, serviceType, serviceName, serviceIP) value(0, "Attraction", "Attraction-1.0", "10.77.50.78");
replace into `agentServices`(serviceID, serviceType, serviceName, serviceIP) value(0, "CarRental", "CarRental-1.0", "10.77.50.78");
replace into `agentServices`(serviceID, serviceType, serviceName, serviceIP) value(0, "Guide", "Guide-1.0", "10.77.50.78");
replace into `agentServices`(serviceID, serviceType, serviceName, serviceIP) value(0, "Hotel", "Hotel-1.0", "10.77.50.78");