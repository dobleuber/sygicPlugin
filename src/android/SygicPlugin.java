package com.seon.navigator;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import android.util.Log;
import android.provider.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Exception;
import java.lang.Long;

import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;

import com.seon.navigator.dto.RouteDTO;
import com.seon.navigator.service.NavigatorService;
import com.seon.navigator.service.impl.SygicNavigatorService;
import com.seon.navigator.util.TestUtils;

public class SygicPlugin extends CordovaPlugin {
    public static final String TAG = "Sygic Plugin";
    private NavigatorService navigatorService;
    /**
     * Constructor.
     */
    public SygicPlugin() {}
    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.v(TAG,"Init Sygic");
        navigatorService = new SygicNavigatorService(cordova.getActivity().getApplicationContext());
    }
    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) {
// Shows a toast
        Log.v(TAG,"Sygic received:"+ action);

        try {
            if(action.equals("showToast")) {
                final String message = args.getString(0);
                cordova.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        SuperToast.create(cordova.getActivity().getApplicationContext(), message, SuperToast.Duration.LONG,
                                Style.getStyle(Style.GREEN, SuperToast.Animations.SCALE)).show();
                    }
                });
                callbackContext.success();
            } else if (action.equals("translateCoords")) {

                callbackContext.success(navigatorService.getLocationAddressInfo());
            } else {
                callbackContext.error("Invalid action");
                return false;
            }
        } catch (JSONException jex){
            System.err.println("Exception: " + jex.getMessage());
            callbackContext.error(jex.getMessage());
            return false;
        } catch (Exception ex) {
            System.err.println("Exception: " + ex.getMessage());
            callbackContext.error(ex.getMessage());
            return false;
        }
        return true;
    }
}