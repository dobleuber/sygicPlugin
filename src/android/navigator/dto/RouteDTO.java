package com.seon.navigator.dto;

import java.util.List;

/**
 * Created by wcornejo on 26/06/15.
 */
public class RouteDTO {

    /** The list of stops */
    private List<StopDTO> stops;

    /** The Name of the Route */
    private String name;

    public RouteDTO() {
    }

    public List<StopDTO> getStops() {
        return stops;
    }

    public void setStops(List<StopDTO> stops) {
        this.stops = stops;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
