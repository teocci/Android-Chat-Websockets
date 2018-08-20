package com.github.teocci.android.chat.utils;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Jun-19
 */
public class Config
{
    public static final String LOG_PREFIX = "[SmartAudio]";

    public static int DEFAULT_CHAT_PORT = 8080;

    public static int REQUEST_ALL = 100;

    public static final String KEY_OPERATION_MODE = "operation_mode";
    public static final String KEY_STATION_NAME = "station_name";
    public static final String KEY_USED_NAMES = "station_used_names";
    public static final String KEY_STATION_NAME_LIST = "station_name_list";
    public static final String KEY_FEATURE_GUIDE = "feature_guide";
    public static final String KEY_MAIN_ACTIVITY = "main_activity";
    public final static String KEY_OPEN_SOURCE_LICENSE = "open_source_license";

    public final static String CMD_REG_USER = "REG";
    public final static String CMD_MSG = "MSG";
    public final static String CMD_REQ = "REQ";
    public final static String CMD_RES = "RES";


//    public static final String SERVICE_TYPE = "_smartmixer._tcp"; // Smart Mixer
    public static final String SERVICE_TYPE = "_wschat._tcp.local.";
    public static final String SERVICE_CHANNEL_NAME = "Channel_00";
    public static final String SERVICE_NAME_SEPARATOR = ":";

    public static final String CLIENT_MODE = "client_mode";

    public static final String COMMAND_SEPARATOR = ";";
    public static final String PARAMETER_SEPARATOR = ":";
    public static final String VALUE_SEPARATOR = ",";
}
