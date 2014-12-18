package com.overturelabs.cannon.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;

/**
 * {@link com.overturelabs.cannon.toolbox.ResponseParser} grants users
 * the flexibility to parse the response any way they want it.
 */
public interface ResponseParser<T> {

    public Response<T> parseNetworkResponse(NetworkResponse response);
}
