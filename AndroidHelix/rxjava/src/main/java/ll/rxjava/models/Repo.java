package ll.rxjava.models;

/**
 * Created by Le on 2016/3/4.
 */

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Repo {
    public static class Owner {
        private String login;
        private String id;
        @SerializedName("avatar_url")
        private String avatarUrl;
        @SerializedName("html_url")
        private String htmlUrl;

        public Owner(String login, String id, String avatarUrl, String htmlUrl) {
            this.login = login;
            this.id = id;
            this.avatarUrl = avatarUrl;
            this.htmlUrl = htmlUrl;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public void setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
        }
    }

    private String id;
    private String name;
    private Owner owner;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Repo(String id, String name, String description, Owner owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
