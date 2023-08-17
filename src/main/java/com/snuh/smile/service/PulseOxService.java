package com.snuh.smile.service;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.PulseOxs;
import com.snuh.smile.repository.PulseOxRepository;
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
public class PulseOxService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final PulseOxRepository pulseOxRepository;

    @Transactional
    public void save(PulseOxs.PulseOx pulseOx){
        logger.info("PulseOxService save start");
        pulseOxRepository.save(pulseOx);
        logger.info("PulseOxService save end");

    }

    public List<PulseOxs.PulseOx> getPulseOxList(){
        logger.info("PulseOxService getPulseOxList start");
        logger.info("PulseOxService getPulseOxList end");
        return pulseOxRepository.findAll();
    }

    public List<PulseOxs.PulseOx> getPulseOxTokenList(String token){
        logger.info("PulseOxService getPulseOxList start");
        logger.info("PulseOxService getPulseOxList end");
        return pulseOxRepository.findByUserAccessToken(token);
    }

    public Page<PulseOxs.PulseOx> getPagelist(Pageable pageable) {
        return pulseOxRepository.findAll(pageable);
    }

    public Page<PulseOxs.PulseOx> getSearchPagelist(String token, Pageable pageable) {
        return pulseOxRepository.findByUserAccessTokenContaining(token, pageable);
    }
}
