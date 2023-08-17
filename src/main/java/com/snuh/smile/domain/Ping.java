package com.snuh.smile.domain;

import lombok.Data;

@Data
public class Ping {

    private String summaryType;
    private String userId;
    private String userAccessToken;
    private String uploadStartTimeInSeconds;
    private String uploadEndTimeInSeconds;
    private String callbackURL;

}
