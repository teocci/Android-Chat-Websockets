package com.github.teocci.android.chat.interfaces;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-13
 */
public interface JmDNSListener
{
    void onInstanceCreated(JmDNS jmDNS);

    void onInstanceCreationFailed();

    void onServiceRegistered(ServiceInfo serviceInfo);

    void onServiceRegistrationFailed(ServiceInfo serviceInfo);
}
