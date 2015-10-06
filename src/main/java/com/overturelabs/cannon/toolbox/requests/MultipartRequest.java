package com.overturelabs.cannon.toolbox.requests;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.overturelabs.cannon.toolbox.parsers.ResponseParser;
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

    private final static int MULTIPART_TIMEOUT_MS = 1000 * 90;
    private RequestBody mRequestBody;

    /**
     * Construct a {@link MultipartRequest}.
     *
     * @param method            HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param url               Request URL.
     * @param headers           Headers to be inserted into request header.
     * @param params            Parameters to be inserted into request body.
     * @param files             Files you want to send. The map should contain the form field name as the
     *                          entry's key and a {@link android.util.Pair} containing the
     *                          actual {@link java.io.File} and MIME type string.
     *                          Refer to {@link android.content.ContentResolver#getType(android.net.Uri)}.
     * @param responseParser    {@link com.overturelabs.cannon.toolbox.parsers.ResponseParser} for parsing response.
     * @param successListener   Success {@link com.android.volley.Response.Listener}.
     * @param errorListener     {@link com.android.volley.Response.ErrorListener}.
     */
    public MultipartRequest(int method,
                            @NonNull String url,
                            @Nullable final Map<String, String> headers,
                            @Nullable final Map<String, String> params,
                            @NonNull final Map<String, Pair<File, String>> files,
                            @NonNull ResponseParser<T> responseParser,
                            @NonNull Response.Listener<T> successListener,
                            @NonNull Response.ErrorListener errorListener) {
        super(method, url, headers, null, responseParser, successListener, errorListener);

        build(params, files);

        setRetryPolicy(new DefaultRetryPolicy(MULTIPART_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void build(@Nullable final Map<String, String> params,
                       @NonNull final Map<String, Pair<File, String>> files) {
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
