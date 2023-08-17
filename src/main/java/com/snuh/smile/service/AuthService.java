package com.snuh.smile.service;

import com.snuh.smile.domain.Auth;
import com.snuh.smile.repository.AuthRepository;
import com.snuh.smile.vo.AuthVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AuthRepository authRepository;

    @Autowired
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Transactional
    public void save(Auth auth){

        logger.info("AuthService save start");
        authRepository.save(auth);
        logger.info("AuthService save end");

    }


    public Auth findByEmail(String email){
        logger.info("AuthService findByEmail start");

        Auth auth = authRepository.findByEmail(email);
        if(auth == null) return null;
        logger.info("AuthService findByEmail end");

        return auth;
    }

    @Transactional
    public void update(Auth auth){

        logger.info("AuthService update start");

        Auth oldAuth = authRepository.findByEmail(auth.getEmail());

        oldAuth.setName(auth.getName());
        oldAuth.setUserAccessToken(auth.getUserAccessToken());
        oldAuth.setUserAccessTokenSecret(auth.getUserAccessTokenSecret());

        authRepository.save(oldAuth);

        logger.info("AuthService update end");

    }

    public List<AuthVO> getList(){

        logger.info("AuthService getList start");

        List<Auth> authList = authRepository.findAll();

        List<AuthVO> authVOList = new ArrayList<>();

        for (Auth auth: authList
             ) {
            AuthVO authVO = new AuthVO();
            authVO.setId(auth.getId());
            authVO.setName(auth.getName());
            authVO.setEmail(auth.getEmail());
            authVO.setUserAccessToken(auth.getUserAccessToken());
            authVO.setUserAccessTokenSecret(auth.getUserAccessTokenSecret());

            authVOList.add(authVO);
        }

        logger.info("AuthService getList end");


        return authVOList;
    }

    public void deleteAuth(int id){
        logger.info("AuthService deleteAuth start");

        authRepository.deleteById(id);
        logger.info("AuthService deleteAuth end");

    }




}
