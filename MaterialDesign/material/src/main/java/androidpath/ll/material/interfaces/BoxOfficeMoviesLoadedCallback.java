package androidpath.ll.material.interfaces;

import java.util.ArrayList;

import androidpath.ll.material.Models.Movie;

/**
 * Created by Le on 2015/6/29.
 */
public interface BoxOfficeMoviesLoadedCallback {
    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> listMovies);
}
