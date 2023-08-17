package com.snuh.smile.service;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.Dailies;
import com.snuh.smile.repository.DailyHeartRateSample;
import com.snuh.smile.repository.DailyRepository;
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
public class DailyService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final DailyRepository dailyRepository;
    @NonNull
    private final DailyHeartRateSample dailyHeartRateSample;

    @Transactional
    public void saveDaily(Dailies.Daily daily){
        logger.info("DailyService saveDaily start");
        dailyRepository.save(daily);
        logger.info("DailyService saveDaily end");
    }

    public List<Dailies.Daily> getDailyList(){
        logger.info("DailyService getDailyList start");

        logger.info("DailyService getDailyList end");
        return dailyRepository.findAll();
    }

    public List<Dailies.Daily> getDailyTokenList(String token){
        logger.info("DailyService getDailyList start");

        logger.info("DailyService getDailyList end");
        return dailyRepository.findByUserAccessToken(token);
    }

    public Page<Dailies.Daily> getPagelist(Pageable pageable) {
        return dailyRepository.findAll(pageable);
    }

    public Page<Dailies.Daily> getSearchPagelist(String token, Pageable pageable) {
        return dailyRepository.findByUserAccessTokenContaining(token, pageable);
    }

}
