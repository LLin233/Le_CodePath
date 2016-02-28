package ll.caching.caches;

import android.content.Context;
import android.util.Log;

import ll.rxjava.caches.cacheobservers.DiskCacheObservable;
import ll.rxjava.caches.cacheobservers.MemoryCacheOvservable;
import ll.rxjava.caches.cacheobservers.NetCacheObservable;
import rx.Observable;

/**
 * Created by Le on 2016/2/11.
 */
public class Sources {
    Context mContext;
    MemoryCacheOvservable mMemoryCacheOvservable;
    DiskCacheObservable mDiskCacheObservable;
    NetCacheObservable mNetCacheObservable;

    public Sources(Context mContext) {
        this.mContext = mContext;
        mMemoryCacheOvservable = new MemoryCacheOvservable();
        mDiskCacheObservable = new DiskCacheObservable(mContext);
        mNetCacheObservable = new NetCacheObservable();
    }


    public Observable<Data> memory(String url) {
        return mMemoryCacheOvservable.getObservable(url)
                .compose(logSource("MEMORY"));
    }

    public Observable<Data> disk(String url) {
        return mDiskCacheObservable.getObservable(url)
                .filter(data -> data.bitmap != null)
                //save picture to disk
                .doOnNext(mMemoryCacheOvservable::putData)
                .compose(logSource("DISK"));

    }

    public Observable<Data> network(String url) {
        return mNetCacheObservable.getObservable(url)
                .doOnNext(data -> {
                    //save picture to disk and memory
                    mMemoryCacheOvservable.putData(data);
                    mDiskCacheObservable.putData(data);
                })
                .compose(logSource("NET"));
    }

    Observable.Transformer<Data, Data> logSource(final String source) {
        return dataObservable -> dataObservable.doOnNext(data -> {
            if (data != null && data.bitmap != null) {
                Log.i("Source ", source + " has the data you are looking for!");
            } else {
                Log.i("Source ",source + " not has the data!");
            }
        });
    }
}