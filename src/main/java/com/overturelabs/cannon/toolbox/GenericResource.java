package com.overturelabs.cannon.toolbox;

import java.util.Map;

/**
 * Created by stevetan on 12/11/14.
 */
public class GenericResource<T> extends Resource<T> {
    private String mSkeletonPath;
    private Class<T> mClassOfT;

    public GenericResource(Map<String, String> params, Class<T> classOfT, String baseUrl, String skeletonPath) {
        super(params, baseUrl);

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
