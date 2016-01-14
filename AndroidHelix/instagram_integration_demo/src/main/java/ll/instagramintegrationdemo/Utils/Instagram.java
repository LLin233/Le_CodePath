package ll.instagramintegrationdemo.Utils;

import java.net.URI;
import java.net.URISyntaxException;

import ll.instagramintegrationdemo.Model.Scope;
import ll.instagramintegrationdemo.Model.UsersEndpoint;

/**
 * Created by Le on 2016/1/12.
 */
public final class Instagram {

// set your desired log level

    private final String accessToken;
    private UsersEndpoint usersEndpoint;

    public Instagram(final String accessToken) {
        this.accessToken = accessToken;
    }

    public UsersEndpoint getUsersEndpoint() {
        if (usersEndpoint == null) {
            usersEndpoint = new UsersEndpoint(accessToken);
        }
        return usersEndpoint;
    }

    public static String requestOAuthUrl(final String clientId, final String redirectUri, final Scope... scopes) throws URISyntaxException {
        final StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("response_type=").append("token");
        urlBuilder.append("&client_id=").append(clientId);
        urlBuilder.append("&redirect_uri=").append(redirectUri);
        if (scopes != null) {
            final StringBuilder scopeBuilder = new StringBuilder();
            for (int i = 0; i < scopes.length; i++) {
                scopeBuilder.append(scopes[i]);
                if (i < scopes.length - 1) {
                    scopeBuilder.append(' ');
                }
            }
            urlBuilder.append("&scope=").append(scopeBuilder.toString());
        }
        return new URI("https", "instagram.com", "/oauth/authorize", urlBuilder.toString(), null).toString();
    }

}