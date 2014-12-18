package com.overturelabs.cannon.toolbox;

import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.Response;
import com.overturelabs.Cannon;

import java.io.File;
import java.util.Map;

/**
 * This is an example data model.
 */
class ModelObject {
    private String _id;
    private String textField;
}

/**
 * An example of how you can implement a custom resource point.
 */
public class ExampleResourcePoint extends ResourcePoint<ModelObject> {
    public static final String sbaseUrl = "http://api.overturelabs.com";
    public static final String sSkeletonResourcePath = "/object/{{ objectId }}";

    public ExampleResourcePoint() {
        super(sbaseUrl, sSkeletonResourcePath, ModelObject.class);
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

    /**
     * ========================================
     * GET
     * ========================================
     */

    public static <T> boolean get(Response.Listener<T> successListener,
                                  Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.GET, null, null, null, successListener, errorListener);
    }

    public static <T> boolean get(final Map<String, String> resourcePathParams,
                                  Response.Listener<T> successListener,
                                  Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.GET, resourcePathParams, null, null, successListener, errorListener);
    }

    public static <T> boolean get(String oAuth2Token,
                                  Response.Listener<T> successListener,
                                  Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.GET, null, null, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean get(final Map<String, String> resourcePathParams,
                                  String oAuth2Token,
                                  Response.Listener<T> successListener,
                                  Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.GET, resourcePathParams, null, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean get(final Map<String, String> resourcePathParams,
                                  final Map<String, String> requestParams,
                                  String oAuth2Token,
                                  Response.Listener<T> successListener,
                                  Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.GET, resourcePathParams, requestParams, oAuth2Token, successListener, errorListener);
    }

    /**
     * ========================================
     * POST
     * ========================================
     */

    public static <T> boolean post(Response.Listener<T> successListener,
                                   Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.POST, null, null, null, successListener, errorListener);
    }

    public static <T> boolean post(final Map<String, String> resourcePathParams,
                                   Response.Listener<T> successListener,
                                   Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.POST, resourcePathParams, null, null, successListener, errorListener);
    }

    public static <T> boolean post(String oAuth2Token,
                                   Response.Listener<T> successListener,
                                   Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.POST, null, null, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean post(final Map<String, String> resourcePathParams,
                                   String oAuth2Token,
                                   Response.Listener<T> successListener,
                                   Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.POST, resourcePathParams, null, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean post(final Map<String, String> resourcePathParams,
                                   final Map<String, String> requestParams,
                                   String oAuth2Token,
                                   Response.Listener<T> successListener,
                                   Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.POST, resourcePathParams, requestParams, oAuth2Token, successListener, errorListener);
    }

    /**
     * ========================================
     * POST Multi-part
     * ========================================
     */

    public static <T> boolean postMultipart(final Map<String, Pair<File, String>> files,
                                            Response.Listener<T> successListener,
                                            Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.POST, null, null, files, null, successListener, errorListener);
    }

    public static <T> boolean postMultipart(final Map<String, String> resourcePathParams,
                                            final Map<String, Pair<File, String>> files,
                                            Response.Listener<T> successListener,
                                            Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.POST, resourcePathParams, null, files, null, successListener, errorListener);
    }

    public static <T> boolean postMultipart(final Map<String, Pair<File, String>> files,
                                            String oAuth2Token,
                                            Response.Listener<T> successListener,
                                            Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.POST, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean postMultipart(final Map<String, String> resourcePathParams,
                                            final Map<String, Pair<File, String>> files,
                                            String oAuth2Token,
                                            Response.Listener<T> successListener,
                                            Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.POST, resourcePathParams, null, files, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean postMultipart(final Map<String, String> resourcePathParams,
                                            final Map<String, String> requestParams,
                                            final Map<String, Pair<File, String>> files,
                                            String oAuth2Token,
                                            Response.Listener<T> successListener,
                                            Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.POST, resourcePathParams, requestParams, files, oAuth2Token, successListener, errorListener);
    }

    /**
     * ========================================
     * PUT
     * ========================================
     */

    public static <T> boolean put(Response.Listener<T> successListener,
                                  Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PUT, null, null, null, successListener, errorListener);
    }

    public static <T> boolean put(final Map<String, String> resourcePathParams,
                                  Response.Listener<T> successListener,
                                  Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PUT, resourcePathParams, null, null, successListener, errorListener);
    }

    public static <T> boolean put(String oAuth2Token,
                                  Response.Listener<T> successListener,
                                  Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PUT, null, null, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean put(final Map<String, String> resourcePathParams,
                                  String oAuth2Token,
                                  Response.Listener<T> successListener,
                                  Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PUT, resourcePathParams, null, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean put(final Map<String, String> resourcePathParams,
                                  final Map<String, String> requestParams,
                                  String oAuth2Token,
                                  Response.Listener<T> successListener,
                                  Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PUT, resourcePathParams, requestParams, oAuth2Token, successListener, errorListener);
    }

    /**
     * ========================================
     * PUT Multi-part
     * ========================================
     */

    public static <T> boolean putMultipart(final Map<String, Pair<File, String>> files,
                                           Response.Listener<T> successListener,
                                           Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PUT, null, null, files, null, successListener, errorListener);
    }

    public static <T> boolean putMultipart(final Map<String, String> resourcePathParams,
                                           final Map<String, Pair<File, String>> files,
                                           Response.Listener<T> successListener,
                                           Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PUT, resourcePathParams, null, files, null, successListener, errorListener);
    }

    public static <T> boolean putMultipart(final Map<String, Pair<File, String>> files,
                                           String oAuth2Token,
                                           Response.Listener<T> successListener,
                                           Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PUT, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean putMultipart(final Map<String, String> resourcePathParams,
                                           final Map<String, Pair<File, String>> files,
                                           String oAuth2Token,
                                           Response.Listener<T> successListener,
                                           Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PUT, resourcePathParams, null, files, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean putMultipart(final Map<String, String> resourcePathParams,
                                           final Map<String, String> requestParams,
                                           final Map<String, Pair<File, String>> files,
                                           String oAuth2Token,
                                           Response.Listener<T> successListener,
                                           Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PUT, resourcePathParams, requestParams, files, oAuth2Token, successListener, errorListener);
    }

    /**
     * ========================================
     * PATCH
     * ========================================
     */

    public static <T> boolean patch(Response.Listener<T> successListener,
                                    Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PATCH, null, null, null, successListener, errorListener);
    }

    public static <T> boolean patch(final Map<String, String> resourcePathParams,
                                    Response.Listener<T> successListener,
                                    Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PATCH, resourcePathParams, null, null, successListener, errorListener);
    }

    public static <T> boolean patch(String oAuth2Token,
                                    Response.Listener<T> successListener,
                                    Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PATCH, null, null, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean patch(final Map<String, String> resourcePathParams,
                                    String oAuth2Token,
                                    Response.Listener<T> successListener,
                                    Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PATCH, resourcePathParams, null, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean patch(final Map<String, String> resourcePathParams,
                                    final Map<String, String> requestParams,
                                    String oAuth2Token,
                                    Response.Listener<T> successListener,
                                    Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PATCH, resourcePathParams, requestParams, oAuth2Token, successListener, errorListener);
    }

    /**
     * ========================================
     * PUT Multi-part
     * ========================================
     */

    public static <T> boolean patchMultipart(final Map<String, Pair<File, String>> files,
                                             Response.Listener<T> successListener,
                                             Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PATCH, null, null, files, null, successListener, errorListener);
    }

    public static <T> boolean patchMultipart(final Map<String, String> resourcePathParams,
                                             final Map<String, Pair<File, String>> files,
                                             Response.Listener<T> successListener,
                                             Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PATCH, resourcePathParams, null, files, null, successListener, errorListener);
    }

    public static <T> boolean patchMultipart(final Map<String, Pair<File, String>> files,
                                             String oAuth2Token,
                                             Response.Listener<T> successListener,
                                             Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PATCH, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean patchMultipart(final Map<String, String> resourcePathParams,
                                             final Map<String, Pair<File, String>> files,
                                             String oAuth2Token,
                                             Response.Listener<T> successListener,
                                             Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PATCH, resourcePathParams, null, files, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean patchMultipart(final Map<String, String> resourcePathParams,
                                             final Map<String, String> requestParams,
                                             final Map<String, Pair<File, String>> files,
                                             String oAuth2Token,
                                             Response.Listener<T> successListener,
                                             Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.PATCH, resourcePathParams, requestParams, files, oAuth2Token, successListener, errorListener);
    }

    /**
     * ========================================
     * DELETE
     * ========================================
     */

    public static <T> boolean delete(Response.Listener<T> successListener,
                                     Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.DELETE, null, null, null, successListener, errorListener);
    }

    public static <T> boolean delete(final Map<String, String> resourcePathParams,
                                     Response.Listener<T> successListener,
                                     Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.DELETE, resourcePathParams, null, null, successListener, errorListener);
    }

    public static <T> boolean delete(String oAuth2Token,
                                     Response.Listener<T> successListener,
                                     Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.DELETE, null, null, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean delete(final Map<String, String> resourcePathParams,
                                     String oAuth2Token,
                                     Response.Listener<T> successListener,
                                     Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.DELETE, resourcePathParams, null, oAuth2Token, successListener, errorListener);
    }

    public static <T> boolean delete(final Map<String, String> resourcePathParams,
                                     final Map<String, String> requestParams,
                                     String oAuth2Token,
                                     Response.Listener<T> successListener,
                                     Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        return Cannon.fireAt(ResourcePoint.class, Request.Method.DELETE, resourcePathParams, requestParams, oAuth2Token, successListener, errorListener);
    }
}
