package com.snuh.smile.domain;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;

public class BodyCompositions {

    @SerializedName("bodyComps")
    private List<BodyComposition> bodyComps;

    public List<BodyComposition> getBodyComps() {
        return bodyComps;
    }

    public void setBodyComps(List<BodyComposition> bodyComps) {
        this.bodyComps = bodyComps;
    }

    @Override
    public String toString() {
        return "BodyCompositions{" +
                "bodyComps=" + bodyComps +
                '}';
    }

    @Entity
    @Table(name = "body_composition")
    public static class BodyComposition {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String userId;
        private String userAccessToken;
        @Column(length = 50)
        private String summaryId;
        @Column(length = 50)
        private String measurementTimeInSeconds;
        @Column(length = 50)
        private String measurementTimeOffsetInSeconds;
        @Column(length = 50)
        private String muscleMassInGrams;
        @Column(length = 50)
        private String boneMassInGrams;
        @Column(length = 50)
        private String bodyWaterInPercent;
        @Column(length = 50)
        private String bodyFatInPercent;
        @Column(length = 50)
        private String bodyMassIndex;
        @Column(length = 50)
        private String weightInGrams;

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

        public String getMeasurementTimeInSeconds() {
            return measurementTimeInSeconds;
        }

        public void setMeasurementTimeInSeconds(String measurementTimeInSeconds) {
            this.measurementTimeInSeconds = measurementTimeInSeconds;
        }

        public String getMeasurementTimeOffsetInSeconds() {
            return measurementTimeOffsetInSeconds;
        }

        public void setMeasurementTimeOffsetInSeconds(String measurementTimeOffsetInSeconds) {
            this.measurementTimeOffsetInSeconds = measurementTimeOffsetInSeconds;
        }

        public String getMuscleMassInGrams() {
            return muscleMassInGrams;
        }

        public void setMuscleMassInGrams(String muscleMassInGrams) {
            this.muscleMassInGrams = muscleMassInGrams;
        }

        public String getBoneMassInGrams() {
            return boneMassInGrams;
        }

        public void setBoneMassInGrams(String boneMassInGrams) {
            this.boneMassInGrams = boneMassInGrams;
        }

        public String getBodyWaterInPercent() {
            return bodyWaterInPercent;
        }

        public void setBodyWaterInPercent(String bodyWaterInPercent) {
            this.bodyWaterInPercent = bodyWaterInPercent;
        }

        public String getBodyFatInPercent() {
            return bodyFatInPercent;
        }

        public void setBodyFatInPercent(String bodyFatInPercent) {
            this.bodyFatInPercent = bodyFatInPercent;
        }

        public String getBodyMassIndex() {
            return bodyMassIndex;
        }

        public void setBodyMassIndex(String bodyMassIndex) {
            this.bodyMassIndex = bodyMassIndex;
        }

        public String getWeightInGrams() {
            return weightInGrams;
        }

        public void setWeightInGrams(String weightInGrams) {
            this.weightInGrams = weightInGrams;
        }

        @Override
        public String toString() {
            return "BodyComposition{" +
                    "id=" + id +
                    ", userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    ", summaryId='" + summaryId + '\'' +
                    ", measurementTimeInSeconds='" + measurementTimeInSeconds + '\'' +
                    ", measurementTimeOffsetInSeconds='" + measurementTimeOffsetInSeconds + '\'' +
                    ", muscleMassInGrams='" + muscleMassInGrams + '\'' +
                    ", boneMassInGrams='" + boneMassInGrams + '\'' +
                    ", bodyWaterInPercent='" + bodyWaterInPercent + '\'' +
                    ", bodyFatInPercent='" + bodyFatInPercent + '\'' +
                    ", bodyMassIndex='" + bodyMassIndex + '\'' +
                    ", weightInGrams='" + weightInGrams + '\'' +
                    '}';
        }
    }

}
