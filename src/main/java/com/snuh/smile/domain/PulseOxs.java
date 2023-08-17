package com.snuh.smile.domain;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public class PulseOxs {

    @SerializedName("pulseox")
    private List<PulseOx> pulseox;

    public List<PulseOx> getPulseox() {
        return pulseox;
    }

    public void setPulseox(List<PulseOx> pulseox) {
        this.pulseox = pulseox;
    }

    @Override
    public String toString() {
        return "PulseOx {" +
                "pulseox=" + pulseox +
                '}';
    }

    @Entity
    @Table(name = "pulse_ox")
    public static class PulseOx {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String userId;
        private String userAccessToken;
        private String summaryId;
        private String calendarDate;
        private String startTimeInSeconds;
        private String startTimeOffsetInSeconds;
        private String durationInSeconds;
        @ElementCollection
        @CollectionTable(
                name = "pulse_ox_time_offset_spo2_values",
                joinColumns = @JoinColumn(name = "id")
        )
        @MapKeyColumn
        @Column(name = "timeOffsetSpo2Values")
        private Map<String, String> timeOffsetSpo2Values;
        private String onDemand;


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

        public String getCalendarDate() {
            return calendarDate;
        }

        public void setCalendarDate(String calendarDate) {
            this.calendarDate = calendarDate;
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


        public Map<String, String> getTimeOffsetSpo2Values() {
            return timeOffsetSpo2Values;
        }

        public void setTimeOffsetSpo2Values(Map<String, String> timeOffsetSpo2Values) {
            this.timeOffsetSpo2Values = timeOffsetSpo2Values;
        }

        public String getOnDemand() {
            return onDemand;
        }

        public void setOnDemand(String onDemand) {
            this.onDemand = onDemand;
        }

        @Override
        public String toString() {
            return "PulseOx{" +
                    "id=" + id +
                    ", userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    ", summaryId='" + summaryId + '\'' +
                    ", calendarDate='" + calendarDate + '\'' +
                    ", startTimeInSeconds='" + startTimeInSeconds + '\'' +
                    ", startTimeOffsetInSeconds='" + startTimeOffsetInSeconds + '\'' +
                    ", durationInSeconds='" + durationInSeconds + '\'' +
                    ", timeOffsetSpo2Values=" + timeOffsetSpo2Values +
                    ", onDemand='" + onDemand + '\'' +
                    '}';
        }
    }

    @Entity
    @Table(name="pulse_ox_time_offset_spo2_values")
    public static class TimeOffsetSpo2Values{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String timeOffsetSpo2Values;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTimeOffsetSpo2Values() {
            return timeOffsetSpo2Values;
        }

        public void setTimeOffsetSpo2Values(String timeOffsetSpo2Values) {
            this.timeOffsetSpo2Values = timeOffsetSpo2Values;
        }

        @Override
        public String toString() {
            return "TimeOffsetSpo2Values{" +
                    "id=" + id +
                    ", timeOffsetSpo2Values='" + timeOffsetSpo2Values + '\'' +
                    '}';
        }
    }


}
