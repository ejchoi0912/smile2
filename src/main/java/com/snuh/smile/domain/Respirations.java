package com.snuh.smile.domain;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public class Respirations {

    @SerializedName("allDayRespiration")
    private List<Respiration> allDayRespiration;

    public List<Respiration> getAllDayRespiration() {
        return allDayRespiration;
    }

    public void setAllDayRespiration(List<Respiration> allDayRespiration) {
        this.allDayRespiration = allDayRespiration;
    }

    @Override
    public String toString() {
        return "Respirations{" +
                "allDayRespiration=" + allDayRespiration +
                '}';
    }

    @Entity
    @Table(name = "respiration")
    public static class Respiration {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String userId;
        private String userAccessToken;
        private String summaryId;
        private String startTimeInSeconds;
        private String durationInSeconds;
        private String startTimeOffsetInSeconds;
        @ElementCollection
        @CollectionTable(
                name = "respiration_time_offset_epoch_to_breaths",
                joinColumns = @JoinColumn(name = "id")
        )
        @MapKeyColumn
        @Column(name = "timeOffsetEpochToBreathsValue")
        private Map<String, String> timeOffsetEpochToBreaths;

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

        public String getDurationInSeconds() {
            return durationInSeconds;
        }

        public void setDurationInSeconds(String durationInSeconds) {
            this.durationInSeconds = durationInSeconds;
        }

        public String getStartTimeOffsetInSeconds() {
            return startTimeOffsetInSeconds;
        }

        public void setStartTimeOffsetInSeconds(String startTimeOffsetInSeconds) {
            this.startTimeOffsetInSeconds = startTimeOffsetInSeconds;
        }

        public Map<String, String> getTimeOffsetEpochToBreaths() {
            return timeOffsetEpochToBreaths;
        }

        public void setTimeOffsetEpochToBreaths(Map<String, String> timeOffsetEpochToBreaths) {
            this.timeOffsetEpochToBreaths = timeOffsetEpochToBreaths;
        }

        @Override
        public String toString() {
            return "Respiration{" +
                    "id=" + id +
                    ", userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    ", summaryId='" + summaryId + '\'' +
                    ", startTimeInSeconds='" + startTimeInSeconds + '\'' +
                    ", durationInSeconds='" + durationInSeconds + '\'' +
                    ", startTimeOffsetInSeconds='" + startTimeOffsetInSeconds + '\'' +
                    ", timeOffsetEpochToBreaths=" + timeOffsetEpochToBreaths +
                    '}';
        }
    }

    @Entity
    @Table(name = "respiration_time_offset_epoch_to_breaths")
    public static class timeOffsetEpochToBreaths{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private float timeOffsetEpochToBreathsValue;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public float getTimeOffsetEpochToBreathsValue() {
            return timeOffsetEpochToBreathsValue;
        }

        public void setTimeOffsetEpochToBreathsValue(float timeOffsetEpochToBreathsValue) {
            this.timeOffsetEpochToBreathsValue = timeOffsetEpochToBreathsValue;
        }

        @Override
        public String toString() {
            return "timeOffsetEpochToBreaths{" +
                    "id=" + id +
                    ", timeOffsetEpochToBreathsValue=" + timeOffsetEpochToBreathsValue +
                    '}';
        }
    }


}
