package com.overturelabs.cannon.toolbox;

import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.Response;
import com.overturelabs.Cannon;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * This is an example data model.
 */
// class ModelObject {
    // private String _id;
    // private String textField;
// }

/**
 * An example of how you can implement a custom resource point.
 */
public class ExampleRefreshResourcePoint extends ResourcePoint<ModelObject> {
    public static final String BASE_URL = "http://api.overturelabs.com";
    public static final String SKELETON_RESOURCE_PATH = "/object/{{ objectId }}";

    public ExampleRefreshResourcePoint() {
        super(BASE_URL, SKELETON_RESOURCE_PATH, ModelObject.class);
    }

    /*========================================
     *
     * HTTP HELPER METHODS! ** TOTALLY OPTIONAL! **
     *
     * You will need to implement this yourself in your child classes.
     * A simple copy and paste would suffice, but make sure you change
     * {@code ResourcePoint.class} to your new class.
     *
     *========================================*/

    /*
     * ========================================
     * POST
     * ========================================
     */

    public static boolean post(final Map<String, String> resourcePathParams,
                               final Map<String, String> requestParams,
                               String oAuth2Token,
                               Response.Listener<ModelObject> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleRefreshResourcePoint.class, Request.Method.POST, resourcePathParams, null, requestParams, null, oAuth2Token, successListener, errorListener, true);
    }

    public static boolean post(final Map<String, String> resourcePathParams,
                               final Map<String, String> requestHeaders,
                               final Map<String, String> requestParams,
                               String oAuth2Token,
                               Response.Listener<ModelObject> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleRefreshResourcePoint.class, Request.Method.POST, resourcePathParams, requestHeaders, requestParams, null, oAuth2Token, successListener, errorListener, true);
    }
    
    public static boolean post(final Map<String, String> resourcePathParams,
                               final Map<String, String> requestHeaders,
                               final Map<String, String> requestParams,
                               String encoding,
                               String oAuth2Token,
                               Response.Listener<ModelObject> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleRefreshResourcePoint.class, Request.Method.POST, resourcePathParams, requestHeaders, requestParams, encoding, oAuth2Token, successListener, errorListener, true);
    }
}
