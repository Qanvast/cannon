package com.overturelabs.cannon.toolbox;

import android.support.v4.util.Pair;
import android.webkit.MimeTypeMap;

import com.android.volley.AuthFailureError;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okio.Buffer;

/**
 * So basically we need all the parts of this multipart request during construction of the request.
 * We need a header for each part, which consists of {@code Content-Disposition: form-data; name="my-control-name"}.
 * We also need the data (of course), which can be either a string or a file.
 *
 * Created by stevetan on 18/11/14.
 */
public abstract class MultipartRequest<T> extends FireRequest<T> {

    private final static String PART_HEADER_PRE = "\"Content-Disposition\", \"form-data; name=\"";
    private final static String PART_HEADER_POST = "\"";

    private RequestBody mRequestBody;

    public MultipartRequest(int method, String url, Map<String, Pair<File, String>> files, GenericErrorListener genericErrorListener) {
        super(method, url, genericErrorListener);

        build(null, files);
    }

    public MultipartRequest(int method, String url, Map<String, String> params, Map<String, Pair<File, String>> files, GenericErrorListener genericErrorListener) {
        super(method, url, genericErrorListener);

        build(params, files);
    }

    public MultipartRequest(int method, String url, Map<String, Pair<File, String>> files, String oAuth2Token, GenericErrorListener genericErrorListener) {
        super(method, url, oAuth2Token, genericErrorListener);

        build(null, files);
    }

    public MultipartRequest(int method, String url, Map<String, String> params, Map<String, Pair<File, String>> files, String oAuth2Token, GenericErrorListener genericErrorListener) {
        super(method, url, oAuth2Token, genericErrorListener);

        build(params, files);
    }

    private void build(Map<String, String> params, Map<String, Pair<File, String>> files) {
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);

        if (params != null) {
            for (Map.Entry<String, String> stringPart : params.entrySet()) {
                multipartBuilder.addPart(
                        Headers.of(PART_HEADER_PRE + stringPart.getKey() + PART_HEADER_POST),
                        RequestBody.create(null, stringPart.getValue())
                );
            }
        }

        for (Map.Entry<String, Pair<File, String>> filePart : files.entrySet()) {
            Pair<File, String> filePair = filePart.getValue();

            File file = filePair.first;
            String mediaType = filePair.second;

            multipartBuilder.addPart(
                    Headers.of(PART_HEADER_PRE + filePart.getKey() + PART_HEADER_POST),
                    RequestBody.create(MediaType.parse(mediaType), file)
            );
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
