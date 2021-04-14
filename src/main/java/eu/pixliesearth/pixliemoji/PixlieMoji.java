package eu.pixliesearth.pixliemoji;

import java.util.HashMap;
import java.util.Map;

public class PixlieMoji {

    private static final Map<String, Character> pixlieMojis = new HashMap<String, Character>(){{
        put(":relax:", '是');
        put(":mad:", '了');
        put(":really:", '不');
        put(":amogus:", '在');
        put(":pog:", '码');
    }};

    public static String replacePixlieMojis(String text) {
        String s = text;
        for (Map.Entry<String, Character> entries : pixlieMojis.entrySet())
            s = s.replace(entries.getKey(), entries.getValue() + "");
        return s;
    }

}
