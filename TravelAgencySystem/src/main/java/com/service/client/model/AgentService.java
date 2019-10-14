package com.service.client.model;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agentServices")
public class AgentService {
	
	@Id
    @Column(name = "serviceID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@Column(name = "serviceType")
    @NotEmpty(message = "*Please provide service type")
    private String serviceType;
	
	@Column(name = "serviceName")
	@NotEmpty(message= "*Please provide service name")
	private String serviceName;
	
	@Column(name = "serviceIP")
	@NotEmpty(message= "*Please provide service IP")
	private String serviceIP;
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getServiceType() {
		return serviceType;
	}


	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public String getServiceIP() {
		return serviceIP;
	}


	public void setServiceIP(String serviceIP) {
		this.serviceIP = serviceIP;
	}

}
