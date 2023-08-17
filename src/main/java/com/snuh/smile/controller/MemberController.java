package com.snuh.smile.controller;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.Member;
import com.snuh.smile.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor(onConstructor = @ __(@Autowired))
@Slf4j
public class MemberController {

    private static final String SESSION_COOKIE_NAME = "smilesession";

    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final MemberService memberService;


    @RequestMapping(value = "/", method = {RequestMethod.GET})
    @ResponseBody
    public String sessionCheck(HttpServletRequest request){
        logger.info("MemberController sessionCheck start");

        String checkMsg = null;

        HttpSession session = request.getSession();
        Member member = (Member)session.getAttribute("member");

        if (member == null) {
            logger.info("MemberController sessionCheck end");
            return checkMsg = "fail";
        } else {
            logger.info("MemberController sessionCheck end");
            return  checkMsg = "success";
        }
    }



    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
        public String loginCheck(@RequestBody Member member, HttpServletRequest request,
                HttpServletResponse response){


        logger.info("MemberController loginCheck start");

        logger.info("Login Request Member : {}", member.toString());

        String checkMsg = memberService.loginCheck(member);

        if (checkMsg.equals("success")){

            logger.info("SESSION create SUCCESS : " + member.getUserId());

            HttpSession session = request.getSession();
            session.setAttribute("member", member);
        }

        logger.info("MemberController loginCheck end");

        return checkMsg;
    }


    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    @ResponseBody
    public String logout(HttpServletRequest request){


        logger.info("MemberController logout start");

        // expire(request);

        HttpSession session = request.getSession();
        session.invalidate();

        logger.info("MemberController logout end");

        return "success";
    }

//
//    /**
//     * 세션 생성
//     */
//    public void createSession(Object value, HttpServletResponse response){
//
//        logger.info("MemberController createSession start");
//
//        // 세션 id를 생성하고, 값을 세션에 저장
//        String sessionId = UUID.randomUUID().toString();
//        sessionStore.put(sessionId, value);
//
//        // 쿠키 생성
//        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
//        response.addCookie(mySessionCookie);
//
//        logger.info("MemberController createSession end");
//    }
//    /**
//     * 세션 조회
//     */
//    public Object getSession(HttpServletRequest request){
//
//        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
//        if (sessionCookie == null){
//            return null;
//        }
//
//        return sessionStore.get(sessionCookie.getValue());
//    }
//
//    /**
//     * 세션 만료
//     */
//    public void expire(HttpServletRequest request){
//        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
//        if (sessionCookie != null){
//            sessionStore.remove(sessionCookie.getValue());
//        }
//    }
//
//    private Cookie findCookie(HttpServletRequest request, String cookieName) {
//        return Arrays.stream(request.getCookies())
//                .filter(cookie -> cookie.getName().equals(cookieName))
//                .findAny()
//                .orElse(null);
//    }

}
