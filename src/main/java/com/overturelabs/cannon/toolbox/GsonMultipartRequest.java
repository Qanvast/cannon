package com.overturelabs.cannon.toolbox;

import android.support.v4.util.Pair;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by stevetan on 11/11/14.
 */
public class GsonMultipartRequest<T> extends MultipartRequest<T> {
    private final Gson mGson = new Gson();
    private final Class<T> mClassOfT;
    private final Map<String, String> mHeaders;
    private final Response.Listener<T> mListener;

    public GsonMultipartRequest(int method, Class<T> classOfT, String url,
                                Map<String, String> params, Map<String, Pair<File, String>> files,
                                Response.Listener<T> listener, GenericErrorListener genericErrorListener) {
        super(method, url, params, files, genericErrorListener);
        this.mClassOfT = classOfT;
        this.mHeaders = null;
        this.mListener = listener;
    }

    public GsonMultipartRequest(int method, Class<T> classOfT, String url,
                                Map<String, Pair<File, String>> files,
                                Response.Listener<T> listener, GenericErrorListener genericErrorListener) {
        super(method, url, files, genericErrorListener);
        this.mClassOfT = classOfT;
        this.mHeaders = null;
        this.mListener = listener;
    }

    public GsonMultipartRequest(int method, Class<T> classOfT, String url,
                                Map<String, String> params, Map<String, Pair<File, String>> files,
                                String oAuth2Token, Response.Listener<T> listener, GenericErrorListener genericErrorListener) {
        super(method, url, params, files, oAuth2Token, genericErrorListener);
        this.mClassOfT = classOfT;
        this.mHeaders = null;
        this.mListener = listener;
    }

    public GsonMultipartRequest(int method, Class<T> classOfT, String url,
                                Map<String, Pair<File, String>> files,
                                String oAuth2Token, Response.Listener<T> listener, GenericErrorListener genericErrorListener) {
        super(method, url, files, oAuth2Token, genericErrorListener);
        this.mClassOfT = classOfT;
        this.mHeaders = null;
        this.mListener = listener;
    }

    public GsonMultipartRequest(int method, Class<T> classOfT, String url,
                                Map<String, String> params, Map<String, Pair<File, String>> files,
                                Map<String, String> headers, Response.Listener<T> listener, GenericErrorListener genericErrorListener) {
        super(method, url, params, files, genericErrorListener);
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