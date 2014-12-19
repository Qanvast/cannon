package ${PACKAGE_NAME};

import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.Response;
import com.overturelabs.Cannon;
import com.overturelabs.cannon.toolbox.ResourcePoint;

import java.io.File;
import java.util.Map;

import ${Model_Package_Name}.${Model};

/**
 * This is an example data model.
 */
/**
class ${Model} {
    private String _id;
    private String textField;
}
*/

/**
 * An example of how you can implement a custom resource point.
 */
public class ${NAME} extends ResourcePoint<${Model}> {
    public static final String BASE_URL = "${Base_URL}";
    public static final String SKELETON_RESOURCE_PATH = "${Skeleton_Resource_Path}";

    public ${NAME}() {
        super(BASE_URL, SKELETON_RESOURCE_PATH, ${Model}.class);
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

    public static boolean get(Response.Listener<${Model}> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.GET, null, null, null, successListener, errorListener);
    }

    public static boolean get(final Map<String, String> resourcePathParams,
                              Response.Listener<${Model}> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.GET, resourcePathParams, null, null, successListener, errorListener);
    }

    public static boolean get(String oAuth2Token,
                              Response.Listener<${Model}> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.GET, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean get(final Map<String, String> resourcePathParams,
                              String oAuth2Token,
                              Response.Listener<${Model}> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.GET, resourcePathParams, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean get(final Map<String, String> resourcePathParams,
                              final Map<String, String> requestParams,
                              String oAuth2Token,
                              Response.Listener<${Model}> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.GET, resourcePathParams, requestParams, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * POST
     * ========================================
     */

    public static boolean post(Response.Listener<${Model}> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.POST, null, null, null, successListener, errorListener);
    }

    public static boolean post(final Map<String, String> resourcePathParams,
                               Response.Listener<${Model}> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.POST, resourcePathParams, null, null, successListener, errorListener);
    }

    public static boolean post(String oAuth2Token,
                               Response.Listener<${Model}> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.POST, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean post(final Map<String, String> resourcePathParams,
                               String oAuth2Token,
                               Response.Listener<${Model}> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.POST, resourcePathParams, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean post(final Map<String, String> resourcePathParams,
                               final Map<String, String> requestParams,
                               String oAuth2Token,
                               Response.Listener<${Model}> successListener,
                               Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.POST, resourcePathParams, requestParams, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * POST Multi-part
     * ========================================
     */

    public static boolean postMultipart(final Map<String, Pair<File, String>> files,
                                        Response.Listener<${Model}> successListener,
                                        Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.POST, null, null, files, null, successListener, errorListener);
    }

    public static boolean postMultipart(final Map<String, String> resourcePathParams,
                                        final Map<String, Pair<File, String>> files,
                                        Response.Listener<${Model}> successListener,
                                        Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.POST, resourcePathParams, null, files, null, successListener, errorListener);
    }

    public static boolean postMultipart(final Map<String, Pair<File, String>> files,
                                        String oAuth2Token,
                                        Response.Listener<${Model}> successListener,
                                        Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.POST, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean postMultipart(final Map<String, String> resourcePathParams,
                                        final Map<String, Pair<File, String>> files,
                                        String oAuth2Token,
                                        Response.Listener<${Model}> successListener,
                                        Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.POST, resourcePathParams, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean postMultipart(final Map<String, String> resourcePathParams,
                                        final Map<String, String> requestParams,
                                        final Map<String, Pair<File, String>> files,
                                        String oAuth2Token,
                                        Response.Listener<${Model}> successListener,
                                        Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.POST, resourcePathParams, requestParams, files, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * PUT
     * ========================================
     */

    public static boolean put(Response.Listener<${Model}> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PUT, null, null, null, successListener, errorListener);
    }

    public static boolean put(final Map<String, String> resourcePathParams,
                              Response.Listener<${Model}> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PUT, resourcePathParams, null, null, successListener, errorListener);
    }

    public static boolean put(String oAuth2Token,
                              Response.Listener<${Model}> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PUT, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean put(final Map<String, String> resourcePathParams,
                              String oAuth2Token,
                              Response.Listener<${Model}> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PUT, resourcePathParams, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean put(final Map<String, String> resourcePathParams,
                              final Map<String, String> requestParams,
                              String oAuth2Token,
                              Response.Listener<${Model}> successListener,
                              Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PUT, resourcePathParams, requestParams, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * PUT Multi-part
     * ========================================
     */

    public static boolean putMultipart(final Map<String, Pair<File, String>> files,
                                       Response.Listener<${Model}> successListener,
                                       Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PUT, null, null, files, null, successListener, errorListener);
    }

    public static boolean putMultipart(final Map<String, String> resourcePathParams,
                                       final Map<String, Pair<File, String>> files,
                                       Response.Listener<${Model}> successListener,
                                       Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PUT, resourcePathParams, null, files, null, successListener, errorListener);
    }

    public static boolean putMultipart(final Map<String, Pair<File, String>> files,
                                       String oAuth2Token,
                                       Response.Listener<${Model}> successListener,
                                       Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PUT, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean putMultipart(final Map<String, String> resourcePathParams,
                                       final Map<String, Pair<File, String>> files,
                                       String oAuth2Token,
                                       Response.Listener<${Model}> successListener,
                                       Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PUT, resourcePathParams, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean putMultipart(final Map<String, String> resourcePathParams,
                                       final Map<String, String> requestParams,
                                       final Map<String, Pair<File, String>> files,
                                       String oAuth2Token,
                                       Response.Listener<${Model}> successListener,
                                       Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PUT, resourcePathParams, requestParams, files, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * PATCH
     * ========================================
     */

    public static boolean patch(Response.Listener<${Model}> successListener,
                                Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PATCH, null, null, null, successListener, errorListener);
    }

    public static boolean patch(final Map<String, String> resourcePathParams,
                                Response.Listener<${Model}> successListener,
                                Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PATCH, resourcePathParams, null, null, successListener, errorListener);
    }

    public static boolean patch(String oAuth2Token,
                                Response.Listener<${Model}> successListener,
                                Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PATCH, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean patch(final Map<String, String> resourcePathParams,
                                String oAuth2Token,
                                Response.Listener<${Model}> successListener,
                                Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PATCH, resourcePathParams, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean patch(final Map<String, String> resourcePathParams,
                                final Map<String, String> requestParams,
                                String oAuth2Token,
                                Response.Listener<${Model}> successListener,
                                Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PATCH, resourcePathParams, requestParams, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * PUT Multi-part
     * ========================================
     */

    public static boolean patchMultipart(final Map<String, Pair<File, String>> files,
                                         Response.Listener<${Model}> successListener,
                                         Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PATCH, null, null, files, null, successListener, errorListener);
    }

    public static boolean patchMultipart(final Map<String, String> resourcePathParams,
                                         final Map<String, Pair<File, String>> files,
                                         Response.Listener<${Model}> successListener,
                                         Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PATCH, resourcePathParams, null, files, null, successListener, errorListener);
    }

    public static boolean patchMultipart(final Map<String, Pair<File, String>> files,
                                         String oAuth2Token,
                                         Response.Listener<${Model}> successListener,
                                         Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PATCH, null, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean patchMultipart(final Map<String, String> resourcePathParams,
                                         final Map<String, Pair<File, String>> files,
                                         String oAuth2Token,
                                         Response.Listener<${Model}> successListener,
                                         Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PATCH, resourcePathParams, null, files, oAuth2Token, successListener, errorListener);
    }

    public static boolean patchMultipart(final Map<String, String> resourcePathParams,
                                         final Map<String, String> requestParams,
                                         final Map<String, Pair<File, String>> files,
                                         String oAuth2Token,
                                         Response.Listener<${Model}> successListener,
                                         Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.PATCH, resourcePathParams, requestParams, files, oAuth2Token, successListener, errorListener);
    }

    /*
     * ========================================
     * DELETE
     * ========================================
     */

    public static boolean delete(Response.Listener<${Model}> successListener,
                                 Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.DELETE, null, null, null, successListener, errorListener);
    }

    public static boolean delete(final Map<String, String> resourcePathParams,
                                 Response.Listener<${Model}> successListener,
                                 Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.DELETE, resourcePathParams, null, null, successListener, errorListener);
    }

    public static boolean delete(String oAuth2Token,
                                 Response.Listener<${Model}> successListener,
                                 Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.DELETE, null, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean delete(final Map<String, String> resourcePathParams,
                                 String oAuth2Token,
                                 Response.Listener<${Model}> successListener,
                                 Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.DELETE, resourcePathParams, null, oAuth2Token, successListener, errorListener);
    }

    public static boolean delete(final Map<String, String> resourcePathParams,
                                 final Map<String, String> requestParams,
                                 String oAuth2Token,
                                 Response.Listener<${Model}> successListener,
                                 Response.ErrorListener errorListener) throws Cannon.NotLoadedException, UnsupportedEncodingException {
        return Cannon.fireAt(${NAME}.class, Request.Method.DELETE, resourcePathParams, requestParams, oAuth2Token, successListener, errorListener);
    }
}
