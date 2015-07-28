package com.overturelabs.cannon.toolbox.requests;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.overturelabs.Cannon;
import com.overturelabs.cannon.toolbox.CannonAuthenticator;
import com.overturelabs.cannon.toolbox.parsers.ResponseParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic request class that supports OAuth 2.0 bearer tokens.
 *
 * @param <T>   Type of expected response object.
 * @author      Steve Tan
 */
public class GenericRequest<T> extends Request<T> {
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private ResponseParser<T> mResponseParser;
    private Response.Listener<T> mListener;

    /**
     * Construct a {@link GenericRequest}.
     *
     * @param method            HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param url               Request URL.
     * @param headers           Headers to be inserted into request header.
     * @param params            Parameters to be inserted into request body.
     * @param responseParser    {@link com.overturelabs.cannon.toolbox.parsers.ResponseParser} for parsing response.
     * @param successListener   Success {@link com.android.volley.Response.Listener}.
     * @param errorListener     {@link com.android.volley.Response.ErrorListener}.
     */
    public GenericRequest(int method,
                          @NonNull String url,
                          @Nullable final Map<String, String> headers,
                          @Nullable final Map<String, String> params,
                          @NonNull ResponseParser<T> responseParser,
                          @NonNull Response.Listener<T> successListener,
                          @NonNull Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.mHeaders = headers;
        this.mParams = params;
        this.mResponseParser = responseParser;
        this.mListener = successListener;
    }    
    
    @Override
    protected Map<String, String> getParams() {
        return mParams;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        return mResponseParser.parseNetworkResponse(response);
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    /**
     * Attaches default headers such as User Agent and Authorization(if exists)
     * Currently only supports OAUTH2.
     *
     * @return request headers
     */
    @Override
    public Map<String, String> getHeaders() {
        if (mHeaders == null) {
            mHeaders = new HashMap<>();
        }

        try {
            Cannon cannon = Cannon.getInstance();

            // Set Authorization header
            CannonAuthenticator authenticator = cannon.getCannonAuthenticator();
            if (authenticator != null) {
                String authToken = authenticator.getAuthToken();

                if (authToken != null && !authToken.isEmpty()) {
                    CannonAuthenticator.AuthTokenType authTokenType =
                            authenticator.getAuthTokenType();

                    switch (authTokenType) {
                        case OAUTH2:
                            mHeaders.put("Authorization", "Bearer " + authToken);
                            break;
                    }
                }
            }

            // Set User Agent header to a special user agent string.
            mHeaders.put("User-Agent", cannon.getUserAgent());
        } catch (Cannon.NotLoadedException e) {
            e.printStackTrace();
        }

        return mHeaders;
    }
}
