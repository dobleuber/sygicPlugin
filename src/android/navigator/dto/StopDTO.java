package com.seon.navigator.dto;

/**
 * Created by wcornejo on 26/06/15.
 */
public class StopDTO {

    /** Latitude in Float format. This is referred commonly as axis y */
    private double latitude;

    /** Longitude in Float format. This is referred commonly as axis x */
    private double longitude;

    /** States if the point is Invisible */
    private boolean isInvisible;

    /** If the point has been visited already. */
    private boolean visited;


    public StopDTO(double latitude, double longitude, boolean isInvisible, boolean visited) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.isInvisible = isInvisible;
        this.visited = visited;
    }

    public StopDTO() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public void setIsInvisible(boolean isInvisible) {
        this.isInvisible = isInvisible;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
