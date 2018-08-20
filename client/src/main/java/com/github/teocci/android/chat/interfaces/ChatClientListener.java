package com.github.teocci.android.chat.interfaces;

import com.github.teocci.android.chat.models.Message;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-20
 */
public interface ChatClientListener
{
    void onMessage(Message message);
}
