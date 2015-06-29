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
import androidpath.ll.material.network.VolleySingleton;

/**
 * Created by Le on 2015/6/29.
 */
public class RequestBoxOffice extends AsyncTask<Void, Void, Boolean> {

    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private ArrayList<Movie> mListMovies;
    private DBHelper mDBHelper;
    private Context mContext;

    public RequestBoxOffice(Context context) {
        mContext = context;
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        mDBHelper = new DBHelper();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

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
            return false;
        }

        mListMovies = Parser.parseMoviesJSON(response);
        mDBHelper.insertMoviesBoxOffice(mListMovies, true);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (!result) {
            Toast.makeText(mContext, "Network is not working", Toast.LENGTH_SHORT).show();
        }
    }
}
