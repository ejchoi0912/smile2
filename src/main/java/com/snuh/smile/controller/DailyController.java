package com.snuh.smile.controller;

import com.snuh.smile.domain.*;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.DailyService;
import com.snuh.smile.service.OauthService;
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
import org.springframework.scheduling.annotation.EnableAsync;
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
@RequestMapping("/daily")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@EnableAsync
public class DailyController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${health.daily.url}")
    private String backfillURL;

    @NonNull
    private final AuthService authService;
    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final DailyService dailyService;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView goDaily(@PageableDefault(size = 10) Pageable pageable){

        logger.info("DailyController goDaily start");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("daily");

        Page<Dailies.Daily> dailyList = dailyService.getPagelist(pageable);


        mv.addObject("dailyList", dailyList );
        mv.addObject("pages", dailyList );
        mv.addObject("maxPage", 10 );

        logger.info("DailyController goDaily end");

        return mv;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView searchDaily(@PageableDefault(size = 10) Pageable pageable, @RequestParam String token){

        logger.info("DailyController searchDaily start");

        logger.info("Search :: " + token);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("daily");

        Page<Dailies.Daily> dailyList = dailyService.getSearchPagelist(token, pageable);


        mv.addObject("dailyList", dailyList );
        mv.addObject("pages", dailyList );
        mv.addObject("maxPage", 10 );

        logger.info("DailyController searchDaily end");

        return mv;
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public void postDaily(@RequestBody Dailies dailies){


        logger.info("DailyController postDaily start");

        responseOKEntity();
        logger.info("DailyController Response send : 200");

        logger.info("Daily : {}", dailies.toString());

        for (Dailies.Daily daily : dailies.getDailies()
        ) {

            dailyService.saveDaily(daily);
        }

        logger.info("DailyController postDaily end");

    }


    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("DailyController excel start");

        Auth getAuth = authService.findByEmail(auth.getEmail());

        if(getAuth == null) {
            response.sendRedirect("/daily/");
            return;
        }

        logger.info("Excel Email :: " + auth.getEmail());

        List<Dailies.Daily> dailyList = dailyService.getDailyTokenList(getAuth.getUserAccessToken());

        if(dailyList == null) {
            response.sendRedirect("/daily/");
            return;
        }

        int rowNo = 0;


        //XSSFWorkbook xworkbook = new XSSFWorkbook();

        //XSSFSheet xsheet = xworkbook.createSheet("daily");		//시트 생성

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxssfWorkbook.createSheet("daily");

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
        headerRow.createCell(7).setCellValue("activityType");
        headerRow.createCell(8).setCellValue("durationInSeconds");
        headerRow.createCell(9).setCellValue("steps");
        headerRow.createCell(10).setCellValue("distanceInMeters");
        headerRow.createCell(11).setCellValue("activeTimeInSeconds");
        headerRow.createCell(12).setCellValue("activeKilocalories");
        headerRow.createCell(13).setCellValue("bmrKilocalories");
        headerRow.createCell(14).setCellValue("consumedCalories");
        headerRow.createCell(15).setCellValue("moderateIntensityDurationInSeconds");
        headerRow.createCell(16).setCellValue("vigorousIntensityDurationInSeconds");
        headerRow.createCell(17).setCellValue("floorsClimbed");
        headerRow.createCell(18).setCellValue("minHeartRateInBeatsPerMinute");
        headerRow.createCell(19).setCellValue("averageHeartRateInBeatsPerMinute");
        headerRow.createCell(20).setCellValue("maxHeartRateInBeatsPerMinute");
        headerRow.createCell(21).setCellValue("restingHeartRateInBeatsPerMinute");
        //headerRow.createCell(22).setCellValue("timeOffsetHeartRateSamplesValue");
        headerRow.createCell(22).setCellValue("averageStressLevel");
        headerRow.createCell(23).setCellValue("maxStressLevel");
        headerRow.createCell(24).setCellValue("stressDurationInSeconds");
        headerRow.createCell(25).setCellValue("restStressDurationInSeconds");
        headerRow.createCell(26).setCellValue("activityStressDurationInSeconds");
        headerRow.createCell(27).setCellValue("lowStressDurationInSeconds");
        headerRow.createCell(28).setCellValue("mediumStressDurationInSeconds");
        headerRow.createCell(29).setCellValue("highStressDurationInSeconds");
        headerRow.createCell(30).setCellValue("stressQualifier");
        headerRow.createCell(31).setCellValue("stepsGoal");
        headerRow.createCell(32).setCellValue("netKilocaloriesGoal");
        headerRow.createCell(33).setCellValue("intensityDurationGoalInSeconds");
        headerRow.createCell(34).setCellValue("floorsClimbedGoal");

        for (Dailies.Daily daily : dailyList) {
            //XSSFRow row = xsheet.createRow(rowNo++);
            SXSSFRow row = sheet.createRow(rowNo++);

            //ObjectMapper sampleMapper = new ObjectMapper();
            //String sampleJson = sampleMapper.writeValueAsString(daily.getTimeOffsetHeartRateSamples());

            row.createCell(0).setCellValue(daily.getId());
            row.createCell(1).setCellValue(daily.getUserId());
            row.createCell(2).setCellValue(daily.getUserAccessToken());
            row.createCell(3).setCellValue(daily.getSummaryId());
            row.createCell(4).setCellValue(daily.getCalendarDate());
            row.createCell(5).setCellValue(daily.getStartTimeInSeconds());
            row.createCell(6).setCellValue(daily.getStartTimeOffsetInSeconds());
            row.createCell(7).setCellValue(daily.getActivityType());
            row.createCell(8).setCellValue(daily.getDurationInSeconds());
            row.createCell(9).setCellValue(daily.getSteps());
            row.createCell(10).setCellValue(daily.getDistanceInMeters());
            row.createCell(11).setCellValue(daily.getActiveTimeInSeconds());
            row.createCell(12).setCellValue(daily.getActiveKilocalories());
            row.createCell(13).setCellValue(daily.getBmrKilocalories());
            row.createCell(14).setCellValue(daily.getConsumedCalories());
            row.createCell(15).setCellValue(daily.getModerateIntensityDurationInSeconds());
            row.createCell(16).setCellValue(daily.getVigorousIntensityDurationInSeconds());
            row.createCell(17).setCellValue(daily.getFloorsClimbed());
            row.createCell(18).setCellValue(daily.getMinHeartRateInBeatsPerMinute());
            row.createCell(19).setCellValue(daily.getAverageHeartRateInBeatsPerMinute());
            row.createCell(20).setCellValue(daily.getMaxHeartRateInBeatsPerMinute());
            row.createCell(21).setCellValue(daily.getRestingHeartRateInBeatsPerMinute());
            //row.createCell(22).setCellValue(sampleJson);
            row.createCell(22).setCellValue(daily.getAverageStressLevel());
            row.createCell(23).setCellValue(daily.getMaxStressLevel());
            row.createCell(24).setCellValue(daily.getStressDurationInSeconds());
            row.createCell(25).setCellValue(daily.getRestStressDurationInSeconds());
            row.createCell(26).setCellValue(daily.getActivityStressDurationInSeconds());
            row.createCell(27).setCellValue(daily.getLowStressDurationInSeconds());
            row.createCell(28).setCellValue(daily.getMediumStressDurationInSeconds());
            row.createCell(29).setCellValue(daily.getHighStressDurationInSeconds());
            row.createCell(30).setCellValue(daily.getStressQualifier());
            row.createCell(31).setCellValue(daily.getStepsGoal());
            row.createCell(32).setCellValue(daily.getNetKilocaloriesGoal());
            row.createCell(33).setCellValue(daily.getIntensityDurationGoalInSeconds());
            row.createCell(34).setCellValue(daily.getFloorsClimbedGoal());
        }


        int rowNo2 = 0;

      //  XSSFSheet xsheet2 = xworkbook.createSheet("time_off_heart_rate_samples");		//시트 생성
        SXSSFSheet xsheet2 = sxssfWorkbook.createSheet("time_off_heart_rate_samples");		//시트 생성

        //Title
      //  XSSFRow headerRow2 = xsheet2.createRow(rowNo2++);
        SXSSFRow headerRow2 = xsheet2.createRow(rowNo2++);

        headerRow2.createCell(0).setCellValue("fk_id");
        headerRow2.createCell(1).setCellValue("key");
        headerRow2.createCell(2).setCellValue("value");


        for (Dailies.Daily daily : dailyList) {
            Map<String, Integer> heartRateSampleMap = daily.getTimeOffsetHeartRateSamples();

            Iterator<String> keys = heartRateSampleMap.keySet().iterator();

            while (keys.hasNext()) {
                String key = keys.next();

            //    XSSFRow row = xsheet2.createRow(rowNo2++);
                SXSSFRow row = xsheet2.createRow(rowNo2++);
                row.createCell(0).setCellValue(daily.getId());
                row.createCell(1).setCellValue(key);
                row.createCell(2).setCellValue(heartRateSampleMap.get(key));
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
            response.setHeader("Content-Disposition", "attachment;filename=daily"+ timeName +".xlsx");

            // Excel File Output
            //xworkbook.write(response.getOutputStream());
            //xworkbook.close();

            OutputStream fileOut = response.getOutputStream();
            sxssfWorkbook.write(fileOut);
            fileOut.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.info("Export Excel SUCCESS");

            logger.info("DailyController excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value = "/thirdparty", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity postThirdPartyDaily(@RequestBody String jsonInfo){


        logger.info("DailyController postThirdPartyDaily start");

        logger.info("Third Party Dailies : " + jsonInfo);

        logger.info("DailyController postThirdPartyDaily end");

        return new ResponseEntity(HttpStatus.OK);
    }
}
