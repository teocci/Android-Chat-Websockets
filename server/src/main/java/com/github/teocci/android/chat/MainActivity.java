package com.github.teocci.android.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.teocci.android.chat.models.MemberData;
import com.github.teocci.android.chat.net.ChatServer;
import com.github.teocci.android.chat.utils.JmDNSHelper;
import com.github.teocci.android.chat.utils.LogHelper;
import com.google.gson.Gson;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.teocci.android.chat.utils.Config.CLIENT_MODE;
import static com.github.teocci.android.chat.utils.Config.DEFAULT_CHAT_PORT;


public class MainActivity extends AppCompatActivity
{
    static final public String TAG = LogHelper.makeLogTag(MainActivity.class);

    private JmDNSHelper jmDNSHelper;

    private ChatServer server = new ChatServer(DEFAULT_CHAT_PORT);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        server.start();

        initJmDNSHelper();

        registerNSD();
    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (server != null) {
            try {
                server.stop();
                LogHelper.e(TAG, "Websocket was closed");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void initJmDNSHelper()
    {
        jmDNSHelper = new JmDNSHelper(this);
        jmDNSHelper.setOperationMode(CLIENT_MODE);
        jmDNSHelper.setStationName("Chat-Server");
    }

    private void registerNSD()
    {
        if (jmDNSHelper != null && !jmDNSHelper.isServiceRegistered()) {
            jmDNSHelper.registerService();
            LogHelper.e(TAG, "registerNSD-in");
        }
    }
}
