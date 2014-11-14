package com.overturelabs.cannon.toolbox;

import com.android.volley.Request;
import com.android.volley.Response;
import com.overturelabs.Cannon;

import java.util.HashMap;
import java.util.Map;

public abstract class FireRequest<T> extends Request<T> {
    private Map<String, String> mParams;

    public FireRequest(int method, String url, GenericErrorListener genericErrorListener) {
        super(method, url, genericErrorListener);
    }

    public FireRequest(int method, String url, Map<String, String> params,
                          GenericErrorListener genericErrorListener) {
        super(method, url, genericErrorListener);

        this.mParams = params;
    }

    @Override
    protected Map<String, String> getParams() {
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() {

        HashMap<String, String> header = new HashMap<String, String>();

        // TODO: Need to inject user token somehow
//        String userToken = UserUtil.getUserToken();
//
//        if (userToken != null) {
//            header.put("Authorization", "Bearer " + UserUtil.getUserToken());
//        }

        // Set User Agent header to a special user agent string.
        header.put("User-Agent", Cannon.getUserAgent());

        return header;
    }
}
