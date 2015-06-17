package androidpath.ll.material.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import androidpath.ll.material.MyApplication;

/**
 * Created by Le on 2015/6/15.
 */
public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton() {

        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private int cacheSize = (int) Runtime.getRuntime().maxMemory() / 1024 / 8;
            private LruCache<String, Bitmap> cache = new LruCache<>(cacheSize);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getInstance() {
        if (mInstance == null) {
            mInstance = new VolleySingleton();
        }
        return mInstance;

    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }
}
