package com.overturelabs.cannon.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;

/**
 * {@link com.overturelabs.cannon.toolbox.ResponseParser} for {@link java.lang.String} responses.
 */
public class StringResponseParser implements ResponseParser<String> {
    @Override
    public Response<String> parseNetworkResponse(NetworkResponse response) {
        return null;
    }
}
