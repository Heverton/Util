package br.com.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

/**
 * Created by c1278778 on 23/05/18.
 */
public abstract class FormataResposta {

    /**
     * Transforma Objeto em Json(String)
     *
     * @param obj
     * @return String
     */
    public static String toJSON(Object obj) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING );
            Gson gson = gsonBuilder.create();
            String result = gson.toJson(obj);
            return result;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        return null;

    }
}
