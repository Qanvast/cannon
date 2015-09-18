package com.overturelabs.cannon.toolbox;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by derricklee on 18/9/15.
 */
public class ImageLoaderCustomRetryPolicy
        extends ImageLoader {
    /** Socket timeout in milliseconds for image requests */
    // From 1 second to 10 seconds
    private static final int IMAGE_TIMEOUT_MS = 10 * 1000;

    /** Default number of retries for image requests */
    // From 2 retries to 3 retries
    private static final int IMAGE_MAX_RETRIES = 3;

    /** Default backoff multiplier for image requests */
    private static final float IMAGE_BACKOFF_MULT = 2f;

    /**
     * Constructs a new ImageLoader.
     *
     * @param queue      The RequestQueue to use for making image requests.
     * @param imageCache The cache to use as an L1 cache.
     */
    public ImageLoaderCustomRetryPolicy(RequestQueue queue,
                                        ImageCache imageCache) {
        super(queue, imageCache);
    }

    @Override
    protected Request<Bitmap> makeImageRequest(String requestUrl,
                                               int maxWidth,
                                               int maxHeight,
                                               ImageView.ScaleType scaleType,
                                               final String cacheKey) {
        Request<Bitmap> request = super.makeImageRequest(requestUrl, maxWidth, maxHeight, scaleType, cacheKey)
;       request.setRetryPolicy(new DefaultRetryPolicy(IMAGE_TIMEOUT_MS, IMAGE_MAX_RETRIES, IMAGE_BACKOFF_MULT));
        return request;
    }
}
