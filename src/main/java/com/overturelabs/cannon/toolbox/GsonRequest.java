package com.overturelabs.cannon.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by stevetan on 11/11/14.
 */
public class GsonRequest<T> extends FireRequest<T> {
    private final Gson mGson = new Gson();
    private final Class<T> mClassOfT;
    private final Map<String, String> mHeaders;
    private final Response.Listener<T> mListener;

    /**
     * Make a request and return a parsed object from JSON.
     *
     * @param method
     * @param url
     * @param classOfT
     * @param listener
     * @param genericErrorListener
     */
    public GsonRequest(int method, String url, Class<T> classOfT,
                       Response.Listener<T> listener, GenericErrorListener genericErrorListener) {
        super(method, url, genericErrorListener);
        this.mClassOfT = classOfT;
        this.mHeaders = null;
        this.mListener = listener;
    }

    public GsonRequest(int method, String url, Class<T> classOfT, Map<String, String> params,
                       Response.Listener<T> listener, GenericErrorListener genericErrorListener) {
        super(method, url, params, genericErrorListener);
        this.mClassOfT = classOfT;
        this.mHeaders = null;
        this.mListener = listener;
    }

    public GsonRequest(int method, String url, Class<T> classOfT,
                       Map<String, String> params, Map<String, String> headers,
                       Response.Listener<T> listener, GenericErrorListener genericErrorListener) {
        super(method, url, params, genericErrorListener);
        this.mClassOfT = classOfT;
        this.mHeaders = headers;
        this.mListener = listener;
    }

    /**
     * Base request will use the provided headers when available;
     * Otherwise, default headers will be injected into the request.
     *
     * @return HTTP headers for this request.
     */
    @Override
    public Map<String, String> getHeaders() {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
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