package com.tutorial.movieapp.remote;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityStatus extends ContextWrapper
{
    public ConnectivityStatus(Context base)
    {
        super(base);
    }

    public static boolean isConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (cm != null && info.isConnectedOrConnecting())
        {
            return true;
        }

        return false;
    }
}
