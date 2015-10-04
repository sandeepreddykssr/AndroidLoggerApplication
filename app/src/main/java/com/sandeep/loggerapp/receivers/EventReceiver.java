package com.sandeep.loggerapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sandeep.loggerapp.util.LoggerUtil;

public class EventReceiver extends BroadcastReceiver {

    /**
     * Flags for GPS and Network Location Services
     */
    public static final int GPS_FLAG = 1;
    public static final int NETWORK_FLAG = 2;
    /**
     * Flags for WIFI and DATA Network Services
     */
    public static final int WIFI_FLAG = 1;
    public static final int DATA_FLAG = 2;
    /**
     * Log Tag for Location Based Changes
     */
    private static final String LOG_LOCATION_TAG = EventReceiver.class.getSimpleName() + "_LOCATION";
    /**
     * Log Tag for Network Based Changes
     */
    private static final String LOG_NETWORK_TAG = EventReceiver.class.getSimpleName() + "_NETWORK";

    public EventReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //checking if we are receiving Network / Location Changes
        if ("android.location.PROVIDERS_CHANGED".equals(intent.getAction())) {
            handelLocationChangeListener(context);
        } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            handelNetworkChangeListener(context);
        }
    }

    /**
     * Handles and Logs the location Based Changes
     *
     * @param context
     */
    private void handelLocationChangeListener(Context context) {
        int locationFlag = 0;

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationFlag = locationFlag | GPS_FLAG;
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationFlag = locationFlag | NETWORK_FLAG;
        }

        switch (locationFlag) {
            case 0:
                LoggerUtil.i(LOG_LOCATION_TAG, "Location Turned OFF");
                break;
            case 1:
                LoggerUtil.i(LOG_LOCATION_TAG, "GPS Location Turned ON and Network Location Turned OFF");
                break;
            case 2:
                LoggerUtil.i(LOG_LOCATION_TAG, "GPS Location Turned OFF and Network Location Turned ON");
                break;
            case 3:
                LoggerUtil.i(LOG_LOCATION_TAG, "GPS Location Turned ON and Network Location Turned ON");
                break;
            default:
        }
    }

    /**
     * Handles and Logs the Network Based Changes
     *
     * @param context
     */
    private void handelNetworkChangeListener(Context context) {
        int networkFlag = 0;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        boolean isWiFi = false;
        if (activeNetwork != null) {
            isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        }

        if (isConnected && isWiFi) {
            networkFlag = networkFlag | WIFI_FLAG;
        }

        if (isConnected && !isWiFi) {
            networkFlag = networkFlag | DATA_FLAG;
        }

        switch (networkFlag) {
            case 0:
                LoggerUtil.i(LOG_NETWORK_TAG, "Network Turned OFF");
                break;
            case 1:
                LoggerUtil.i(LOG_NETWORK_TAG, "Wifi Network is connected");
                break;
            case 2:
                LoggerUtil.i(LOG_NETWORK_TAG, "Data Network is connected");
                break;
            default:
        }

    }

}
