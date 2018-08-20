package com.github.teocci.android.chat.net;

import com.github.teocci.android.chat.models.Frame;
import com.github.teocci.android.chat.models.MemberData;
import com.github.teocci.android.chat.models.Message;
import com.github.teocci.android.chat.utils.LogHelper;
import com.github.teocci.android.chat.utils.WrapHelper;
import com.google.gson.Gson;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.teocci.android.chat.utils.Config.CMD_MSG;
import static com.github.teocci.android.chat.utils.Config.CMD_REG_USER;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-16
 */
public class ChatServer extends WebSocketServer
{
    private final String TAG = LogHelper.makeLogTag(ChatServer.class);

    private Map<String, WebSocket> clients = new ConcurrentHashMap<>();

    public ChatServer(int port)
    {
        super(new InetSocketAddress(port));
    }

    public ChatServer(InetSocketAddress address)
    {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        String uniqueID = UUID.randomUUID().toString();
        clients.put(uniqueID, conn);

        conn.send(WrapHelper.regToJson(uniqueID));
        conn.send(WrapHelper.resToJson("Welcome to the server!"));


        broadcast((WrapHelper.resToJson("new connection: " + handshake.getResourceDescriptor()))); //This method sends a message to all clients connected
        LogHelper.e(TAG, conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        broadcast(WrapHelper.resToJson(conn + " has left the room!"));
        LogHelper.e(TAG, conn + " has left the room!");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Gson gson = new Gson();
        final Frame frame = gson.fromJson(message, Frame.class);
        if (frame != null) {
            LogHelper.e(TAG, "frame: " + frame);

            switch (frame.getCmd()) {
                case CMD_MSG:
                    broadcast(message);

                    break;

                case CMD_REG_USER:

                    break;
            }
        }
        LogHelper.e(TAG, conn + ": " + message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        broadcast(message.array());
        LogHelper.e(TAG, conn + ": " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex)
    {
        ex.printStackTrace();
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart()
    {
        LogHelper.e(TAG, "Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }
}