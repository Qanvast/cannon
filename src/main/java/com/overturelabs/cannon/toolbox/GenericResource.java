package com.overturelabs.cannon.toolbox;

import java.util.Map;

/**
 * Created by stevetan on 12/11/14.
 */
public class GenericResource<T> extends Resource<T> {
    private String mSkeletonPath;
    private Class<T> mClassOfT;

    public GenericResource(Class<T> classOfT, String baseUrl, String skeletonPath, String oAuth2Token) {
        super(baseUrl, oAuth2Token);

        mClassOfT = classOfT;
        mSkeletonPath = skeletonPath;
    }

    public GenericResource(Class<T> classOfT, String baseUrl, String skeletonPath, Map<String, String> params, String oAuth2Token) {
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
