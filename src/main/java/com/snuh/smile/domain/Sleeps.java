package com.snuh.smile.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sleeps {

    @SerializedName("sleeps")
    private List<Sleep> sleeps;

    public List<Sleep> getSleeps() {
        return sleeps;
    }

    public void setSleeps(List<Sleep> sleeps) {
        this.sleeps = sleeps;
    }

    @Override
    public String toString() {
        return "Sleeps{" +
                "sleeps=" + sleeps +
                '}';
    }

    @Entity
    @Table(name = "sleep")
    public static class Sleep{

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
        private String durationInSeconds;
        @Column(length = 50)
        private String unmeasurableSleepInSeconds;
        @Column(length = 50)
        private String deepSleepDurationInSeconds;
        @Column(length = 50)
        private String lightSleepDurationInSeconds;
        @Column(length = 50)
        private String remSleepInSeconds;
        @Column(length = 50)
        private String awakeDurationInSeconds;

        @SerializedName("sleepLevelsMap")
        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "sleep_id" , insertable = true , updatable = false)
        private SleepLevelsMap sleepLevelsMap;

        @Column(length = 50)
        private String Validation;

        @ElementCollection
        @CollectionTable(
                name = "sleep_time_offset_sleep_respiration",
                joinColumns = @JoinColumn(name = "id")
        )
        @MapKeyColumn
        @Column(name = "SleepTimeOffsetSleepRespirationValue")
        private Map<String, String> timeOffsetSleepRespiration = new HashMap<>();

        @ElementCollection
        @CollectionTable(
                name = "sleep_time_offset_sleep_spo2",
                joinColumns = @JoinColumn(name = "id")
        )
        @MapKeyColumn
        @Column(name = "SleepTimeOffsetSleepSpo2Value")
        private Map<String, String> timeOffsetSleepSpo2 = new HashMap<>();



        public SleepLevelsMap getSleepLevelsMap() {
            return sleepLevelsMap;
        }

        public void setSleepLevelsMap(SleepLevelsMap sleepLevelsMap) {
            this.sleepLevelsMap = sleepLevelsMap;
            sleepLevelsMap.setSleep(this);
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

        public String getDurationInSeconds() {
            return durationInSeconds;
        }

        public void setDurationInSeconds(String durationInSeconds) {
            this.durationInSeconds = durationInSeconds;
        }

        public String getUnmeasurableSleepInSeconds() {
            return unmeasurableSleepInSeconds;
        }

        public void setUnmeasurableSleepInSeconds(String unmeasurableSleepInSeconds) {
            this.unmeasurableSleepInSeconds = unmeasurableSleepInSeconds;
        }

        public String getDeepSleepDurationInSeconds() {
            return deepSleepDurationInSeconds;
        }

        public void setDeepSleepDurationInSeconds(String deepSleepDurationInSeconds) {
            this.deepSleepDurationInSeconds = deepSleepDurationInSeconds;
        }

        public String getLightSleepDurationInSeconds() {
            return lightSleepDurationInSeconds;
        }

        public void setLightSleepDurationInSeconds(String lightSleepDurationInSeconds) {
            this.lightSleepDurationInSeconds = lightSleepDurationInSeconds;
        }

        public String getRemSleepInSeconds() {
            return remSleepInSeconds;
        }

        public void setRemSleepInSeconds(String remSleepInSeconds) {
            this.remSleepInSeconds = remSleepInSeconds;
        }

        public String getAwakeDurationInSeconds() {
            return awakeDurationInSeconds;
        }

        public void setAwakeDurationInSeconds(String awakeDurationInSeconds) {
            this.awakeDurationInSeconds = awakeDurationInSeconds;
        }


        public String getValidation() {
            return Validation;
        }

        public void setValidation(String validation) {
            Validation = validation;
        }


        public Map<String, String> getTimeOffsetSleepRespiration() {
            return timeOffsetSleepRespiration;
        }

        public void setTimeOffsetSleepRespiration(Map<String, String> timeOffsetSleepRespiration) {
            this.timeOffsetSleepRespiration = timeOffsetSleepRespiration;
        }

        public Map<String, String> getTimeOffsetSleepSpo2() {
            return timeOffsetSleepSpo2;
        }

        public void setTimeOffsetSleepSpo2(Map<String, String> timeOffsetSleepSpo2) {
            this.timeOffsetSleepSpo2 = timeOffsetSleepSpo2;
        }

        @Override
        public String toString() {
            return "Sleep{" +
                    "id=" + id +
                    ", userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    ", summaryId='" + summaryId + '\'' +
                    ", calendarDate='" + calendarDate + '\'' +
                    ", startTimeInSeconds='" + startTimeInSeconds + '\'' +
                    ", startTimeOffsetInSeconds='" + startTimeOffsetInSeconds + '\'' +
                    ", durationInSeconds='" + durationInSeconds + '\'' +
                    ", unmeasurableSleepInSeconds='" + unmeasurableSleepInSeconds + '\'' +
                    ", deepSleepDurationInSeconds='" + deepSleepDurationInSeconds + '\'' +
                    ", lightSleepDurationInSeconds='" + lightSleepDurationInSeconds + '\'' +
                    ", remSleepInSeconds='" + remSleepInSeconds + '\'' +
                    ", awakeDurationInSeconds='" + awakeDurationInSeconds + '\'' +
                    ", sleepLevelsMap=" + sleepLevelsMap +
                    ", Validation='" + Validation + '\'' +
                    ", timeOffsetSleepRespiration=" + timeOffsetSleepRespiration +
                    ", timeOffsetSleepSpo2=" + timeOffsetSleepSpo2 +
                    '}';
        }
    }

    @Entity
    @Table(name = "sleep_levels_map")
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    public static class SleepLevelsMap{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @SerializedName("deep")
        @JsonManagedReference
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "sleepLevelsMap", fetch = FetchType.LAZY)
        private List<Deep> deep;

        @SerializedName("light")
        @JsonManagedReference
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "sleepLevelsMap", fetch = FetchType.LAZY)
        private List<Light> light;

        @SerializedName("awake")
        @JsonManagedReference
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "sleepLevelsMap", fetch = FetchType.LAZY)
        private List<Awake> awake;

        @OneToOne
        private Sleep sleep;


        public List<Deep> getDeep() {
            return deep;
        }

        public void setDeep(List<Deep> deep) {
            this.deep = deep;
            deep.forEach(entity -> entity.setSleepLevelsMap(this));
        }

        public List<Light> getLight() {
            return light;
        }

        public void setLight(List<Light> light) {
            this.light = light;
            light.forEach(entity -> entity.setSleepLevelsMap(this));
        }

        public List<Awake> getAwake() {
            return awake;
        }

        public void setAwake(List<Awake> awake) {
            this.awake = awake;
            awake.forEach(entity -> entity.setSleepLevelsMap(this));
        }

        public Sleep getSleep() {
            return sleep;
        }

        public void setSleep(Sleep sleep) {
            this.sleep = sleep;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    }

    @Entity
    @Table(name = "sleep_levels_map_deep")
    public static class Deep {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String startTimeInSeconds;
        private String endTimeInSeconds;

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonBackReference
        @JoinColumn(name = "sleepLevelsMap_id" , insertable = true , updatable = false)
        private SleepLevelsMap sleepLevelsMap;

        public SleepLevelsMap getSleepLevelsMap() {
            return sleepLevelsMap;
        }

        public void setSleepLevelsMap(SleepLevelsMap sleepLevelsMap) {
            this.sleepLevelsMap = sleepLevelsMap;
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

        public String getEndTimeInSeconds() {
            return endTimeInSeconds;
        }

        public void setEndTimeInSeconds(String endTimeInSeconds) {
            this.endTimeInSeconds = endTimeInSeconds;
        }


    }

    @Entity
    @Table(name = "sleep_levels_map_light")
    public static class Light {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String startTimeInSeconds;
        private String endTimeInSeconds;
        @ManyToOne(fetch = FetchType.LAZY)
        @JsonBackReference
        @JoinColumn(name = "sleepLevelsMap_id" , insertable = true , updatable = false)
        private SleepLevelsMap sleepLevelsMap;


        public SleepLevelsMap getSleepLevelsMap() {
            return sleepLevelsMap;
        }

        public void setSleepLevelsMap(SleepLevelsMap sleepLevelsMap) {
            this.sleepLevelsMap = sleepLevelsMap;
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

        public String getEndTimeInSeconds() {
            return endTimeInSeconds;
        }

        public void setEndTimeInSeconds(String endTimeInSeconds) {
            this.endTimeInSeconds = endTimeInSeconds;
        }

    }

    @Entity
    @Table(name = "sleep_levels_map_awake")
    public static class Awake {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String startTimeInSeconds;
        private String endTimeInSeconds;
        @ManyToOne(fetch = FetchType.LAZY)
        @JsonBackReference
        @JoinColumn(name = "sleepLevelsMap_id" , insertable = true , updatable = false)
        private SleepLevelsMap sleepLevelsMap;


        public SleepLevelsMap getSleepLevelsMap() {
            return sleepLevelsMap;
        }

        public void setSleepLevelsMap(SleepLevelsMap sleepLevelsMap) {
            this.sleepLevelsMap = sleepLevelsMap;
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

        public String getEndTimeInSeconds() {
            return endTimeInSeconds;
        }

        public void setEndTimeInSeconds(String endTimeInSeconds) {
            this.endTimeInSeconds = endTimeInSeconds;
        }

    }

    @Entity
    @Table(name = "sleep_time_offset_sleep_respiration")
    public static class SleepTimeOffsetSleepRespiration{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String SleepTimeOffsetSleepRespirationValue;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSleepTimeOffsetSleepRespirationValue() {
            return SleepTimeOffsetSleepRespirationValue;
        }

        public void setSleepTimeOffsetSleepRespirationValue(String sleepTimeOffsetSleepRespirationValue) {
            SleepTimeOffsetSleepRespirationValue = sleepTimeOffsetSleepRespirationValue;
        }

        @Override
        public String toString() {
            return "SleepTimeOffsetSleepRespiration{" +
                    "id=" + id +
                    ", SleepTimeOffsetSleepRespirationValue='" + SleepTimeOffsetSleepRespirationValue + '\'' +
                    '}';
        }
    }

    @Entity
    @Table(name = "sleep_time_offset_sleep_spo2")
    public static class SleepTimeOffsetSleepSpo2{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String SleepTimeOffsetSleepSpo2Value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSleepTimeOffsetSleepSpo2Value() {
            return SleepTimeOffsetSleepSpo2Value;
        }

        public void setSleepTimeOffsetSleepSpo2Value(String sleepTimeOffsetSleepSpo2Value) {
            SleepTimeOffsetSleepSpo2Value = sleepTimeOffsetSleepSpo2Value;
        }

        @Override
        public String toString() {
            return "SleepTimeOffsetSleepSpo2{" +
                    "id=" + id +
                    ", SleepTimeOffsetSleepSpo2Value='" + SleepTimeOffsetSleepSpo2Value + '\'' +
                    '}';
        }
    }

}
