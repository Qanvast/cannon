package com.overturelabs.cannon.toolbox;

import com.android.volley.Request;
import com.overturelabs.Cannon;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CannonAuthenticator {
    private final long REFRESH_LIMIT = 1000 * 60 * 5; // 5 minutes earlier

    public interface RefreshResourcePointCallback {
        void execute();
    }

    public enum AuthTokenType {
        BSDAUTH, CDSA, GSSAPI, JAAS, NMAS, OAUTH, OAUTH2, OID, OIDC, PAM, SASL, SSPI, XUDA
    }

    private String mAuthToken;
    private long mAuthTokenExpiry;
    private AuthTokenType mAuthTokenType;
    private RefreshResourcePointCallback mRefreshResourcePointCallback;

    private AtomicBoolean mRefreshRequestIsProcessing = new AtomicBoolean(false);
    private Queue<Request> mPendingRequestQueue = new ArrayDeque<>();

    private static CannonAuthenticator sInstance;

    public static CannonAuthenticator getInstance() {
        if (sInstance == null) {
            sInstance = new CannonAuthenticator();
        }

        return sInstance;
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public CannonAuthenticator setAuthToken(String authToken) {
        mAuthToken = authToken;
        return this;
    }

    public AuthTokenType getAuthTokenType() {
        return mAuthTokenType;
    }

    public CannonAuthenticator setAuthTokenType(AuthTokenType authTokenType) {
        mAuthTokenType = authTokenType;
        return this;
    }

    public long getAuthTokenExpiry() {
        return mAuthTokenExpiry;
    }

    public CannonAuthenticator setAuthTokenExpiry(long authTokenExpiry) {
        mAuthTokenExpiry = authTokenExpiry;
        return this;
    }

    public void invalidate() {
        mAuthToken = null;
        mAuthTokenType = null;
        mAuthTokenExpiry = 0l;
        mRefreshResourcePointCallback = null;
        mPendingRequestQueue.clear();
        mRefreshRequestIsProcessing.getAndSet(false);
    }

    public CannonAuthenticator set(String authToken,
                    AuthTokenType authTokenType,
                    long authTokenExpiry) {
        mAuthToken = authToken;
        mAuthTokenType = authTokenType;
        mAuthTokenExpiry = authTokenExpiry;

        return this;
    }

    public CannonAuthenticator setRefreshResourcePointCallback(RefreshResourcePointCallback callback) {
        mRefreshResourcePointCallback = null;
        mRefreshResourcePointCallback = callback;

        return this;
    }

    public boolean isRefreshResoucePointCallbackSet() {
        return mRefreshResourcePointCallback != null;
    }

    /**
     * Executes the refresh token request if its expired
     * If the refresh token request is processing, the request is added to the queue
     *
     * @param request
     * @return
     */
    public boolean didRefreshRequestExecute(Request request) {
        if (mAuthTokenType == null || mRefreshResourcePointCallback == null) {
            return false;
        }

        // Add to Pending Queue if refresh request is processing
        if (mRefreshRequestIsProcessing.get()) {
            mPendingRequestQueue.add(request);
            return true;
        }

        final long now = new Date().getTime();
        final long difference = mAuthTokenExpiry - now;
        // Execute refresh request if below limit
        if (difference <= REFRESH_LIMIT) {
            mRefreshResourcePointCallback.execute();
            mRefreshRequestIsProcessing.getAndSet(true);
            mPendingRequestQueue.add(request);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Process the pending queue by adding it to the processing queue
     */
    public void processPendingQueue() {
        mRefreshRequestIsProcessing.getAndSet(false);

        if (mPendingRequestQueue == null) return;

        Cannon.addRequestQueue(mPendingRequestQueue);
        mPendingRequestQueue.clear();
    }

    /**
     * Clears the pending queue
     */
    public void clearPendingQueue() {
        mRefreshRequestIsProcessing.getAndSet(false);

        if (mPendingRequestQueue == null) return;
        mPendingRequestQueue.clear();
    }
}