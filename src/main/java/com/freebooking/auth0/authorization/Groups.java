package com.freebooking.auth0.authorization;

import lombok.Data;

import java.util.List;

/**
 * Created by nguyenqmai on 1/18/2018.
 */
@Data
public class Groups extends AuthorizationApiResult {
    private List<Group> groups;
}
