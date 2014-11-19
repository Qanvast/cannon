package com.overturelabs.cannon.toolbox;

import android.support.v4.util.Pair;

import com.android.volley.Request;
import com.android.volley.Response;
import com.overturelabs.Cannon;

import java.io.File;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by stevetan on 12/11/14.
 */
public abstract class ResourcePoint<T> {
    private final static String SKELETON_PATH_REGEX = "^(?:/(?:(?:\\{\\{\\s*[\\d\\w]+\\s*\\}{2})|[\\w]+))+$";
    private final static String PLACEHOLDER_KEY_REGEX = "^[\\d\\w]+$";
    private final static String PLACEHOLDER_VALUE_REGEX = "^[\\d\\w]+$";

    private final static Pattern SKELETON_PATH_PATTERN = Pattern.compile(SKELETON_PATH_REGEX);
    private final static Pattern PLACEHOLDER_KEY_PATTERN = Pattern.compile(PLACEHOLDER_KEY_REGEX);
    private final static Pattern PLACEHOLDER_VALUE_PATTERN = Pattern.compile(PLACEHOLDER_VALUE_REGEX);

    private String mBaseUrl;
    private Map<String, String> mParams = null;
    private String mOAuth2Token = null;

    /**
     * @param baseUrl       API base URL.
     * @param params        Resource path parameters.
     * @param oAuth2Token   OAuth 2.0 Bearer token to be injected into request header.
     */
    public ResourcePoint(String baseUrl, Map<String, String> params, String oAuth2Token) {
        mBaseUrl = baseUrl;
        mParams = params;
        mOAuth2Token = oAuth2Token;
    }


    /**
     * @param baseUrl       API base URL.
     * @param oAuth2Token   OAuth 2.0 Bearer token to be injected into request header.
     */
    public ResourcePoint(String baseUrl, String oAuth2Token) {
        mBaseUrl = baseUrl;
        mOAuth2Token = oAuth2Token;
    }

    /**
     * @param baseUrl       API base URL.
     * @param params        Resource path parameters.
     */
    public ResourcePoint(String baseUrl, Map<String, String> params) {
        mBaseUrl = baseUrl;
        mParams = params;
    }

    /**
     * @param baseUrl       API base URL.
     */
    public ResourcePoint(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    public abstract Class<T> getResourceClass();

    /**
     * This function will only process the skeleton resource path if it's valid.
     * Otherwise, the entire skeleton path is passed, as-is, upwards.
     *
     * @return Returns a URN (Uniform Resource Name) reference path that is relative to the API base URL.
     */
    public String getResourcePath() {
        String resourcePath = getSkeletonResourcePath();

        // Validate skeleton resource path.
        if (SKELETON_PATH_PATTERN.matcher(resourcePath).matches() && mParams != null && mParams.size() > 0) {
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                // Check if key is valid
                String key = entry.getKey();
                String value = entry.getValue();

                if (key != null && key.length() > 0) {
                    key = key.trim();
                }

                if (value != null && value.length() > 0) {
                    value = value.trim();
                }

                if (PLACEHOLDER_KEY_PATTERN.matcher(key).matches()
                        && PLACEHOLDER_VALUE_PATTERN.matcher(value).matches()) {
                    String placeholderRegexp = "\\{\\{\\s*" + entry.getKey() + "\\s*}}";

                    resourcePath.replaceAll(placeholderRegexp, value);
                }
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
     * The default {@link ResourcePoint#getResourcePath()} method is expecting
     * placeholders in the following format:
     *
     * <code>{{ placeholderIdentifier }}</code>
     *
     * {@link ResourcePoint#getResourcePath()} will replace the placeholders with
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

    public String getOAuth2Token() {
        return mOAuth2Token;
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, ResourcePoint, java.util.Map, com.android.volley.Response.Listener, com.android.volley.Response.ErrorListener)} method.
     *
     * @param params                        Query parameters for this request.
     * @param successListener               Success listener.
     * @param errorListener                 Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void get(Map<String, String> params, Response.Listener<T> successListener, Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.GET, this, params, successListener, errorListener);
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, ResourcePoint, java.util.Map, com.android.volley.Response.Listener, com.android.volley.Response.ErrorListener)} method.
     *
     * @param params                        POST body for this request.
     * @param successListener               Success listener.
     * @param errorListener                 Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void post(Map<String, String> params, Response.Listener<T> successListener, Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.POST, this, params, successListener, errorListener);
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, ResourcePoint, java.util.Map, com.android.volley.Response.Listener, com.android.volley.Response.ErrorListener)} method.
     *
     * @param params                        POST body for this request.
     * @param files                         Files to be included in this multipart/form-date request.
     * @param successListener               Success listener.
     * @param errorListener                 Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void post(Map<String, String> params, Map<String, Pair<File, String>> files, Response.Listener<T> successListener, Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.POST, this, params, files, successListener, errorListener);
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, ResourcePoint, java.util.Map, com.android.volley.Response.Listener, com.android.volley.Response.ErrorListener)} method.
     *
     * @param params                        PUT body for this request.
     * @param successListener               Success listener.
     * @param errorListener                 Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void put(Map<String, String> params, Response.Listener<T> successListener, Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.PUT, this, params, successListener, errorListener);
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, ResourcePoint, java.util.Map, com.android.volley.Response.Listener, com.android.volley.Response.ErrorListener)} method.
     *
     * @param params                        PUT body for this request.
     * @param files                         Files to be included in this multipart/form-date request.
     * @param successListener               Success listener.
     * @param errorListener                 Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void put(Map<String, String> params, Map<String, Pair<File, String>> files, Response.Listener<T> successListener, Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.PUT, this, params, files, successListener, errorListener);
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, ResourcePoint, java.util.Map, com.android.volley.Response.Listener, com.android.volley.Response.ErrorListener)} method.
     *
     * @param params                        DELETE body for this request.
     * @param successListener               Success listener.
     * @param errorListener                 Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void delete(Map<String, String> params, Response.Listener<T> successListener, Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.DELETE, this, params, successListener, errorListener);
    }

    /**
     * Wrapper around {@link com.overturelabs.Cannon#fire(int, ResourcePoint, java.util.Map, com.android.volley.Response.Listener, com.android.volley.Response.ErrorListener)} method.
     *
     * @param params                        PATCH body for this request.
     * @param successListener               Success listener.
     * @param errorListener                 Error listener.
     * @throws Cannon.NotLoadedException    Can't do much if the cannon is not loaded.
     */
    public void patch(Map<String, String> params, Response.Listener successListener, Response.ErrorListener errorListener) throws Cannon.NotLoadedException {
        Cannon.fire(Request.Method.PATCH, this, params, successListener, errorListener);
    }
}
