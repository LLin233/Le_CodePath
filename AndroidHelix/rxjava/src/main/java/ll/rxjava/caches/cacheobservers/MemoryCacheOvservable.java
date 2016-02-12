package ll.rxjava.caches.cacheobservers;

import android.util.Log;

import ll.rxjava.caches.Data;
import ll.rxjava.caches.MemoryCache;
import rx.Observable;

/**
 * Created by Le on 2016/2/11.
 */
public class MemoryCacheOvservable extends CacheObservable {
    public static final int DEFAULT_CACHE_SIZE = (24 /* MiB */ * 1024 * 1024);
    MemoryCache<String> mCache = new MemoryCache<>(DEFAULT_CACHE_SIZE);

    @Override
    public Observable<Data> getObservable(String url) {
        return Observable.create(subscriber -> {
            Log.i("Memory","search in memory");
            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(new Data(mCache.get(url), url));
                subscriber.onCompleted();
            }
        });
    }

    public void putData(Data data) {
        mCache.put(data.url, data.bitmap);
    }
}