package com.seon.navigator.util;

import com.seon.navigator.dto.RouteDTO;
import com.seon.navigator.dto.StopDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wcornejo on 26/06/15.
 */
public class TestUtils {

    public static RouteDTO generateTestRoute(){
        RouteDTO route = new RouteDTO();
        route.setName("TestRoute");

        List<StopDTO> stops = new ArrayList<StopDTO>();

        // invisible / visited
        stops.add(new StopDTO(40.73937, -74.04734, true, false)); // Waypoint 1
        stops.add(new StopDTO(40.73716, -74.04876, true, false)); // Waypoint 2

        stops.add(new StopDTO(40.73318, -74.05133, false, false)); // Stop 1

        stops.add(new StopDTO(40.73148, -74.05249, true, false)); // Waypoint 3

        stops.add(new StopDTO(40.73105, -74.05115, false, false)); // Stop 2

        stops.add(new StopDTO(40.73102, -74.04986, true, false)); // Waypoint 4

        stops.add(new StopDTO(40.73091, -74.04789, false, false)); // Finish


        route.setStops(stops);

        return route;
    }
}