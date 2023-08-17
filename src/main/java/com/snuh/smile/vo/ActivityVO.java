package com.snuh.smile.vo;

import lombok.Data;

@Data
public class ActivityVO {

    private String id;
    private String userId;
    private String userAccessToken;
    private String summaryId;
    private String activityId;
    private String startTimeInSeconds;
    private String startTimeOffsetInSeconds;
    private String activityType;
    private String durationInSeconds;
    private String averageBikeCadenceInRoundsPerMinute;
    private String averageHeartRateInBeatsPerMinute;
    private String averageRunCadenceInStepsPerMinute;
    private String averageSpeedInMetersPerSecond;
    private String averageSwimCadenceInStrokesPerMinute;
    private String averagePaceInMinutesPerKilometer;
    private String activeKilocalories;
    private String deviceName;
    private String distanceInMeters;
    private String maxBikeCadenceInRoundsPerMinute;
    private String maxHeartRateInBeatsPerMinute;
    private String maxPaceInMinutesPerKilometer;
    private String maxRunCadenceInStepsPerMinute;
    private String maxSpeedInMetersPerSecond;
    private String numberOfActiveLengths;
    private String startingLatitudeInDegree;
    private String startingLongitudeInDegree;
    private String steps;
    private String totalElevationGainInMeters;
    private String totalElevationLossInMeters;
    private String isParent;
    private String parentSummaryId;
    private String Manual;
}
