package com.overturelabs.cannon.toolbox.parsers;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;

/**
 * {@link ResponseParser} grants users
 * the flexibility to parse the response any way they want it.
 */
public interface ResponseParser<T> {

    Response<T> parseNetworkResponse(NetworkResponse response);
}
