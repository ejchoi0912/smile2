package com.snuh.smile.vo;

import lombok.Data;

@Data
public class ActivityDetailSampleVO {

    private int id;
    private String summaryId;
    private String activityId;
    private String startTimeInSeconds;
    private String latitudeInDegree;
    private String longitudeInDegree;
    private String elevationInMeters;
    private String airTemperatureCelcius;
    private String heartrate;
    private String speedMetersPerSecond;
    private String stepsPerMinute;
    private String totalDistanceInMeters;
    private String timerDurationInSeconds;
    private String clockDurationInSeconds;
    private String movingDurationInSeconds;
    private String powerInWatts;
    private String bikeCadenceInRPM;
    private String swimCadenceInStrokesPerMinute;
    private String onDemand;
}
