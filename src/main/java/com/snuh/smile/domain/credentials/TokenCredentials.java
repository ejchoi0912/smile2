package com.snuh.smile.domain.credentials;

import com.snuh.smile.util.OAuth10aConstants;
import lombok.Data;
import lombok.NonNull;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-08-19.
 */
@Data
public class TokenCredentials extends AbstractOAuth10aCredentials {

    // underscore name is needed to be bound automatically

    private String oauth_token_secret;

    public TokenCredentials(@NonNull String responseBody) {
        String[] elements = responseBody.split("&");
        for (String element : elements) {
            String[] kv = element.split("=");
            if (OAuth10aConstants.OAUTH_TOKEN.equals(kv[0].toLowerCase()))
                this.setOauth_token(kv[1]);
            if (OAuth10aConstants.OAUTH_TOKEN_SECRET.equals(kv[0].toLowerCase()))
                this.setOauth_token_secret(kv[1]);
        }
    }

    @Override
    public String toString() {
        return "TokenCredentials{" +
                "oauth_token_secret='" + oauth_token_secret + '\'' +
                ", oauth_token='" + oauth_token + '\'' +
                '}';
    }
}
