package com.snuh.smile.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;

public class ActivityDetails {

    @SerializedName("activityDetails")
    private List<ActivityDetail> activityDetails;

    public List<ActivityDetail> getActivityDetails() {
        return activityDetails;
    }

    public void setActivityDetails(List<ActivityDetail> activityDetails) {
        this.activityDetails = activityDetails;
    }

    @Override
    public String toString() {
        return "ActivityDetail{" +
                "activityDetails=" + activityDetails +
                '}';
    }

    @Entity
    @Table(name = "activity_detail")
    public static class ActivityDetail {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String userId;
        private String userAccessToken;
        private String summaryId;
        private String activityId;
        @SerializedName("summary")
        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "activityDetail_id")
        private Summarys summary;
        @SerializedName("samples")
        @JsonManagedReference
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "activityDetails", fetch = FetchType.LAZY)
        private List<Samples> samples;
        @SerializedName("laps")
        @JsonManagedReference
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "activityDetails", fetch = FetchType.LAZY)
        private List<Laps> laps;

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

        public Summarys getSummary() {
            return summary;
        }

        public void setSummary(Summarys summary) {
            this.summary = summary;
            summary.setActivityDetails(this);
        }

        public List<Samples> getSamples() {
            return samples;
        }

        public void setSamples(List<Samples> samples) {
            this.samples = samples;
            samples.forEach(entity -> entity.setActivityDetails(this));
        }

        public List<Laps> getLaps() {
            return laps;
        }

        public void setLaps(List<Laps> laps) {
            this.laps = laps;
            laps.forEach(entity -> entity.setActivityDetails(this));
        }

        @Override
        public String toString() {
            return "ActivityDetail{" +
                    "id=" + id +
                    ", userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    ", summaryId='" + summaryId + '\'' +
                    ", activityId='" + activityId + '\'' +
                    ", summary=" + summary +
                    ", samples=" + samples +
                    ", laps=" + laps +
                    '}';
        }
    }


    @Entity
    @Table(name="activity_detail_summary")
    public static class Summarys {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
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
        @OneToOne
        private ActivityDetail activityDetails;

        public ActivityDetail getActivityDetails() {
            return activityDetails;
        }

        public void setActivityDetails(ActivityDetail activityDetails) {
            this.activityDetails = activityDetails;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
            return "Summary{" +
                    "id=" + id +
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

    @Entity
    @Table(name="activity_detail_sample")
    public static class Samples{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
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

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonBackReference
        @JoinColumn(name = "activityDetails_id" , insertable = true , updatable = false)
        private ActivityDetail activityDetails;

        public ActivityDetail getActivityDetails() {
            return activityDetails;
        }

        public void setActivityDetails(ActivityDetail activityDetails) {
            this.activityDetails = activityDetails;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStartTimeInSeconds() {
            return startTimeInSeconds;
        }

        public void setStartTimeInSeconds(String startTimeInSeconds) {
            this.startTimeInSeconds = startTimeInSeconds;
        }

        public String getLatitudeInDegree() {
            return latitudeInDegree;
        }

        public void setLatitudeInDegree(String latitudeInDegree) {
            this.latitudeInDegree = latitudeInDegree;
        }

        public String getLongitudeInDegree() {
            return longitudeInDegree;
        }

        public void setLongitudeInDegree(String longitudeInDegree) {
            this.longitudeInDegree = longitudeInDegree;
        }

        public String getElevationInMeters() {
            return elevationInMeters;
        }

        public void setElevationInMeters(String elevationInMeters) {
            this.elevationInMeters = elevationInMeters;
        }

        public String getAirTemperatureCelcius() {
            return airTemperatureCelcius;
        }

        public void setAirTemperatureCelcius(String airTemperatureCelcius) {
            this.airTemperatureCelcius = airTemperatureCelcius;
        }

        public String getHeartrate() {
            return heartrate;
        }

        public void setHeartrate(String heartrate) {
            this.heartrate = heartrate;
        }

        public String getSpeedMetersPerSecond() {
            return speedMetersPerSecond;
        }

        public void setSpeedMetersPerSecond(String speedMetersPerSecond) {
            this.speedMetersPerSecond = speedMetersPerSecond;
        }

        public String getStepsPerMinute() {
            return stepsPerMinute;
        }

        public void setStepsPerMinute(String stepsPerMinute) {
            this.stepsPerMinute = stepsPerMinute;
        }

        public String getTotalDistanceInMeters() {
            return totalDistanceInMeters;
        }

        public void setTotalDistanceInMeters(String totalDistanceInMeters) {
            this.totalDistanceInMeters = totalDistanceInMeters;
        }

        public String getTimerDurationInSeconds() {
            return timerDurationInSeconds;
        }

        public void setTimerDurationInSeconds(String timerDurationInSeconds) {
            this.timerDurationInSeconds = timerDurationInSeconds;
        }

        public String getClockDurationInSeconds() {
            return clockDurationInSeconds;
        }

        public void setClockDurationInSeconds(String clockDurationInSeconds) {
            this.clockDurationInSeconds = clockDurationInSeconds;
        }

        public String getMovingDurationInSeconds() {
            return movingDurationInSeconds;
        }

        public void setMovingDurationInSeconds(String movingDurationInSeconds) {
            this.movingDurationInSeconds = movingDurationInSeconds;
        }

        public String getPowerInWatts() {
            return powerInWatts;
        }

        public void setPowerInWatts(String powerInWatts) {
            this.powerInWatts = powerInWatts;
        }

        public String getBikeCadenceInRPM() {
            return bikeCadenceInRPM;
        }

        public void setBikeCadenceInRPM(String bikeCadenceInRPM) {
            this.bikeCadenceInRPM = bikeCadenceInRPM;
        }

        public String getSwimCadenceInStrokesPerMinute() {
            return swimCadenceInStrokesPerMinute;
        }

        public void setSwimCadenceInStrokesPerMinute(String swimCadenceInStrokesPerMinute) {
            this.swimCadenceInStrokesPerMinute = swimCadenceInStrokesPerMinute;
        }

        public String getOnDemand() {
            return onDemand;
        }

        public void setOnDemand(String onDemand) {
            this.onDemand = onDemand;
        }

//        @Override
//        public String toString() {
//            return "Samples{" +
//                    "id=" + id +
//                    ", startTimeInSeconds='" + startTimeInSeconds + '\'' +
//                    ", latitudeInDegree='" + latitudeInDegree + '\'' +
//                    ", longitudeInDegree='" + longitudeInDegree + '\'' +
//                    ", elevationInMeters='" + elevationInMeters + '\'' +
//                    ", airTemperatureCelcius='" + airTemperatureCelcius + '\'' +
//                    ", heartrate='" + heartrate + '\'' +
//                    ", speedMetersPerSecond='" + speedMetersPerSecond + '\'' +
//                    ", stepsPerMinute='" + stepsPerMinute + '\'' +
//                    ", totalDistanceInMeters='" + totalDistanceInMeters + '\'' +
//                    ", timerDurationInSeconds='" + timerDurationInSeconds + '\'' +
//                    ", clockDurationInSeconds='" + clockDurationInSeconds + '\'' +
//                    ", movingDurationInSeconds='" + movingDurationInSeconds + '\'' +
//                    ", powerInWatts='" + powerInWatts + '\'' +
//                    ", bikeCadenceInRPM='" + bikeCadenceInRPM + '\'' +
//                    ", swimCadenceInStrokesPerMinute='" + swimCadenceInStrokesPerMinute + '\'' +
//                    ", onDemand='" + onDemand + '\'' +
//                    ", activityDetails=" + activityDetails +
//                    '}';
//        }
    }

    @Entity
    @Table(name="activity_detail_lap")
    public static class Laps{


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String startTimeInSeconds;

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonBackReference
        @JoinColumn(name = "activityDetails_id" , insertable = true, updatable = false)
        private ActivityDetail activityDetails;

        public ActivityDetail getActivityDetails() {
            return activityDetails;
        }

        public void setActivityDetails(ActivityDetail activityDetails) {
            this.activityDetails = activityDetails;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStartTimeInSeconds() {
            return startTimeInSeconds;
        }

        public void setStartTimeInSeconds(String startTimeInSeconds) {
            this.startTimeInSeconds = startTimeInSeconds;
        }

//        @Override
//        public String toString() {
//            return "Laps{" +
//                    "id=" + id +
//                    ", startTimeInSeconds='" + startTimeInSeconds + '\'' +
//                    ", activityDetails=" + activityDetails +
//                    '}';
//        }
    }
}
