package com.service.client.model;

public class QueryResult {
    private long DepartureAirlineId;
    private String DepartureAirlineName;

    private long ReturnAirlineId;
    private String ReturnAirlineName;

    private long AttractionId;
    private String AttractionName;

    private long CarRentalId;
    private String CarRentalName;

    private long GuideId;
    private String GuideName;

    private long HotelId;
    private String HotelName;

    private float cost;

    public QueryResult(Airline departureAirline, Airline returnAirline, Attraction attraction,
                CarRental carRental, Guide guide, Hotel hotel) {
        this.DepartureAirlineId = departureAirline.getAirlineId();
        this.DepartureAirlineName = departureAirline.getAirlineName();
        this.ReturnAirlineId = returnAirline.getAirlineId();
        this.ReturnAirlineName = returnAirline.getAirlineName();
        this.AttractionId = attraction.getId();
        this.AttractionName = attraction.getName();
        this.CarRentalId = carRental.getId();
        this.CarRentalName = carRental.getName();
        this.GuideId = guide.getId();
        this.GuideName = guide.getName();
        this.HotelId = hotel.getId();
        this.HotelName = hotel.getName();
        this.cost = departureAirline.getAirlinePrice()
                + returnAirline.getAirlinePrice()
                + attraction.getAttractionPrice()
                + carRental.getCarPrice()
                + guide.getPrice()
                + hotel.getHotelPrice();
    }

    public long getDepartureAirlineId() {
        return DepartureAirlineId;
    }
    public String getDepartureAirlineName() {
        return DepartureAirlineName;
    }

    public long getReturnAirlineId() {
        return ReturnAirlineId;
    }
    public String getReturnAirlineName() {
        return ReturnAirlineName;
    }

    public long getAttractionId() {
        return AttractionId;
    }
    public String getAttractionName() {
        return AttractionName;
    }

    public long getCarRentalId() {
        return CarRentalId;
    }
    public String getCarRentalName() {
        return CarRentalName;
    }

    public long getGuideId() {
        return GuideId;
    }
    public String getGuideName() {
        return GuideName;
    }

    public long getHotelId() {
        return HotelId;
    }
    public String getHotelName() {
        return HotelName;
    }

    public float getCost() {
        return cost;
    }
}
