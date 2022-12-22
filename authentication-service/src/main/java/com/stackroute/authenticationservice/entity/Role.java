package com.stackroute.authenticationservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Role {
    @JsonProperty("owner")
    owner,
    @JsonProperty("user")
    user
}
