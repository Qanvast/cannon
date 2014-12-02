package com.overturelabs.cannon.toolbox;

import java.util.Map;

/**
 * Created by stevetan on 12/11/14.
 */
public class GenericResourcePoint<T> extends ResourcePoint<T> {
    private String mSkeletonPath;
    private Class<T> mClassOfT;

    public GenericResourcePoint(Class<T> classOfT, String baseUrl, String skeletonPath) {
        super(baseUrl);

        mClassOfT = classOfT;
        mSkeletonPath = skeletonPath;
    }

    public GenericResourcePoint(Class<T> classOfT, String baseUrl, String skeletonPath, String oAuth2Token) {
        super(baseUrl, oAuth2Token);

        mClassOfT = classOfT;
        mSkeletonPath = skeletonPath;
    }

    public GenericResourcePoint(Class<T> classOfT, String baseUrl, String skeletonPath, final Map<String, String> params) {
        super(baseUrl, params);

        mClassOfT = classOfT;
        mSkeletonPath = skeletonPath;
    }

    public GenericResourcePoint(Class<T> classOfT, String baseUrl, String skeletonPath, final Map<String, String> params, String oAuth2Token) {
        super(baseUrl, params, oAuth2Token);

        mClassOfT = classOfT;
        mSkeletonPath = skeletonPath;
    }

    @Override
    public Class<T> getResourceClass() {
        return mClassOfT;
    }

    @Override
    public String getSkeletonResourcePath() {
        return mSkeletonPath;
    }
}
