package androidpath.ll.material.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.util.SQLiteUtils;

import java.util.Date;

/**
 * Created by Le on 2015/6/17.
 */


@Table(name = "Movies")
public class Movie extends Model implements Parcelable {
    @Column(name = "movieID", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long movieId;
    @Column(name = "title")
    private String title;
    @Column(name = "releaseDateTheater")
    private Date releaseDateTheater;
    @Column(name = "audienceScore")
    private int audienceScore;
    @Column(name = "synopsis")
    private String synopsis;
    @Column(name = "urlThumbnail")
    private String urlThumbnail;
    private String urlSelf;
    private String urlCast;
    private String urlReviews;
    private String urlSimilar;


    public Movie() {
        super();
    }


    public Movie(Parcel input) {
        movieId = input.readLong();
        title = input.readString();
        long dateMillis = input.readLong();
        releaseDateTheater = (dateMillis == -1 ? null : new Date(dateMillis));
        audienceScore = input.readInt();
        synopsis = input.readString();
        urlThumbnail = input.readString();
        urlSelf = input.readString();
        urlCast = input.readString();
        urlReviews = input.readString();
        urlSimilar = input.readString();
    }

    public Movie(long movieId,
                 String title,
                 Date releaseDateTheater,
                 int audienceScore,
                 String synopsis,
                 String urlThumbnail,
                 String urlSelf,
                 String urlCast,
                 String urlReviews,
                 String urlSimilar) {
        this.movieId = movieId;
        this.title = title;
        this.releaseDateTheater = releaseDateTheater;
        this.audienceScore = audienceScore;
        this.synopsis = synopsis;
        this.urlThumbnail = urlThumbnail;
        this.urlSelf = urlSelf;
        this.urlCast = urlCast;
        this.urlReviews = urlReviews;
        this.urlSimilar = urlSimilar;
    }


    public long getMovieId() {
        return movieId;
    }

    public Movie setMovieId(long movieId) {
        this.movieId = movieId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public Date getReleaseDateTheater() {
        return releaseDateTheater;
    }

    public Movie setReleaseDateTheater(Date releaseDateTheater) {
        this.releaseDateTheater = releaseDateTheater;
        return this;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public Movie setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
        return this;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Movie setSynopsis(String synopsis) {
        this.synopsis = synopsis;
        return this;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public Movie setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
        return this;
    }

    public String getUrlSelf() {
        return urlSelf;
    }

    public Movie setUrlSelf(String urlSelf) {
        this.urlSelf = urlSelf;
        return this;
    }

    public String getUrlCast() {
        return urlCast;
    }

    public Movie setUrlCast(String urlCast) {
        this.urlCast = urlCast;
        return this;
    }

    public String getUrlReviews() {
        return urlReviews;
    }

    public Movie setUrlReviews(String urlReviews) {
        this.urlReviews = urlReviews;
        return this;
    }

    public String getUrlSimilar() {
        return urlSimilar;
    }

    public Movie setUrlSimilar(String urlSimilar) {
        this.urlSimilar = urlSimilar;
        return this;
    }

    @Override
    public String toString() {
        return "ID: " + movieId + "\n"
                + "Title: " + title + "\n"
                + "Synopsis: " + synopsis + "\n"
                + "Audience Score: " + audienceScore + "\n"
                + "Thumbnail: " + urlThumbnail + "\n"
                + "Date: " + getReleaseDateTheater().getTime();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(movieId);
        dest.writeString(title);
        dest.writeLong(releaseDateTheater == null ? -1 : releaseDateTheater.getTime());
        dest.writeInt(audienceScore);
        dest.writeString(synopsis);
        dest.writeString(urlThumbnail);
        dest.writeString(urlSelf);
        dest.writeString(urlCast);
        dest.writeString(urlReviews);
        dest.writeString(urlSimilar);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public static void clearData() {
        SQLiteUtils.execSql("DELETE FROM Movies");
    }

}
