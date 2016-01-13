package ll.instagramintegrationdemo.Model;

/**
 * Created by Le on 2016/1/12.
 */
public class Media {

    private String attribution;
    private String type;
    private String link;
    private String filter;
    private Long created_time;
    private Images images;
    private String id;
    private User user;

    public String getAttribution() {
        return attribution;
    }

    public String getType() {
        return type;
    }


    public String getFilter() {
        return filter;
    }

    public Long getCreatedTime() {
        return created_time;
    }

    public String getLink() {
        return link;
    }


    public Images getImages() {
        return images;
    }


    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Media{" +
                "attribution='" + attribution + '\'' +
                ", type='" + type + '\'' +
                ", link='" + link + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}