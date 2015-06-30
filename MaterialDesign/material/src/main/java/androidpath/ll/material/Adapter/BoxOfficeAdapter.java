package androidpath.ll.material.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidpath.ll.material.Models.AppConstants;
import androidpath.ll.material.Models.Movie;
import androidpath.ll.material.R;
import androidpath.ll.material.Utils.AnimationUtils;
import androidpath.ll.material.network.VolleySingleton;

/**
 * Created by Le on 2015/6/17.
 */
public class BoxOfficeAdapter extends RecyclerView.Adapter<BoxOfficeAdapter.BoxOfficeViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int previousPosition = 0;

    public BoxOfficeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
    }

    @Override
    public BoxOfficeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.box_office_list_item, parent, false);
        return new BoxOfficeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BoxOfficeViewHolder holder, int position) {
        Movie movie = listMovies.get(position);
        holder.movieTitle.setText(movie.getTitle());

        //bind Date
        Date releaseDate = movie.getReleaseDateTheater();
        if (releaseDate != null) {
            String formatedDate = mDateFormat.format(releaseDate);
            holder.movieReleaseDate.setText(formatedDate);
        } else {
            holder.movieReleaseDate.setText(AppConstants.Constants.NA);
        }

        //bind AudienceScore
        int audienceScore = movie.getAudienceScore();

        if (audienceScore == -1) {
            holder.movieAudienceScore.setRating(0.0f);
            holder.movieAudienceScore.setAlpha(0.5f);
        } else {
            holder.movieAudienceScore.setRating(audienceScore / 20.0f);
            holder.movieAudienceScore.setAlpha(1.0f);
        }

        if (position > previousPosition) {
            //scroll down
            AnimationUtils.animate(holder, true);
        } else {
            //scroll up
            AnimationUtils.animate(holder, false);
        }
        previousPosition = position;

        String urlThumnail = movie.getUrlThumbnail();
        loadPoster(urlThumnail, holder);

    }


    private void loadPoster(String urlThumbnail, final BoxOfficeViewHolder holder) {

        if (!urlThumbnail.equals(AppConstants.Constants.NA)) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    public void setMovieList(ArrayList<Movie> listMovies) {
        this.listMovies = listMovies;
        notifyItemRangeChanged(0, listMovies.size());
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }


    static class BoxOfficeViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieThumbnail;
        private TextView movieTitle;
        private TextView movieReleaseDate;
        private RatingBar movieAudienceScore;

        public BoxOfficeViewHolder(View itemView) {
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);

        }
    }

}
