package com.snuh.smile.service;

import com.snuh.smile.domain.Member;
import com.snuh.smile.repository.MemberRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MemberService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final MemberRepository memberRepository;

    public String loginCheck(Member member){
        logger.info("MemberService loginCheck start");

        Member findMember = memberRepository.findByUserId(member.getUserId());

        if(findMember == null) {
            logger.info("MemberService loginCheck end");
            return "null";
        }else {
            if(member.getPassword().equals(findMember.getPassword())){
                logger.info("MemberService loginCheck end");
                return "success";
            } else {
                logger.info("MemberService loginCheck end");
                return "fail";
            }
        }
    }
}
