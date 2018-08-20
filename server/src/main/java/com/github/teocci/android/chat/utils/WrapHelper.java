package com.github.teocci.android.chat.utils;

import com.github.teocci.android.chat.models.Frame;
import com.github.teocci.android.chat.models.MemberData;
import com.google.gson.Gson;

import java.util.UUID;

import static com.github.teocci.android.chat.utils.Config.CMD_MSG;
import static com.github.teocci.android.chat.utils.Config.CMD_REG_USER;
import static com.github.teocci.android.chat.utils.Config.CMD_RES;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-17
 */
public class WrapHelper
{
    static Gson gson = new Gson();

    public static String regToJson(String id)
    {
        MemberData userData = new MemberData(UUID.randomUUID().toString(), null, null);
        Frame frame = new Frame(CMD_REG_USER, userData, null);

        return gson.toJson(frame);
    }

    public static String msgToJson(String text, MemberData userData)
    {
        Frame frame = new Frame(CMD_MSG, userData, text);

        return gson.toJson(frame);
    }

    public static String resToJson(String text)
    {
        Frame frame = new Frame(CMD_RES, null, text);

        return gson.toJson(frame);
    }

    public static String resToJson(String text, MemberData userData)
    {
        Frame frame = new Frame(CMD_RES, userData, text);

        return gson.toJson(frame);
    }

    public static Frame unwrapFrame(String message)
    {
        Gson gson = new Gson();
        return gson.fromJson(message, Frame.class);
    }
}
