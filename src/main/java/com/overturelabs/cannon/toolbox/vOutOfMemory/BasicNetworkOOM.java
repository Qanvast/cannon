package com.overturelabs.cannon.toolbox.vOutOfMemory;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.ByteArrayPool;
import com.android.volley.toolbox.HttpStack;

/**
 * Created by derricklee on 4/1/15.
 */
public class BasicNetworkOOM extends BasicNetwork {
    public BasicNetworkOOM(HttpStack httpStack) {
        super(httpStack);
    }

    public BasicNetworkOOM(HttpStack httpStack, ByteArrayPool pool) {
        super(httpStack, pool);
    }

    @Override
    public NetworkResponse performRequest(Request<?> request) throws VolleyError {
        try {
            return super.performRequest(request);
        } catch (java.lang.OutOfMemoryError error) {
            throw new OutOfMemoryError();
        }
    }
}
