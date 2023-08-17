package com.snuh.smile.service;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.Respirations;
import com.snuh.smile.repository.RespirationRepository;
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
public class RespirationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final RespirationRepository respirationRepository;

    @Transactional
    public void save(Respirations.Respiration respiration){
        logger.info("RespirationService save start");
        respirationRepository.save(respiration);
        logger.info("RespirationService save end");
    }

    public List<Respirations.Respiration> getRespirationList(){
        logger.info("RespirationService getRespirationList start");
        logger.info("RespirationService getRespirationList end");
        return respirationRepository.findAll();    }

    public List<Respirations.Respiration> getRespirationTokenList(String token){
        logger.info("RespirationService getRespirationList start");
        logger.info("RespirationService getRespirationList end");
        return respirationRepository.findByUserAccessToken(token);    }

    public Page<Respirations.Respiration> getPagelist(Pageable pageable) {
        return respirationRepository.findAll(pageable);
    }

    public Page<Respirations.Respiration> getSearchPagelist(String token, Pageable pageable) {
        return respirationRepository.findByUserAccessTokenContaining(token, pageable);
    }
}
