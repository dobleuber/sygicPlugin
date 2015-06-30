package com.seon.navigator.service.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seon.navigator.dto.RouteDTO;
import com.seon.navigator.dto.StopDTO;
import com.seon.navigator.exception.NavigatorException;
import com.seon.navigator.service.NavigatorService;
import com.seon.navigator.util.SygicSdkConstants;
import com.seon.navigator.util.Util;
import com.sygic.sdk.remoteapi.Api;
import com.sygic.sdk.remoteapi.ApiCallback;
import com.sygic.sdk.remoteapi.ApiDialog;
import com.sygic.sdk.remoteapi.ApiItinerary;
import com.sygic.sdk.remoteapi.ApiLocation;
import com.sygic.sdk.remoteapi.ApiNavigation;
import com.sygic.sdk.remoteapi.events.ApiEvents;
import com.sygic.sdk.remoteapi.exception.GeneralException;
import com.sygic.sdk.remoteapi.exception.GpsException;
import com.sygic.sdk.remoteapi.model.GpsPosition;
import com.sygic.sdk.remoteapi.model.Position;
import com.sygic.sdk.remoteapi.model.StopOffPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wcornejo on 24/06/15.
 */
public class SygicNavigatorService implements NavigatorService {

    private Api mApi;

    private ApiCallback mApiCallback;

    private Context context;

    public SygicNavigatorService(Context context){
        this.context = context;
        mApiCallback = new ApiCallbackSygic();
        mApi = Api.init(context, SygicSdkConstants.SYGIC2D_PACKAGE, SygicSdkConstants.SYGIC2D_PACKAGE + "." + SygicSdkConstants.SYGIC2D_CLASSNAME, mApiCallback);
        mApi.connect();
    }

    @Override
    public String getLocationAddressInfo() {
        String address = null;

        try {
            this.verifyNavigatorRunning();
            GpsPosition gpsPosition = ApiNavigation.getActualGpsPosition(true, SygicSdkConstants.MAX_FUNCTION_EXECUTION);
            Position position =  new Position(gpsPosition.getLongitude(), gpsPosition.getLatitude());
            address = ApiLocation.getLocationAddressInfo(position, SygicSdkConstants.MAX_FUNCTION_EXECUTION);
        }catch(Exception e){
            e.printStackTrace();
            //throw new NavigatorException(e);
        }

        return address;
    }


    /**
     Contents of a Sygic StopOffPoint :
     searchAddress;
     visited;
     pointType : VIAPOINT = 1; FINISH = 2; START = 3; INVISIBLE = 4;
     this.Location = new Position(x, y); (longitude, latitude) int value
     offset;
     id;
     iso;
     caption;
     address;
     */
    @Override
    public void performNavigation(RouteDTO route){

        this.verifyNavigatorRunning();

        List<StopDTO> stopList = route.getStops();
        ArrayList<StopOffPoint> sygicStopList = new ArrayList<StopOffPoint>();
        int index = 0;
        int invisibleWaypointIndex = 0;
        int stopIndex = 0;
        int latitude;
        int longitude;
        int pointType;
        StopOffPoint sygicStopPoint;

        try {
            // Stop current navigation
            ApiNavigation.stopNavigation(SygicSdkConstants.MAX_FUNCTION_EXECUTION);
            // Add the starting point taking into account the current location
            GpsPosition currentPosition = ApiNavigation.getActualGpsPosition(true, SygicSdkConstants.MAX_FUNCTION_EXECUTION);
            sygicStopPoint = new StopOffPoint(false, false, StopOffPoint.PointType.START,currentPosition.getLongitude() , currentPosition.getLatitude(),
                    -1, index + 1, Util.EMPTY_STRING, "Start", Util.EMPTY_STRING);
            sygicStopList.add(sygicStopPoint);
            index++;

            for (StopDTO stop : stopList) {
                StringBuilder caption = new StringBuilder();
                if (index == stopList.size() - 1) {
                    caption.append("Finish");
                    pointType = StopOffPoint.PointType.FINISH;
                } else {
                    if (stop.isInvisible()) {
                        caption.append("Waypoint_"+(++invisibleWaypointIndex));
                        pointType = StopOffPoint.PointType.INVISIBLE;
                    } else {
                        caption.append("Stop_"+(++stopIndex));
                        pointType = StopOffPoint.PointType.VIAPOINT;
                    }
                }
                latitude = Util.convertDoublePositioningToLong(stop.getLatitude());
                longitude = Util.convertDoublePositioningToLong(stop.getLongitude());
                sygicStopPoint = new StopOffPoint(false, stop.isVisited(), pointType, longitude, latitude, -1, index + 1, Util.EMPTY_STRING, caption.toString(), Util.EMPTY_STRING);
                sygicStopList.add(sygicStopPoint);
                index++;
            }

            mApi.show(false);
            ApiDialog.flashMessage("Starting Route: " + route.getName(), 10000);
            String strItineraryName = route.getName(); //itinerary name
            int MaxTime = 0;
            int flags = 0;
            ApiItinerary.addItinerary(sygicStopList, strItineraryName, MaxTime);
            ApiItinerary.setRoute(strItineraryName, flags, MaxTime);

        }catch(GpsException gpsException){
            // If it comes to this point it means it was not possible to get GPS Position
            gpsException.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        } catch (GeneralException ge) {
            ge.printStackTrace();
        }


    }

    @Override
    public void closeConnection() {
        try {
            mApi.unregisterCallback();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mApi.disconnect();
    }

    private void verifyNavigatorRunning(){
        try {
            boolean navigatorRunning = mApi.isAppRunning();
            if(!navigatorRunning){
                mApi.show(true);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Api Callback that detects all the events and proceeds accordingly
     */
    private class ApiCallbackSygic implements  ApiCallback {

        @Override
        public void onEvent(final int event, final String data) {

            Log.e("Event" , String.format("Generado el evento: %s ", event));
            boolean show = false;
            switch (event) {
                case ApiEvents.EVENT_SPEED_EXCEEDING:
                    show = true;
                    break;
                case ApiEvents.EVENT_WAIPOINT_VISITED:
                    Log.e("Itinerary", String.format("Pasé por el  waypoint %s !!!", data));
                    break;
            }
        }

        @Override
        public void onServiceConnected() {
            try {
                mApi.registerCallback();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected() {

        }
    }



}

