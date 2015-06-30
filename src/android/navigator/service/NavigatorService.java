package com.seon.navigator.service;

import com.seon.navigator.dto.RouteDTO;
import com.sygic.sdk.remoteapi.model.StopOffPoint;

import java.util.List;

/**
 * Created by wcornejo on 24/06/15.
 */
public interface NavigatorService {

    /**
     * This method will make a call to the Navigator API and retrieve the Address
     * @author william.cornejo
     * @return The location where the Navigator is currently
     */
    public String getLocationAddressInfo();



    /**
     * Receives the Navigation points and starts navigation.
     * @author william.cornejo
     * @param route
     */
    void performNavigation(RouteDTO route);

    /**
     * Closes the connection to the Navigator Application (if necessary).
     */
    public void closeConnection();

}
