package com.overturelabs.cannon.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

/**
 * {@link com.overturelabs.cannon.toolbox.ResponseParser} for {@link com.google.gson.Gson} objects.
 *
 * @param <T> Expected class of response object.
 * @author Steve Tan
 */
public class GsonResponseParser<T> implements ResponseParser<T> {
    private static final String MONGODB_UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private Gson mGson;
    private Class<T> mClassOfT;

    public GsonResponseParser(Class<T> classOfT) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(MONGODB_UTC_FORMAT);

        mGson = gsonBuilder.create();
        mClassOfT = classOfT;
    }

    @Override
    public Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    mGson.fromJson(json, mClassOfT),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
