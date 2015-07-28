package com.overturelabs.cannon.toolbox;

import com.overturelabs.cannon.toolbox.parsers.GsonResponseParser;
import com.overturelabs.cannon.toolbox.parsers.ResponseParser;
import com.overturelabs.cannon.toolbox.parsers.StringResponseParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Resource point is a helper class that acts as an interface to a specific API endpoint.
 *
 * @param <T>   Type of expected response object.
 * @author      Steve Tan
 */
public abstract class ResourcePoint<T> {
    private final static String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private final static String SKELETON_PATH_REGEX = "^(?:/(?:(?:\\{\\{\\s*[\\d\\w]+\\s*\\}{2})|(?:\\w+[-]*[\\w]*)))+$";
    private final static Pattern SKELETON_PATH_PATTERN = Pattern.compile(SKELETON_PATH_REGEX);
    private final static String PLACEHOLDER_KEY_REGEX = "^[\\d\\w]+$";
    private final static Pattern PLACEHOLDER_KEY_PATTERN = Pattern.compile(PLACEHOLDER_KEY_REGEX);
    // private final static String PLACEHOLDER_VALUE_REGEX = "^[\\d\\w]+$";
    // private final static Pattern PLACEHOLDER_VALUE_PATTERN = Pattern.compile(PLACEHOLDER_VALUE_REGEX);
    private final static String PLACEHOLDER_REGEX_PRE = "\\{\\{\\s*";
    private final static String PLACEHOLDER_REGEX_POST = "\\s*\\}{2}";

    private String mBaseUrl = "http://127.0.0.1";
    private String mSkeletonResourcePath = "/";
    private ResponseParser mResponseParser;

    /**
     * Constructs a resource point with a default {@link com.overturelabs.cannon.toolbox.parsers.StringResponseParser}.
     *
     * @param baseUrl API base URL.
     */
    public ResourcePoint(String baseUrl) {
        mBaseUrl = baseUrl;
        mResponseParser = new StringResponseParser();
    }

    /**
     * Constructs a resource point with a custom {@link com.overturelabs.cannon.toolbox.parsers.ResponseParser}.
     *
     * @param baseUrl        API base URL.
     * @param responseParser {@link com.overturelabs.cannon.toolbox.parsers.ResponseParser} for parsing response.
     */
    public ResourcePoint(String baseUrl, ResponseParser<T> responseParser) {
        mBaseUrl = baseUrl;
        mResponseParser = responseParser;
    }

    /**
     * Constructs a resource point that uses a {@link com.overturelabs.cannon.toolbox.parsers.GsonResponseParser}
     * to parse an object of the specified resource class.
     *
     * @param baseUrl       API base URL.
     * @param resourceClass Resource class of the response object.
     */
    public ResourcePoint(String baseUrl, Class<T> resourceClass) {
        mBaseUrl = baseUrl;
        mResponseParser = new GsonResponseParser<>(resourceClass);
    }

    /**
     * Constructs a resource point with a default {@link com.overturelabs.cannon.toolbox.parsers.StringResponseParser}.
     *
     * @param baseUrl              API base URL.
     * @param skeletonResourcePath Skeleton resource path is a resource path with placeholders
     *                             that you can replace with actual values during runtime.
     *                             The path should be relative to the API base URL.
     *                             <br/>
     *                             For example:
     *                             <br/>
     *                             <code>/user/{{ userId }}</code>
     *                             <br/>
     *                             The default {@link ResourcePoint#getResourcePath(java.util.Map)} method is expecting
     *                             placeholders in the following format:
     *                             <br/>
     *                             <code>{{ placeholderIdentifier }}</code>
     *                             <br/>
     *                             {@link ResourcePoint#getResourcePath(java.util.Map)} will replace the placeholders with
     *                             provided parameters.
     */
    public ResourcePoint(String baseUrl, String skeletonResourcePath) {
        mBaseUrl = baseUrl;
        mSkeletonResourcePath = skeletonResourcePath;
        mResponseParser = new StringResponseParser();
    }

    /**
     * Constructs a resource point with a custom {@link com.overturelabs.cannon.toolbox.parsers.ResponseParser}.
     *
     * @param baseUrl              API base URL.
     * @param skeletonResourcePath Skeleton resource path is a resource path with placeholders
     *                             that you can replace with actual values during runtime.
     *                             The path should be relative to the API base URL.
     *                             <br/>
     *                             For example:
     *                             <br/>
     *                             <code>/user/{{ userId }}</code>
     *                             <br/>
     *                             The default {@link ResourcePoint#getResourcePath(java.util.Map)} method is expecting
     *                             placeholders in the following format:
     *                             <br/>
     *                             <code>{{ placeholderIdentifier }}</code>
     *                             <br/>
     *                             {@link ResourcePoint#getResourcePath(java.util.Map)} will replace the placeholders with
     *                             provided parameters.
     * @param responseParser       {@link com.overturelabs.cannon.toolbox.parsers.ResponseParser} for parsing response.
     */
    public ResourcePoint(String baseUrl, String skeletonResourcePath, ResponseParser<T> responseParser) {
        mBaseUrl = baseUrl;
        mSkeletonResourcePath = skeletonResourcePath;
        mResponseParser = responseParser;
    }

    /**
     * Constructs a resource point that uses a {@link com.overturelabs.cannon.toolbox.parsers.GsonResponseParser}
     * to parse an object of the specified resource class.
     *
     * @param baseUrl              API base URL.
     * @param skeletonResourcePath Skeleton resource path is a resource path with placeholders
     *                             that you can replace with actual values during runtime.
     *                             The path should be relative to the API base URL.
     *                             <br/>
     *                             For example:
     *                             <br/>
     *                             <code>/user/{{ userId }}</code>
     *                             <br/>
     *                             The default {@link ResourcePoint#getResourcePath(java.util.Map)} method is expecting
     *                             placeholders in the following format:
     *                             <br/>
     *                             <code>{{ placeholderIdentifier }}</code>
     *                             <br/>
     *                             {@link ResourcePoint#getResourcePath(java.util.Map)} will replace the placeholders with
     *                             provided parameters.
     * @param resourceClass        Resource class of the response object.
     */
    public ResourcePoint(String baseUrl, String skeletonResourcePath, Class<T> resourceClass) {
        mBaseUrl = baseUrl;
        mSkeletonResourcePath = skeletonResourcePath;
        mResponseParser = new GsonResponseParser<>(resourceClass);
    }

    /**
     * This function will only process the skeleton resource path if it's valid.
     * Otherwise, the entire skeleton path is passed, as-is, upwards as the URL
     * of this resource point.
     *
     * @param params   Parameters for filling into the placeholders.
     * @param encoding Charset to encode the URL parameters in.
     * @return Returns a URN (Uniform Resource Name) reference path that is relative to the API base URL.
     * @throws UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    public String getResourcePath(Map<String, String> params, String encoding) throws UnsupportedEncodingException {
        String resourcePath = mSkeletonResourcePath;

        // Validate skeleton resource path.
        if (SKELETON_PATH_PATTERN.matcher(resourcePath).matches() && params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                // Check if key is valid
                String key = entry.getKey();
                String value = entry.getValue();

                if (key != null && key.length() > 0) {
                    key = key.trim();
                }

                if (value != null && value.length() > 0) {
                    value = value.trim();
                }

                // We don't validate the value, we just encode it.
                value = URLEncoder.encode(value, encoding);

                if (PLACEHOLDER_KEY_PATTERN.matcher(key).matches()) {
                    String placeholderRegexp = PLACEHOLDER_REGEX_PRE + entry.getKey() + PLACEHOLDER_REGEX_POST;


                    resourcePath = resourcePath.replaceAll(placeholderRegexp, value);
                }
            }
        }

        return resourcePath;
    }

    /**
     * This function will only process the skeleton resource path if it's valid.
     * Otherwise, the entire skeleton path is passed, as-is, upwards as the URL
     * of this resource point.
     * <br/>
     * Uses the default UTF-8 encoding to encode the resource path.
     *
     * @param params   Parameters for filling into the placeholders.
     * @return Returns a URN (Uniform Resource Name) reference path that is relative to the API base URL.
     * @throws UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    public String getResourcePath(Map<String, String> params) throws UnsupportedEncodingException {
        return getResourcePath(params, DEFAULT_PARAMS_ENCODING);
    }

    /**
     * Set the skeleton resource path.
     *
     * @param skeletonResourcePath Skeleton resource path is a resource path with placeholders
     *                             that you can replace with actual values during runtime.
     *                             The path should be relative to the API base URL.
     *                             <br/>
     *                             For example:
     *                             <br/>
     *                             <code>/user/{{ userId }}</code>
     *                             <br/>
     *                             The default {@link ResourcePoint#getResourcePath(java.util.Map)} method is expecting
     *                             placeholders in the following format:
     *                             <br/>
     *                             <code>{{ placeholderIdentifier }}</code>
     *                             <br/>
     *                             {@link ResourcePoint#getResourcePath(java.util.Map)} will replace the placeholders with
     *                             provided parameters.
     * @return Returns the updated {@link ResourcePoint}.
     */
    public ResourcePoint<T> setSkeletonResourcePath(String skeletonResourcePath) {
        mSkeletonResourcePath = skeletonResourcePath;

        return this;
    }

    /**
     * Constructs the URL based on the base URL and resource path provided.
     *
     * @param resourcePathParams Map of values for filling into the skeleton resource path.
     * @return Returns a full URI path.
     * @throws UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    public String getUrl(Map<String, String> resourcePathParams) throws UnsupportedEncodingException {
        return getUrl(resourcePathParams, DEFAULT_PARAMS_ENCODING);
    }

    /**
     * Constructs the URL based on the base URL and resource path provided.
     *
     * @param resourcePathParams Map of values for filling into the skeleton resource path.
     * @param encoding           Charset to encode the URL parameters in.
     * @return Returns a full URI path.
     * @throws UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    public String getUrl(Map<String, String> resourcePathParams, String encoding) throws UnsupportedEncodingException {
        return mBaseUrl + getResourcePath(resourcePathParams, encoding);
    }

    /**
     * Constructs the URL based on the base URL and resource path provided.
     * If a map of query parameters are provided, a URL query parameter string
     * will be constructed and appended to the URL. {@code UTF-8} is used as the
     * default charset for encoding the URL query parameter string.
     * <br/>
     * NOTE: Any key/value pair that cannot be encoded properly will be ignored!
     *
     * @param resourcePathParams Map of values for filling into the skeleton resource path.
     * @param urlQueryParams     Map of query params.
     * @return Returns a full URI path.
     * @throws UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    public String getUrl(Map<String, String> resourcePathParams, Map<String, String> urlQueryParams) throws UnsupportedEncodingException {
        return getUrl(resourcePathParams, urlQueryParams, DEFAULT_PARAMS_ENCODING);
    }

    /**
     * Constructs the URL based on the base URL and resource path provided.
     * If a map of query parameters are provided, a URL query parameter string
     * will be constructed and appended to the URL.
     * <br/>
     * NOTE: Any key/value pair that cannot be encoded properly will be ignored!
     *
     * @param resourcePathParams Map of values for filling into the skeleton resource path.
     * @param urlQueryParams     Map of query params.
     * @param encoding           Charset to encode the URL parameters in.
     * @return Returns a full URI path.
     * @throws UnsupportedEncodingException Thrown when value cannot be encoded.
     */
    public String getUrl(Map<String, String> resourcePathParams, Map<String, String> urlQueryParams, String encoding) throws UnsupportedEncodingException {
        String url = mBaseUrl + getResourcePath(resourcePathParams);

        if (urlQueryParams != null && urlQueryParams.size() > 0) {
            boolean isFirst = true;
            for (Map.Entry<String, String> entry : urlQueryParams.entrySet()) {
                if (isFirst) {
                    isFirst = false;
                    url += "?";
                } else {
                    url += "&";
                }

                try {
                    url += URLEncoder.encode(entry.getKey(), encoding);
                    url += "=";
                    url += URLEncoder.encode(entry.getValue(), encoding);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace(); // Ignore this pair of key/value.
                }
            }
        }

        return url;
    }

    /**
     * Response parser is used to parse any successful response. Overide
     * this method in your child class to change to a different response parser.
     * <br/>
     * By default, a {@link com.overturelabs.cannon.toolbox.parsers.GsonResponseParser} is used.
     *
     * @return Returns a {@link com.overturelabs.cannon.toolbox.parsers.ResponseParser}
     */
    public ResponseParser<T> getResponseParser() {
        return mResponseParser;
    }
}
