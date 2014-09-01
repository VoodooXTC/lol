package joss.jacobo.lol.lcs.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.lang.reflect.Type;

/**
 * Created by Joss on 8/31/2014
 */
public class GGson {

    private static Gson ggson;

    public static Gson getGson(){
        if(ggson == null)
            ggson = new Gson();

        return ggson;
    }

    public static String toJson(Object object) {
        return getGson().toJson(object);
    }

    public static <T> T fromJson(String s, Class<T> c) {
        return getGson().fromJson(s, c);
    }

    public static <T> T fromJson(String s, Type t) {
        return getGson().fromJson(s, t);
    }

    public static <T> T fromJson(BufferedReader reader, Type t) {
        return getGson().fromJson(reader, t);
    }

}