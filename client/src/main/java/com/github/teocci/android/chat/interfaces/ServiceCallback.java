package com.github.teocci.android.chat.interfaces;

import javax.jmdns.ServiceInfo;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-17
 */
public interface ServiceCallback
{
    void onServiceAdded(ServiceInfo info);

    void onServiceRemoved(ServiceInfo info);
}
