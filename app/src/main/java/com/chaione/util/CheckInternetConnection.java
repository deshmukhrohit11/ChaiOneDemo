package com.chaione.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The Class CheckInternetConnection.
 */
public class CheckInternetConnection {

    /**
     * The mContext.
     */
    private final Context mContext;

    /**
     * Instantiates a new check internet connection.
     *
     * @param contextObject the mContext object
     */
    public CheckInternetConnection(Context contextObject) {
        mContext = contextObject;
    }

    /**
     * Checks if is connecting to internet.
     *
     * @return true, if is connecting to internet
     */
    public boolean isConnectingToInternet() {
        final ConnectivityManager connectivity =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            final NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (final NetworkInfo element : info) {
                    if (element.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
