package com.overturelabs;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.overturelabs.cannon.BitmapLruCache;
import com.overturelabs.cannon.OkHttpStack;
import com.overturelabs.cannon.toolbox.CannonAuthenticator;
import com.overturelabs.cannon.toolbox.CannonImageLoader;
import com.overturelabs.cannon.toolbox.ImageLoaderCustomRetryPolicy;
import com.overturelabs.cannon.toolbox.ResourcePoint;
import com.overturelabs.cannon.toolbox.SwissArmyKnife;
import com.overturelabs.cannon.toolbox.gson.deserializers.DateDeserializer;
import com.overturelabs.cannon.toolbox.gson.deserializers.JsonMixedDeserializer;
import com.overturelabs.cannon.toolbox.requests.GenericRequest;
import com.overturelabs.cannon.toolbox.requests.MultipartRequest;
import com.overturelabs.cannon.toolbox.requests.RefreshRequest;
import com.overturelabs.cannon.toolbox.vOutOfMemory.BasicNetworkOOM;
import com.overturelabs.cannon.toolbox.vOutOfMemory.DiskBasedCacheOOM;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * CANNON! FIRES VOLLEY!
 *
 * @author Steve Tan
 */
public class Cannon {
    public static final String TAG = "Cannon";

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private static final int DEFAULT_DISK_CACHE_MEMORY_ALLOCATION_MB = 300; // 300 MiB
    private static final String DEFAULT_DISK_CACHE_NAME = "AmmunitionBox";

    private final static AtomicBoolean SAFETY_SWITCH = new AtomicBoolean(true); // If safety switch is set, you can't fire the cannon! Loading the cannon will disable the safety switch.

    private static Cannon sInstance;
    private static ImageLoaderCustomRetryPolicy sImageLoader;
    private static CannonImageLoader cannonImageLoader;

    private WeakReference<Context> mAppContext;
    private String mUserAgent = "Cannon/0.0.1 (Android)"; // Default user agent string
    private RequestQueue mRequestQueue;
    private CannonAuthenticator mCannonAuthenticator;
    private GsonBuilder mGsonBuilder; // To allow custom deserializers to be added in
    private Gson mGson;

    public static Cannon getInstance()
        throws NotLoadedException {
        synchronized (SAFETY_SWITCH) {
            if (sInstance == null) throw new NotLoadedException();
            return sInstance;
        }
    }

    /**
     * Load the cannon! You cannot fire any volleys if the cannon is not loaded, so load it up!
     *
     * @param context Current context. Cannon needs this to load the request queue.
     */
    public static void load(@NonNull Context context) {
        load(context, null, true);
    }

    /**
     * Load the cannon! You cannot fire any volleys if the cannon is not loaded, so load it up!
     *
     * @param context Current context. Cannon needs this to load the request queue.
     * @param diskCacheMemoryAllocMB Disk cache memory allocation in megabytes, null sets to default 300MB
     * @param outOfMemoryVersion Use Out Of Memory versions of BasicNetwork and DiskBasedCache, null sets to default false
     */
    public static void load(@NonNull Context context,
                            @Nullable Integer diskCacheMemoryAllocMB,
                            @Nullable Boolean outOfMemoryVersion) {
        /**
         * Let's lock on the safety switch first, so that only one thread can perform write operations
         * at any one time. Then we check if the safety switch is on; If the safety switch is on, we will
         * load cannon and then switch off the safety.
         */
        synchronized (SAFETY_SWITCH) {
            if (SAFETY_SWITCH.get()) {
                // Not loaded!
                if (sInstance == null) {
                    sInstance = new Cannon(context, diskCacheMemoryAllocMB, outOfMemoryVersion);
                    SAFETY_SWITCH.set(false);
                }
            }
        }
    }

    private Cannon(@NonNull Context context,
                   @Nullable Integer diskCacheMemoryAllocMB,
                   @Nullable Boolean outOfMemoryVersion) {
        try {
            /**
             * We load the cannon as part of the application
             * context to ensure the request queue persists
             * throughout the application lifecycle.
             */
            mAppContext = new WeakReference<>(context.getApplicationContext());

            PackageManager pm = context.getApplicationContext().getPackageManager();
            PackageInfo pInfo = pm.getPackageInfo(context.getPackageName(), 0);

            // Set globals
            String appVersion = pInfo.versionName;
            String appName = "";
            if (pInfo.applicationInfo != null) {
                CharSequence appNameChar = pm.getApplicationLabel(pInfo.applicationInfo);
                if (appNameChar != null) appName = appNameChar.toString();
            }

            // Build and set the custom user agent string
            // We lock on the safety switch when setting user agent because we this variable
            // is available via a static method and we don't want other threads to read this
            // in the middle of a write operation.
            synchronized (SAFETY_SWITCH) {
                mUserAgent = appName + '/' + appVersion + " (" + Build.MANUFACTURER + " " + Build.MODEL + " " + Build.DEVICE + "; " + Build.VERSION.RELEASE + "; )";
            }
            // Based on com.android.volley.toolbox.Volley.java newRequestQueue method.

            if (diskCacheMemoryAllocMB == null) {
                diskCacheMemoryAllocMB = DEFAULT_DISK_CACHE_MEMORY_ALLOCATION_MB;
            }

            File cacheDir;
            int DISK_CACHE_MEMORY_ALLOCATION;
            if (Environment.isExternalStorageEmulated()) {
                cacheDir = new File(context.getExternalCacheDir(), DEFAULT_DISK_CACHE_NAME);
                DISK_CACHE_MEMORY_ALLOCATION = diskCacheMemoryAllocMB * 1024 * 1024 * 2;
            } else {
                cacheDir = new File(context.getCacheDir(), DEFAULT_DISK_CACHE_NAME);
                DISK_CACHE_MEMORY_ALLOCATION = diskCacheMemoryAllocMB * 1024 * 1024;
            }

            // Create a DiskBasedCache of 300 MiB for internal storage,
            // 300MiB*2=600MiB for external storage
            HttpStack httpStack = new OkHttpStack();
            if (outOfMemoryVersion != null && outOfMemoryVersion) {
                mRequestQueue = new RequestQueue(
                        new DiskBasedCacheOOM(cacheDir, DISK_CACHE_MEMORY_ALLOCATION),
                        new BasicNetworkOOM(httpStack)
                );
            } else {
                mRequestQueue = new RequestQueue(
                        new DiskBasedCache(cacheDir, DISK_CACHE_MEMORY_ALLOCATION),
                        new BasicNetwork(httpStack)
                );
            }
            mRequestQueue.start();

            cannonImageLoader = new CannonImageLoader(mRequestQueue, new BitmapLruCache());

            // Create gson
            if (mGsonBuilder == null) {
                mGsonBuilder = new GsonBuilder();
            }
            mGsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
            mGson = null;
            mGson = mGsonBuilder.create();

            mCannonAuthenticator = null;
        } catch (PackageManager.NameNotFoundException e) {
            // Crashlytics.logException(e);
        }
    }

    public CannonAuthenticator getCannonAuthenticator() {
        return mCannonAuthenticator;
    }

    /**
     * FIRE ALL ZE CANNONS! FIRE AT WILLZ!
     *
     * @param request {@link com.android.volley.Request} to fire!
     * @return Returns true if cannon was fired, false if otherwise.
     * @throws NotLoadedException OMGZ! ZE CANNON IS NOT ZE LOADED! If the Cannon is not loaded, we can't fire it, can we?
     */
    public boolean fire(Request request) throws NotLoadedException {
        if (SAFETY_SWITCH.get()) {
            // Alas, my captain! The cannon is not loaded!
            throw new NotLoadedException();
        } else {
            // Null pointer and network available checking
            boolean allOkay = mRequestQueue != null;
            if (mAppContext != null) {
                Context appContext = mAppContext.get();
                allOkay = appContext == null ||
                        SwissArmyKnife.isAppConnectedToNetwork(appContext);
            }
            if (!allOkay) return false;

            // Execute refresh request if exists
            if (mCannonAuthenticator != null &&
                    mCannonAuthenticator.hasRefreshRequest() &&
                    !(request instanceof RefreshRequest)) {
                boolean refreshRequestExecuted =
                        mCannonAuthenticator.didRefreshRequestExecute(request);
                if (refreshRequestExecuted) return true;
            }

            return mRequestQueue.add(request) != null;
        }
    }

    /**
     * CONCENTRATE FIRE AT ZE POINT! FIRE! Fires a simple request at the resource point.
     *
     * @param resourcePoint        Cannon Resource Point. Refer to {@link ResourcePoint}.
     * @param method               HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param resourcePathParams   Parameters for populating placeholders in the skeleton resource path.
     * @param requestHeaders       Request headers.
     * @param requestParams        Request body. If method is {@code GET}, provided parameters will be treated as URL.
     *                             queries and will not be added to the request body but appended to the URL.
     * @param encoding             Charset to encode the URL in.
     * @param successListener      Success {@link com.android.volley.Response.Listener}.
     * @param errorListener        {@link com.android.volley.Response.ErrorListener}.
     * @param <T>                  Type of expected response object.
     * @return Returns true if cannon was fired, false if otherwise.
     * @throws NotLoadedException OMGZ! ZE CANNON IS NOT ZE LOADED! If the Cannon is not loaded, we can't fire it, can we?
     * @throws java.io.UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    public <T> boolean fireAt(@NonNull ResourcePoint<T> resourcePoint,
                              int method,
                              @Nullable final Map<String, String> resourcePathParams,
                              @Nullable final Map<String, String> requestHeaders,
                              @Nullable final Map<String, String> requestParams,
                              @Nullable String encoding,
                              @NonNull Response.Listener<T> successListener,
                              @NonNull Response.ErrorListener errorListener)
            throws NotLoadedException, UnsupportedEncodingException {
        final String url = getUrl(resourcePoint, method, resourcePathParams, requestParams, encoding);
        final GenericRequest<T> request = new GenericRequest<>(method, url, requestHeaders, requestParams, resourcePoint.getResponseParser(), successListener, errorListener);
        request.setTag(resourcePoint.getClass().getName());
        return fire(request);
    }

    /**
     * CONCENTRATE FIRE AT ZE POINT! FIRE! Fires a multi-part request at the resource point.
     *
     * @param resourcePoint        Cannon Resource Point. Refer to {@link ResourcePoint}.
     * @param method               HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param resourcePathParams   Parameters for populating placeholders in the skeleton resource path.
     * @param requestHeaders       Request headers.
     * @param requestParams        Request body. If method is {@code GET}, provided parameters will be treated as URL.
     *                             queries and will not be added to the request body but appended to the URL.
     * @param encoding             Charset to encode the URL in.
     * @param files                Files you want to send. The map should contain the form field name as the
     *                             entry's key and a {@link android.util.Pair} containing the
     *                             actual {@link java.io.File} and MIME type string.
     *                             Refer to {@link android.content.ContentResolver#getType(android.net.Uri)}.
     * @param successListener      Success {@link com.android.volley.Response.Listener}.
     * @param errorListener        {@link com.android.volley.Response.ErrorListener}.
     * @param <T>                  Type of expected response object.
     * @return Returns true if cannon was fired, false if otherwise.
     * @throws NotLoadedException OMGZ! ZE CANNON IS NOT ZE LOADED! If the Cannon is not loaded, we can't fire it, can we?
     * @throws java.io.UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    public <T> boolean fireMultipartRequest(@NonNull ResourcePoint<T> resourcePoint,
                                            int method,
                                            @Nullable final Map<String, String> resourcePathParams,
                                            @Nullable final Map<String, String> requestHeaders,
                                            @Nullable final Map<String, String> requestParams,
                                            @Nullable String encoding,
                                            @NonNull final Map<String, Pair<File, String>> files,
                                            @NonNull Response.Listener<T> successListener,
                                            @NonNull Response.ErrorListener errorListener)
            throws NotLoadedException, UnsupportedEncodingException {
        final String url = getUrl(resourcePoint, method, resourcePathParams, requestParams, encoding);
        final MultipartRequest<T> request = new MultipartRequest<>(method, url, requestHeaders, requestParams, files, resourcePoint.getResponseParser(), successListener, errorListener);
        request.setTag(resourcePoint.getClass().getName());
        return fire(request);
    }

    /**
     * CONCENTRATE FIRE AT ZE POINT! FIRE! Fires a refresh token request at the resource point.
     *
     * @param resourcePoint        Cannon Resource Point. Refer to {@link ResourcePoint}.
     * @param method               HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param resourcePathParams   Parameters for populating placeholders in the skeleton resource path.
     * @param requestHeaders       Request headers.
     * @param requestParams        Request body. If method is {@code GET}, provided parameters will be treated as URL.
     *                             queries and will not be added to the request body but appended to the URL.
     * @param encoding             Charset to encode the URL in.
     * @param successListener      Success {@link com.android.volley.Response.Listener}.
     * @param errorListener        {@link com.android.volley.Response.ErrorListener}.
     * @param <T>                  Type of expected response object.
     * @return Returns true if cannon was fired, false if otherwise.
     * @throws NotLoadedException OMGZ! ZE CANNON IS NOT ZE LOADED! If the Cannon is not loaded, we can't fire it, can we?
     * @throws java.io.UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    public <T> boolean fireRefreshRequest(@NonNull ResourcePoint<T> resourcePoint,
                                          int method,
                                          @Nullable final Map<String, String> resourcePathParams,
                                          @Nullable final Map<String, String> requestHeaders,
                                          @Nullable final Map<String, String> requestParams,
                                          @Nullable String encoding,
                                          @NonNull Response.Listener<T> successListener,
                                          @NonNull Response.ErrorListener errorListener)
            throws NotLoadedException, UnsupportedEncodingException {
        final String url = getUrl(resourcePoint, method, resourcePathParams, requestParams, encoding);
        final RefreshRequest<T> request = new RefreshRequest<>(method, url, requestHeaders, requestParams, resourcePoint.getResponseParser(), successListener, errorListener);
        request.setTag(resourcePoint.getClass().getName());
        return fire(request);
    }

    /**
     * Get url based on {@link ResourcePoint#getUrl}, {@code resourcePathParams}, {@code requestParams} and {@code encoding}
     *
     * @param resourcePoint        Resource Point. Refer to {@link ResourcePoint}.
     * @param method               HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param resourcePathParams   Parameters for populating placeholders in the skeleton resource path.
     * @param requestParams        Request body. If method is {@code GET}, provided parameters will be treated as URL.
     * @param encoding             Charset to encode the URL in.
     * @param <T>                  Type of expected response object.
     * @return Returns url string
     * @throws java.io.UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    private <T> String getUrl(@NonNull ResourcePoint<T> resourcePoint,
                        int method,
                        @Nullable final Map<String, String> resourcePathParams,
                        @Nullable final Map<String, String> requestParams,
                        @Nullable String encoding)
            throws UnsupportedEncodingException {
        if (encoding == null) encoding = DEFAULT_PARAMS_ENCODING;

        if (method == Request.Method.GET) {
            return resourcePoint.getUrl(resourcePathParams, requestParams, encoding);
        } else {
            return resourcePoint.getUrl(resourcePathParams, encoding);
        }
    }

    /**
     * Get Network User Agent
     *
     * @return Network User Agent
     */
    public String getUserAgent() {
        // Don't lock on static methods, we'll be locking the entire class.
        // We lock on the safety switch to make sure the string we get is
        // not in the midst of a write cycle.
        synchronized (SAFETY_SWITCH) {
            return mUserAgent;
        }
    }

    /**
     * Get Volley's Image Loader
     *
     * @return Volley's Image Loader
     * @throws NotLoadedException Cannon has not been loaded
     */
    public static CannonImageLoader getImageLoader() throws NotLoadedException {
        /**
         * No need to lock on SAFETY_SWITCH here since we implicitly assumes
         * that Cannon is loaded before user can call this function.
         */
        if (SAFETY_SWITCH.get()) {
            // Well it looks like the cannon was not loaded. I'll be damned.
            throw new NotLoadedException();
        } else {
            return cannonImageLoader;
        }
    }

    public void clearCache() {
        if (mRequestQueue != null && mRequestQueue.getCache() != null) {
            mRequestQueue.getCache().clear();
        }
    }

    /**
     * Disables Cannon Authenticator Manager
     */
    public void disableAuthenticator() {
        synchronized (SAFETY_SWITCH) {
            if (mCannonAuthenticator == null) return;

            mCannonAuthenticator.invalidate();
            mCannonAuthenticator = null;
        }
    }

    /**
     * Enables Cannon Authenticator Manager
     *
     * @param authToken authentication token
     * @param authTokenType type of authentication token. Refer to {@link CannonAuthenticator.AuthTokenType}.
     */
    public void enableAuthenticator(String authToken,
                                    CannonAuthenticator.AuthTokenType authTokenType) {
        synchronized (SAFETY_SWITCH) {
            mCannonAuthenticator = null;
            mCannonAuthenticator = new CannonAuthenticator();

            mCannonAuthenticator
                    .set(authToken, authTokenType);
        }
    }

    /**
     * Enables Cannon Authenticator Manager with Refresh Token capabilities
     *
     * @param authToken authentication token
     * @param authTokenType type of authentication token. Refer to {@link CannonAuthenticator.AuthTokenType}.
     * @param authTokenExpiry authentication token expiry date in milliseconds
     * @param refreshCallback authentication token refresh callback
     */
    public void enableAuthenticatorWithRefresh(String authToken,
                                               CannonAuthenticator.AuthTokenType authTokenType,
                                               long authTokenExpiry,
                                               CannonAuthenticator.RefreshResourcePointCallback refreshCallback) {
        synchronized (SAFETY_SWITCH) {
            mCannonAuthenticator = null;
            mCannonAuthenticator = new CannonAuthenticator();

            mCannonAuthenticator
                    .set(authToken, authTokenType, authTokenExpiry)
                    .setRefreshResourcePointCallback(refreshCallback);
        }
    }

    /**
     * Adds a queue of pending requests to the processing/network queue
     *
     * @param pendingRequests pending requests
     */
    public void addPendingRequests(Queue<Request> pendingRequests) {
        while (!pendingRequests.isEmpty()) {
            Request request = pendingRequests.poll();
            mRequestQueue.add(request);
        }
    }

    /**
     * Get Gson
     *
     * @return Gson
     */
    @NonNull
    public Gson getGson() {
        return mGson;
    }

    /**
     * Get Gson Builder main purpose is to add custom deserializers which {@link #recreateGson()} must be called to be in effect.
     *
     * @return Gson Builder
     */
    public GsonBuilder getGsonBuilder() {
        return mGsonBuilder;
    }

    /**
     * Recreates the gson builder purpose is to be called after {@link #getGsonBuilder()}
     */
    public void recreateGson() {
        if (mGsonBuilder != null) {
            mGson = null;
            mGson = mGsonBuilder.create();
        }
    }

    /**
     * Adds {@link com.overturelabs.cannon.toolbox.gson.models.JsonMixed} child classes to be used with {@link JsonMixedDeserializer}
     *
     * @param jsonMixedClasses {@link com.overturelabs.cannon.toolbox.gson.models.JsonMixed} child classes
     */
    public void addJsonMixedClassDeserializers(Class<?>... jsonMixedClasses) {
        if (mGsonBuilder != null &&
                jsonMixedClasses != null &&
                jsonMixedClasses.length > 0) {
            for (Class<?> jsonMixedClass : jsonMixedClasses) {
                mGsonBuilder.registerTypeAdapter(jsonMixedClass, new JsonMixedDeserializer<>());
            }
            mGson = null;
            mGson = mGsonBuilder.create();
        }
    }

    /**
     * Adds cannon's default request headers
     *
     * @param headers user supplied request headers
     */
    public void addCannonDefaultHeaders(Map<String, String> headers) {
        synchronized (SAFETY_SWITCH) {
            // Set Authorization header
            if (mCannonAuthenticator != null) {
                mCannonAuthenticator.updateCannonHeaders(headers);
            }

            // Set User Agent header to a special user agent string.
            if (headers.get("User-Agent") == null) {
                headers.put("User-Agent", mUserAgent);
            }
        }
    }

    /**
     * Cancel all requests that match the {@link com.android.volley.RequestQueue.RequestFilter}.
     *
     * @param filter
     */
    public void cancelAll(final RequestQueue.RequestFilter filter) {
        mRequestQueue.cancelAll(filter);
    }

    /**
     * Cancel all requests that match the tag.
     *
     * @param tag
     */
    public void cancelAll(final Object tag) {
        mRequestQueue.cancelAll(tag);
    }

    public static class NotLoadedException extends Exception {

        public NotLoadedException() {
            super("Howdy cowboy! You might wanna load the damn cannon first?");
        }
    }
}
