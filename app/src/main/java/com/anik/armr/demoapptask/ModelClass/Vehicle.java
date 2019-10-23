package com.anik.armr.demoapptask.ModelClass;

public class Vehicle {
    private String user_id;
    private String timestamp;
    private String last_timestamp;
    private String lon;
    private String lat;
    private String speed;
    private float heading;
    private String distance;
    private String device_id;
    private String location_name;
    private String description;
    private String icon;
    private float engine;
    private float door;
    private float online;
    private float fuel;
    private float temperature;
    private float gsm_signal;
    private float gps_sattelites;
    private float gps_signal;

    public Vehicle() {
    }

    public Vehicle(String user_id, String timestamp, String last_timestamp, String lon, String lat, String speed, float heading, String distance, String device_id, String location_name, String description, String icon, float engine, float door, float online, float fuel, float temperature, float gsm_signal, float gps_sattelites, float gps_signal) {
        this.user_id = user_id;
        this.timestamp = timestamp;
        this.last_timestamp = last_timestamp;
        this.lon = lon;
        this.lat = lat;
        this.speed = speed;
        this.heading = heading;
        this.distance = distance;
        this.device_id = device_id;
        this.location_name = location_name;
        this.description = description;
        this.icon = icon;
        this.engine = engine;
        this.door = door;
        this.online = online;
        this.fuel = fuel;
        this.temperature = temperature;
        this.gsm_signal = gsm_signal;
        this.gps_sattelites = gps_sattelites;
        this.gps_signal = gps_signal;
    }


    public String getUser_id() {
        return user_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getLast_timestamp() {
        return last_timestamp;
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

    public float getHeading() {
        return heading;
    }

    public String getDistance() {
        return distance;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public float getEngine() {
        return engine;
    }

    public float getDoor() {
        return door;
    }

    public float getOnline() {
        return online;
    }

    public float getFuel() {
        return fuel;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getGsm_signal() {
        return gsm_signal;
    }

    public float getGps_sattelites() {
        return gps_sattelites;
    }

    public float getGps_signal() {
        return gps_signal;
    }
}
