package com.github.teocci.android.chat.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Base64;

import com.github.teocci.android.chat.interfaces.JmDNSListener;
import com.github.teocci.android.chat.net.CreateTask;
import com.github.teocci.android.chat.net.RegisterTask;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import static com.github.teocci.android.chat.utils.Config.DEFAULT_CHAT_PORT;
import static com.github.teocci.android.chat.utils.Config.SERVICE_CHANNEL_NAME;
import static com.github.teocci.android.chat.utils.Config.SERVICE_NAME_SEPARATOR;
import static com.github.teocci.android.chat.utils.Config.SERVICE_TYPE;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-10
 */
public class JmDNSHelper implements JmDNSListener
{
    private static String TAG = LogHelper.makeLogTag(JmDNSHelper.class);

    private Context context;

    private JmDNS jmDNS;

    private boolean isServiceRegistered = false;

    private String operationMode;
    private String stationName;
    private String serviceName;

//    private WifiManager.MulticastLock multicastLock;

    public JmDNSHelper(Context context)
    {
        this.context = context;

        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager != null ? wifiManager.getConnectionInfo() : null;
        if (info == null) return;

        new CreateTask(info, this).execute();
    }

    @Override
    public void onInstanceCreated(JmDNS jmDNS)
    {
        this.jmDNS = jmDNS;
        registerService();
    }

    @Override
    public void onInstanceCreationFailed()
    {
        LogHelper.e(TAG, "Error in JmDNS creation.");
    }

    @Override
    public void onServiceRegistered(ServiceInfo serviceInfo)
    {
        isServiceRegistered = true;
        LogHelper.e(TAG, "onServiceRegistered()-> " + serviceInfo.getName());
    }

    @Override
    public void onServiceRegistrationFailed(ServiceInfo serviceInfo)
    {
        clearServiceRegistered();
        LogHelper.e(TAG, "onServiceRegistrationFailed()-> " + serviceInfo.getName());
    }


    public void registerService()
    {
        LogHelper.e(TAG, "registerService");

        unregisterService();

//            wifiLock();

        // Android NSD implementation is very unstable when services
        // registers with the same name.
        // Therefore, we will use "SERVICE_CHANNEL_NAME:STATION_NAME:DEVICE_ID:".
        serviceName = Base64.encodeToString(SERVICE_CHANNEL_NAME.getBytes(), (Base64.NO_WRAP)) +
                SERVICE_NAME_SEPARATOR +
                Base64.encodeToString(stationName.getBytes(), (Base64.NO_WRAP)) +
                SERVICE_NAME_SEPARATOR;

        final ServiceInfo serviceInfo = ServiceInfo.create(SERVICE_TYPE, serviceName, DEFAULT_CHAT_PORT, stationName);

        if (jmDNS != null) {
            new RegisterTask(jmDNS, serviceInfo, this).execute();
        }
    }


    public void setStationName(String stationName)
    {
        this.stationName = stationName;
    }

    public void setOperationMode(String operationMode)
    {
        this.operationMode = operationMode;
    }


//    private void wifiLock()
//    {
//        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//
//        multicastLock = wifiManager != null ? wifiManager.createMulticastLock(serviceName) : null;
//        if (multicastLock == null) return;
//
//        multicastLock.setReferenceCounted(true);
//        multicastLock.acquire();
//    }

    public void unregisterService()
    {
        if (jmDNS != null) {
            jmDNS.unregisterAllServices();
            clearServiceRegistered();

            LogHelper.e(TAG, "unregisterService()");
        }
//        if (multicastLock != null && multicastLock.isHeld()) {
//            multicastLock.release();
//        }
    }


    private void clearServiceRegistered()
    {
        serviceName = null;
        isServiceRegistered = false;
    }

    public boolean isServiceRegistered()
    {
        return isServiceRegistered;
    }
}
