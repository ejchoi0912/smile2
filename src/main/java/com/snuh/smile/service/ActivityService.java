package com.snuh.smile.service;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.repository.ActivityRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ActivityService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final ActivityRepository activityRepository;

    @Transactional
    public void save(Activity.Activities activity){

        logger.info("ActivityService save start");
        activityRepository.save(activity);
        logger.info("ActivityService save end");


    }

    public List<Activity.Activities> getActivityList(){
        logger.info("ActivityService getActivityList start");

        List<Activity.Activities> activityList = activityRepository.findAll();
        logger.info("ActivityService getActivityList end");

        return activityList;
    }

    public List<Activity.Activities> getActivityTokenList(String token){
        logger.info("ActivityService getActivityEmailList start");

        List<Activity.Activities> activityList = activityRepository.findByUserAccessToken(token);

        logger.info("ActivityService getActivityEmailList end");

        return activityList;
    }

    public int getCount(){
        int total  = (int) activityRepository.count();

        return total;
    }

    public Page<Activity.Activities> getPagelist(Pageable pageable) {
        return activityRepository.findAll(pageable);
    }

    public Page<Activity.Activities> getSearchPagelist(String token, Pageable pageable) {
        return activityRepository.findByUserAccessTokenContaining(token, pageable);
    }
}
