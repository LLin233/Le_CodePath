package ll.caching.caches.cacheobservers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import ll.rxjava.caches.Data;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Le on 2016/2/11.
 */
public class DiskCacheObservable extends CacheObservable {
    Context mContext;
    File mCacheFile;

    public DiskCacheObservable(Context mContext) {
        this.mContext = mContext;
        mCacheFile = mContext.getCacheDir();
    }

    @Override
    public Observable<Data> getObservable(String url) {
        return Observable.create(new Observable.OnSubscribe<Data>() {
            @Override
            public void call(Subscriber<? super Data> subscriber) {
                Log.i("Disk", "read file from disk");
                File f = getFile(url);
                Data data = new Data(f, url);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private File getFile(String url) {
        url = url.replaceAll(File.separator, "-");
        return new File(mCacheFile, url);
    }

    /**
     * save pictures downloaded from net to disk
     *
     * @param data data to be saved
     */
    public void putData(Data data) {
        Observable.create(new Observable.OnSubscribe<Data>() {
            @Override
            public void call(Subscriber<? super Data> subscriber) {
                File f = getFile(data.url);
                OutputStream out = null;
                try {
                    out = new FileOutputStream(f);
                    Bitmap.CompressFormat format;
                    if (data.url.endsWith("png") || data.url.endsWith("PNG")) {
                        format = Bitmap.CompressFormat.PNG;
                    } else {
                        format = Bitmap.CompressFormat.JPEG;
                    }
                    data.bitmap.compress(format, 100, out);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}