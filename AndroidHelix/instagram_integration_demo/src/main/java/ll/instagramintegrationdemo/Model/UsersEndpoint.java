package ll.instagramintegrationdemo.Model;

import android.util.Log;

import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Le on 2016/1/12.
 */
public final class UsersEndpoint extends BaseEndpoint {

    private List<Media> list;

    public interface UserService {
        @GET("users/{user_id}")
        Call<Profile> getUser(
                @Path("user_id") String userId,
                @Query("access_token") String accessToken);

        // https://api.instagram.com/v1/users/self/media/recent?access_token=2539450151.ffc2750.9f17f7158cf048269c213d37c9fb8a54&count=20
        @GET("users/{user_id}/media/recent")
        Call<Recent> getRecent(
                @Path("user_id") String userId,
                @Query("access_token") String accessToken,
                @Query("count") Integer count);
    }

    private final UserService userService;

    public UsersEndpoint(final String accessToken) {
        super(accessToken);
        //final RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(logLevel).setEndpoint(BASE_URL).build();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        httpClient.interceptors().add(logging);
        Retrofit retrofit = builder.client(httpClient).build();
        Log.d("User", retrofit.toString());
        userService = retrofit.create(UserService.class);
    }

    public UserService getUserService() {
        return userService;
    }

    public User getSelf() {
        //return userService.getUser("self", accessToken).execute().body().getUser();
        final User[] self = {null};
        userService.getUser("self", accessToken).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Response<Profile> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    self[0] = response.body().getUser();
                    Log.d("User", self[0].toString());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("User", t.toString());
            }
        });
        if (self.length == 0) {
            throw new IllegalArgumentException();
        }
        return self[0];
    }

    public User getUser(final String userId) throws IOException {
        return userService.getUser(userId, accessToken).execute().body().getUser();
    }


    public Recent getRecent() {
        final Recent[] recent = {null};
        userService.getRecent("self", accessToken, 20).enqueue(new Callback<Recent>() {
            @Override
            public void onResponse(Response<Recent> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    recent[0] = response.body();
                    for (Media item : response.body().getMediaList()) {
                        Log.d("User", item.toString());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                throw new IllegalArgumentException();
            }
        });

        return recent[0];
    }

    public List<Media> getRecentMediaList() {
        userService.getRecent("self", accessToken, 20).enqueue(new Callback<Recent>() {
            @Override
            public void onResponse(Response<Recent> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    list = response.body().getMediaList();
                    EventBus.getDefault().post(new LoadPhotoEvent("photo", list));
                    for (Media item : response.body().getMediaList()) {
                        Log.d("User", item.toString());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                throw new IllegalArgumentException();
            }
        });
        return list;
    }

    public Recent getRecent(final String userId, final Integer count, final String minId, final String maxId, final Long minTimestamp, final Long maxTimestamp) {
        return (Recent) userService.getRecent(userId, accessToken, count);
    }


}