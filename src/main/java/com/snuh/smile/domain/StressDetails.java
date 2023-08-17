package com.snuh.smile.domain;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public class StressDetails {

    @SerializedName("stressDetails")
    private List<StressDetail> stressDetails;

    public List<StressDetail> getStressDetails() {
        return stressDetails;
    }

    public void setStressDetails(List<StressDetail> stressDetails) {
        this.stressDetails = stressDetails;
    }

    @Override
    public String toString() {
        return "StressDetails{" +
                "stressDetails=" + stressDetails +
                '}';
    }

    @Entity
    @Table(name = "stress_detail")
    public static class StressDetail {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String userId;
        private String userAccessToken;
        @Column(length = 50)
        private String summaryId;
        @Column(length = 50)
        private String startTimeInSeconds;
        @Column(length = 50)
        private String startTimeOffsetInSeconds;
        @Column(length = 50)
        private String durationInSeconds;
        @Column(length = 50)
        private String calendarDate;
        @ElementCollection
        @CollectionTable(
                name = "stress_time_offset_stress_level_values",
                joinColumns = @JoinColumn(name = "id")
        )
        @MapKeyColumn
        @Column(name = "timeOffsetStressLevelValues")
        private Map<String, Integer> timeOffsetStressLevelValues;
        @ElementCollection
        @CollectionTable(
                name = "stress_time_offset_body_battery_values",
                joinColumns = @JoinColumn(name = "id")
        )
        @MapKeyColumn
        @Column(name = "timeOffsetBodyBatteryValues")
        private Map<String, Integer> timeOffsetBodyBatteryValues;

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

        public String getDurationInSeconds() {
            return durationInSeconds;
        }

        public void setDurationInSeconds(String durationInSeconds) {
            this.durationInSeconds = durationInSeconds;
        }

        public String getCalendarDate() {
            return calendarDate;
        }

        public void setCalendarDate(String calendarDate) {
            this.calendarDate = calendarDate;
        }

        public Map<String, Integer> getTimeOffsetStressLevelValues() {
            return timeOffsetStressLevelValues;
        }

        public void setTimeOffsetStressLevelValues(Map<String, Integer> timeOffsetStressLevelValues) {
            this.timeOffsetStressLevelValues = timeOffsetStressLevelValues;
        }


        public Map<String, Integer> getTimeOffsetBodyBatteryValues() {
            return timeOffsetBodyBatteryValues;
        }

        public void setTimeOffsetBodyBatteryValues(Map<String, Integer> timeOffsetBodyBatteryValues) {
            this.timeOffsetBodyBatteryValues = timeOffsetBodyBatteryValues;
        }

        @Override
        public String toString() {
            return "StressDetail{" +
                    "id=" + id +
                    ", userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    ", summaryId='" + summaryId + '\'' +
                    ", startTimeInSeconds='" + startTimeInSeconds + '\'' +
                    ", startTimeOffsetInSeconds='" + startTimeOffsetInSeconds + '\'' +
                    ", durationInSeconds='" + durationInSeconds + '\'' +
                    ", calendarDate='" + calendarDate + '\'' +
                    ", timeOffsetStressLevelValues=" + timeOffsetStressLevelValues +
                    ", timeOffsetBodyBatteryValues=" + timeOffsetBodyBatteryValues +
                    '}';
        }
    }

    @Entity
    @Table(name = "stress_time_offset_stress_level_values")
    public static class timeOffsetStressLevelValues{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private int timeOffsetStressLevelValues;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTimeOffsetStressLevelValues() {
            return timeOffsetStressLevelValues;
        }

        public void setTimeOffsetStressLevelValues(int timeOffsetStressLevelValues) {
            this.timeOffsetStressLevelValues = timeOffsetStressLevelValues;
        }

        @Override
        public String toString() {
            return "timeOffsetStressLevelValues{" +
                    "id=" + id +
                    ", timeOffsetStressLevelValues=" + timeOffsetStressLevelValues +
                    '}';
        }
    }

    @Entity
    @Table(name = "stress_time_offset_body_battery_values")
    public static class timeOffsetBodyBatteryDetails{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private int timeOffsetBodyBatteryValues;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTimeOffsetBodyBatteryValues() {
            return timeOffsetBodyBatteryValues;
        }

        public void setTimeOffsetBodyBatteryValues(int timeOffsetBodyBatteryValues) {
            this.timeOffsetBodyBatteryValues = timeOffsetBodyBatteryValues;
        }

        @Override
        public String toString() {
            return "timeOffsetBodyBatteryDetails{" +
                    "id=" + id +
                    ", timeOffsetBodyBatteryValues=" + timeOffsetBodyBatteryValues +
                    '}';
        }
    }
}
