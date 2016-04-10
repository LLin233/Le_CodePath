package ll.morerxjava.network;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Le on 2016/4/9.
 */
public class ServiceFactory {
    public static <T> T createServiceFrom(final Class<T> serviceClass, String endpoint) {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return adapter.create(serviceClass);
    }
}