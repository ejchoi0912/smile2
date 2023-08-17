package com.snuh.smile.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public class HealthSnapshots {

    @SerializedName("healthSnapshot")
    private List<HealthSnapshot> healthSnapshot;

    public List<HealthSnapshot> getHealthSnapshot() {
        return healthSnapshot;
    }

    public void setHealthSnapshot(List<HealthSnapshot> healthSnapshot) {
        this.healthSnapshot = healthSnapshot;
    }

    @Override
    public String toString() {
        return "HealthSnapshots{" +
                "healthSnapshot=" + healthSnapshot +
                '}';
    }

    @Entity
    @Table(name="health_snapshot")
    public static class HealthSnapshot{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String userId;
        private String userAccessToken;
        private String summaryId;
        private String calendarDate;
        private String startTimeInSeconds;
        private String durationInSeconds;
        private String startTimeOffsetInSeconds;

        @SerializedName("summaries")
        @JsonManagedReference
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "healthSnapshot", fetch = FetchType.LAZY)
        private List<SnapshotSummary> summaries;

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

        public List<SnapshotSummary> getSummaries() {
            return summaries;
        }

        public void setSummaries(List<SnapshotSummary> summaries) {
            this.summaries = summaries;
            summaries.forEach(entity -> entity.setHealthSnapshot(this));
        }

        @Override
        public String toString() {
            return "HealthSnapshot{" +
                    "id=" + id +
                    ", userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    ", summaryId='" + summaryId + '\'' +
                    ", calendarDate='" + calendarDate + '\'' +
                    ", startTimeInSeconds='" + startTimeInSeconds + '\'' +
                    ", durationInSeconds='" + durationInSeconds + '\'' +
                    ", startTimeOffsetInSeconds='" + startTimeOffsetInSeconds + '\'' +
                    ", summaries=" + summaries +
                    '}';
        }
    }

    @Entity
    @Table(name="health_snapshot_summary")
    public static class SnapshotSummary{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String summaryType;
        private String minValue;
        private String maxValue;
        private String avgValue;

        @ElementCollection
        @CollectionTable(
                name = "health_snapshot_summary_epoch",
                joinColumns = @JoinColumn(name = "id")
        )
        @MapKeyColumn
        @Column(name = "epochSummariesValue")
        private Map<String, String> epochSummaries;

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonBackReference
        @JoinColumn(name = "healthSnapshot_id" , insertable = true , updatable = false)
        private HealthSnapshot healthSnapshot;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSummaryType() {
            return summaryType;
        }

        public void setSummaryType(String summaryType) {
            this.summaryType = summaryType;
        }

        public String getMinValue() {
            return minValue;
        }

        public void setMinValue(String minValue) {
            this.minValue = minValue;
        }

        public String getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(String maxValue) {
            this.maxValue = maxValue;
        }

        public String getAvgValue() {
            return avgValue;
        }

        public void setAvgValue(String avgValue) {
            this.avgValue = avgValue;
        }

        public Map<String, String> getEpochSummaries() {
            return epochSummaries;
        }

        public void setEpochSummaries(Map<String, String> epochSummaries) {
            this.epochSummaries = epochSummaries;
        }

        public HealthSnapshot getHealthSnapshot() {
            return healthSnapshot;
        }

        public void setHealthSnapshot(HealthSnapshot healthSnapshot) {
            this.healthSnapshot = healthSnapshot;
        }

    }

    @Entity
    @Table(name="health_snapshot_summary_epoch")
    public static class EpochSummaries{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String epochSummariesValue;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEpochSummariesValue() {
            return epochSummariesValue;
        }

        public void setEpochSummariesValue(String epochSummariesValue) {
            this.epochSummariesValue = epochSummariesValue;
        }

        @Override
        public String toString() {
            return "EpochSummaries{" +
                    "id=" + id +
                    ", epochSummariesValue='" + epochSummariesValue + '\'' +
                    '}';
        }
    }
}
