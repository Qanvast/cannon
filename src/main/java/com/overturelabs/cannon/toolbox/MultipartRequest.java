package com.overturelabs.cannon.toolbox;

import android.util.Pair;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okio.Buffer;

/**
 * Multipart Request.
 *
 * @param <T>   Type of expected response object.
 * @author      Steve Tan
 */
public class MultipartRequest<T> extends GenericRequest<T> {

    private RequestBody mRequestBody;

    /**
     * Construct a {@link com.overturelabs.cannon.toolbox.MultipartRequest}.
     *
     * @param method            HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param url               Request URL.
     * @param files             Files you want to send. The map should contain the form field name as the
     *                          entry's key and a {@link android.util.Pair} containing the
     *                          actual {@link java.io.File} and MIME type string.
     *                          Refer to {@link android.content.ContentResolver#getType(android.net.Uri)}.
     * @param responseParser    {@link com.overturelabs.cannon.toolbox.ResponseParser} for parsing response.
     * @param successListener   Success {@link com.android.volley.Response.Listener}.
     * @param errorListener     {@link com.android.volley.Response.ErrorListener}.
     */
    public MultipartRequest(int method, String url, final Map<String, Pair<File, String>> files,
                            ResponseParser<T> responseParser,
                            Response.Listener<T> successListener,
                            Response.ErrorListener errorListener) {
        super(method, url, responseParser, successListener, errorListener);

        build(null, files);
    }

    /**
     * Construct a {@link com.overturelabs.cannon.toolbox.MultipartRequest}.
     *
     * @param method            HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param url               Request URL.
     * @param params            Parameters to be inserted into request body.
     * @param files             Files you want to send. The map should contain the form field name as the
     *                          entry's key and a {@link android.util.Pair} containing the
     *                          actual {@link java.io.File} and MIME type string.
     *                          Refer to {@link android.content.ContentResolver#getType(android.net.Uri)}.
     * @param responseParser    {@link com.overturelabs.cannon.toolbox.ResponseParser} for parsing response.
     * @param successListener   Success {@link com.android.volley.Response.Listener}.
     * @param errorListener     {@link com.android.volley.Response.ErrorListener}.
     */
    public MultipartRequest(int method, String url, final Map<String, String> params,
                            final Map<String, Pair<File, String>> files,
                            ResponseParser<T> responseParser,
                            Response.Listener<T> successListener,
                            Response.ErrorListener errorListener) {
        super(method, url, responseParser, successListener, errorListener);

        build(params, files);
    }

    /**
     * Construct a {@link com.overturelabs.cannon.toolbox.MultipartRequest}.
     *
     * @param method            HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param url               Request URL.
     * @param files             Files you want to send. The map should contain the form field name as the
     *                          entry's key and a {@link android.util.Pair} containing the
     *                          actual {@link java.io.File} and MIME type string.
     *                          Refer to {@link android.content.ContentResolver#getType(android.net.Uri)}.
     * @param oAuth2Token       OAuth 2.0 token to be inserted into the request header.
     * @param responseParser    {@link com.overturelabs.cannon.toolbox.ResponseParser} for parsing response.
     * @param successListener   Success {@link com.android.volley.Response.Listener}.
     * @param errorListener     {@link com.android.volley.Response.ErrorListener}.
     */
    public MultipartRequest(int method, String url, final Map<String, Pair<File, String>> files,
                            String oAuth2Token,
                            ResponseParser<T> responseParser,
                            Response.Listener<T> successListener,
                            Response.ErrorListener errorListener) {
        super(method, url, oAuth2Token, responseParser, successListener, errorListener);

        build(null, files);
    }


    /**
     * Construct a {@link com.overturelabs.cannon.toolbox.MultipartRequest}.
     *
     * @param method            HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param url               Request URL.
     * @param params            Parameters to be inserted into request body.
     * @param files             Files you want to send. The map should contain the form field name as the
     *                          entry's key and a {@link android.util.Pair} containing the
     *                          actual {@link java.io.File} and MIME type string.
     *                          Refer to {@link android.content.ContentResolver#getType(android.net.Uri)}.
     * @param oAuth2Token       OAuth 2.0 token to be inserted into the request header.
     * @param responseParser    {@link com.overturelabs.cannon.toolbox.ResponseParser} for parsing response.
     * @param successListener   Success {@link com.android.volley.Response.Listener}.
     * @param errorListener     {@link com.android.volley.Response.ErrorListener}.
     */
    public MultipartRequest(int method, String url, final Map<String, String> params,
                            final Map<String, Pair<File, String>> files, String oAuth2Token,
                            ResponseParser<T> responseParser,
                            Response.Listener<T> successListener,
                            Response.ErrorListener errorListener) {
        super(method, url, oAuth2Token, responseParser, successListener, errorListener);

        build(params, files);
    }

    private void build(final Map<String, String> params, final Map<String, Pair<File, String>> files) {
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);

        for (Map.Entry<String, Pair<File, String>> filePart : files.entrySet()) {
            Pair<File, String> filePair = filePart.getValue();

            File file = filePair.first;
            String mediaType = filePair.second;

            multipartBuilder.addFormDataPart(
                    filePart.getKey(),
                    file.getName(),
                    RequestBody.create(MediaType.parse(mediaType), file)
            );
        }

        if (params != null) {
            for (Map.Entry<String, String> stringPart : params.entrySet()) {
                multipartBuilder.addFormDataPart(
                        stringPart.getKey(),
                        stringPart.getValue()
                );
            }
        }

        mRequestBody = multipartBuilder.build();
    }

    public String getBodyContentType() {
        return mRequestBody.contentType().toString();
    }

    /**
     * Returns the raw POST or PUT body to be sent.
     *
     * @throws com.android.volley.AuthFailureError in the event of auth failure
     */
    public byte[] getBody() throws AuthFailureError {
        Buffer buffer = new Buffer();

        try {
            mRequestBody.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.readByteArray();
    }
}
