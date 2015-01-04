package com.overturelabs.cannon.toolbox.gson.deserializers;

import android.util.Log;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.overturelabs.Cannon;

/**
 * Deserializer for parsing Date strings and converting them to the timezone of current locale.
 *
 * @author Steve Tan
 */
public class DateDeserializer implements JsonDeserializer<Date> {
    private static final String MONGODB_UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Override
    public Date deserialize(JsonElement element, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String date = element.getAsString();

        SimpleDateFormat formatter = new SimpleDateFormat(MONGODB_UTC_FORMAT);
        formatter.setTimeZone(TimeZone.getDefault()); // Get the default timezone.

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            Log.e(Cannon.TAG, "Unable to parse date. (" + e.getMessage() + ")");
            return null;
        }
    }
}