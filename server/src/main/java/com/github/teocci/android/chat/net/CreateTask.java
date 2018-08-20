package com.github.teocci.android.chat.net;

import android.net.wifi.WifiInfo;
import android.os.AsyncTask;

import com.github.teocci.android.chat.interfaces.JmDNSListener;

import java.io.IOException;

import javax.jmdns.JmDNS;

import static com.github.teocci.android.chat.utils.Utilities.obtainIPv4Address;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-17
 */
public class CreateTask extends AsyncTask<Void, Void, Boolean>
{
    private JmDNS jmDNS;

    private final WifiInfo info;

    private final JmDNSListener listener;

    public CreateTask(final WifiInfo info, JmDNSListener listener)
    {
        this.info = info;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        try {
            jmDNS = JmDNS.create(obtainIPv4Address(info));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean registered)
    {
        if (registered) {
            listener.onInstanceCreated(jmDNS);
        } else {
            listener.onInstanceCreationFailed();
        }
    }
}