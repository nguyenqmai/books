package com.freebooking.auth0.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nguyenqmai on 2/21/2018.
 */
public class Authorization {
    @JsonProperty("groups")
    List<String> groups = new LinkedList<>();
    @JsonProperty("roles")
    List<String> roles =  new LinkedList<>();
    @JsonProperty("permissions")
    List<String> permissions = new LinkedList<>();

    Authorization(List<String> groups, List<String> roles, List<String> permissions) {
        this.groups.addAll(groups);
        this.roles.addAll(roles);
        this.permissions.addAll(permissions);
    }
}