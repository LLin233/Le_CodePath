package androidpath.ll.material.Utils;

/**
 * Created by Le on 2015/6/17.
 */
public class RottenTomatoesClient {
    private final String API_KEY = "9htuhtcb4ymusd73d4z6jxcj";
    private final String API_BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0/";
    private final String API_BOX_OFFICE = "lists/movies/box_office.json";
    private final String URL_QUESTION_MARK = "?";
    private final String URL_AND_MARK = "&";
    private final String PARAM_API_KEY = "apikey=";
    private final String PARAM_LIMIT = "limit=";

    private static RottenTomatoesClient mInstance = null;

    private RottenTomatoesClient() {

    }

    public static RottenTomatoesClient getInstance() {
        if (mInstance == null) {
            mInstance = new RottenTomatoesClient();
        }
        return mInstance;

    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=<key>&limit=<number>
    public String getRequestUrl(int limit) {
        return getApiUrl(API_BOX_OFFICE)
                + URL_QUESTION_MARK
                + PARAM_API_KEY + API_KEY
                + URL_AND_MARK
                + PARAM_LIMIT + limit;
    }
}
