package com.snuh.smile.domain;


import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class Dailies {

    @SerializedName("dailies")
    private List<Daily> dailies;

    public List<Daily> getDailies() {
        return dailies;
    }

    public void setDailies(List<Daily> dailies) {
        this.dailies = dailies;
    }

    @Override
    public String toString() {
        return "Dailies{" +
                "dailies=" + dailies +
                '}';
    }

    @Entity
    @Table(name = "daily")
    public static class Daily {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String userId;
        private String userAccessToken;
        @Column(length = 50)
        private String summaryId;
        @Column(length = 50)
        private String calendarDate;
        @Column(length = 50)
        private String startTimeInSeconds;
        @Column(length = 50)
        private String startTimeOffsetInSeconds;
        @Column(length = 50)
        private String activityType;
        @Column(length = 50)
        private String durationInSeconds;
        @Column(length = 50)
        private String steps;
        @Column(length = 50)
        private String distanceInMeters;
        @Column(length = 50)
        private String activeTimeInSeconds;
        @Column(length = 50)
        private String activeKilocalories;
        @Column(length = 50)
        private String bmrKilocalories;
        @Column(length = 50)
        private String consumedCalories;
        @Column(length = 50)
        private String moderateIntensityDurationInSeconds;
        @Column(length = 50)
        private String vigorousIntensityDurationInSeconds;
        @Column(length = 50)
        private String floorsClimbed;
        @Column(length = 50)
        private String minHeartRateInBeatsPerMinute;
        @Column(length = 50)
        private String averageHeartRateInBeatsPerMinute;
        @Column(length = 50)
        private String maxHeartRateInBeatsPerMinute;
        @Column(length = 50)
        private String restingHeartRateInBeatsPerMinute;

        @ElementCollection
        @CollectionTable(
                name = "daily_time_offset_heart_rate_samples",
                joinColumns = @JoinColumn(name = "id")
        )
        @MapKeyColumn
        @Column(name = "timeOffsetHeartRateSamplesValue")
        private Map<String, Integer> timeOffsetHeartRateSamples;
        @Column(length = 50)
        private String averageStressLevel;
        @Column(length = 50)
        private String maxStressLevel;
        @Column(length = 50)
        private String stressDurationInSeconds;
        @Column(length = 50)
        private String restStressDurationInSeconds;
        @Column(length = 50)
        private String activityStressDurationInSeconds;
        @Column(length = 50)
        private String lowStressDurationInSeconds;
        @Column(length = 50)
        private String mediumStressDurationInSeconds;
        @Column(length = 50)
        private String highStressDurationInSeconds;
        @Column(length = 50)
        private String stressQualifier;
        @Column(length = 50)
        private String stepsGoal;
        @Column(length = 50)
        private String netKilocaloriesGoal;
        @Column(length = 50)
        private String intensityDurationGoalInSeconds;
        @Column(length = 50)
        private String floorsClimbedGoal;



        public Map<String, Integer> getTimeOffsetHeartRateSamples() {
            return timeOffsetHeartRateSamples;
        }

        public void setTimeOffsetHeartRateSamples(Map<String, Integer> timeOffsetHeartRateSamples) {
            this.timeOffsetHeartRateSamples = timeOffsetHeartRateSamples;
        }

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

        public String getSteps() {
            return steps;
        }

        public void setSteps(String steps) {
            this.steps = steps;
        }

        public String getDistanceInMeters() {
            return distanceInMeters;
        }

        public void setDistanceInMeters(String distanceInMeters) {
            this.distanceInMeters = distanceInMeters;
        }

        public String getActiveTimeInSeconds() {
            return activeTimeInSeconds;
        }

        public void setActiveTimeInSeconds(String activeTimeInSeconds) {
            this.activeTimeInSeconds = activeTimeInSeconds;
        }

        public String getActiveKilocalories() {
            return activeKilocalories;
        }

        public void setActiveKilocalories(String activeKilocalories) {
            this.activeKilocalories = activeKilocalories;
        }

        public String getBmrKilocalories() {
            return bmrKilocalories;
        }

        public void setBmrKilocalories(String bmrKilocalories) {
            this.bmrKilocalories = bmrKilocalories;
        }

        public String getConsumedCalories() {
            return consumedCalories;
        }

        public void setConsumedCalories(String consumedCalories) {
            this.consumedCalories = consumedCalories;
        }

        public String getModerateIntensityDurationInSeconds() {
            return moderateIntensityDurationInSeconds;
        }

        public void setModerateIntensityDurationInSeconds(String moderateIntensityDurationInSeconds) {
            this.moderateIntensityDurationInSeconds = moderateIntensityDurationInSeconds;
        }

        public String getVigorousIntensityDurationInSeconds() {
            return vigorousIntensityDurationInSeconds;
        }

        public void setVigorousIntensityDurationInSeconds(String vigorousIntensityDurationInSeconds) {
            this.vigorousIntensityDurationInSeconds = vigorousIntensityDurationInSeconds;
        }

        public String getFloorsClimbed() {
            return floorsClimbed;
        }

        public void setFloorsClimbed(String floorsClimbed) {
            this.floorsClimbed = floorsClimbed;
        }

        public String getMinHeartRateInBeatsPerMinute() {
            return minHeartRateInBeatsPerMinute;
        }

        public void setMinHeartRateInBeatsPerMinute(String minHeartRateInBeatsPerMinute) {
            this.minHeartRateInBeatsPerMinute = minHeartRateInBeatsPerMinute;
        }

        public String getAverageHeartRateInBeatsPerMinute() {
            return averageHeartRateInBeatsPerMinute;
        }

        public void setAverageHeartRateInBeatsPerMinute(String averageHeartRateInBeatsPerMinute) {
            this.averageHeartRateInBeatsPerMinute = averageHeartRateInBeatsPerMinute;
        }

        public String getMaxHeartRateInBeatsPerMinute() {
            return maxHeartRateInBeatsPerMinute;
        }

        public void setMaxHeartRateInBeatsPerMinute(String maxHeartRateInBeatsPerMinute) {
            this.maxHeartRateInBeatsPerMinute = maxHeartRateInBeatsPerMinute;
        }

        public String getRestingHeartRateInBeatsPerMinute() {
            return restingHeartRateInBeatsPerMinute;
        }

        public void setRestingHeartRateInBeatsPerMinute(String restingHeartRateInBeatsPerMinute) {
            this.restingHeartRateInBeatsPerMinute = restingHeartRateInBeatsPerMinute;
        }


        public String getAverageStressLevel() {
            return averageStressLevel;
        }

        public void setAverageStressLevel(String averageStressLevel) {
            this.averageStressLevel = averageStressLevel;
        }

        public String getMaxStressLevel() {
            return maxStressLevel;
        }

        public void setMaxStressLevel(String maxStressLevel) {
            this.maxStressLevel = maxStressLevel;
        }

        public String getStressDurationInSeconds() {
            return stressDurationInSeconds;
        }

        public void setStressDurationInSeconds(String stressDurationInSeconds) {
            this.stressDurationInSeconds = stressDurationInSeconds;
        }

        public String getRestStressDurationInSeconds() {
            return restStressDurationInSeconds;
        }

        public void setRestStressDurationInSeconds(String restStressDurationInSeconds) {
            this.restStressDurationInSeconds = restStressDurationInSeconds;
        }

        public String getActivityStressDurationInSeconds() {
            return activityStressDurationInSeconds;
        }

        public void setActivityStressDurationInSeconds(String activityStressDurationInSeconds) {
            this.activityStressDurationInSeconds = activityStressDurationInSeconds;
        }

        public String getLowStressDurationInSeconds() {
            return lowStressDurationInSeconds;
        }

        public void setLowStressDurationInSeconds(String lowStressDurationInSeconds) {
            this.lowStressDurationInSeconds = lowStressDurationInSeconds;
        }

        public String getMediumStressDurationInSeconds() {
            return mediumStressDurationInSeconds;
        }

        public void setMediumStressDurationInSeconds(String mediumStressDurationInSeconds) {
            this.mediumStressDurationInSeconds = mediumStressDurationInSeconds;
        }

        public String getHighStressDurationInSeconds() {
            return highStressDurationInSeconds;
        }

        public void setHighStressDurationInSeconds(String highStressDurationInSeconds) {
            this.highStressDurationInSeconds = highStressDurationInSeconds;
        }

        public String getStressQualifier() {
            return stressQualifier;
        }

        public void setStressQualifier(String stressQualifier) {
            this.stressQualifier = stressQualifier;
        }

        public String getStepsGoal() {
            return stepsGoal;
        }

        public void setStepsGoal(String stepsGoal) {
            this.stepsGoal = stepsGoal;
        }

        public String getNetKilocaloriesGoal() {
            return netKilocaloriesGoal;
        }

        public void setNetKilocaloriesGoal(String netKilocaloriesGoal) {
            this.netKilocaloriesGoal = netKilocaloriesGoal;
        }

        public String getIntensityDurationGoalInSeconds() {
            return intensityDurationGoalInSeconds;
        }

        public void setIntensityDurationGoalInSeconds(String intensityDurationGoalInSeconds) {
            this.intensityDurationGoalInSeconds = intensityDurationGoalInSeconds;
        }

        public String getFloorsClimbedGoal() {
            return floorsClimbedGoal;
        }

        public void setFloorsClimbedGoal(String floorsClimbedGoal) {
            this.floorsClimbedGoal = floorsClimbedGoal;
        }

        @Override
        public String toString() {
            return "Daily{" +
                    "id='" + id + '\'' +
                    ", userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    ", summaryId='" + summaryId + '\'' +
                    ", calendarDate='" + calendarDate + '\'' +
                    ", startTimeInSeconds='" + startTimeInSeconds + '\'' +
                    ", startTimeOffsetInSeconds='" + startTimeOffsetInSeconds + '\'' +
                    ", activityType='" + activityType + '\'' +
                    ", durationInSeconds='" + durationInSeconds + '\'' +
                    ", steps='" + steps + '\'' +
                    ", distanceInMeters='" + distanceInMeters + '\'' +
                    ", activeTimeInSeconds='" + activeTimeInSeconds + '\'' +
                    ", activeKilocalories='" + activeKilocalories + '\'' +
                    ", bmrKilocalories='" + bmrKilocalories + '\'' +
                    ", consumedCalories='" + consumedCalories + '\'' +
                    ", moderateIntensityDurationInSeconds='" + moderateIntensityDurationInSeconds + '\'' +
                    ", vigorousIntensityDurationInSeconds='" + vigorousIntensityDurationInSeconds + '\'' +
                    ", floorsClimbed='" + floorsClimbed + '\'' +
                    ", minHeartRateInBeatsPerMinute='" + minHeartRateInBeatsPerMinute + '\'' +
                    ", averageHeartRateInBeatsPerMinute='" + averageHeartRateInBeatsPerMinute + '\'' +
                    ", maxHeartRateInBeatsPerMinute='" + maxHeartRateInBeatsPerMinute + '\'' +
                    ", restingHeartRateInBeatsPerMinute='" + restingHeartRateInBeatsPerMinute + '\'' +
                    ", timeOffsetHeartRateSamples='" + timeOffsetHeartRateSamples + '\'' +
                    ", averageStressLevel='" + averageStressLevel + '\'' +
                    ", maxStressLevel='" + maxStressLevel + '\'' +
                    ", stressDurationInSeconds='" + stressDurationInSeconds + '\'' +
                    ", restStressDurationInSeconds='" + restStressDurationInSeconds + '\'' +
                    ", activityStressDurationInSeconds='" + activityStressDurationInSeconds + '\'' +
                    ", lowStressDurationInSeconds='" + lowStressDurationInSeconds + '\'' +
                    ", mediumStressDurationInSeconds='" + mediumStressDurationInSeconds + '\'' +
                    ", highStressDurationInSeconds='" + highStressDurationInSeconds + '\'' +
                    ", stressQualifier='" + stressQualifier + '\'' +
                    ", stepsGoal='" + stepsGoal + '\'' +
                    ", netKilocaloriesGoal='" + netKilocaloriesGoal + '\'' +
                    ", intensityDurationGoalInSeconds='" + intensityDurationGoalInSeconds + '\'' +
                    ", floorsClimbedGoal='" + floorsClimbedGoal + '\'' +
                    '}';
        }
    }

    @Entity
    @Table(name="daily_time_offset_heart_rate_samples")
    public static class HeartRateSample {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private int timeOffsetHeartRateSamplesValue;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTimeOffsetHeartRateSamplesValue() {
            return timeOffsetHeartRateSamplesValue;
        }

        public void setTimeOffsetHeartRateSamplesValue(int timeOffsetHeartRateSamplesValue) {
            this.timeOffsetHeartRateSamplesValue = timeOffsetHeartRateSamplesValue;
        }

        @Override
        public String toString() {
            return "HeartRateSample{" +
                    "id=" + id +
                    ", timeOffsetHeartRateSamplesValue=" + timeOffsetHeartRateSamplesValue +
                    '}';
        }
    }

}
