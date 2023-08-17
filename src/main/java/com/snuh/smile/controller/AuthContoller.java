package com.snuh.smile.controller;

import com.snuh.smile.domain.*;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.BackfillRequestHeader;
import com.snuh.smile.domain.header.DeregistrationRequestHeader;
import com.snuh.smile.service.AuthLogService;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.OauthService;
import com.snuh.smile.util.AuthLogEnum;
import com.snuh.smile.vo.AuthVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/auth")
@Slf4j
public class AuthContoller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final AuthService authService;
    @NonNull
    private final AuthLogService authLogService;

    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${deregistration.url}")
    private String deregistrationURL;


    @GetMapping("/req")
    public ModelAndView goReq() {

        logger.info("AuthContoller goReq start");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("auth");
        List<AuthVO> authVOList = authService.getList();
        mv.addObject("authVOList", authVOList );

        logger.info("AuthContoller goReq end");
        return mv;
    }


    @GetMapping("/regi")
    public ModelAndView goRegi(@RequestParam String token, @RequestParam String secret) {

        logger.info("AuthContoller goRegi start");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("regi");
        mv.addObject("token", token );
        mv.addObject("secret", secret );
        logger.info("AuthContoller goRegi end");

        return mv;
    }


    @PostMapping("/regi")
    @ResponseBody
    public String doRegi(@RequestBody Auth auth) {
        logger.info("AuthContoller doRegi start");


        Auth authCheck = authService.findByEmail(auth.getEmail());

        if(authCheck != null){
            logger.info("AuthContoller doRegi end");
            return "used";
        }
        try {

            authService.save(auth);

            AuthLog authLog = new AuthLog();
            authLog.setEmail(auth.getEmail());
            authLog.setName(auth.getName());
            authLog.setUserAccessToken(auth.getUserAccessToken());
            authLog.setLogType(AuthLogEnum.create.getValue());

            authLogService.save(authLog);

            logger.info("AuthContoller doRegi end");
            return "success";
        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping("/delete")
    public ModelAndView deleteMember() {

        logger.info("AuthContoller deleteMemberPost start");


        ModelAndView mv = new ModelAndView();
        mv.setViewName("deleteMember");
        logger.info("AuthContoller deleteMemberPost start");

        return mv;
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteMemberPost(@RequestBody Auth auth){

        logger.info("AuthContoller deleteMemberPost start");
        logger.info("Deregistraion Email : {}", auth.getEmail());

        try {
            Auth findAuth = authService.findByEmail(auth.getEmail());
            logger.info("Request Auth : " +findAuth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new DeregistrationRequestHeader(
                            this.deregistrationURL,
                            this.consumerKey,
                            this.consumerSecret,
                            findAuth.getUserAccessToken(),
                            findAuth.getUserAccessTokenSecret());

            final ResponseEntity<String> response =
                    this.oauthService.deregister(tcHeader);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                log.info("Deregistration Process: SUCCESS");


                authService.deleteAuth(findAuth.getId());

                AuthLog authLog = new AuthLog();
                authLog.setEmail(findAuth.getEmail());
                authLog.setName(findAuth.getName());
                authLog.setUserAccessToken(findAuth.getUserAccessToken());
                authLog.setLogType(AuthLogEnum.delete.getValue());

                authLogService.save(authLog);

                logger.info("DELETE AUTH : " + findAuth.getEmail());

                logger.info("AuthContoller deleteMemberPost end");
                return "success";
            }

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }

        return "success";
    }


    @PostMapping("/update")
    @ResponseBody
    public String updateAuth(@RequestBody Auth auth) {

        logger.info("AuthContoller updateAuth start");


        try {
            authService.update(auth);

            AuthLog authLog = new AuthLog();
            authLog.setEmail(auth.getEmail());
            authLog.setName(auth.getName());
            authLog.setUserAccessToken(auth.getUserAccessToken());
            authLog.setLogType(AuthLogEnum.recreate.getValue());

            authLogService.save(authLog);

            logger.info("AuthContoller updateAuth end");

            return "success";
        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @GetMapping("/list")
    public ModelAndView getList() {

        logger.info("AuthContoller getList start");

        List<AuthVO> authList = authService.getList();

        ModelAndView mv = new ModelAndView();
        mv.setViewName("authlist");
        mv.addObject("list", authList );

        logger.info("AuthContoller getList end");

        return mv;
    }

    @PostMapping("/deregistration")
    @ResponseBody
    public void deregistration(@RequestBody Deregistrations deregistrations){

        logger.info("AuthContoller deregistration start");

        logger.info("Deregistration request : " +deregistrations.toString());

        logger.info("AuthContoller deregistration end");


    }



    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("AuthContoller excel start");

        List<AuthVO> authVOList = authService.getList();

        if(authVOList == null) {
            response.sendRedirect("/auth/req");
            return;
        }

        int rowNo = 0;


        XSSFWorkbook xworkbook = new XSSFWorkbook();

        XSSFSheet xsheet = xworkbook.createSheet("auth");		//시트 생성

        //Title
        XSSFRow headerRow = xsheet.createRow(rowNo++);

        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("name");
        headerRow.createCell(2).setCellValue("email");
        headerRow.createCell(3).setCellValue("userAccessToken");
        headerRow.createCell(4).setCellValue("userAccessTokenSecret");

        for (AuthVO authVO : authVOList) {
            XSSFRow row = xsheet.createRow(rowNo++);

            row.createCell(0).setCellValue(authVO.getId());
            row.createCell(1).setCellValue(authVO.getName());
            row.createCell(2).setCellValue(authVO.getEmail());
            row.createCell(3).setCellValue(authVO.getUserAccessToken());
            row.createCell(4).setCellValue(authVO.getUserAccessTokenSecret());

        }


        try {
            long curTime = System.currentTimeMillis();

            String timeName = String.valueOf(curTime);

            response.setContentType("ms-vnd/excel");
            response.setHeader("Content-Disposition", "attachment;filename=auth"+ timeName +".xlsx");

            // Excel File Output
            xworkbook.write(response.getOutputStream());
            xworkbook.close();

            logger.info("Export Excel SUCCESS");

            logger.info("AuthContoller excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
