package com.overturelabs.cannon.toolbox;

import com.android.volley.Request;
import com.overturelabs.Cannon;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CannonAuthenticator {
    private long REFRESH_LIMIT = 1000 * 60 * 5; // 5 minutes earlier

    public interface RefreshResourcePointCallback {
        void execute();
    }

    public enum AuthTokenType {
        BSDAUTH, CDSA, GSSAPI, JAAS, NMAS, OAUTH, OAUTH2, OID, OIDC, PAM, SASL, SSPI, XUDA
    }

    private String mAuthToken;
    private AuthTokenType mAuthTokenType;
    private long mAuthTokenExpiry;

    /*** (Optional) To Refresh Authentication Token ***/
    private RefreshResourcePointCallback mRefreshResourcePointCallback;
    private AtomicBoolean mRefreshRequestIsProcessing;
    private Queue<Request> mPendingRequestQueue;

    public CannonAuthenticator() {
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

    public CannonAuthenticator set(String authToken,
                                   AuthTokenType authTokenType) {
        mAuthToken = authToken;
        mAuthTokenType = authTokenType;

        return this;
    }

    public CannonAuthenticator set(String authToken,
                                   AuthTokenType authTokenType,
                                   long authTokenExpiry) {
        mAuthToken = authToken;
        mAuthTokenType = authTokenType;
        mAuthTokenExpiry = authTokenExpiry;

        return this;
    }

    /**
     * Checks whether there is a callback for the refresh resource point
     *
     * @return whether there is a callback for the refresh resource point
     */
    public boolean hasRefreshRequest() {
        return mRefreshResourcePointCallback != null;
    }

    /**
     * Set the callback for the refresh token resource point
     * Once set it assumes there would be a refreshing process
     *
     * @param callback refresh token resource point callback
     * @return this
     */
    public CannonAuthenticator setRefreshResourcePointCallback(RefreshResourcePointCallback callback) {
        mRefreshResourcePointCallback = null;
        mRefreshResourcePointCallback = callback;

        mRefreshRequestIsProcessing = new AtomicBoolean(false);
        mPendingRequestQueue = new ArrayDeque<>();

        return this;
    }

    /**
     * Change the refresh time limit
     *
     * @param newRefreshLimit new refresh time limit
     */
    public void changeRefreshLimit(long newRefreshLimit) {
        REFRESH_LIMIT = newRefreshLimit;
    }

    public void invalidate() {
        mAuthToken = null;
        mAuthTokenType = null;
        mAuthTokenExpiry = 0l;

        if (mRefreshResourcePointCallback != null) {
            mRefreshResourcePointCallback = null;
            mPendingRequestQueue.clear();
            mRefreshRequestIsProcessing.getAndSet(false);
        }
    }

    /**
     * Executes the refresh token request if its expired
     * If the refresh token request is processing, the request is added to the queue
     *
     * @param request request to process
     * @return whether refresh request has/had been executed
     */
    public boolean didRefreshRequestExecute(Request request) {
        if (mAuthTokenType == null || // Not logged in
                mRefreshResourcePointCallback == null) { // There isn't any refreshing process
            return false;
        }

        if (mRefreshRequestIsProcessing == null) {
            mRefreshRequestIsProcessing = new AtomicBoolean(false);
        }
        if (mPendingRequestQueue == null) {
            mPendingRequestQueue = new ArrayDeque<>();
        }

        // Add to Pending Queue if refresh request is processing
        if (mRefreshRequestIsProcessing.get()) {
            mPendingRequestQueue.add(request);
            return true;
        }

        // Execute refresh request if below limit
        final long now = new Date().getTime();
        final long difference = mAuthTokenExpiry - now;
        if (difference <= REFRESH_LIMIT) {
            mRefreshResourcePointCallback.execute();
            mRefreshRequestIsProcessing.getAndSet(true);
            mPendingRequestQueue.add(request);
            return true;
        }
        return false;
    }

    /**
     * Process the pending queue by adding it to the processing queue
     */
    public void processPendingQueue()
            throws Cannon.NotLoadedException {
        Cannon cannon = Cannon.getInstance();

        mRefreshRequestIsProcessing.getAndSet(false);

        if (mPendingRequestQueue != null) {
            cannon.addPendingRequests(mPendingRequestQueue);
            mPendingRequestQueue.clear();
        }
    }
}