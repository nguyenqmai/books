package com.freebooking.service;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.users.User;
import com.freebooking.auth0.Auth0Token;
import com.freebooking.auth0.authorization.AuthorizationApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

/**
 * Created by nguyenqmai on 10/4/2017.
 */
@Service
public class Auth0Services {
    private static final Logger logger = LoggerFactory.getLogger(Auth0Services.class);

    private static String DEFAULT_SCHEME = "https:";
    @Value("${app.auth0.domain}")
    private String auth0Domain;
    @Value("${app.auth0.oauth.token}")
    private String authToken;

    @Value("${app.auth0.accountManagement.audience}")
    private String auth0AccountManagementAudience;

    @Value("${app.auth0.accountAuthorization.api}")
    private String auth0AccountAuthorizationApi;

    @Value("${app.auth0.accountAuthorization.audience}")
    private String auth0AccountAuthorizationAudience;

    @Value("${app.auth0.client.id}")
    private String auth0ClientId;
    @Value("${app.auth0.client.secret}")
    private String auth0ClientSecret;

    @Value("${app.jwt.TOKEN_LEEWAY_SECONDS}")
    private int tokenLeewaySeconds;

    private Auth0Token managementToken;

    private Auth0Token authorizationToken;

    @Autowired
    private ManagementAPI managementAPI;

    @Autowired
    private AuthAPI authAPI;

    @Autowired
    private AuthorizationApi authorizationApi;


    @Bean
    public AuthAPI getAuthAPI() {
        return new AuthAPI(auth0Domain, auth0ClientId, auth0ClientSecret);
    }

    @Bean
    public ManagementAPI getManagementAPI() {
        return new ManagementAPI(auth0Domain, getManagementToken().getAccess_token());
    }

    @Bean
    public AuthorizationApi getAuthorizationApi() {
        return new AuthorizationApi(auth0AccountAuthorizationApi, getAuthorizationToken().getAccess_token());
    }

    private synchronized Auth0Token getManagementToken() throws RestClientException {
        long current = System.currentTimeMillis();

        if (managementToken != null && managementToken.hasExpired(current)) {
            logger.info("re-use current AccountManagement token");
            return managementToken;
        }

        logger.info("need to renew AccountManagement token");
        managementToken = getToken(auth0AccountManagementAudience, current);
        logger.info("NEW renewTokenTimestamp_milliseconds {} current {}", managementToken.getIssuedAt(), current);
        return managementToken;
    }

    private synchronized Auth0Token getAuthorizationToken() throws RestClientException {
        long current = System.currentTimeMillis();

        if (authorizationToken != null && authorizationToken.hasExpired(current)) {
            logger.info("re-use current AccountAuthorization token");
            return authorizationToken;
        }

        logger.info("need to renew AccountAuthorization token");
        authorizationToken = getToken(auth0AccountAuthorizationAudience, current);
        return authorizationToken;
    }

    private Auth0Token getToken(String audience, long current) {
        return Auth0Token.getToken(authToken, auth0ClientId, auth0ClientSecret, audience, current);
    }

    public void signupNewShop(String groupName, User user) throws Auth0Exception {
        User auth0User = getManagementAPI().
                users().
                create(user).
                execute();

        getAuthorizationApi().addMemberToGroup(groupName, auth0User.getId());
    }
}
