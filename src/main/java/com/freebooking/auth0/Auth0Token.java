package com.freebooking.auth0;

import lombok.Data;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by nguyenqmai on 1/18/2018.
 */
@Data
public class Auth0Token {
    String access_token;
    String token_type;
    String scope;
    long expires_in;
    long issuedAt;

    public void setIssuedAt(long current) {
        issuedAt = current;
    }

    public boolean hasExpired(long currentTimestampMillis) {
        return issuedAt + expires_in * 1000 < currentTimestampMillis;
    }

    public static Auth0Token getToken(String authTokenUrl, String auth0ClientId, String auth0ClientSecret, String audience, long current) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity =
                new HttpEntity<>(
                        String.format("{\"grant_type\":\"client_credentials\", \"client_id\": \"%s\", \"client_secret\": \"%s\",\"audience\": \"%s\"}",
                                auth0ClientId,
                                auth0ClientSecret,
                                audience),
                        headers);

        ResponseEntity<Auth0Token> tmp = restTemplate.exchange(authTokenUrl, HttpMethod.POST, entity, Auth0Token.class);
        Auth0Token ret = tmp.getBody();
        ret.setIssuedAt(current);
        return ret;
    }
}
