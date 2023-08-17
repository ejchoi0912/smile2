package com.snuh.smile.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


public class Activity {

    @SerializedName("activities")
    private List<Activities> activities;

    public List<Activities> getActivities() {
        return activities;
    }

    public void setActivities(List<Activities> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activities=" + activities +
                '}';
    }


    @Entity
    @Table(name = "activity")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Activities {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserAccessToken() {
            return userAccessToken;
        }

        public void setUserAccessToken(String userAccessToken) {
            this.userAccessToken = userAccessToken;
        }

        public String getSummaryId() {
            return summaryId;
        }

        public void setSummaryId(String summaryId) {
            this.summaryId = summaryId;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getStartTimeInSeconds() {
            return startTimeInSeconds;
        }

        public void setStartTimeInSeconds(String startTimeInSeconds) {
            this.startTimeInSeconds = startTimeInSeconds;
        }

        public String getStartTimeOffsetInSeconds() {
            return startTimeOffsetInSeconds;
        }

        public void setStartTimeOffsetInSeconds(String startTimeOffsetInSeconds) {
            this.startTimeOffsetInSeconds = startTimeOffsetInSeconds;
        }

        public String getActivityType() {
            return activityType;
        }

        public void setActivityType(String activityType) {
            this.activityType = activityType;
        }

        public String getDurationInSeconds() {
            return durationInSeconds;
        }

        public void setDurationInSeconds(String durationInSeconds) {
            this.durationInSeconds = durationInSeconds;
        }

        public String getAverageBikeCadenceInRoundsPerMinute() {
            return averageBikeCadenceInRoundsPerMinute;
        }

        public void setAverageBikeCadenceInRoundsPerMinute(String averageBikeCadenceInRoundsPerMinute) {
            this.averageBikeCadenceInRoundsPerMinute = averageBikeCadenceInRoundsPerMinute;
        }

        public String getAverageHeartRateInBeatsPerMinute() {
            return averageHeartRateInBeatsPerMinute;
        }

        public void setAverageHeartRateInBeatsPerMinute(String averageHeartRateInBeatsPerMinute) {
            this.averageHeartRateInBeatsPerMinute = averageHeartRateInBeatsPerMinute;
        }

        public String getAverageRunCadenceInStepsPerMinute() {
            return averageRunCadenceInStepsPerMinute;
        }

        public void setAverageRunCadenceInStepsPerMinute(String averageRunCadenceInStepsPerMinute) {
            this.averageRunCadenceInStepsPerMinute = averageRunCadenceInStepsPerMinute;
        }

        public String getAverageSpeedInMetersPerSecond() {
            return averageSpeedInMetersPerSecond;
        }

        public void setAverageSpeedInMetersPerSecond(String averageSpeedInMetersPerSecond) {
            this.averageSpeedInMetersPerSecond = averageSpeedInMetersPerSecond;
        }

        public String getAverageSwimCadenceInStrokesPerMinute() {
            return averageSwimCadenceInStrokesPerMinute;
        }

        public void setAverageSwimCadenceInStrokesPerMinute(String averageSwimCadenceInStrokesPerMinute) {
            this.averageSwimCadenceInStrokesPerMinute = averageSwimCadenceInStrokesPerMinute;
        }

        public String getAveragePaceInMinutesPerKilometer() {
            return averagePaceInMinutesPerKilometer;
        }

        public void setAveragePaceInMinutesPerKilometer(String averagePaceInMinutesPerKilometer) {
            this.averagePaceInMinutesPerKilometer = averagePaceInMinutesPerKilometer;
        }

        public String getActiveKilocalories() {
            return activeKilocalories;
        }

        public void setActiveKilocalories(String activeKilocalories) {
            this.activeKilocalories = activeKilocalories;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDistanceInMeters() {
            return distanceInMeters;
        }

        public void setDistanceInMeters(String distanceInMeters) {
            this.distanceInMeters = distanceInMeters;
        }

        public String getMaxBikeCadenceInRoundsPerMinute() {
            return maxBikeCadenceInRoundsPerMinute;
        }

        public void setMaxBikeCadenceInRoundsPerMinute(String maxBikeCadenceInRoundsPerMinute) {
            this.maxBikeCadenceInRoundsPerMinute = maxBikeCadenceInRoundsPerMinute;
        }

        public String getMaxHeartRateInBeatsPerMinute() {
            return maxHeartRateInBeatsPerMinute;
        }

        public void setMaxHeartRateInBeatsPerMinute(String maxHeartRateInBeatsPerMinute) {
            this.maxHeartRateInBeatsPerMinute = maxHeartRateInBeatsPerMinute;
        }

        public String getMaxPaceInMinutesPerKilometer() {
            return maxPaceInMinutesPerKilometer;
        }

        public void setMaxPaceInMinutesPerKilometer(String maxPaceInMinutesPerKilometer) {
            this.maxPaceInMinutesPerKilometer = maxPaceInMinutesPerKilometer;
        }

        public String getMaxRunCadenceInStepsPerMinute() {
            return maxRunCadenceInStepsPerMinute;
        }

        public void setMaxRunCadenceInStepsPerMinute(String maxRunCadenceInStepsPerMinute) {
            this.maxRunCadenceInStepsPerMinute = maxRunCadenceInStepsPerMinute;
        }

        public String getMaxSpeedInMetersPerSecond() {
            return maxSpeedInMetersPerSecond;
        }

        public void setMaxSpeedInMetersPerSecond(String maxSpeedInMetersPerSecond) {
            this.maxSpeedInMetersPerSecond = maxSpeedInMetersPerSecond;
        }

        public String getNumberOfActiveLengths() {
            return numberOfActiveLengths;
        }

        public void setNumberOfActiveLengths(String numberOfActiveLengths) {
            this.numberOfActiveLengths = numberOfActiveLengths;
        }

        public String getStartingLatitudeInDegree() {
            return startingLatitudeInDegree;
        }

        public void setStartingLatitudeInDegree(String startingLatitudeInDegree) {
            this.startingLatitudeInDegree = startingLatitudeInDegree;
        }

        public String getStartingLongitudeInDegree() {
            return startingLongitudeInDegree;
        }

        public void setStartingLongitudeInDegree(String startingLongitudeInDegree) {
            this.startingLongitudeInDegree = startingLongitudeInDegree;
        }

        public String getSteps() {
            return steps;
        }

        public void setSteps(String steps) {
            this.steps = steps;
        }

        public String getTotalElevationGainInMeters() {
            return totalElevationGainInMeters;
        }

        public void setTotalElevationGainInMeters(String totalElevationGainInMeters) {
            this.totalElevationGainInMeters = totalElevationGainInMeters;
        }

        public String getTotalElevationLossInMeters() {
            return totalElevationLossInMeters;
        }

        public void setTotalElevationLossInMeters(String totalElevationLossInMeters) {
            this.totalElevationLossInMeters = totalElevationLossInMeters;
        }

        public String getIsParent() {
            return isParent;
        }

        public void setIsParent(String isParent) {
            this.isParent = isParent;
        }

        public String getParentSummaryId() {
            return parentSummaryId;
        }

        public void setParentSummaryId(String parentSummaryId) {
            this.parentSummaryId = parentSummaryId;
        }

        public String getManual() {
            return Manual;
        }

        public void setManual(String manual) {
            Manual = manual;
        }

        @Override
        public String toString() {
            return "Activities{" +
                    "userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    ", summaryId='" + summaryId + '\'' +
                    ", activityId='" + activityId + '\'' +
                    ", startTimeInSeconds='" + startTimeInSeconds + '\'' +
                    ", startTimeOffsetInSeconds='" + startTimeOffsetInSeconds + '\'' +
                    ", activityType='" + activityType + '\'' +
                    ", durationInSeconds='" + durationInSeconds + '\'' +
                    ", averageBikeCadenceInRoundsPerMinute='" + averageBikeCadenceInRoundsPerMinute + '\'' +
                    ", averageHeartRateInBeatsPerMinute='" + averageHeartRateInBeatsPerMinute + '\'' +
                    ", averageRunCadenceInStepsPerMinute='" + averageRunCadenceInStepsPerMinute + '\'' +
                    ", averageSpeedInMetersPerSecond='" + averageSpeedInMetersPerSecond + '\'' +
                    ", averageSwimCadenceInStrokesPerMinute='" + averageSwimCadenceInStrokesPerMinute + '\'' +
                    ", averagePaceInMinutesPerKilometer='" + averagePaceInMinutesPerKilometer + '\'' +
                    ", activeKilocalories='" + activeKilocalories + '\'' +
                    ", deviceName='" + deviceName + '\'' +
                    ", distanceInMeters='" + distanceInMeters + '\'' +
                    ", maxBikeCadenceInRoundsPerMinute='" + maxBikeCadenceInRoundsPerMinute + '\'' +
                    ", maxHeartRateInBeatsPerMinute='" + maxHeartRateInBeatsPerMinute + '\'' +
                    ", maxPaceInMinutesPerKilometer='" + maxPaceInMinutesPerKilometer + '\'' +
                    ", maxRunCadenceInStepsPerMinute='" + maxRunCadenceInStepsPerMinute + '\'' +
                    ", maxSpeedInMetersPerSecond='" + maxSpeedInMetersPerSecond + '\'' +
                    ", numberOfActiveLengths='" + numberOfActiveLengths + '\'' +
                    ", startingLatitudeInDegree='" + startingLatitudeInDegree + '\'' +
                    ", startingLongitudeInDegree='" + startingLongitudeInDegree + '\'' +
                    ", steps='" + steps + '\'' +
                    ", totalElevationGainInMeters='" + totalElevationGainInMeters + '\'' +
                    ", totalElevationLossInMeters='" + totalElevationLossInMeters + '\'' +
                    ", isParent='" + isParent + '\'' +
                    ", parentSummaryId='" + parentSummaryId + '\'' +
                    ", Manual='" + Manual + '\'' +
                    '}';
        }
    }




}
