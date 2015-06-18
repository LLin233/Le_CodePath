package androidpath.ll.material.Models;

import android.os.Parcel;

import java.util.Date;

/**
 * Created by Le on 2015/6/17.
 */
public class Movie {
    private long id;
    private String title;
    private Date releaseDateTheater;
    private int audienceScore;
    private String synopsis;
    private String urlThumbnail;
    private String urlSelf;
    private String urlCast;
    private String urlReviews;
    private String urlSimilar;


    public Movie() {

    }


    public Movie(Parcel input) {
        id = input.readLong();
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

    public Movie(long id,
                 String title,
                 Date releaseDateTheater,
                 int audienceScore,
                 String synopsis,
                 String urlThumbnail,
                 String urlSelf,
                 String urlCast,
                 String urlReviews,
                 String urlSimilar) {
        this.id = id;
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


    public long getId() {
        return id;
    }

    public Movie setId(long id) {
        this.id = id;
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
        return "ID: " + id + "\n"
                + "Title: " + title + "\n"
                + "Synopsis: " + synopsis + "\n"
                + "Audience Score: " + audienceScore + "\n"
                + "Thumbnail: " + urlThumbnail;
    }
}
