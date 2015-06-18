package com.overturelabs.cannon.toolbox;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.overturelabs.Cannon;
import com.overturelabs.cannon.toolbox.parsers.ResponseParser;

import java.util.Map;

/**
 * Generic request class that supports OAuth 2.0 bearer tokens.
 *
 * @param <T>   Type of expected response object.
 * @author      Steve Tan
 */
public class RefreshRequest<T> extends GenericRequest<T> {
    public RefreshRequest(int method, String url,
                          final Map<String, String> headers,
                          final Map<String, String> params,
                          ResponseParser<T> responseParser,
                          Response.Listener<T> successListener,
                          Response.ErrorListener errorListener) {
        super(method, url, headers, null, params,
              responseParser, successListener, errorListener);
    }
    
    /**
    * Returns the {@link Priority} of this request; {@link Priority#IMMEDIATE} by default.
    */
    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }

    @Override
    protected void deliverResponse(T response) {
        super.deliverResponse(response);

        if (Cannon.isAuthenticatorEnabled()) {
            CannonAuthenticator.getInstance().processPendingQueue();
        }
    }
    
    /**
    * Clears Auth Token if Refresh Token has Expired
    */
    @Override
    public void deliverError(VolleyError error) {
        if (!Cannon.isAuthenticatorEnabled()) {
            super.deliverError(error);
            return;
        }

        final CannonAuthenticator authenticator = CannonAuthenticator.getInstance();
        if (error.networkResponse != null) {
            if (error.networkResponse.statusCode != 0) {
                // Refresh Token Expired
                authenticator.invalidate();
            } else {
                authenticator.processPendingQueue();
            }
        } else {
            // No/Limited Connectivity Error
            authenticator.processPendingQueue();
        }
        super.deliverError(error);
    }
}
