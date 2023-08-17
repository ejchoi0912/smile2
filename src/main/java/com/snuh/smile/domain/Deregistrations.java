package com.snuh.smile.domain;

import com.google.gson.annotations.SerializedName;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class Deregistrations {

    @SerializedName("deregistrations")
    private List<Deregistration> deregistrations;

    public List<Deregistration> getDeregistrations() {
        return deregistrations;
    }

    public void setDeregistrations(List<Deregistration> deregistrations) {
        this.deregistrations = deregistrations;
    }

    @Override
    public String toString() {
        return "Deregistrations{" +
                "deregistrations=" + deregistrations +
                '}';
    }

    public static class Deregistration {
        private String userId;
        private String userAccessToken;

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

        @Override
        public String toString() {
            return "Deregistration{" +
                    "userId='" + userId + '\'' +
                    ", userAccessToken='" + userAccessToken + '\'' +
                    '}';
        }
    }

}
