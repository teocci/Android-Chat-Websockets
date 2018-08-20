package com.github.teocci.android.chat.models;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-17
 */
public class Message
{
    private String text;
    private MemberData data;
    private boolean belongsToCurrentUser;

    public Message(String text, MemberData data, boolean belongsToCurrentUser)
    {
        this.text = text;
        this.data = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    @Override
    public String toString()
    {
        return "Message{" +
                "text='" + text + '\'' +
                ", belongsToCurrentUser='" + belongsToCurrentUser + '\'' +
                '}';
    }

    public String getText()
    {
        return text;
    }

    public MemberData getData()
    {
        return data;
    }

    public boolean isBelongsToCurrentUser()
    {
        return belongsToCurrentUser;
    }
}