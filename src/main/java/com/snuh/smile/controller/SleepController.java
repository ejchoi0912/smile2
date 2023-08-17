package com.snuh.smile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snuh.smile.domain.*;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.BackfillRequestHeader;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.DailyService;
import com.snuh.smile.service.OauthService;
import com.snuh.smile.service.SleepService;
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
@RequestMapping("/sleep")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SleepController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${health.sleep.url}")
    private String backfillURL;



    @NonNull
    private final AuthService authService;
    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final SleepService sleepService;


    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView goSleep(@PageableDefault(size = 10) Pageable pageable){

        logger.info("SleepController goSleep start");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("sleep");

        Page<Sleeps.Sleep> sleepList = sleepService.getPagelist(pageable);

        mv.addObject("sleepList", sleepList );
        mv.addObject("pages", sleepList );
        mv.addObject("maxPage", 10 );
;

        logger.info("SleepController goSleep end");
        return mv;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView searchSleep(@PageableDefault(size = 10) Pageable pageable, @RequestParam String token){

        logger.info("SleepController searchSleep start");

        logger.info("Search :: " + token);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("sleep");

        Page<Sleeps.Sleep> sleepList = sleepService.getSearchPagelist(token, pageable);

        mv.addObject("sleepList", sleepList );
        mv.addObject("pages", sleepList );
        mv.addObject("maxPage", 10 );
        ;

        logger.info("SleepController searchSleep end");
        return mv;
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public void postSleep(@RequestBody Sleeps sleeps){


        logger.info("SleepController postSleep start");

        responseOKEntity();
        logger.info("SleepController Response send : 200");

        logger.info("Sleep : {}", sleeps.toString());

        for (Sleeps.Sleep sleep: sleeps.getSleeps()
        ) {

            sleepService.saveSleep(sleep);

        }

        logger.info("SleepController postSleep end");
    }


    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("SleepController excel start");

        Auth getAuth = authService.findByEmail(auth.getEmail());

        if(getAuth == null){
            response.sendRedirect("/sleep/");
            return;
        }

        logger.info("Excel Email :: " + auth.getEmail());

        List<Sleeps.Sleep> sleepList = sleepService.getSleepTokenList(getAuth.getUserAccessToken());

        if(sleepList == null){
            response.sendRedirect("/sleep/");
            return;
        }

        int rowNo = 0;


        //XSSFWorkbook xworkbook = new XSSFWorkbook();

        //XSSFSheet xsheet = xworkbook.createSheet("sleep");		//시트 생성

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxssfWorkbook.createSheet("sleep");

        //Title
        //XSSFRow headerRow = xsheet.createRow(rowNo++);
        SXSSFRow headerRow = sheet.createRow(rowNo++);


        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("userId");
        headerRow.createCell(2).setCellValue("userAccessToken");
        headerRow.createCell(3).setCellValue("summaryId");
        headerRow.createCell(4).setCellValue("calendarDate");
        headerRow.createCell(5).setCellValue("startTimeInSeconds");
        headerRow.createCell(6).setCellValue("startTimeOffsetInSeconds");
        headerRow.createCell(7).setCellValue("durationInSeconds");
        headerRow.createCell(8).setCellValue("unmeasurableSleepInSeconds");
        headerRow.createCell(9).setCellValue("deepSleepDurationInSeconds");
        headerRow.createCell(10).setCellValue("lightSleepDurationInSeconds");
        headerRow.createCell(11).setCellValue("remSleepInSeconds");
        headerRow.createCell(12).setCellValue("awakeDurationInSeconds");
        //headerRow.createCell(13).setCellValue("sleepLevelsMapDeep");
        //headerRow.createCell(14).setCellValue("sleepLevelsMapLight");
        //headerRow.createCell(15).setCellValue("sleepLevelsMapAwake");
        headerRow.createCell(13).setCellValue("Validation");


        for (Sleeps.Sleep sleep : sleepList) {
            //XSSFRow row = xsheet.createRow(rowNo++);
            SXSSFRow row = sheet.createRow(rowNo++);
/*
            ObjectMapper deepMapper = new ObjectMapper();
            String deepJson = deepMapper.writeValueAsString(sleep.getSleepLevelsMap().getDeep());

            ObjectMapper lightMapper = new ObjectMapper();
            String lightJson = lightMapper.writeValueAsString(sleep.getSleepLevelsMap().getLight());

            ObjectMapper awakeMapper = new ObjectMapper();
            String awakeJson = awakeMapper.writeValueAsString(sleep.getSleepLevelsMap().getAwake());


 */
            row.createCell(0).setCellValue(sleep.getId());
            row.createCell(1).setCellValue(sleep.getUserId());
            row.createCell(2).setCellValue(sleep.getUserAccessToken());
            row.createCell(3).setCellValue(sleep.getSummaryId());
            row.createCell(4).setCellValue(sleep.getCalendarDate());
            row.createCell(5).setCellValue(sleep.getStartTimeInSeconds());
            row.createCell(6).setCellValue(sleep.getStartTimeOffsetInSeconds());
            row.createCell(7).setCellValue(sleep.getDurationInSeconds());
            row.createCell(8).setCellValue(sleep.getUnmeasurableSleepInSeconds());
            row.createCell(9).setCellValue(sleep.getDeepSleepDurationInSeconds());
            row.createCell(10).setCellValue(sleep.getLightSleepDurationInSeconds());
            row.createCell(11).setCellValue(sleep.getRemSleepInSeconds());
            row.createCell(12).setCellValue(sleep.getAwakeDurationInSeconds());
            //row.createCell(13).setCellValue(deepJson);
            //row.createCell(14).setCellValue(lightJson);
            //row.createCell(15).setCellValue(awakeJson);
            row.createCell(13).setCellValue(sleep.getValidation());
        }



        int rowNo2 = 0;

        //XSSFSheet xsheet2 = xworkbook.createSheet("sleep_levels_map_deep");		//시트 생성
        SXSSFSheet sheet2 = sxssfWorkbook.createSheet("sleep_levels_map_deep");		//시트 생성

        //Title
        //XSSFRow headerRow2 = xsheet2.createRow(rowNo2++);
        SXSSFRow headerRow2 = sheet2.createRow(rowNo2++);

        headerRow2.createCell(0).setCellValue("fk_id");
        headerRow2.createCell(1).setCellValue("startTimeInSeconds");
        headerRow2.createCell(2).setCellValue("endTimeInSeconds");


        for (Sleeps.Sleep sleep : sleepList) {

            if(sleep.getSleepLevelsMap() != null) {
                for (Sleeps.Deep deep : sleep.getSleepLevelsMap().getDeep()) {

                    //XSSFRow row = xsheet2.createRow(rowNo2++);
                    SXSSFRow row = sheet2.createRow(rowNo2++);

                    row.createCell(0).setCellValue(sleep.getId());
                    row.createCell(1).setCellValue(deep.getStartTimeInSeconds());
                    row.createCell(2).setCellValue(deep.getEndTimeInSeconds());
                }
            }
        }

        int rowNo3 = 0;

        //XSSFSheet xsheet3 = xworkbook.createSheet("sleep_levels_map_light");		//시트 생성
        SXSSFSheet sheet3 = sxssfWorkbook.createSheet("sleep_levels_map_light");		//시트 생성

        //Title
        //XSSFRow headerRow3 = xsheet3.createRow(rowNo3++);
        SXSSFRow headerRow3 = sheet3.createRow(rowNo3++);

        headerRow3.createCell(0).setCellValue("fk_id");
        headerRow3.createCell(1).setCellValue("startTimeInSeconds");
        headerRow3.createCell(2).setCellValue("endTimeInSeconds");


        for (Sleeps.Sleep sleep : sleepList) {
            if(sleep.getSleepLevelsMap() != null) {
                for (Sleeps.Light light : sleep.getSleepLevelsMap().getLight()) {

                    //XSSFRow row = xsheet3.createRow(rowNo3++);
                    SXSSFRow row = sheet3.createRow(rowNo3++);

                    row.createCell(0).setCellValue(sleep.getId());
                    row.createCell(1).setCellValue(light.getStartTimeInSeconds());
                    row.createCell(2).setCellValue(light.getEndTimeInSeconds());
                }
            }
        }

        int rowNo4 = 0;

        //XSSFSheet xsheet4 = xworkbook.createSheet("sleep_levels_map_awake");		//시트 생성
        SXSSFSheet sheet4 = sxssfWorkbook.createSheet("sleep_levels_map_awake");		//시트 생성


        //Title
        //XSSFRow headerRow4 = xsheet4.createRow(rowNo4++);
        SXSSFRow headerRow4 = sheet4.createRow(rowNo4++);

        headerRow4.createCell(0).setCellValue("fk_id");
        headerRow4.createCell(1).setCellValue("startTimeInSeconds");
        headerRow4.createCell(2).setCellValue("endTimeInSeconds");


        for (Sleeps.Sleep sleep : sleepList) {
            if(sleep.getSleepLevelsMap() != null) {
                for (Sleeps.Awake awake : sleep.getSleepLevelsMap().getAwake()) {

                    //XSSFRow row = xsheet4.createRow(rowNo4++);
                    SXSSFRow row = sheet4.createRow(rowNo4++);

                    row.createCell(0).setCellValue(sleep.getId());
                    row.createCell(1).setCellValue(awake.getStartTimeInSeconds());
                    row.createCell(2).setCellValue(awake.getEndTimeInSeconds());
                }
            }
        }

        int rowNo5 = 0;

        //XSSFSheet xsheet5 = xworkbook.createSheet("time_offset_sleep_respiration");		//시트 생성
        SXSSFSheet sheet5 = sxssfWorkbook.createSheet("time_offset_sleep_respiration");		//시트 생성


        //Title
        //XSSFRow headerRow5 = xsheet5.createRow(rowNo5++);
        SXSSFRow headerRow5 = sheet5.createRow(rowNo5++);

        headerRow5.createCell(0).setCellValue("fk_id");
        headerRow5.createCell(1).setCellValue("key");
        headerRow5.createCell(2).setCellValue("value");


        for (Sleeps.Sleep sleep : sleepList) {
            Map<String, String> respirationMap = sleep.getTimeOffsetSleepRespiration();

            Iterator<String> keys = respirationMap.keySet().iterator();

            while (keys.hasNext()) {
                String key = keys.next();

                //XSSFRow row = xsheet5.createRow(rowNo5++);
                SXSSFRow row = sheet5.createRow(rowNo5++);

                row.createCell(0).setCellValue(sleep.getId());
                row.createCell(1).setCellValue(key);
                row.createCell(2).setCellValue(respirationMap.get(key));
            }
        }


        int rowNo6 = 0;

        //XSSFSheet xsheet6 = xworkbook.createSheet("time_offset_sleep_spo2");		//시트 생성
        SXSSFSheet sheet6 = sxssfWorkbook.createSheet("time_offset_sleep_spo2");		//시트 생성

        //Title
        //XSSFRow headerRow6 = xsheet6.createRow(rowNo6++);
        SXSSFRow headerRow6 = sheet6.createRow(rowNo6++);

        headerRow6.createCell(0).setCellValue("fk_id");
        headerRow6.createCell(1).setCellValue("key");
        headerRow6.createCell(2).setCellValue("value");


        for (Sleeps.Sleep sleep : sleepList) {
            Map<String, String> spo2Map = sleep.getTimeOffsetSleepSpo2();

            Iterator<String> keys = spo2Map.keySet().iterator();

            while (keys.hasNext()) {
                String key = keys.next();

                //XSSFRow row = xsheet6.createRow(rowNo6++);
                SXSSFRow row = sheet6.createRow(rowNo6++);


                row.createCell(0).setCellValue(sleep.getId());
                row.createCell(1).setCellValue(key);
                row.createCell(2).setCellValue(spo2Map.get(key));
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
            response.setHeader("Content-Disposition", "attachment;filename=sleep"+ timeName +".xlsx");

            // Excel File Output
            //xworkbook.write(response.getOutputStream());
            //xworkbook.close();

            OutputStream fileOut = response.getOutputStream();
            sxssfWorkbook.write(fileOut);
            fileOut.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.info("Export Excel SUCCESS");

            logger.info("SleepController excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
