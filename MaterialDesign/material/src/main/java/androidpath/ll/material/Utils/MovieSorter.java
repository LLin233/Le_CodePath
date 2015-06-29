package androidpath.ll.material.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidpath.ll.material.Models.Movie;

/**
 * Created by Le on 2015/6/25.
 */
public class MovieSorter {
    public void sortMoviesByName(ArrayList<Movie> movies) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }

    public void sortMoviesByDate(ArrayList<Movie> movies) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return rhs.getReleaseDateTheater().compareTo(lhs.getReleaseDateTheater());
            }
        });
    }

    public void sortMoviesByRating(ArrayList<Movie> movies) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return rhs.getAudienceScore() - lhs.getAudienceScore();
            }
        });
    }

}
