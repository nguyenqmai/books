package com.freebooking.auth0.authorization;


import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nguyenqmai on 1/18/2018.
 */
public class AuthorizationApi {
    private String baseUrl;
    private String accessToken;
    private Map<String, Group> authorizationGroups;
    private RestTemplate restTemplate;

    public AuthorizationApi(String baseUrl, String accessToken) {
        this.baseUrl = baseUrl;
        this.accessToken = accessToken;
        authorizationGroups = new HashMap<>();
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        for (Group group : fetchGroups().getGroups()) {
            authorizationGroups.put(group.getName(), group);
        }
    }

    Groups fetchGroups() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", String.format("Bearer %s", accessToken));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Groups> tmp = restTemplate.exchange(baseUrl + "/groups", HttpMethod.GET, entity, Groups.class);
        return tmp.getBody();
    }

    public boolean addMemberToGroup(String groupName, String memberId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", String.format("Bearer %s", accessToken));

        HttpEntity<String> entity = new HttpEntity<>(String.format("[\"%s\"]", memberId),headers);

        ResponseEntity<String> ret = restTemplate.exchange(String.format(baseUrl + "/groups/%s/members", authorizationGroups.get(groupName).get_id()),
                HttpMethod.PATCH,
                entity,
                String.class);
        return ret.getStatusCode() == HttpStatus.OK;
    }

    public boolean deleteMemberFromGroup(String groupName, String memberId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", String.format("Bearer %s", accessToken));

        HttpEntity<String> entity = new HttpEntity<>(String.format("[\"%s\"]", memberId),headers);

        ResponseEntity<String> ret = restTemplate.exchange(String.format(baseUrl + "/groups/%s/members", authorizationGroups.get(groupName).get_id()),
                HttpMethod.DELETE,
                entity,
                String.class);
        return ret.getStatusCode() == HttpStatus.OK;
    }

}
