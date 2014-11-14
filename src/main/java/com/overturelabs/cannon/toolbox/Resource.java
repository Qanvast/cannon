package com.overturelabs.cannon.toolbox;

import com.android.volley.Request;
import com.android.volley.Response;
import com.overturelabs.Cannon;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by stevetan on 12/11/14.
 */
public abstract class Resource<T> {

    private final static String SKELETON_PATH_REGEX = "(?:/(?:(?:\\{\\{\\s*[\\d\\w]+\\s*}})|[\\w]+))+";
    private String mBaseUrl;
    private Map<String, String> mParams;

    public Resource(Map<String, String> params, String baseUrl) {
        mBaseUrl = baseUrl;
        mParams = params;
    }

    public abstract Class<T> getResourceClass();

    /**
     * This function will only process the skeleton resource path if it's valid.
     * Otherwise, the entire skeleton path is passed, as-is, upwards.
     *
     * @return Returns a URN (Uniform Resource Name) reference path that is relative to the API base URL.
     */
    public String getResourcePath() {
        // TODO: Parse skeleton path and replace placeholders with values from parameters map.
        String resourcePath = getSkeletonResourcePath();

        // Validate skeleton resource path.
        if (Pattern.matches(SKELETON_PATH_REGEX, resourcePath)) {
            for (Map.Entry<String, String> entry : mParams.entrySet()) {

            }
        }


        return resourcePath;
    }

    /**
     * Skeleton resource path is a resource path with placeholders
     * that you can replace with actual values during runtime.
     * The path should be relative to the API base URL.
     *
     * For example:
     *
     * <code>/user/{{ userId }}</code>
     *
     * The default {@link Resource#getResourcePath()} method is expecting
     * placeholders in the following format:
     *
     * <code>{{ placeholderIdentifier }}</code>
     *
     * {@link Resource#getResourcePath()} will replace the placeholders with
     * the provided parameters.
     *
     * @return Returns a skeleton resource path.
     */
    public abstract String getSkeletonResourcePath();

    /**
     * @return Returns a full URI path.
     */
    public String getUrl() {
        return mBaseUrl + getResourcePath();
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, Resource, java.util.Map, com.android.volley.Response.Listener, GenericErrorListener)} method.
     *
     * @param params                        Query parameters for this request.
     * @param successListener               Success listener.
     * @param genericErrorListener          Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void get(Map<String, String> params, Response.Listener<T> successListener, GenericErrorListener genericErrorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.GET, this, params, successListener, genericErrorListener);
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, Resource, java.util.Map, com.android.volley.Response.Listener, GenericErrorListener)} method.
     *
     * @param params                        POST body for this request.
     * @param successListener               Success listener.
     * @param genericErrorListener          Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void post(Map<String, String> params, Response.Listener<T> successListener, GenericErrorListener genericErrorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.POST, this, params, successListener, genericErrorListener);
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, Resource, java.util.Map, com.android.volley.Response.Listener, GenericErrorListener)} method.
     *
     * @param params                        PUT body for this request.
     * @param successListener               Success listener.
     * @param genericErrorListener          Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void put(Map<String, String> params, Response.Listener<T> successListener, GenericErrorListener genericErrorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.PUT, this, params, successListener, genericErrorListener);
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, Resource, java.util.Map, com.android.volley.Response.Listener, GenericErrorListener)} method.
     *
     * @param params                        DELETE body for this request.
     * @param successListener               Success listener.
     * @param genericErrorListener          Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void delete(Map<String, String> params, Response.Listener<T> successListener, GenericErrorListener genericErrorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.DELETE, this, params, successListener, genericErrorListener);
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, Resource, java.util.Map, com.android.volley.Response.Listener, GenericErrorListener)} method.
     *
     * @param params                        PATCH body for this request.
     * @param successListener               Success listener.
     * @param genericErrorListener          Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void patch(Map<String, String> params, Response.Listener successListener, GenericErrorListener genericErrorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.PATCH, this, params, successListener, genericErrorListener);
    }
}
