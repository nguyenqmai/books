package com.freebooking.auth0.authorization;

import lombok.Data;

import java.util.List;

/**
 * Created by nguyenqmai on 1/18/2018.
 */
@Data
public class Group {
    private String _id;
    private String name;
    private String description;
    private List<Mapping> mappings;
    private List<String> members;
    private List<String> nested;
    private List<String> roles;

    @Data
    static class Mapping {
        private String _id;
        private String groupName;
        private String connectionName;

    }
}
