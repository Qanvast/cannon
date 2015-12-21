package com.overturelabs.cannon.toolbox.vOutOfMemory;

import com.android.volley.toolbox.DiskBasedCache;

import java.io.File;

public class DiskBasedCacheOOM extends DiskBasedCache {
    public DiskBasedCacheOOM(File rootDirectory, int maxCacheSizeInBytes) {
        super(rootDirectory, maxCacheSizeInBytes);
    }

    public DiskBasedCacheOOM(File rootDirectory) {
        super(rootDirectory);
    }

    @Override
    public synchronized Entry get(String key) {
        try {
            return super.get(key);
        } catch(java.lang.OutOfMemoryError error) {
            return null;
        }
    }

}
