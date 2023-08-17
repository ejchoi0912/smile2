package com.snuh.smile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snuh.smile.domain.*;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.BackfillRequestHeader;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.DailyService;
import com.snuh.smile.service.OauthService;
import com.snuh.smile.service.StressDetailService;
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
@RequestMapping("/stressDetail")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class StressDetailController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${health.stress.details.url}")
    private String backfillURL;

    @NonNull
    private final AuthService authService;
    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final StressDetailService stressDetailService;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView goStressDetail(@PageableDefault(size = 10) Pageable pageable){

        logger.info("StressDetailController goStressDetail start");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("stressDetail");

        Page<StressDetails.StressDetail> stressDetailList = stressDetailService.getPagelist(pageable);

        mv.addObject("stressDetailList", stressDetailList );
        mv.addObject("pages", stressDetailList );
        mv.addObject("maxPage", 10 );

        logger.info("StressDetailController goStressDetail end");

        return mv;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView searchStressDetail(@PageableDefault(size = 10) Pageable pageable, @RequestParam String token){

        logger.info("StressDetailController searchStressDetail start");

        logger.info("Search :: " + token);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("stressDetail");

        Page<StressDetails.StressDetail> stressDetailList = stressDetailService.getSearchPagelist(token, pageable);

        mv.addObject("stressDetailList", stressDetailList );
        mv.addObject("pages", stressDetailList );
        mv.addObject("maxPage", 10 );

        logger.info("StressDetailController searchStressDetail end");

        return mv;
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public void postStressDetail(@RequestBody StressDetails stressDetails){


        logger.info("StressDetailController postStressDetail start");

        responseOKEntity();
        logger.info("StressDetailController Response send : 200");

        logger.info("Stress Details : {}", stressDetails.toString());

        for (StressDetails.StressDetail stressDetail : stressDetails.getStressDetails()
        ) {

            stressDetailService.save(stressDetail);

        }

        logger.info("StressDetailController postStressDetail end");
    }

    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("StressDetailController excel start");
        Auth getAuth = authService.findByEmail(auth.getEmail());

        if(getAuth == null) {
            response.sendRedirect("/stressDetail/");
            return;
        }

        logger.info("Excel Email :: " + auth.getEmail());

        List<StressDetails.StressDetail> stressDetailList = stressDetailService.getStressDetailTokenList(getAuth.getUserAccessToken());

        if(stressDetailList == null) {
            response.sendRedirect("/stressDetail/");
            return;
        }


        int rowNo = 0;


        //XSSFWorkbook xworkbook = new XSSFWorkbook();

        //XSSFSheet xsheet = xworkbook.createSheet("stressDetail");		//시트 생성

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxssfWorkbook.createSheet("stressDetail");

        //Title
        //XSSFRow headerRow = xsheet.createRow(rowNo++);
        SXSSFRow headerRow = sheet.createRow(rowNo++);

        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("userId");
        headerRow.createCell(2).setCellValue("userAccessToken");
        headerRow.createCell(3).setCellValue("summaryId");
        headerRow.createCell(4).setCellValue("startTimeInSeconds");
        headerRow.createCell(5).setCellValue("startTimeOffsetInSeconds");
        headerRow.createCell(6).setCellValue("durationInSeconds");
        headerRow.createCell(7).setCellValue("calendarDate");
       // headerRow.createCell(8).setCellValue("timeOffsetStressLevelValues");
       // headerRow.createCell(9).setCellValue("timeOffsetBodyBatteryDetails");


        for (StressDetails.StressDetail stressDetail : stressDetailList) {
            //XSSFRow row = xsheet.createRow(rowNo++);
            SXSSFRow row = sheet.createRow(rowNo++);

            /*
            ObjectMapper stressMapper = new ObjectMapper();
            String stressJson = stressMapper.writeValueAsString(stressDetail.getTimeOffsetStressLevelValues());

            ObjectMapper batteryMapper = new ObjectMapper();
            String batteryJson = batteryMapper.writeValueAsString(stressDetail.getTimeOffsetBodyBatteryDetails());


             */
            row.createCell(0).setCellValue(stressDetail.getId());
            row.createCell(1).setCellValue(stressDetail.getUserId());
            row.createCell(2).setCellValue(stressDetail.getUserAccessToken());
            row.createCell(3).setCellValue(stressDetail.getSummaryId());
            row.createCell(4).setCellValue(stressDetail.getStartTimeInSeconds());
            row.createCell(5).setCellValue(stressDetail.getStartTimeOffsetInSeconds());
            row.createCell(6).setCellValue(stressDetail.getDurationInSeconds());
            row.createCell(7).setCellValue(stressDetail.getCalendarDate());
         //   row.createCell(8).setCellValue(stressJson);
            //  row.createCell(9).setCellValue(batteryJson);
        }

        int rowNo2 = 0;

        //XSSFSheet xsheet2 = xworkbook.createSheet("time_offset_body_battery_values");		//시트 생성
        SXSSFSheet sheet2 = sxssfWorkbook.createSheet("time_offset_body_battery_values");		//시트 생성

        //Title
        //XSSFRow headerRow2 = xsheet2.createRow(rowNo2++);
        SXSSFRow headerRow2 = sheet2.createRow(rowNo2++);

        headerRow2.createCell(0).setCellValue("fk_id");
        headerRow2.createCell(1).setCellValue("key");
        headerRow2.createCell(2).setCellValue("value");


        for (StressDetails.StressDetail stressDetail : stressDetailList) {
            Map<String, Integer> batteryMap = stressDetail.getTimeOffsetBodyBatteryValues();

            Iterator<String> keys = batteryMap.keySet().iterator();

            while (keys.hasNext()) {
                String key = keys.next();

                //XSSFRow row = xsheet2.createRow(rowNo2++);
                SXSSFRow row = sheet2.createRow(rowNo2++);

                row.createCell(0).setCellValue(stressDetail.getId());
                row.createCell(1).setCellValue(key);
                row.createCell(2).setCellValue(batteryMap.get(key));
            }
        }


        int rowNo3 = 0;

        //XSSFSheet xsheet3 = xworkbook.createSheet("time_offset_stress_level_values");		//시트 생성
        SXSSFSheet sheet3 = sxssfWorkbook.createSheet("time_offset_stress_level_values");		//시트 생성


        //Title
        //XSSFRow headerRow3 = xsheet3.createRow(rowNo3++);
        SXSSFRow headerRow3 = sheet3.createRow(rowNo3++);

        headerRow3.createCell(0).setCellValue("fk_id");
        headerRow3.createCell(1).setCellValue("key");
        headerRow3.createCell(2).setCellValue("value");


        for (StressDetails.StressDetail stressDetail : stressDetailList) {
            Map<String, Integer> levelMap = stressDetail.getTimeOffsetStressLevelValues();

            Iterator<String> keys = levelMap.keySet().iterator();

            while (keys.hasNext()) {
                String key = keys.next();

                //XSSFRow row = xsheet3.createRow(rowNo3++);
                SXSSFRow row = sheet3.createRow(rowNo3++);

                row.createCell(0).setCellValue(stressDetail.getId());
                row.createCell(1).setCellValue(key);
                row.createCell(2).setCellValue(levelMap.get(key));
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
            response.setHeader("Content-Disposition", "attachment;filename=stressDetail"+ timeName +".xlsx");

            // Excel File Output
            //xworkbook.write(response.getOutputStream());
            //xworkbook.close();

            OutputStream fileOut = response.getOutputStream();
            sxssfWorkbook.write(fileOut);
            fileOut.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.info("Export Excel SUCCESS");

            logger.info("StressDetailController excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
