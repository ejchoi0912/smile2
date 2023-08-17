package com.snuh.smile.service;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.ActivityDetails;
import com.snuh.smile.repository.ActivityDetailRepository;
import com.snuh.smile.repository.LapRepository;
import com.snuh.smile.repository.SampleRepository;
import com.snuh.smile.repository.SummaryRepository;
import com.snuh.smile.vo.ActivityDetailVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ActivityDetailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final ActivityDetailRepository activityDetailRepository;
    @NonNull
    private final SampleRepository sampleRepository;
    @NonNull
    private final SummaryRepository summaryRepository;
    @NonNull
    private final LapRepository lapRepository;

    @Transactional
    public ActivityDetails.ActivityDetail saveDetail(ActivityDetails.ActivityDetail activityDetails) {
        logger.info("ActivityDetailService saveDetail start");

        ActivityDetails.ActivityDetail getActivityDetails = activityDetailRepository.saveAndFlush(activityDetails);

        logger.info("ActivityDetailService saveDetail end");

        return getActivityDetails;
    }

    @Transactional
    public void saveLap(ActivityDetails.Laps laps) {
        lapRepository.save(laps);
    }

    public List<ActivityDetails.ActivityDetail> getActivityDetailList() {
        logger.info("ActivityDetailService getActivityDetailList start");

        List<ActivityDetails.ActivityDetail> activityDetailList = activityDetailRepository.findAll();

        logger.info("ActivityDetailService getActivityDetailList end");

        return activityDetailList;
    }

    public List<ActivityDetails.ActivityDetail> getActivityDetailTokenList(String token) {
        logger.info("ActivityDetailService getActivityDetailTokenList start");

        List<ActivityDetails.ActivityDetail> activityDetailList = activityDetailRepository.findByUserAccessToken(token);

        logger.info("ActivityDetailService getActivityDetailTokenList end");

        return activityDetailList;
    }

    public Page<ActivityDetails.ActivityDetail> getPagelist(Pageable pageable) {
        return activityDetailRepository.findAll(pageable);
    }

    public Page<ActivityDetails.ActivityDetail> getSearchPagelist(String token, Pageable pageable) {
        return activityDetailRepository.findByUserAccessTokenContaining(token, pageable);
    }




}
