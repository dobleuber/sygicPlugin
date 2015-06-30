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

public class SygicPlugin extends CordovaPlugin {
    public static final String TAG = "Sygic Plugin";
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
    }
    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) {
// Shows a toast
        Log.v(TAG,"Sygic received:"+ action);

        try {
            if(action.equals("showToast")) {
                final String message = args.getString(0);
                cordova.getActivity().runOnUiThread(new Runnable() {

                });
                callbackContext.success();
            } else if (action.equals("translateCoords")) {

                callbackContext.success("Mi casa " + args.getLong(0) + " " + args.getLong(1));
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