package com.snuh.smile.vo;

import lombok.Data;

@Data
public class AuthVO {

    private int id;
    private String name;
    private String email;
    private String userAccessToken;
    private String userAccessTokenSecret;
}

