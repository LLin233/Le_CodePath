package androidpath.ll.material.Views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidpath.ll.material.Adapter.BoxOfficeAdapter;
import androidpath.ll.material.Models.Movie;
import androidpath.ll.material.R;
import androidpath.ll.material.Utils.DBHelper;
import androidpath.ll.material.Utils.JSON.Parser;
import androidpath.ll.material.Utils.JSON.RequestBoxOffice;
import androidpath.ll.material.Utils.MovieSorter;
import androidpath.ll.material.interfaces.SortListener;
import androidpath.ll.material.network.VolleySingleton;

import static androidpath.ll.material.Utils.RottenTomatoesClient.getInstance;
import static com.android.volley.Response.ErrorListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment implements SortListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_MOVIES = "stateMovies";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private DateFormat dateFormat;
    private ArrayList<Movie> listMovies;
    private MovieSorter mMovieSorter;
    private DBHelper mDBHelper;

    //UI
    private RecyclerView mRecyclerViewMovieList;
    private BoxOfficeAdapter mBoxOfficeAdapter;
    private TextView errorMessage;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save movieList here so data won't reload without order when orientation has been changed
        outState.putParcelableArrayList(STATE_MOVIES, listMovies);
    }

    private void init() {
        listMovies = new ArrayList<>();
        dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        mMovieSorter = new MovieSorter();
        mDBHelper = new DBHelper();
        //request data via network
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        init();
        new RequestBoxOffice(getActivity()).execute();
    }

    private void sendJsonRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getInstance().getRequestUrl(30),
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        errorMessage.setVisibility(View.GONE);
                        listMovies = Parser.parseMoviesJSON(response);
                        mDBHelper.insertMoviesBoxOffice(listMovies, true);
                        mBoxOfficeAdapter.setMovieList(listMovies);
                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error);
            }
        });
        mRequestQueue.add(request);

    }


    private void handleVolleyError(VolleyError error) {

        errorMessage.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

            errorMessage.setText(R.string.error_timeout);

        } else if (error instanceof AuthFailureError) {

            errorMessage.setText(R.string.error_auth_failure);

        } else if (error instanceof NetworkError) {

            errorMessage.setText(R.string.error_network);

        } else if (error instanceof ParseError) {

            errorMessage.setText(R.string.error_parser);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        errorMessage = (TextView) view.findViewById(R.id.textVolleyError);
        mRecyclerViewMovieList = (RecyclerView) view.findViewById(R.id.listMovieHits);
        mRecyclerViewMovieList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBoxOfficeAdapter = new BoxOfficeAdapter(getActivity());
        mRecyclerViewMovieList.setAdapter(mBoxOfficeAdapter);
        if (savedInstanceState != null) {
            listMovies = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
        } else {
            listMovies = (ArrayList<Movie>) mDBHelper.getAll();
            //request data only when app started and no data in db
//            if (listMovies.isEmpty()) {
//                sendJsonRequest();
//            }
        }

        mBoxOfficeAdapter.setMovieList(listMovies);
        return view;
    }


    //this method will be call before onCreateView(), to avoid nullException, instantiateItem before consuming this event
    @Override
    public void onSortByName() {
        mMovieSorter.sortMoviesByName(listMovies);
        mBoxOfficeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSortByDate() {
        mMovieSorter.sortMoviesByDate(listMovies);
        mBoxOfficeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSortByRating() {
        mMovieSorter.sortMoviesByRating(listMovies);
        mBoxOfficeAdapter.notifyDataSetChanged();
    }
}
