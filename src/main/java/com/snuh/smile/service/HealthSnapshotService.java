package com.snuh.smile.service;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.HealthSnapshots;
import com.snuh.smile.repository.HealthSnaphotSummaryRepository;
import com.snuh.smile.repository.HealthSnapshotRepository;
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
public class HealthSnapshotService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final HealthSnapshotRepository healthSnapshotRepository;

    @NonNull
    private final HealthSnaphotSummaryRepository healthSnaphotSummaryRepository;

    @Transactional
    public void saveHealthSnapshot(HealthSnapshots.HealthSnapshot healthSnapshot){
        logger.info("HealthSnapshotService saveHealthSnapshot start");
        healthSnapshotRepository.save(healthSnapshot);
        logger.info("HealthSnapshotService saveHealthSnapshot end");

    }

    public List<HealthSnapshots.HealthSnapshot> getHealthSnapshot(){
        logger.info("HealthSnapshotService getHealthSnapshot start");
        logger.info("HealthSnapshotService getHealthSnapshot end");
        return healthSnapshotRepository.findAll();

    }
    public List<HealthSnapshots.HealthSnapshot> getHealthSnapTokenshot(String token){
        logger.info("HealthSnapshotService getHealthSnapshot start");
        logger.info("HealthSnapshotService getHealthSnapshot end");
        return healthSnapshotRepository.findByUserAccessToken(token);

    }

    public Page<HealthSnapshots.HealthSnapshot> getPagelist(Pageable pageable) {
        return healthSnapshotRepository.findAll(pageable);
    }

    public Page<HealthSnapshots.HealthSnapshot> getSearchPagelist(String token, Pageable pageable) {
        return healthSnapshotRepository.findByUserAccessTokenContaining(token, pageable);
    }

}
