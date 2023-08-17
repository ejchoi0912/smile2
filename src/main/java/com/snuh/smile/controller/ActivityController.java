package com.snuh.smile.controller;

import com.snuh.smile.domain.Activity;

import com.snuh.smile.domain.Auth;
import com.snuh.smile.domain.Backfill;
import com.snuh.smile.domain.DataTable;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.BackfillRequestHeader;
import com.snuh.smile.service.ActivityService;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.OauthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.snuh.smile.util.ResponseUtil.responseOKEntity;

@Controller
@RequestMapping("/activity")
@RequiredArgsConstructor(onConstructor = @ __(@Autowired))
@Slf4j
public class ActivityController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${activity.url}")
    private String activityURL;

    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${activity.backfill.url}")
    private String backfillURL;


    @NonNull
    private final AuthService authService;
    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final ActivityService activityService;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView goActivity(@PageableDefault(size = 10) Pageable pageable){

        logger.info("ActivityController goActivity start");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("activity");

        Page<Activity.Activities> activityList = activityService.getPagelist(pageable);

        mv.addObject("activityList", activityList );
        mv.addObject("pages", activityList );
        mv.addObject("maxPage", 10 );

        logger.info("ActivityController goActivity end");

        return mv;
    }



    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView searchActivity(@PageableDefault(size = 10) Pageable pageable, @RequestParam String token){

        logger.info("ActivityController searchActivity start");

        logger.info("Search :: " + token);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("activity");

        Page<Activity.Activities> activityList = activityService.getSearchPagelist(token, pageable);

        mv.addObject("activityList", activityList );
        mv.addObject("pages", activityList );
        mv.addObject("maxPage", 10 );

        logger.info("ActivityController searchActivity end");

        return mv;
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public void postActivity(@RequestBody Activity activity){


        logger.info("ActivityController postActivity start");

        responseOKEntity();

        logger.info("ActivityController Response send : 200");


        logger.info("Activity : {}", activity.toString());

        for (Activity.Activities activities : activity.getActivities()
             ) {

            activityService.save(activities);
        }

        logger.info("ActivityController postActivity end");
    }

    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("ActivityController excel start");

        Auth getAuth = authService.findByEmail(auth.getEmail());

        if(getAuth == null) {
            response.sendRedirect("/activity/");
            return;
        }

        logger.info("Excel Email :: " + auth.getEmail());

        List<Activity.Activities> activityList = activityService.getActivityTokenList(getAuth.getUserAccessToken());

        if(activityList == null) {
            response.sendRedirect("/activity/");
            return;
        }

        int rowNo = 0;

        //XSSFWorkbook xworkbook = new XSSFWorkbook();

        //XSSFSheet xsheet = xworkbook.createSheet("activity");		//시트 생성

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxssfWorkbook.createSheet("activity");

        //Title
        //XSSFRow headerRow = xsheet.createRow(rowNo++);

        SXSSFRow headerRow = sheet.createRow(rowNo++);

        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("userId");
        headerRow.createCell(2).setCellValue("userAccessToken");
        headerRow.createCell(3).setCellValue("summaryId");
        headerRow.createCell(4).setCellValue("activityId");
        headerRow.createCell(5).setCellValue("startTimeInSeconds");
        headerRow.createCell(6).setCellValue("startTimeOffsetInSeconds");
        headerRow.createCell(7).setCellValue("activityType");
        headerRow.createCell(8).setCellValue("durationInSeconds");
        headerRow.createCell(9).setCellValue("averageBikeCadenceInRoundsPerMinute");
        headerRow.createCell(10).setCellValue("averageHeartRateInBeatsPerMinute");
        headerRow.createCell(11).setCellValue("averageRunCadenceInStepsPerMinute");
        headerRow.createCell(12).setCellValue("averageSpeedInMetersPerSecond");
        headerRow.createCell(13).setCellValue("averageSwimCadenceInStrokesPerMinute");
        headerRow.createCell(14).setCellValue("averagePaceInMinutesPerKilometer");
        headerRow.createCell(15).setCellValue("activeKilocalories");
        headerRow.createCell(16).setCellValue("deviceName");
        headerRow.createCell(17).setCellValue("distanceInMeters");
        headerRow.createCell(18).setCellValue("maxBikeCadenceInRoundsPerMinute");
        headerRow.createCell(19).setCellValue("maxHeartRateInBeatsPerMinute");
        headerRow.createCell(20).setCellValue("maxPaceInMinutesPerKilometer");
        headerRow.createCell(21).setCellValue("maxRunCadenceInStepsPerMinute");
        headerRow.createCell(22).setCellValue("maxSpeedInMetersPerSecond");
        headerRow.createCell(23).setCellValue("numberOfActiveLengths");
        headerRow.createCell(24).setCellValue("startingLatitudeInDegree");
        headerRow.createCell(25).setCellValue("startingLongitudeInDegree");
        headerRow.createCell(26).setCellValue("steps");
        headerRow.createCell(27).setCellValue("totalElevationGainInMeters");
        headerRow.createCell(28).setCellValue("totalElevationLossInMeters");
        headerRow.createCell(29).setCellValue("isParent");
        headerRow.createCell(30).setCellValue("parentSummaryId");
        headerRow.createCell(31).setCellValue("Manual");

        for (Activity.Activities activities : activityList) {
            //XSSFRow row = xsheet.createRow(rowNo++);
            SXSSFRow row = sheet.createRow(rowNo++);

            row.createCell(0).setCellValue(activities.getId());
            row.createCell(1).setCellValue(activities.getUserId());
            row.createCell(2).setCellValue(activities.getUserAccessToken());
            row.createCell(3).setCellValue(activities.getSummaryId());
            row.createCell(4).setCellValue(activities.getActivityId());
            row.createCell(5).setCellValue(activities.getStartTimeInSeconds());
            row.createCell(6).setCellValue(activities.getStartTimeOffsetInSeconds());
            row.createCell(7).setCellValue(activities.getActivityType());
            row.createCell(8).setCellValue(activities.getDurationInSeconds());
            row.createCell(9).setCellValue(activities.getAverageBikeCadenceInRoundsPerMinute());
            row.createCell(10).setCellValue(activities.getAverageHeartRateInBeatsPerMinute());
            row.createCell(11).setCellValue(activities.getAverageRunCadenceInStepsPerMinute());
            row.createCell(12).setCellValue(activities.getAverageSpeedInMetersPerSecond());
            row.createCell(13).setCellValue(activities.getAverageSwimCadenceInStrokesPerMinute());
            row.createCell(14).setCellValue(activities.getAveragePaceInMinutesPerKilometer());
            row.createCell(15).setCellValue(activities.getActiveKilocalories());
            row.createCell(16).setCellValue(activities.getDeviceName());
            row.createCell(17).setCellValue(activities.getDistanceInMeters());
            row.createCell(18).setCellValue(activities.getMaxBikeCadenceInRoundsPerMinute());
            row.createCell(19).setCellValue(activities.getMaxHeartRateInBeatsPerMinute());
            row.createCell(20).setCellValue(activities.getMaxPaceInMinutesPerKilometer());
            row.createCell(21).setCellValue(activities.getMaxRunCadenceInStepsPerMinute());
            row.createCell(22).setCellValue(activities.getMaxSpeedInMetersPerSecond());
            row.createCell(23).setCellValue(activities.getNumberOfActiveLengths());
            row.createCell(24).setCellValue(activities.getStartingLatitudeInDegree());
            row.createCell(25).setCellValue(activities.getStartingLongitudeInDegree());
            row.createCell(26).setCellValue(activities.getSteps());
            row.createCell(27).setCellValue(activities.getTotalElevationGainInMeters());
            row.createCell(28).setCellValue(activities.getTotalElevationLossInMeters());
            row.createCell(29).setCellValue(activities.getIsParent());
            row.createCell(30).setCellValue(activities.getParentSummaryId());
            row.createCell(31).setCellValue(activities.getManual());
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
            response.setHeader("Content-Disposition", "attachment;filename=activity"+ timeName +".xlsx");

            // Excel File Output
            //xworkbook.write(response.getOutputStream());
            //xworkbook.close();

            OutputStream fileOut = response.getOutputStream();
            sxssfWorkbook.write(fileOut);
            fileOut.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.info("Export Excel SUCCESS");

            logger.info("ActivityController excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
