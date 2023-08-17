package com.snuh.smile.service;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.StressDetails;
import com.snuh.smile.repository.StressDetailRepository;
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
public class StressDetailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final StressDetailRepository stressDetailRepository;

    @Transactional
    public void save(StressDetails.StressDetail stressDetail){
        logger.info("StressDetailService save start");
        stressDetailRepository.save(stressDetail);
        logger.info("StressDetailService save end");
    }

    public List<StressDetails.StressDetail> getStressDetailList(){
        logger.info("StressDetailService getStressDetailList start");
        logger.info("StressDetailService getStressDetailList end");
        return stressDetailRepository.findAll();
    }

    public List<StressDetails.StressDetail> getStressDetailTokenList(String token){
        logger.info("StressDetailService getStressDetailList start");
        logger.info("StressDetailService getStressDetailList end");
        return stressDetailRepository.findByUserAccessToken(token);
    }

    public Page<StressDetails.StressDetail> getPagelist(Pageable pageable) {
        return stressDetailRepository.findAll(pageable);
    }

    public Page<StressDetails.StressDetail> getSearchPagelist(String token, Pageable pageable) {
        return stressDetailRepository.findByUserAccessTokenContaining(token, pageable);
    }
}
