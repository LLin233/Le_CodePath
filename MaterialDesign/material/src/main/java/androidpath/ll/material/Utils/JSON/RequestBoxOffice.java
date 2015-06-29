package androidpath.ll.material.Utils.JSON;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidpath.ll.material.Models.Movie;
import androidpath.ll.material.Utils.DBHelper;
import androidpath.ll.material.Utils.RottenTomatoesClient;
import androidpath.ll.material.interfaces.BoxOfficeMoviesLoadedCallback;
import androidpath.ll.material.network.VolleySingleton;

/**
 * Created by Le on 2015/6/29.
 */
public class RequestBoxOffice extends AsyncTask<Void, Void, ArrayList<Movie>> {

    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private BoxOfficeMoviesLoadedCallback mCallback;
    private DBHelper mDBHelper;
    private Context mContext;

    public RequestBoxOffice(Context context, BoxOfficeMoviesLoadedCallback callback) {
        mContext = context;
        mCallback = callback;
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        mDBHelper = new DBHelper();
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {
        JSONObject response = null;
        final RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(
                com.android.volley.Request.Method.GET,
                RottenTomatoesClient.getInstance().getRequestUrl(30),
                (String) null,
                requestFuture,
                requestFuture);
        mRequestQueue.add(request);
        try {
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        ArrayList<Movie> mListMovies = Parser.parseMoviesJSON(response);
        mDBHelper.insertMoviesBoxOffice(mListMovies, true);
        return mListMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> result) {
        if (result.isEmpty()) {
            Toast.makeText(mContext, "Network is not working", Toast.LENGTH_SHORT).show();
        } else if (mCallback != null) {
            mCallback.onBoxOfficeMoviesLoaded(result);
            Toast.makeText(mContext, "Data loading completed ", Toast.LENGTH_SHORT).show();
        }

    }

}
