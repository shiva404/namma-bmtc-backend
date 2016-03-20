package com.intuit.types;


/**
 *
 */
public class Location {
    private String routeNumber;
    private String crowdLevel;
    private Double latitude;
    private Double longitude;

    public Location() {
    }

    public Location(Double latitude, Double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

//    public Map<String, Object> getMap(){
//        HashMap<String, Object> hashMap = new HashMap<>(4);
//        hashMap.put("routeNumber", routeNumber);
//        hashMap.put("crowdLevel", crowdLevel);
//        hashMap.put("latitude", latitude);
//        hashMap.put("longitude", longitude);
//        return hashMap;
//    }

    public String getCrowdLevel() {
        return crowdLevel;
    }

    public void setCrowdLevel(String crowdLevel) {
        this.crowdLevel = crowdLevel;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    @Override
    public String toString() {
        return "Location{" +
                "crowdLevel='" + crowdLevel + '\'' +
                ", routeNumber='" + routeNumber + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
