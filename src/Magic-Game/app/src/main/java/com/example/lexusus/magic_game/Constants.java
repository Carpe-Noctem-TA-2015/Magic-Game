package com.example.lexusus.magic_game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Constants {
    static final Set<String> SPELLS = new HashSet<String>(Arrays.asList(
            new String[]{"fireball", "iceshard"}
    ));

    public static Map<String, String> init(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("fireball","Star");
        return map;
    }
}
