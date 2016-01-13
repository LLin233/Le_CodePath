package ll.instagramintegrationdemo.Model;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Le on 2016/1/12.
 */
public abstract class BaseEndpoint {

    protected static final String BASE_URL = "https://api.instagram.com/v1/";
    protected static OkHttpClient httpClient = new OkHttpClient();
    protected static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    protected static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    protected final String accessToken;
    protected BaseEndpoint(final String accessToken) {
        this.accessToken = accessToken;
    }

}