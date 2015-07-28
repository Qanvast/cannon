package ${PACKAGE};

import android.support.annotation.NonNull;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.Response;
import com.overturelabs.Cannon;
import com.overturelabs.cannon.toolbox.resources.ResourcePoint;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * An example of how you can implement a custom resource point.
 */
public class ${CLASS} extends ResourcePoint<${MODEL}> {
    public static final String BASE_URL = "${BASE_URL}";
    public static final String SKELETON_RESOURCE_PATH = "${SKELETON_RESOURCE_PATH}";

    public ${CLASS}() {
        super(BASE_URL, SKELETON_RESOURCE_PATH, ${MODEL}.class);
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
    public static boolean get(final Map<String, String> resourcePathParams,
                              final Map<String, String> requestHeaders,
                              final Map<String, String> requestParams,
                              @NonNull Response.Listener<${MODEL}> successListener,
                              @NonNull Response.ErrorListener errorListener)
            throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.getInstance().fireAt(new ${CLASS}(), Request.Method.GET, resourcePathParams, requestHeaders, requestParams, null, successListener, errorListener);
    }

    /*
     * ========================================
     * POST
     * ========================================
     */

    public static boolean post(final Map<String, String> resourcePathParams,
                               final Map<String, String> requestHeaders,
                               final Map<String, String> requestParams,
                               @NonNull Response.Listener<${MODEL}> successListener,
                               @NonNull Response.ErrorListener errorListener)
            throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.getInstance().fireAt(new ${CLASS}(), Request.Method.POST, resourcePathParams, requestHeaders, requestParams, null, successListener, errorListener);
    }

    /*
     * ========================================
     * POST Multi-part
     * ========================================
     */

    public static boolean postMultipart(final Map<String, String> resourcePathParams,
                                        final Map<String, String> requestHeaders,
                                        final Map<String, String> requestParams,
                                        final Map<String, Pair<File, String>> files,
                                        @NonNull Response.Listener<${MODEL}> successListener,
                                        @NonNull Response.ErrorListener errorListener)
            throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.getInstance().fireMultipartRequest(new ${CLASS}(), Request.Method.POST, resourcePathParams, requestHeaders, requestParams, null, files, successListener, errorListener);
    }

    /*
     * ========================================
     * PUT
     * ========================================
     */

    public static boolean put(final Map<String, String> resourcePathParams,
                              final Map<String, String> requestHeaders,
                              final Map<String, String> requestParams,
                              @NonNull Response.Listener<${MODEL}> successListener,
                              @NonNull Response.ErrorListener errorListener)
            throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.getInstance().fireAt(new ${CLASS}(), Request.Method.PUT, resourcePathParams, requestHeaders, requestParams, null, successListener, errorListener);
    }

    /*
     * ========================================
     * PUT Multi-part
     * ========================================
     */

    public static boolean putMultipart(final Map<String, String> resourcePathParams,
                                       final Map<String, String> requestHeaders,
                                       final Map<String, String> requestParams,
                                       final Map<String, Pair<File, String>> files,
                                       @NonNull Response.Listener<${MODEL}> successListener,
                                       @NonNull Response.ErrorListener errorListener)
            throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.getInstance().fireMultipartRequest(new ${CLASS}(), Request.Method.PUT, resourcePathParams, requestHeaders, requestParams, null, files, successListener, errorListener);
    }

    /*
     * ========================================
     * PATCH
     * ========================================
     */

    public static boolean patch(final Map<String, String> resourcePathParams,
                                final Map<String, String> requestHeaders,
                                final Map<String, String> requestParams,
                                @NonNull Response.Listener<${MODEL}> successListener,
                                @NonNull Response.ErrorListener errorListener)
            throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.getInstance().fireAt(new ${CLASS}(), Request.Method.PATCH, resourcePathParams, requestHeaders, requestParams, null, successListener, errorListener);
    }

    /*
     * ========================================
     * PATCH Multi-part
     * ========================================
     */

    public static boolean patchMultipart(final Map<String, String> resourcePathParams,
                                         final Map<String, String> requestHeaders,
                                         final Map<String, String> requestParams,
                                         final Map<String, Pair<File, String>> files,
                                         Response.Listener<${MODEL}> successListener,
                                         Response.ErrorListener errorListener)
            throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.getInstance().fireMultipartRequest(new ${CLASS}(), Request.Method.PATCH, resourcePathParams, requestHeaders, requestParams, null, files, successListener, errorListener);
    }

    /*
     * ========================================
     * DELETE
     * ========================================
     */

    public static boolean delete(final Map<String, String> resourcePathParams,
                                 final Map<String, String> requestHeaders,
                                 final Map<String, String> requestParams,
                                 Response.Listener<${MODEL}> successListener,
                                 Response.ErrorListener errorListener)
            throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.getInstance().fireAt(new ${CLASS}(), Request.Method.DELETE, resourcePathParams, requestHeaders, requestParams, null, successListener, errorListener);
    }

    /*
     * ========================================
     * POST REFRESH REQUEST
     * ========================================
     */

    public static boolean postRefresh(final Map<String, String> resourcePathParams,
                                      final Map<String, String> requestHeaders,
                                      final Map<String, String> requestParams,
                                      Response.Listener<${MODEL}> successListener,
                                      Response.ErrorListener errorListener)
            throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.getInstance().fireRefreshRequest(new ${CLASS}(), Request.Method.POST, resourcePathParams, requestHeaders, requestParams, null, successListener, errorListener);
    }
}