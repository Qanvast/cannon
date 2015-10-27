package com.overturelabs.cannon.toolbox.requests;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.overturelabs.Cannon;
import com.overturelabs.cannon.toolbox.CannonAuthenticator;
import com.overturelabs.cannon.toolbox.parsers.ResponseParser;

import java.util.Map;

/**
 * Generic refresh request class
 *
 * @param <T>   Type of expected response object.
 * @author      Derrick Lee
 */
public class RefreshRequest<T> extends GenericRequest<T> {
    public RefreshRequest(int method, String url,
                          @Nullable final Map<String, String> headers,
                          @Nullable final Map<String, String> params,
                          @NonNull ResponseParser<T> responseParser,
                          @NonNull Response.Listener<T> successListener,
                          @NonNull Response.ErrorListener errorListener) {
        super(method, url, headers, params,
              responseParser, successListener, errorListener);
    }
    
    /**
    * Returns the {@link Priority} of this request; {@link Priority#IMMEDIATE} by default.
    */
    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }

    /**
     * Process pending requests queue IF cannon/authenticator/refresh request all exists
     *
     * @param response network response
     */
    @Override
    protected void deliverResponse(T response) {
        super.deliverResponse(response);

        try {
            Cannon cannon = Cannon.getInstance();
            CannonAuthenticator authenticator = cannon.getCannonAuthenticator();
            if (authenticator != null &&
                    authenticator.hasRefreshRequest()) {
                authenticator.processPendingQueue();
            }
        } catch (Cannon.NotLoadedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invalidate authenticator if refresh token had expired.
     * Otherwise process the pending queue.
     *
     * @param error network error given by Volley
     */
    @Override
    public void deliverError(VolleyError error) {
        try {
            Cannon cannon = Cannon.getInstance();
            CannonAuthenticator authenticator = cannon.getCannonAuthenticator();
            if (authenticator != null &&
                    authenticator.hasRefreshRequest()) {
                // Refresh Token Expired
                if (error.networkResponse != null &&
                        error.networkResponse.statusCode == 400) {
                    authenticator.invalidate();
                }
                // No/Limited Connectivity or Server Error
                else {
                    authenticator.processPendingQueue();
                }
            }
        }
        catch(Cannon.NotLoadedException e){
            e.printStackTrace();
        }

        super.deliverError(error);
    }
}
