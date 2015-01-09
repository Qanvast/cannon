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
class ModelObject {
    private String _id;
    private String textField;
}

/**
 * An example of how you can implement a custom resource point.
 */
public class ExampleResourcePoint extends ResourcePoint<ModelObject> {
    public static final String BASE_URL = "http://api.overturelabs.com";
    public static final String SKELETON_RESOURCE_PATH = "/object/{{ objectId }}";

    public ExampleResourcePoint() {
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
     * GET
     * ========================================
     */

    public static boolean get(Response.Listener<ModelObject> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.GET, null, null, null, null, successListener, errorListener);
    }

    public static boolean get(final Map<String, String> resourcePathParams,
                              Response.Listener<ModelObject> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.GET, resourcePathParams, null, null, null, successListener, errorListener);
    }

    public static boolean get(String oAuth2Token,
                              Response.Listener<ModelObject> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.GET, null, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean get(final Map<String, String> resourcePathParams,
                              String oAuth2Token,
                              Response.Listener<ModelObject> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.GET, resourcePathParams,  null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean get(final Map<String, String> resourcePathParams,
                              final Map<String, String> requestParams,
                              String oAuth2Token,
                              Response.Listener<ModelObject> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.GET, resourcePathParams,  null, requestParams, oAuth2Token, successListener, errorListener);
    }

    public static boolean get(final Map<String, String> resourcePathParams,
                              final Map<String, String> requestHeaders,
                              final Map<String, String> requestParams,
                              String oAuth2Token,
                              Response.Listener<ModelObject> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.GET, resourcePathParams,  requestHeaders, requestParams, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * POST
     * ========================================
     */

    public static boolean post(Response.Listener<ModelObject> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.POST, null, null, null, null, successListener, errorListener);
    }

    public static boolean post(final Map<String, String> resourcePathParams,
                               Response.Listener<ModelObject> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.POST, resourcePathParams, null, null, null, successListener, errorListener);
    }

    public static boolean post(String oAuth2Token,
                               Response.Listener<ModelObject> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.POST, null, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean post(final Map<String, String> resourcePathParams,
                               String oAuth2Token,
                               Response.Listener<ModelObject> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.POST, resourcePathParams, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean post(final Map<String, String> resourcePathParams,
                               final Map<String, String> requestHeaders,
                               final Map<String, String> requestParams,
                               String oAuth2Token,
                               Response.Listener<ModelObject> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.POST, resourcePathParams, requestHeaders, requestParams, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * POST Multi-part
     * ========================================
     */

    public static boolean postMultipart(final Map<String, Pair<File, String>> files,
                                        Response.Listener<ModelObject> successListener,
                                        Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.POST, null, null, null, files, null, successListener, errorListener);
    }

    public static boolean postMultipart(final Map<String, String> resourcePathParams,
                                        final Map<String, Pair<File, String>> files,
                                        Response.Listener<ModelObject> successListener,
                                        Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.POST, resourcePathParams, null, null, files, null, successListener, errorListener);
    }

    public static boolean postMultipart(final Map<String, Pair<File, String>> files,
                                        String oAuth2Token,
                                        Response.Listener<ModelObject> successListener,
                                        Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.POST, null, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean postMultipart(final Map<String, String> resourcePathParams,
                                        final Map<String, Pair<File, String>> files,
                                        String oAuth2Token,
                                        Response.Listener<ModelObject> successListener,
                                        Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.POST, resourcePathParams, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean postMultipart(final Map<String, String> resourcePathParams,
                                        final Map<String, String> requestHeaders,
                                        final Map<String, String> requestParams,
                                        final Map<String, Pair<File, String>> files,
                                        String oAuth2Token,
                                        Response.Listener<ModelObject> successListener,
                                        Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.POST, resourcePathParams, requestHeaders, requestParams, files, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * PUT
     * ========================================
     */

    public static boolean put(Response.Listener<ModelObject> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PUT, null, null, null, null, successListener, errorListener);
    }

    public static boolean put(final Map<String, String> resourcePathParams,
                              Response.Listener<ModelObject> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PUT, resourcePathParams, null, null, null, successListener, errorListener);
    }

    public static boolean put(String oAuth2Token,
                              Response.Listener<ModelObject> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PUT, null, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean put(final Map<String, String> resourcePathParams,
                              String oAuth2Token,
                              Response.Listener<ModelObject> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PUT, resourcePathParams, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean put(final Map<String, String> resourcePathParams,
                              final Map<String, String> requestHeaders,
                              final Map<String, String> requestParams,
                              String oAuth2Token,
                              Response.Listener<ModelObject> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PUT, resourcePathParams, requestHeaders, requestParams, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * PUT Multi-part
     * ========================================
     */

    public static boolean putMultipart(final Map<String, Pair<File, String>> files,
                                       Response.Listener<ModelObject> successListener,
                                       Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PUT, null, null, null, files, null, successListener, errorListener);
    }

    public static boolean putMultipart(final Map<String, String> resourcePathParams,
                                       final Map<String, Pair<File, String>> files,
                                       Response.Listener<ModelObject> successListener,
                                       Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PUT, resourcePathParams, null, null, files, null, successListener, errorListener);
    }

    public static boolean putMultipart(final Map<String, Pair<File, String>> files,
                                       String oAuth2Token,
                                       Response.Listener<ModelObject> successListener,
                                       Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PUT, null, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean putMultipart(final Map<String, String> resourcePathParams,
                                       final Map<String, Pair<File, String>> files,
                                       String oAuth2Token,
                                       Response.Listener<ModelObject> successListener,
                                       Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PUT, resourcePathParams, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean putMultipart(final Map<String, String> resourcePathParams,
                                       final Map<String, String> requestHeaders,
                                       final Map<String, String> requestParams,
                                       final Map<String, Pair<File, String>> files,
                                       String oAuth2Token,
                                       Response.Listener<ModelObject> successListener,
                                       Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PUT, resourcePathParams, requestHeaders, requestParams, files, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * PATCH
     * ========================================
     */

    public static boolean patch(Response.Listener<ModelObject> successListener,
                                Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PATCH, null, null, null, null, successListener, errorListener);
    }

    public static boolean patch(final Map<String, String> resourcePathParams,
                                Response.Listener<ModelObject> successListener,
                                Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PATCH, resourcePathParams, null, null, null, successListener, errorListener);
    }

    public static boolean patch(String oAuth2Token,
                                Response.Listener<ModelObject> successListener,
                                Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PATCH, null, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean patch(final Map<String, String> resourcePathParams,
                                String oAuth2Token,
                                Response.Listener<ModelObject> successListener,
                                Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PATCH, resourcePathParams, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean patch(final Map<String, String> resourcePathParams,
                                final Map<String, String> requestHeaders,
                                final Map<String, String> requestParams,
                                String oAuth2Token,
                                Response.Listener<ModelObject> successListener,
                                Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PATCH, resourcePathParams, requestHeaders, requestParams, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * PUT Multi-part
     * ========================================
     */

    public static boolean patchMultipart(final Map<String, Pair<File, String>> files,
                                         Response.Listener<ModelObject> successListener,
                                         Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PATCH, null, null, null, files, null, successListener, errorListener);
    }

    public static boolean patchMultipart(final Map<String, String> resourcePathParams,
                                         final Map<String, Pair<File, String>> files,
                                         Response.Listener<ModelObject> successListener,
                                         Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PATCH, resourcePathParams, null, null, files, null, successListener, errorListener);
    }

    public static boolean patchMultipart(final Map<String, Pair<File, String>> files,
                                         String oAuth2Token,
                                         Response.Listener<ModelObject> successListener,
                                         Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PATCH, null, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean patchMultipart(final Map<String, String> resourcePathParams,
                                         final Map<String, Pair<File, String>> files,
                                         String oAuth2Token,
                                         Response.Listener<ModelObject> successListener,
                                         Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PATCH, resourcePathParams, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean patchMultipart(final Map<String, String> resourcePathParams,
                                         final Map<String, String> requestHeaders,
                                         final Map<String, String> requestParams,
                                         final Map<String, Pair<File, String>> files,
                                         String oAuth2Token,
                                         Response.Listener<ModelObject> successListener,
                                         Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.PATCH, resourcePathParams, requestHeaders, requestParams, files, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * DELETE
     * ========================================
     */

    public static boolean delete(Response.Listener<ModelObject> successListener,
                                 Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.DELETE, null, null, null, null, successListener, errorListener);
    }

    public static boolean delete(final Map<String, String> resourcePathParams,
                                 Response.Listener<ModelObject> successListener,
                                 Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.DELETE, resourcePathParams, null, null, null, successListener, errorListener);
    }

    public static boolean delete(String oAuth2Token,
                                 Response.Listener<ModelObject> successListener,
                                 Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.DELETE, null, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean delete(final Map<String, String> resourcePathParams,
                                 String oAuth2Token,
                                 Response.Listener<ModelObject> successListener,
                                 Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.DELETE, resourcePathParams, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean delete(final Map<String, String> resourcePathParams,
                                 final Map<String, String> requestHeaders,
                                 final Map<String, String> requestParams,
                                 String oAuth2Token,
                                 Response.Listener<ModelObject> successListener,
                                 Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(ExampleResourcePoint.class, Request.Method.DELETE, resourcePathParams, requestHeaders, requestParams, oAuth2Token, successListener, errorListener);
    }
}
