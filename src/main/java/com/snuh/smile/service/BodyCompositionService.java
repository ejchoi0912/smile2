package com.snuh.smile.service;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.BodyCompositions;
import com.snuh.smile.repository.BodyCompositionRepository;
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
public class BodyCompositionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final BodyCompositionRepository bodyCompositionRepository;

    @Transactional
    public void save(BodyCompositions.BodyComposition bodyComposition){

        logger.info("BodyCompositionService save start");

        bodyCompositionRepository.save(bodyComposition);
        logger.info("BodyCompositionService save end");

    }

    public List<BodyCompositions.BodyComposition> getBodyCompositionList(){
        logger.info("BodyCompositionService getBodyCompositionList start");

        logger.info("BodyCompositionService getBodyCompositionList end");

        return bodyCompositionRepository.findAll();

    }

    public List<BodyCompositions.BodyComposition> getBodyCompositionTokenList(String token){
        logger.info("BodyCompositionService getBodyCompositionTokenList start");

        logger.info("BodyCompositionService getBodyCompositionTokenList end");

        return bodyCompositionRepository.findByUserAccessToken(token);

    }

    public Page<BodyCompositions.BodyComposition> getPagelist(Pageable pageable) {
        return bodyCompositionRepository.findAll(pageable);
    }

    public Page<BodyCompositions.BodyComposition> getSearchPagelist(String token, Pageable pageable) {
        return bodyCompositionRepository.findByUserAccessTokenContaining(token, pageable);
    }
}
