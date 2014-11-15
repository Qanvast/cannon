package com.overturelabs;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.ImageLoader;
import com.overturelabs.cannon.BitmapLruCache;
import com.overturelabs.cannon.OkHttpStack;
import com.overturelabs.cannon.toolbox.GenericErrorListener;
import com.overturelabs.cannon.toolbox.GsonRequest;
import com.overturelabs.cannon.toolbox.Resource;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by stevetan on 10/11/14.
 */
public class Cannon {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private static final int DISK_CACHE_MEMORY_ALLOCATION = 300; // 300 MiB
    private static final String DISK_CACHE_NAME = "AmmunitionBox";
    private static final String TAG = "Cannon";

    private static final AtomicBoolean SAFETY_SWITCH = new AtomicBoolean(true); // If safety switch is set, you can't fire the cannon! Loading the cannon will disable the safety switch.

    private static String sUserAgent = "Cannon/0.0.1 (Android)"; // Default user agent string

    private static Cannon sInstance;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private Cannon(Context context, String appName) {
        try {
            /**
             * We load the cannon as part of the application
             * context to ensure the request queue persists
             * throughout the application lifecycle.
             */
            context = context.getApplicationContext();

            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

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
            File cacheDir = new File(context.getApplicationContext().getCacheDir(), DISK_CACHE_NAME);

            // Create a DiskBasedCache of 300 MiB
            DiskBasedCache diskBasedCache
                    = new DiskBasedCache(cacheDir, DISK_CACHE_MEMORY_ALLOCATION * 1024 * 1024);

            HttpStack httpStack = new OkHttpStack();

            mRequestQueue = new RequestQueue(diskBasedCache, new BasicNetwork(httpStack));
            mRequestQueue.start();

            mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache());
        } catch (PackageManager.NameNotFoundException e) {
            // Crashlytics.logException(e);
        } catch (Exception e) {
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
        // We check if the safety switch is off, but just in case it's on, let's switch it off.
        if (SAFETY_SWITCH.getAndSet(false)) {
            // Not loaded!
            if (sInstance == null) {
                sInstance = new Cannon(context, appName);
            }
        }

        return sInstance;
    }

    /**
     * FIRE ALL ZE CANNONS! FIRE AT WILLZ!
     *
     * Ok serious stuff.
     *
     * For GET requests, we will treat the params provided
     * as query parameters, and append them to the URL.
     *
     * For all other requests, we will send the params
     * in the request body.
     *
     * @param method                Refer to {@link com.android.volley.Request.Method com.android.volley.Request.Method}.
     * @param resource              {@link com.overturelabs.cannon.toolbox.Resource} object that cannon should be expecting.
     * @param params                Request body or query parameters, depending on method.
     * @param successListener       Success listener.
     * @param genericErrorListener  Error listener.
     * @param <T>                   Type of data encapsulated in {@link com.overturelabs.cannon.toolbox.Resource}.
     * @throws NotLoadedException   If the Cannon is not loaded, we can't fire it, can we?
     */
    public static <T> void fire(int method, Resource<T> resource, Map<String, String> params,
                                             Response.Listener<T> successListener, GenericErrorListener genericErrorListener) throws NotLoadedException {
        if (SAFETY_SWITCH.get()) {
            // Alas, my captain! The cannon is not loaded!
            throw new NotLoadedException();
        } else {
            String url = resource.getUrl();

            // Art thou a GET?
            if (method == Request.Method.GET && params != null && params.size() > 0) {
                // Thou art!
                boolean isFirst = true;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (isFirst) {
                        isFirst = false;
                        url += "?";
                    } else {
                        url += "&";
                    }

                    try {
                        url += URLEncoder.encode(entry.getKey(), DEFAULT_PARAMS_ENCODING);
                        url += "=";
                        url += URLEncoder.encode(entry.getValue(), DEFAULT_PARAMS_ENCODING);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            Request<T> request =
                    new GsonRequest<T>(
                            method,
                            resource.getResourceClass(),
                            url,
                            method != Request.Method.GET ? params : null, // We only pass in the params to request constructor if it is a GET call.
                            resource.getOAuth2Token(),
                            successListener, genericErrorListener
                    );

            if (sInstance != null && sInstance.mRequestQueue != null) {
                sInstance.mRequestQueue.add(request);
            }
        }
    }

    public static String getUserAgent() {
        // Don't lock on static methods, we'll be locking the entire class.
        // We lock on the safety switch to make sure the string we get is
        // not in the midst of a write cycle.
        synchronized (SAFETY_SWITCH) {
            return sUserAgent;
        }
    }

    public ImageLoader getImageLoader() throws NotLoadedException {
        /**
         * No need to lock on SAFETY_SWITCH here since we implicitly assumes
         * that Cannon is loaded before user can call this function.
         */
        if (SAFETY_SWITCH.get()) {
            // Well it looks like the cannon was not loaded. I'll be damned.
            throw new NotLoadedException();
        } else {
            return mImageLoader;
        }
    }

    public static class NotLoadedException extends Exception {

        public NotLoadedException() {
            super("Howdy cowboy! You might wanna load the damn cannon first?");
        }
    }
}
