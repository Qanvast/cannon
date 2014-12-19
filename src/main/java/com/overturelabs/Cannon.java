package com.overturelabs;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.ImageLoader;
import com.overturelabs.cannon.BitmapLruCache;
import com.overturelabs.cannon.OkHttpStack;
import com.overturelabs.cannon.toolbox.GenericRequest;
import com.overturelabs.cannon.toolbox.MultipartRequest;
import com.overturelabs.cannon.toolbox.ResourcePoint;
import com.overturelabs.cannon.toolbox.SwissArmyKnife;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * CANNON! FIRES VOLLEY!
 *
 * @author Steve Tan
 */
public class Cannon {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private static final int DISK_CACHE_MEMORY_ALLOCATION = 300; // 300 MiB
    private static final String DISK_CACHE_NAME = "AmmunitionBox";
    private static final String TAG = "Cannon";

    private static final AtomicBoolean SAFETY_SWITCH = new AtomicBoolean(true); // If safety switch is set, you can't fire the cannon! Loading the cannon will disable the safety switch.

    private static String sUserAgent = "Cannon/0.0.1 (Android)"; // Default user agent string

    private static Cannon sInstance;
    private static Context sApplicationContext;
    private static HashMap<Class<? extends ResourcePoint>, ResourcePoint<?>> sResourcePoints = new HashMap<>();

    private static RequestQueue sRequestQueue;
    private static ImageLoader sImageLoader;

    private Cannon(Context context, String appName) {
        try {
            /**
             * We load the cannon as part of the application
             * context to ensure the request queue persists
             * throughout the application lifecycle.
             */
            sApplicationContext = context.getApplicationContext();

            PackageInfo pInfo = sApplicationContext.getPackageManager().getPackageInfo(sApplicationContext.getPackageName(), 0);

            // Set globals
            String appVersion = pInfo.versionName;

            // Build and set the custom user agent string
            // We lock on the safety switch when setting user agent because we this variable
            // is available via a static method and we don't want other threads to read this
            // in the middle of a write operation.
            synchronized (SAFETY_SWITCH) {
                sUserAgent = appName + '/' + appVersion + " (" + Build.MANUFACTURER + " " + Build.MODEL + " " + Build.DEVICE + "; " + Build.VERSION.RELEASE + "; )";
            }

            // Based on com.android.volley.toolbox.Volley.java newRequestQueue method.
            File cacheDir = new File(sApplicationContext.getCacheDir(), DISK_CACHE_NAME);

            // Create a DiskBasedCache of 300 MiB
            DiskBasedCache diskBasedCache
                    = new DiskBasedCache(cacheDir, DISK_CACHE_MEMORY_ALLOCATION * 1024 * 1024);

            HttpStack httpStack = new OkHttpStack();

            sRequestQueue = new RequestQueue(diskBasedCache, new BasicNetwork(httpStack));
            sRequestQueue.start();

            sImageLoader = new ImageLoader(sRequestQueue, new BitmapLruCache());
        } catch (PackageManager.NameNotFoundException e) {
            // Crashlytics.logException(e);
        }
    }

    /**
     * Load the cannon! You cannot fire any volleys if the cannon is not loaded, so load it up!
     *
     * @param context Current context. Cannon needs this to load the request queue.
     */
    public static Cannon load(Context context) {
        return load(context, TAG);
    }

    /**
     * Load the cannon! You cannot fire any volleys if the cannon is not loaded, so load it up!
     *
     * @param context Current context. Cannon needs this to load the request queue.
     * @param appName Application name.
     */
    public static Cannon load(Context context, String appName) {
        /**
         * Let's lock on the safety switch first, so that only one thread can perform write operations
         * at any one time. Then we check if the safety switch is on; If the safety switch is on, we will
         * load cannon and then switch off the safety.
         */
        synchronized (SAFETY_SWITCH) {
            if (SAFETY_SWITCH.get()) {
                // Not loaded!
                if (sInstance == null) {
                    sInstance = new Cannon(context, appName);
                    SAFETY_SWITCH.set(false);
                }
            }
        }

        return sInstance;
    }

    /**
     * Prepare a resource point for firing.
     *
     * @param resourcePoint The {@link com.overturelabs.cannon.toolbox.ResourcePoint} that you want to prepare.
     */
    public static void prepare(ResourcePoint<?> resourcePoint) {
        sResourcePoints.put(resourcePoint.getClass(), resourcePoint);
    }

    /**
     * Prepare a list of resource points for firing.
     *
     * @param resourcePoints The {@link com.overturelabs.cannon.toolbox.ResourcePoint}s that you want to prepare.
     */
    public static void prepare(ResourcePoint... resourcePoints) {
        for (ResourcePoint resourcePoint : resourcePoints) {
            prepare(resourcePoint);
        }
    }

    /**
     * FIRE ALL ZE CANNONS! FIRE AT WILLZ!
     *
     * @param request {@link com.android.volley.Request} to fire!
     * @return Returns true if cannon was fired, false if otherwise.
     * @throws NotLoadedException OMGZ! ZE CANNON IS NOT ZE LOADED! If the Cannon is not loaded, we can't fire it, can we?
     */
    public static boolean fire(Request request) throws NotLoadedException {
        if (SAFETY_SWITCH.get()) {
            // Alas, my captain! The cannon is not loaded!
            throw new NotLoadedException();
        } else {
            return SwissArmyKnife.isAppConnectedToNetwork(sApplicationContext)
                    && sInstance != null && sInstance.sRequestQueue != null
                    && sInstance.sRequestQueue.add(request) != null;
        }
    }

    /**
     * CONCENTRATE FIRE AT ZE POINT! FIRE! Fires a simple request at the resource point.
     *
     * @param classOfResourcePoint {@link java.lang.Class} of {@link com.overturelabs.cannon.toolbox.ResourcePoint}.
     * @param method               HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param resourcePathParams   Parameters for populating placeholders in the skeleton resource path.
     * @param requestParams        Request body. If method is {@code GET}, provided parameters will be treated as URL.
     *                             queries and will not be added to the request body but appended to the URL.
     * @param oAuth2Token          OAuth 2.0 token to be inserted into the request header.
     * @param successListener      Success {@link com.android.volley.Response.Listener}.
     * @param errorListener        {@link com.android.volley.Response.ErrorListener}.
     * @param <T>                  Type of expected response object.
     * @return Returns true if cannon was fired, false if otherwise.
     * @throws NotLoadedException OMGZ! ZE CANNON IS NOT ZE LOADED! If the Cannon is not loaded, we can't fire it, can we?
     * @throws java.io.UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    public static <T> boolean fireAt(Class<? extends ResourcePoint<T>> classOfResourcePoint, int method,
                                     final Map<String, String> resourcePathParams,
                                     final Map<String, String> requestParams,
                                     String oAuth2Token,
                                     Response.Listener<T> successListener,
                                     Response.ErrorListener errorListener)
            throws NotLoadedException, UnsupportedEncodingException {
        return fireAt(classOfResourcePoint, method, resourcePathParams, requestParams, DEFAULT_PARAMS_ENCODING, oAuth2Token, successListener, errorListener);
    }

    /**
     * CONCENTRATE FIRE AT ZE POINT! FIRE! Fires a simple request at the resource point.
     *
     * @param classOfResourcePoint {@link java.lang.Class} of {@link com.overturelabs.cannon.toolbox.ResourcePoint}.
     * @param method               HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param resourcePathParams   Parameters for populating placeholders in the skeleton resource path.
     * @param requestParams        Request body. If method is {@code GET}, provided parameters will be treated as URL.
     *                             queries and will not be added to the request body but appended to the URL.
     * @param encoding             Charset to encode the URL in.
     * @param oAuth2Token          OAuth 2.0 token to be inserted into the request header.
     * @param successListener      Success {@link com.android.volley.Response.Listener}.
     * @param errorListener        {@link com.android.volley.Response.ErrorListener}.
     * @param <T>                  Type of expected response object.
     * @return Returns true if cannon was fired, false if otherwise.
     * @throws NotLoadedException OMGZ! ZE CANNON IS NOT ZE LOADED! If the Cannon is not loaded, we can't fire it, can we?
     * @throws java.io.UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    public static <T> boolean fireAt(Class<? extends ResourcePoint<T>> classOfResourcePoint, int method,
                                     final Map<String, String> resourcePathParams,
                                     final Map<String, String> requestParams,
                                     String encoding,
                                     String oAuth2Token,
                                     Response.Listener<T> successListener,
                                     Response.ErrorListener errorListener)
            throws NotLoadedException, UnsupportedEncodingException {
        ResourcePoint<T> resourcePoint = (ResourcePoint<T>) sResourcePoints.get(classOfResourcePoint);

        String url;

        if (method == Request.Method.GET) {
            url = resourcePoint.getUrl(resourcePathParams, requestParams, encoding);
        } else {
            url = resourcePoint.getUrl(resourcePathParams, encoding);
        }

        return fire(new GenericRequest<>(method, url, requestParams, oAuth2Token, resourcePoint.getResponseParser(), successListener, errorListener));
    }

    /**
     * CONCENTRATE FIRE AT ZE POINT! FIRE! Fires a multi-part request at the resource point.
     *
     * @param classOfResourcePoint {@link java.lang.Class} of {@link com.overturelabs.cannon.toolbox.ResourcePoint}.
     * @param method               HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param resourcePathParams   Parameters for populating placeholders in the skeleton resource path.
     * @param requestParams        Request body. If method is {@code GET}, provided parameters will be treated as URL.
     *                             queries and will not be added to the request body but appended to the URL.
     * @param oAuth2Token          OAuth 2.0 token to be inserted into the request header.
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
    public static <T> boolean fireAt(Class<? extends ResourcePoint<T>> classOfResourcePoint, int method,
                                     final Map<String, String> resourcePathParams,
                                     final Map<String, String> requestParams,
                                     final Map<String, Pair<File, String>> files,
                                     String oAuth2Token,
                                     Response.Listener<T> successListener,
                                     Response.ErrorListener errorListener)
            throws NotLoadedException, UnsupportedEncodingException {
        return fireAt(classOfResourcePoint, method, resourcePathParams, requestParams, DEFAULT_PARAMS_ENCODING, files, oAuth2Token, successListener, errorListener);
    }

    /**
     * CONCENTRATE FIRE AT ZE POINT! FIRE! Fires a multi-part request at the resource point.
     *
     * @param classOfResourcePoint {@link java.lang.Class} of {@link com.overturelabs.cannon.toolbox.ResourcePoint}.
     * @param method               HTTP request method. Refer to {@link com.android.volley.Request.Method}.
     * @param resourcePathParams   Parameters for populating placeholders in the skeleton resource path.
     * @param requestParams        Request body. If method is {@code GET}, provided parameters will be treated as URL.
     *                             queries and will not be added to the request body but appended to the URL.
     * @param encoding             Charset to encode the URL in.
     * @param oAuth2Token          OAuth 2.0 token to be inserted into the request header.
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
    public static <T> boolean fireAt(Class<? extends ResourcePoint<T>> classOfResourcePoint, int method,
                                     final Map<String, String> resourcePathParams,
                                     final Map<String, String> requestParams,
                                     String encoding,
                                     final Map<String, Pair<File, String>> files,
                                     String oAuth2Token,
                                     Response.Listener<T> successListener,
                                     Response.ErrorListener errorListener)
            throws NotLoadedException, UnsupportedEncodingException {
        ResourcePoint<T> resourcePoint = (ResourcePoint<T>) sResourcePoints.get(classOfResourcePoint);

        String url;

        if (method == Request.Method.GET) {
            url = resourcePoint.getUrl(resourcePathParams, requestParams, encoding);
        } else {
            url = resourcePoint.getUrl(resourcePathParams, encoding);
        }

        return fire(new MultipartRequest<>(method, url, requestParams, files, oAuth2Token, resourcePoint.getResponseParser(), successListener, errorListener));
    }

    public static String getUserAgent() {
        // Don't lock on static methods, we'll be locking the entire class.
        // We lock on the safety switch to make sure the string we get is
        // not in the midst of a write cycle.
        synchronized (SAFETY_SWITCH) {
            return sUserAgent;
        }
    }

    public static ImageLoader getImageLoader() throws NotLoadedException {
        /**
         * No need to lock on SAFETY_SWITCH here since we implicitly assumes
         * that Cannon is loaded before user can call this function.
         */
        if (SAFETY_SWITCH.get()) {
            // Well it looks like the cannon was not loaded. I'll be damned.
            throw new NotLoadedException();
        } else {
            return sImageLoader;
        }
    }

    public static class NotLoadedException extends Exception {

        public NotLoadedException() {
            super("Howdy cowboy! You might wanna load the damn cannon first?");
        }
    }
}
