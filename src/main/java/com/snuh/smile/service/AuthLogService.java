package com.snuh.smile.service;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.AuthLog;
import com.snuh.smile.repository.AuthLogRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final AuthLogRepository authLogRepository;

    @Transactional
    public void save(AuthLog authLog){

        logger.info("AuthLogService save start");
        authLogRepository.save(authLog);
        logger.info("AuthLogService save end");


    }

    public void delete(int id){

        logger.info("AuthLogService save start");
        authLogRepository.deleteById(id);
        logger.info("AuthLogService save end");


    }

}
