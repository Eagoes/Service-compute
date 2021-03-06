package com.service.client.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attractions")
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attractionId")
    private long id;
    
    @Column(name = "attractionName")
    @NotEmpty(message = "*Please provide attraction name")
    private String name;
    
    
    @Column(name = "attractionPhone")
    @NotEmpty(message = "*Please provide attraction phone")
    private String phone;
    
    @Column(name = "attractionState")
    @NotEmpty(message = "*Please provide attraction state")
    private String attractionState;
    
    @Column(name = "attractionCity")
    @NotEmpty(message = "*Please provide attraction city")
    private String attractionCity;
    
    @Column(name = "attractionAddress")
    @NotEmpty(message = "*Please provide attraction address")
    private String attractionAddress;
    
    @Column(name = "attractionStar")
    @NotNull(message = "*Please provide your attraction star")
    private int attractionStar;
    
    @Column(name = "attractionComment")
    @NotEmpty(message = "*Please provide your attraction comment")
    private String attractionComment;
    
    @Column(name = "attractionPrice")
    @NotNull(message = "*Please provide your attraction Price")
    private long attractionPrice;
    
    
    public void setId(long id) {
		this.id = id;
	}
    public long getId() {
		return id;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    public String getName() {
		return name;
	}
    
    public void setPhone(String phone) {
		this.phone = phone;
	}
    public String getPhone() {
		return phone;
	}
    
    public void setAttractionState(String attractionState) {
		this.attractionState = attractionState;
	}
    public String getAttractionState() {
		return this.attractionState;
	}
    
    public void setAttractionCity(String attractionCity) {
		this.attractionCity = attractionCity;
	}
    public String getAttractionCity() {
		return this.attractionCity;
	}
    
    public void setAttractionAddress(String attractionAddress) {
		this.attractionAddress = attractionAddress;
	}
    public String getAttractionAddress() {
		return this.attractionAddress;
	}
    
    public void setattractionStar(int attractionStar) {
		this.attractionStar = attractionStar;
	}
    public int getAttractionStar() {
		return attractionStar;
	}
    
    public void setattractionComment(String attractionComment) {
		this.attractionComment = attractionComment;
	}
    public String getAttractionComment() {
		return attractionComment;
	}
    
    public void setattractionPrice(long attractionPrice) {
		this.attractionPrice = attractionPrice;
	}
    public long getAttractionPrice() {
		return attractionPrice;
	}
}
