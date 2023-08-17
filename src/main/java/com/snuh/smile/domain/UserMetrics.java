package com.snuh.smile.domain;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.List;

public class UserMetrics {

    @SerializedName("userMetrics")
    private List<UserMetric> userMetrics;

    public List<UserMetric> getUserMetrics() {
        return userMetrics;
    }

    public void setUserMetrics(List<UserMetric> userMetrics) {
        this.userMetrics = userMetrics;
    }

    @Override
    public String toString() {
        return "UserMetrics{" +
                "userMetrics=" + userMetrics +
                '}';
    }
    @Entity
    @Table(name = "user_metrics")
    public static class UserMetric {
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
        private String vo2Max;
        @Column(length = 50)
        private String enhanced;
        @Column(length = 50)
        private String fitnessAge;

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

        public String getVo2Max() {
            return vo2Max;
        }

        public void setVo2Max(String vo2Max) {
            this.vo2Max = vo2Max;
        }

        public String getEnhanced() {
            return enhanced;
        }

        public void setEnhanced(String enhanced) {
            this.enhanced = enhanced;
        }

        public String getFitnessAge() {
            return fitnessAge;
        }

        public void setFitnessAge(String fitnessAge) {
            this.fitnessAge = fitnessAge;
        }

        @Override
        public String toString() {
            return "UserMetric{" +
                    "id=" + id +
                    ", userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    ", summaryId='" + summaryId + '\'' +
                    ", calendarDate='" + calendarDate + '\'' +
                    ", vo2Max='" + vo2Max + '\'' +
                    ", enhanced='" + enhanced + '\'' +
                    ", fitnessAge='" + fitnessAge + '\'' +
                    '}';
        }
    }
}
