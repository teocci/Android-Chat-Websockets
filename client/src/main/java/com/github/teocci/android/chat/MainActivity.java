package com.github.teocci.android.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.github.teocci.android.chat.adapters.MessageAdapter;
import com.github.teocci.android.chat.interfaces.ChatClientListener;
import com.github.teocci.android.chat.interfaces.ServiceCallback;
import com.github.teocci.android.chat.models.Frame;
import com.github.teocci.android.chat.models.MemberData;
import com.github.teocci.android.chat.models.Message;
import com.github.teocci.android.chat.net.ChatClient;
import com.github.teocci.android.chat.utils.JmDNSHelper;
import com.github.teocci.android.chat.utils.LogHelper;
import com.github.teocci.android.chat.utils.WrapHelper;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.Inet4Address;
import java.net.URI;
import java.net.URISyntaxException;

import javax.jmdns.ServiceInfo;

import static com.github.teocci.android.chat.utils.Config.CLIENT_MODE;
import static com.github.teocci.android.chat.utils.Config.CMD_MSG;
import static com.github.teocci.android.chat.utils.Config.CMD_REG_USER;

public class MainActivity extends AppCompatActivity implements ServiceCallback, ChatClientListener
{
    static final public String TAG = LogHelper.makeLogTag(MainActivity.class);

    private JmDNSHelper jmDNSHelper;

    private ChatClient ws;

    private MessageAdapter messageAdapter;

    private EditText editText;
    private ListView messagesView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);


        initJmDNSHelper();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (ws != null) {
            ws.close();
            LogHelper.e(TAG, "Websocket was closed");
        }

        teardownJmDNS();
    }

    @Override
    public void onServiceAdded(ServiceInfo info)
    {
        info.getInet4Addresses();
        Inet4Address[] ips = info.getInet4Addresses();
        String location = "ws://" + ips[0].getHostAddress() + ":" + info.getPort();
        try {
            ws = new ChatClient(new URI(location), this);
            ws.connect();
        } catch (URISyntaxException ex) {
            LogHelper.e(TAG, location + " is not a valid WebSocket URI");
            ex.printStackTrace();
        }
        LogHelper.e(TAG, location);
    }

    @Override
    public void onServiceRemoved(ServiceInfo info)
    {
        if (ws != null) {
            ws.close();
            LogHelper.e(TAG, "Websocket was closed");
        }
    }

    @Override
    public void onMessage(final Message message)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                messageAdapter.add(message);
                messagesView.setSelection(messagesView.getCount() - 1);
            }
        });
    }

    private void initJmDNSHelper()
    {
        jmDNSHelper = new JmDNSHelper(this, this);
        jmDNSHelper.setOperationMode(CLIENT_MODE);
        jmDNSHelper.setStationName("Client-Server");
    }

    private void teardownJmDNS()
    {
        if (jmDNSHelper != null) {
            jmDNSHelper.stopDiscovery();
        }
    }

    public void sendMessage(View view)
    {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            if (ws.isOpen()) {
                ws.sendMessage(message);
            }
            editText.getText().clear();
        }
    }
}
