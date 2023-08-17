package com.snuh.smile.service;


import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.UserMetrics;
import com.snuh.smile.repository.UserMetricsRepository;
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
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserMetricsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @NonNull
    private final UserMetricsRepository userMetricsRepository;

    @Transactional
    public void save(UserMetrics.UserMetric userMetric){
        logger.info("UserMetricsService save start");
        userMetricsRepository.save(userMetric);
        logger.info("UserMetricsService save end");
    }

    public List<UserMetrics.UserMetric> getUserMetricList(){
        logger.info("UserMetricsService getUserMetricList start");
        logger.info("UserMetricsService getUserMetricList end");
        return userMetricsRepository.findAll();
    }

    public List<UserMetrics.UserMetric> getUserMetricTokenList(String token){
        logger.info("UserMetricsService getUserMetricList start");
        logger.info("UserMetricsService getUserMetricList end");
        return userMetricsRepository.findByUserAccessToken(token);
    }

    public Page<UserMetrics.UserMetric> getPagelist(Pageable pageable) {
        return userMetricsRepository.findAll(pageable);
    }

    public Page<UserMetrics.UserMetric> getSearchPagelist(String token, Pageable pageable) {
        return userMetricsRepository.findByUserAccessTokenContaining(token, pageable);
    }
}
