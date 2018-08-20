package com.github.teocci.android.chat.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-17
 */
public class Frame
{
    private String cmd;

    @SerializedName("user_data")
    private MemberData userData;

    private String text;

    public Frame(String cmd, MemberData userData)
    {
        this(cmd, userData, null);
    }

    public Frame(String cmd, String text)
    {
        this(cmd, null, text);
    }

    public Frame(String cmd, MemberData userData, String text)
    {
        this.cmd = cmd;
        this.userData = userData;
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "Frame{" +
                "cmd='" + cmd + '\'' +
                ", userData='" + userData + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public String getCmd()
    {
        return cmd;
    }

    public MemberData getUserData()
    {
        return userData;
    }

    public String getText()
    {
        return text;
    }
}
