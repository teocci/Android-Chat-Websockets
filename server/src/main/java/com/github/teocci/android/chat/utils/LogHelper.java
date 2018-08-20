package com.github.teocci.android.chat.utils;

import android.util.Log;

import com.github.teocci.android.chat.BuildConfig;

import static com.github.teocci.android.chat.utils.Config.LOG_PREFIX;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Feb-02
 */
public class LogHelper
{
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;
    private static final int RESERVED_LENGTH = MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 2;

    public static String makeLogTag(String str)
    {
        return LOG_PREFIX
                + '['
                + (str.length() > RESERVED_LENGTH ? str.substring(0, RESERVED_LENGTH - 1) : str)
                + ']';
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls)
    {
        return makeLogTag(cls.getSimpleName());
    }

    public static void v(String tag, Object... messages)
    {
        // Only log VERBOSE if build type is DEBUG
        if (BuildConfig.DEBUG) {
            log(tag, Log.VERBOSE, null, messages);
        }
    }

    public static void d(String tag, Object... messages)
    {
        // Only log DEBUG if build type is DEBUG
        if (BuildConfig.DEBUG) {
            log(tag, Log.DEBUG, null, messages);
        }
    }

    public static void i(String tag, Object... messages)
    {
        log(tag, Log.INFO, null, messages);
    }

    public static void w(String tag, Object... messages)
    {
        log(tag, Log.WARN, null, messages);
    }

    public static void w(String tag, Throwable t, Object... messages)
    {
        log(tag, Log.WARN, t, messages);
    }

    public static void e(String tag, Object... messages)
    {
        log(tag, Log.ERROR, null, messages);
    }

    public static void e(String tag, Throwable t, Object... messages)
    {
        log(tag, Log.ERROR, t, messages);
    }

    public static void log(String tag, int level, Throwable t, Object... messages)
    {
        if (Log.isLoggable(tag, level)) {
            String message;
            if (t == null && messages != null && messages.length == 1) {
                // Handle this common case without the extra cost of creating a StringBuffer:
                message = messages[0].toString();
            } else {
                StringBuilder sb = new StringBuilder();
                if (messages != null) for (Object m : messages) {
                    sb.append(m);
                }
                if (t != null) {
                    sb.append("\n").append(Log.getStackTraceString(t));
                }
                message = sb.toString();
            }

            Log.println(level, tag, message);
        }
    }
}
