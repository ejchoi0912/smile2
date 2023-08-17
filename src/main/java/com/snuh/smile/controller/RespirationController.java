package com.snuh.smile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snuh.smile.domain.*;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.BackfillRequestHeader;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.OauthService;
import com.snuh.smile.service.RespirationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.snuh.smile.util.ResponseUtil.responseOKEntity;

@Controller
@RequestMapping("/respiration")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RespirationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${health.respiration.url}")
    private String backfillURL;

    @NonNull
    private final AuthService authService;
    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final RespirationService respirationService;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView goRespiration(@PageableDefault(size = 10) Pageable pageable){

        logger.info("RespirationController goRespiration start");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("respiration");

        Page<Respirations.Respiration> respirationList = respirationService.getPagelist(pageable);

        mv.addObject("respirationList", respirationList );
        mv.addObject("pages", respirationList );
        mv.addObject("maxPage", 10 );

        logger.info("RespirationController goRespiration end");

        return mv;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView searchRespiration(@PageableDefault(size = 10) Pageable pageable,  @RequestParam String token){

        logger.info("RespirationController searchRespiration start");

        logger.info("Search :: " + token);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("respiration");

        Page<Respirations.Respiration> respirationList = respirationService.getSearchPagelist(token, pageable);

        mv.addObject("respirationList", respirationList );
        mv.addObject("pages", respirationList );
        mv.addObject("maxPage", 10 );

        logger.info("RespirationController searchRespiration end");

        return mv;
    }


    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public void postRespiration(@RequestBody Respirations respirations){


        logger.info("RespirationController postRespiration start");

        responseOKEntity();
        logger.info("RespirationController Response send : 200");

        logger.info("Respiration : {}", respirations.toString());

        for (Respirations.Respiration respiration : respirations.getAllDayRespiration()
        ) {
            respirationService.save(respiration);
        }

        logger.info("RespirationController postRespiration end");
    }


    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("RespirationController excel start");

        Auth getAuth = authService.findByEmail(auth.getEmail());

        if(getAuth == null) {
            response.sendRedirect("/respiration/");
            return;
        }

        logger.info("Excel Email :: " + auth.getEmail());

        List<Respirations.Respiration> respirationList = respirationService.getRespirationTokenList(getAuth.getUserAccessToken());

        if(respirationList == null){
            response.sendRedirect("/respiration/");
            return;
        }

        int rowNo = 0;


        //XSSFWorkbook xworkbook = new XSSFWorkbook();

        //XSSFSheet xsheet = xworkbook.createSheet("respiration");		//시트 생성

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxssfWorkbook.createSheet("respiration");

        //Title
        //XSSFRow headerRow = xsheet.createRow(rowNo++);
        SXSSFRow headerRow = sheet.createRow(rowNo++);


        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("userId");
        headerRow.createCell(2).setCellValue("userAccessToken");
        headerRow.createCell(3).setCellValue("summaryId");
        headerRow.createCell(4).setCellValue("startTimeInSeconds");
        headerRow.createCell(5).setCellValue("durationInSeconds");
        headerRow.createCell(6).setCellValue("startTimeOffsetInSeconds");
    //    headerRow.createCell(7).setCellValue("timeOffsetEpochToBreaths");


        for (Respirations.Respiration respiration : respirationList) {
            //XSSFRow row = xsheet.createRow(rowNo++);
            SXSSFRow row = sheet.createRow(rowNo++);


            //    ObjectMapper sampleMapper = new ObjectMapper();
        //    String timeJson = sampleMapper.writeValueAsString(respiration.getTimeOffsetEpochToBreaths());

            row.createCell(0).setCellValue(respiration.getId());
            row.createCell(1).setCellValue(respiration.getUserId());
            row.createCell(2).setCellValue(respiration.getUserAccessToken());
            row.createCell(3).setCellValue(respiration.getSummaryId());
            row.createCell(4).setCellValue(respiration.getStartTimeInSeconds());
            row.createCell(5).setCellValue(respiration.getDurationInSeconds());
            row.createCell(6).setCellValue(respiration.getStartTimeOffsetInSeconds());
        //    row.createCell(7).setCellValue(timeJson);
        }


        int rowNo2 = 0;

        //XSSFSheet xsheet2 = xworkbook.createSheet("time_offset_epoch_to_breaths");		//시트 생성
        SXSSFSheet sheet2 = sxssfWorkbook.createSheet("time_off_heart_rate_samples");		//시트 생성


        //Title
        //XSSFRow headerRow2 = xsheet2.createRow(rowNo2++);
        SXSSFRow headerRow2 = sheet2.createRow(rowNo2++);


        headerRow2.createCell(0).setCellValue("fk_id");
        headerRow2.createCell(1).setCellValue("key");
        headerRow2.createCell(2).setCellValue("value");


        for (Respirations.Respiration respiration : respirationList) {
            Map<String, String> timeMap = respiration.getTimeOffsetEpochToBreaths();

            Iterator<String> keys = timeMap.keySet().iterator();

            while (keys.hasNext()) {
                String key = keys.next();

                //XSSFRow row = xsheet2.createRow(rowNo2++);
                SXSSFRow row = sheet2.createRow(rowNo2++);

                row.createCell(0).setCellValue(respiration.getId());
                row.createCell(1).setCellValue(key);
                row.createCell(2).setCellValue(timeMap.get(key));
            }
        }



        try {
            /*
            FileOutputStream out = new FileOutputStream(new File(filePath, fileNm));
            xworkbook.write(out);
            out.close();

             */

            long curTime = System.currentTimeMillis();

            String timeName = String.valueOf(curTime);

            response.setContentType("ms-vnd/excel");
            response.setHeader("Content-Disposition", "attachment;filename=respiration"+ timeName +".xlsx");

            // Excel File Output
            //xworkbook.write(response.getOutputStream());
            //xworkbook.close();

            OutputStream fileOut = response.getOutputStream();
            sxssfWorkbook.write(fileOut);
            fileOut.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.info("Export Excel SUCCESS");

            logger.info("RespirationController excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
