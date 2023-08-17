package com.snuh.smile.domain;

import lombok.Data;

@Data
public class Backfill {

    private String summaryStartTimeInSeconds;
    private String summaryEndTimeInSeconds;
    private String email;

}
