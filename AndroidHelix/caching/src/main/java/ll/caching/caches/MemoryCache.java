package ll.caching.caches;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Le on 2016/2/11.
 */

public class MemoryCache<T> extends LruCache<T, Bitmap> {

    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(T key, Bitmap b) {
        int size = 0;
        if (b != null) {
            size = b.getRowBytes() * b.getHeight();
        }
        return size;
    }
}