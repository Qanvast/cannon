package com.overturelabs.cannon.toolbox;

import com.android.volley.Request;
import com.android.volley.Response;
import com.overturelabs.Cannon;

import java.util.HashMap;
import java.util.Map;

public abstract class FireRequest<T> extends Request<T> {
    private Map<String, String> mParams;
    private String mOAuth2Token = null;

    public FireRequest(int method, String url, GenericErrorListener genericErrorListener) {
        super(method, url, genericErrorListener);
    }

    public FireRequest(int method, String url, Map<String, String> params,
                          GenericErrorListener genericErrorListener) {
        super(method, url, genericErrorListener);

        this.mParams = params;
    }

    public FireRequest(int method, String url, String oAuth2Token,
                       GenericErrorListener genericErrorListener) {
        super(method, url, genericErrorListener);

        this.mOAuth2Token = oAuth2Token;
    }

    public FireRequest(int method, String url, Map<String, String> params,
                       String oAuth2Token, GenericErrorListener genericErrorListener) {
        super(method, url, genericErrorListener);

        this.mParams = params;
        this.mOAuth2Token = oAuth2Token;
    }

    @Override
    protected Map<String, String> getParams() {
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() {

        HashMap<String, String> header = new HashMap<String, String>();

        if (mOAuth2Token != null && mOAuth2Token.length() > 0) {
            header.put("Authorization", "Bearer " + mOAuth2Token);
        }

        // Set User Agent header to a special user agent string.
        header.put("User-Agent", Cannon.getUserAgent());

        return header;
    }
}
