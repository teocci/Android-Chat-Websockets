package com.github.teocci.android.chat.net;

import com.github.teocci.android.chat.interfaces.ChatClientListener;
import com.github.teocci.android.chat.models.Frame;
import com.github.teocci.android.chat.models.MemberData;
import com.github.teocci.android.chat.models.Message;
import com.github.teocci.android.chat.utils.LogHelper;
import com.github.teocci.android.chat.utils.WrapHelper;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import static com.github.teocci.android.chat.utils.Config.CMD_MSG;
import static com.github.teocci.android.chat.utils.Config.CMD_REG_USER;
import static com.github.teocci.android.chat.utils.MemberDataHelper.getRandomColor;
import static com.github.teocci.android.chat.utils.MemberDataHelper.getRandomName;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-16
 */
public class ChatClient extends WebSocketClient
{
    private final String TAG = LogHelper.makeLogTag(ChatClient.class);

    private MemberData currentMemberData;

    private ChatClientListener callback;

    public ChatClient(URI address, ChatClientListener callback)
    {
        super(address);

        this.callback = callback;
    }

    @Override
    public void onOpen(ServerHandshake handshake)
    {
        handshake.getHttpStatus();
        LogHelper.e(TAG, "You are connected to ChatClient: " + getURI());
    }

    @Override
    public void onMessage(String message)
    {
        LogHelper.e(TAG, "got: " + message);

        Frame frame = WrapHelper.unwrapFrame(message);
        if (frame == null) return;

        switch (frame.getCmd()) {
            case CMD_MSG:
                if (currentMemberData == null) return;

                final MemberData memberData = frame.getUserData();
                if (memberData == null) return;

                boolean belongsToCurrentUser = currentMemberData.getId().equals(memberData.getId());
                final Message msg = new Message(frame.getText(), memberData, belongsToCurrentUser);

                if (callback != null) {
                    callback.onMessage(msg);
                }

                break;

            case CMD_REG_USER:
                final MemberData data = frame.getUserData();
                if (data == null) return;

                currentMemberData = new MemberData(data.getId(), getRandomName(), getRandomColor());

                break;
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote)
    {
        LogHelper.e(TAG, "You have been disconnected from: " + getURI() + "; Code: " + code + " " + reason);
    }

    @Override
    public void onError(Exception ex)
    {
        LogHelper.e(TAG, "Exception occured ...\n" + ex);
    }

    public void sendMessage(String message)
    {
        if (currentMemberData == null) return;

        send(WrapHelper.msgToJson(message, currentMemberData));
    }
}