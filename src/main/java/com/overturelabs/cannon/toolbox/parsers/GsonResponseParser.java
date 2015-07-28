package com.overturelabs.cannon.toolbox.parsers;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonSyntaxException;
import com.overturelabs.Cannon;

import java.io.UnsupportedEncodingException;

/**
 * {@link ResponseParser} for {@link com.google.gson.Gson} objects.
 *
 * @param <T> Expected class of response object.
 * @author Steve Tan
 */
public class GsonResponseParser<T> implements ResponseParser<T> {
    private Class<T> mClassOfT;

    public GsonResponseParser(Class<T> classOfT) {
        mClassOfT = classOfT;
    }

    @Override
    public Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(Cannon.getInstance()
                            .getGson()
                            .fromJson(json, mClassOfT),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (Cannon.NotLoadedException e) {
            return Response.error(new ParseError(e));
        }
    }
}
