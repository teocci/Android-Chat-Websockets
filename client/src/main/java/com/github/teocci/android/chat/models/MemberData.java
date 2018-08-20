package com.github.teocci.android.chat.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-17
 */
public class MemberData
{
    @SerializedName("user_id")
    private final String id;

    private String name;
    private String color;

    public MemberData(String id, String name, String color)
    {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString()
    {
        return "MemberData{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getColor()
    {
        return color;
    }
}