package ll.caching.caches.cacheobservers;


import ll.caching.caches.Data;
import rx.Observable;

/**
 * Created by Le on 2016/2/11.
 */
public abstract class CacheObservable {
    public abstract Observable<Data> getObservable(String url);
}