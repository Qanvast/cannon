package com.overturelabs.cannon.toolbox.gson.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.overturelabs.cannon.toolbox.gson.models.JsonMixed;

import java.lang.reflect.Type;

/**
 * Created by derricklee on 28/7/15.
 */
public class JsonMixedDeserializer<T extends JsonMixed>
        implements JsonDeserializer<T> {

    /**
     * Get class name from reflection type
     *
     * @param type reflection type
     * @return class name
     */
    private String getClassName(Type type) {
        String NAME_PREFIX = "class ";
        String fullName = type.toString();
        if (fullName.startsWith(NAME_PREFIX))
            return fullName.substring(NAME_PREFIX.length());
        return fullName;
    }

    /**
     * Deserialize a mixed type of either string or object
     *
     * @param json json element
     * @param typeOfT mixed type class
     * @param context JsonDeserializationContext
     * @return mixed type object
     * @throws JsonParseException
     */
    @Override
    public T deserialize(JsonElement json,
                         Type typeOfT,
                         JsonDeserializationContext context)
            throws JsonParseException {

        System.err.println(json.toString());

        try {
            Class<?> genericsType = Class.forName(getClassName(typeOfT));
            T obj = (T) genericsType.newInstance();

            if (json.isJsonObject()) {
                obj.setIsJsonObject(true);
                obj.setString(json.toString());
                obj.transform();
                return obj;
            } else if (json.isJsonPrimitive()) {
                obj.setIsJsonObject(false);

                JsonPrimitive primitive = json.getAsJsonPrimitive();
                if (primitive.isString()) {
                    obj.setString(json.getAsString());
                    return obj;
                }
            }

            return null;
        } catch (Exception e) {
            String msg = e.getLocalizedMessage();
            throw new JsonParseException(msg == null ? e.toString() : msg);
        }
    }

}
