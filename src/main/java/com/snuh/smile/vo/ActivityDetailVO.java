package com.snuh.smile.vo;

import com.google.gson.annotations.SerializedName;
import com.snuh.smile.domain.ActivityDetails;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class ActivityDetailVO {

    private int id;
    private String userId;
    private String userAccessToken;
    private String summaryId;
    private String activityId;
    private ActivityDetailSummayVO summary;
    private List<ActivityDetailSampleVO> samples;
    private List<ActivityDetailLapVO> laps;

}
