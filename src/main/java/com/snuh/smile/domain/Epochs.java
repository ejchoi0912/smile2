package com.snuh.smile.domain;


import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;

public class Epochs {

    @SerializedName("epochs")
    private List<Epoch> epochs;

    public List<Epoch> getEpochs() {
        return epochs;
    }

    public void setEpochs(List<Epoch> epochs) {
        this.epochs = epochs;
    }

    @Override
    public String toString() {
        return "Epochs{" +
                "epochs=" + epochs +
                '}';
    }

    @Entity
    @Table(name = "epoch")
    public static class Epoch{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String userId;
        private String userAccessToken;
        private String summaryId;
        private String startTimeInSeconds;
        private String startTimeOffsetInSeconds;
        private String activityType;
        private String durationInSeconds;
        private String activeTimeInSeconds;
        private String Steps;
        private String distanceInMeters;
        private String activeKilocalories;
        private String Met;
        private String intensity;
        private String meanMotionIntensity;
        private String maxMotionIntensity;

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

        public String getActiveTimeInSeconds() {
            return activeTimeInSeconds;
        }

        public void setActiveTimeInSeconds(String activeTimeInSeconds) {
            this.activeTimeInSeconds = activeTimeInSeconds;
        }

        public String getSteps() {
            return Steps;
        }

        public void setSteps(String steps) {
            Steps = steps;
        }

        public String getDistanceInMeters() {
            return distanceInMeters;
        }

        public void setDistanceInMeters(String distanceInMeters) {
            this.distanceInMeters = distanceInMeters;
        }

        public String getActiveKilocalories() {
            return activeKilocalories;
        }

        public void setActiveKilocalories(String activeKilocalories) {
            this.activeKilocalories = activeKilocalories;
        }

        public String getMet() {
            return Met;
        }

        public void setMet(String met) {
            Met = met;
        }

        public String getIntensity() {
            return intensity;
        }

        public void setIntensity(String intensity) {
            this.intensity = intensity;
        }

        public String getMeanMotionIntensity() {
            return meanMotionIntensity;
        }

        public void setMeanMotionIntensity(String meanMotionIntensity) {
            this.meanMotionIntensity = meanMotionIntensity;
        }

        public String getMaxMotionIntensity() {
            return maxMotionIntensity;
        }

        public void setMaxMotionIntensity(String maxMotionIntensity) {
            this.maxMotionIntensity = maxMotionIntensity;
        }

        @Override
        public String toString() {
            return "Epoch{" +
                    "id=" + id +
                    ", userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    ", summaryId='" + summaryId + '\'' +
                    ", startTimeInSeconds='" + startTimeInSeconds + '\'' +
                    ", startTimeOffsetInSeconds='" + startTimeOffsetInSeconds + '\'' +
                    ", activityType='" + activityType + '\'' +
                    ", durationInSeconds='" + durationInSeconds + '\'' +
                    ", activeTimeInSeconds='" + activeTimeInSeconds + '\'' +
                    ", Steps='" + Steps + '\'' +
                    ", distanceInMeters='" + distanceInMeters + '\'' +
                    ", activeKilocalories='" + activeKilocalories + '\'' +
                    ", Met='" + Met + '\'' +
                    ", intensity='" + intensity + '\'' +
                    ", meanMotionIntensity='" + meanMotionIntensity + '\'' +
                    ", maxMotionIntensity='" + maxMotionIntensity + '\'' +
                    '}';
        }
    }
}
