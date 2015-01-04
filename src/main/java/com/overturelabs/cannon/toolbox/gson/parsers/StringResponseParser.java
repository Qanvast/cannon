package com.overturelabs.cannon.toolbox.gson.parsers;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.overturelabs.cannon.toolbox.ResponseParser;

/**
 * {@link com.overturelabs.cannon.toolbox.ResponseParser} for {@link java.lang.String} responses.
 * @author Steve Tan
 */
public class StringResponseParser implements ResponseParser<String> {
    @Override
    public Response<String> parseNetworkResponse(NetworkResponse response) {
        return null;
    }
}
