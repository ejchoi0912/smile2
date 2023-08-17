package com.snuh.smile.service;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.Sleeps;
import com.snuh.smile.repository.*;
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
public class SleepService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final SleepRepository sleepRepository;

    @NonNull
    private final SleepLevelsMapRepository sleepLevelsMapRepository;

    @NonNull
    private final SleepLevelsMapDeepRepository sleepLevelsMapDeepRepository;

    @NonNull
    private final SleepLevelsMapLightRepository sleepLevelsMapLightRepository;

    @NonNull
    private final SleepLevelsMapAwakeRepository sleepLevelsMapAwakeRepository;


    @Transactional
    public void saveSleep(Sleeps.Sleep sleep){
        logger.info("SleepService saveSleep start");
        sleepRepository.save(sleep);
        logger.info("SleepService saveSleep end");

    }

    public List<Sleeps.Sleep> getSleepList(){
        logger.info("SleepService getSleepList start");
        logger.info("SleepService getSleepList end");
        return sleepRepository.findAll();

    }

    public List<Sleeps.Sleep> getSleepTokenList(String token){
        logger.info("SleepService getSleepList start");
        logger.info("SleepService getSleepList end");
        return sleepRepository.findByUserAccessToken(token);

    }

    public Page<Sleeps.Sleep> getPagelist(Pageable pageable) {
        return sleepRepository.findAll(pageable);
    }

    public Page<Sleeps.Sleep> getSearchPagelist(String token, Pageable pageable) {
        return sleepRepository.findByUserAccessTokenContaining(token, pageable);
    }
}

