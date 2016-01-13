package ll.instagramintegrationdemo.Model;

/**
 * Created by Le on 2016/1/12.
 */
public class User {

    private String id;
    private String username;
    private String full_name;
    private String profile_picture;
    private String bio;
    private String website;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return full_name;
    }

    public String getProfilePicture() {
        return profile_picture;
    }

    public String getBio() {
        return bio;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", profile_picture='" + profile_picture + '\'' +
                ", full_name='" + full_name + '\'' +
                '}';
    }
}