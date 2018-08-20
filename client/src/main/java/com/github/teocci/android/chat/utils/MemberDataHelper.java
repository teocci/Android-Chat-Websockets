package com.github.teocci.android.chat.utils;

import java.util.Random;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-17
 */
public class MemberDataHelper
{
    public static String getRandomName()
    {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy",
                "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson",
                "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green",
                "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still",
                "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy",
                "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished",
                "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake",
                "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun",
                "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly",
                "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound",
                "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance",
                "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (adjs[(int) Math.floor(Math.random() * adjs.length)] + "_" +
                nouns[(int) Math.floor(Math.random() * nouns.length)]
        );
    }

    public static String getRandomColor()
    {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while (sb.length() < 7) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }
}
