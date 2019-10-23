package com.anik.armr.demoapptask.ModelClass;

public class Vehicle {
    private String userId;
    private String timestamp;
    private String lastTimestamp;
    private String lon;
    private String lat;
    private String speed;
    private Integer heading;
    private String distance;
    private String deviceId;
    private String locationName;
    private String description;
    private String icon;
    private Integer engine;
    private Integer door;
    private Integer online;
    private Integer fuel;
    private Integer temperature;
    private Integer gsmSignal;
    private Integer gpsSattelites;
    private Integer gpsSignal;
    private Boolean gpsPositioning;

    public Vehicle() {
    }

    public Vehicle(String userId, String timestamp, String lastTimestamp, String lon, String lat, String speed, Integer heading, String distance, String deviceId, String locationName, String description, String icon, Integer engine, Integer door, Integer online, Integer fuel, Integer temperature, Integer gsmSignal, Integer gpsSattelites, Integer gpsSignal, Boolean gpsPositioning) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.lastTimestamp = lastTimestamp;
        this.lon = lon;
        this.lat = lat;
        this.speed = speed;
        this.heading = heading;
        this.distance = distance;
        this.deviceId = deviceId;
        this.locationName = locationName;
        this.description = description;
        this.icon = icon;
        this.engine = engine;
        this.door = door;
        this.online = online;
        this.fuel = fuel;
        this.temperature = temperature;
        this.gsmSignal = gsmSignal;
        this.gpsSattelites = gpsSattelites;
        this.gpsSignal = gpsSignal;
        this.gpsPositioning = gpsPositioning;
    }

    public String getUserId() {
        return userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getLastTimestamp() {
        return lastTimestamp;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public String getSpeed() {
        return speed;
    }

    public Integer getHeading() {
        return heading;
    }

    public String getDistance() {
        return distance;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getEngine() {
        return engine;
    }

    public Integer getDoor() {
        return door;
    }

    public Integer getOnline() {
        return online;
    }

    public Integer getFuel() {
        return fuel;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public Integer getGsmSignal() {
        return gsmSignal;
    }

    public Integer getGpsSattelites() {
        return gpsSattelites;
    }

    public Integer getGpsSignal() {
        return gpsSignal;
    }

    public Boolean getGpsPositioning() {
        return gpsPositioning;
    }
}
