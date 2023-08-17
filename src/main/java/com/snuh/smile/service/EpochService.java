package com.snuh.smile.service;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.Epochs;
import com.snuh.smile.repository.EpochRepository;
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
public class EpochService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final EpochRepository epochRepository;

    @Transactional
    public void save(Epochs.Epoch epoch){
        logger.info("EpochService save start");
        epochRepository.save(epoch);
        logger.info("EpochService save end");
    }

    public List<Epochs.Epoch> getEpochList(){
        logger.info("EpochService getEpochList start");
        logger.info("EpochService getEpochList end");
        return epochRepository.findAll();
    }
    public List<Epochs.Epoch> getEpochTokenList(String token){
        logger.info("EpochService getEpochList start");
        logger.info("EpochService getEpochList end");
        return epochRepository.findByUserAccessToken(token);
    }

    public Page<Epochs.Epoch> getPagelist(Pageable pageable) {
        return epochRepository.findAll(pageable);
    }

    public Page<Epochs.Epoch> getSearchPagelist(String token, Pageable pageable) {
        return epochRepository.findByUserAccessTokenContaining(token, pageable);
    }

}
