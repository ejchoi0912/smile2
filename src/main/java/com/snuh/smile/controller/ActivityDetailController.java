package com.snuh.smile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snuh.smile.domain.Activity;
import com.snuh.smile.domain.ActivityDetails;
import com.snuh.smile.domain.Auth;
import com.snuh.smile.service.ActivityDetailService;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.vo.ActivityDetailVO;
import com.snuh.smile.vo.AuthVO;
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
import java.util.List;

import static com.snuh.smile.util.ResponseUtil.responseOKEntity;

@Controller
@RequestMapping("/activityDetails")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ActivityDetailController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private final AuthService authService;

    @NonNull
    private final ActivityDetailService activityDetailService;

    @GetMapping("/")
    public ModelAndView goActivityDetail(@PageableDefault(size = 10) Pageable pageable) {

        logger.info("ActivityDetailController goActivityDetail start");


        ModelAndView mv = new ModelAndView();
        mv.setViewName("activityDetail");

        Page<ActivityDetails.ActivityDetail> activityDetailList = activityDetailService.getPagelist(pageable);


        mv.addObject("activityDetailVOList", activityDetailList );
        mv.addObject("pages", activityDetailList );
        mv.addObject("maxPage", 10 );

        logger.info("ActivityDetailController goActivityDetail end");

        return mv;
    }

    @GetMapping("/search")
    public ModelAndView searchActivityDetail(@PageableDefault(size = 10) Pageable pageable, @RequestParam String token) {

        logger.info("ActivityDetailController searchActivityDetail start");

        logger.info("Search :: " + token);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("activityDetail");

        Page<ActivityDetails.ActivityDetail> activityDetailList = activityDetailService.getSearchPagelist(token, pageable);


        mv.addObject("activityDetailVOList", activityDetailList );
        mv.addObject("pages", activityDetailList );
        mv.addObject("maxPage", 10 );

        logger.info("ActivityDetailController searchActivityDetail end");

        return mv;
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public void postActivityDetails(@RequestBody ActivityDetails activityDetail){


        logger.info("ActivityDetailController postActivityDetails start");

        responseOKEntity();
        logger.info("ActivityDetailController Response send : 200");

        logger.info("ActivityDetail : {}", activityDetail.toString());

        for (ActivityDetails.ActivityDetail activityDetails : activityDetail.getActivityDetails()
             ) {
            activityDetailService.saveDetail(activityDetails);
        }

        logger.info("ActivityDetailController postActivityDetails end");
    }


    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("ActivityDetailController excel start");

        Auth getAuth = authService.findByEmail(auth.getEmail());

        if(getAuth == null){
            response.sendRedirect("/activityDetails/");
            return;
        }

        logger.info("Excel Email :: " + auth.getEmail());


        List<ActivityDetails.ActivityDetail> activityDetailList = activityDetailService.getActivityDetailTokenList(getAuth.getUserAccessToken());

        if(activityDetailList == null) {
            response.sendRedirect("/activityDetails/");
            return;
        }

        int rowNo = 0;


        //XSSFWorkbook xworkbook = new XSSFWorkbook();

        //XSSFSheet xsheet = xworkbook.createSheet("activityDetail");		//시트 생성

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxssfWorkbook.createSheet("activityDetail");

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
       // headerRow.createCell(32).setCellValue("samples");
       // headerRow.createCell(33).setCellValue("laps");


        for (ActivityDetails.ActivityDetail activityDetail : activityDetailList) {
            //XSSFRow row = xsheet.createRow(rowNo++);

            SXSSFRow row = sheet.createRow(rowNo++);

 /*
            ObjectMapper sampleMapper = new ObjectMapper();
            String sampleJson = sampleMapper.writeValueAsString(activityDetail.getSamples());

            ObjectMapper lapMapper = new ObjectMapper();
            String lapJson = lapMapper.writeValueAsString(activityDetail.getLaps());
*/
            row.createCell(0).setCellValue(activityDetail.getId());
            row.createCell(1).setCellValue(activityDetail.getUserId());
            row.createCell(2).setCellValue(activityDetail.getUserAccessToken());
            row.createCell(3).setCellValue(activityDetail.getSummaryId());
            row.createCell(4).setCellValue(activityDetail.getActivityId());
            row.createCell(5).setCellValue(activityDetail.getSummary().getStartTimeInSeconds());
            row.createCell(6).setCellValue(activityDetail.getSummary().getStartTimeOffsetInSeconds());
            row.createCell(7).setCellValue(activityDetail.getSummary().getActivityType());
            row.createCell(8).setCellValue(activityDetail.getSummary().getDurationInSeconds());
            row.createCell(9).setCellValue(activityDetail.getSummary().getAverageBikeCadenceInRoundsPerMinute());
            row.createCell(10).setCellValue(activityDetail.getSummary().getAverageHeartRateInBeatsPerMinute());
            row.createCell(11).setCellValue(activityDetail.getSummary().getAverageRunCadenceInStepsPerMinute());
            row.createCell(12).setCellValue(activityDetail.getSummary().getAverageSpeedInMetersPerSecond());
            row.createCell(13).setCellValue(activityDetail.getSummary().getAverageSwimCadenceInStrokesPerMinute());
            row.createCell(14).setCellValue(activityDetail.getSummary().getAveragePaceInMinutesPerKilometer());
            row.createCell(15).setCellValue(activityDetail.getSummary().getActiveKilocalories());
            row.createCell(16).setCellValue(activityDetail.getSummary().getDeviceName());
            row.createCell(17).setCellValue(activityDetail.getSummary().getDistanceInMeters());
            row.createCell(18).setCellValue(activityDetail.getSummary().getMaxBikeCadenceInRoundsPerMinute());
            row.createCell(19).setCellValue(activityDetail.getSummary().getMaxHeartRateInBeatsPerMinute());
            row.createCell(20).setCellValue(activityDetail.getSummary().getMaxPaceInMinutesPerKilometer());
            row.createCell(21).setCellValue(activityDetail.getSummary().getMaxRunCadenceInStepsPerMinute());
            row.createCell(22).setCellValue(activityDetail.getSummary().getMaxSpeedInMetersPerSecond());
            row.createCell(23).setCellValue(activityDetail.getSummary().getNumberOfActiveLengths());
            row.createCell(24).setCellValue(activityDetail.getSummary().getStartingLatitudeInDegree());
            row.createCell(25).setCellValue(activityDetail.getSummary().getStartingLongitudeInDegree());
            row.createCell(26).setCellValue(activityDetail.getSummary().getSteps());
            row.createCell(27).setCellValue(activityDetail.getSummary().getTotalElevationGainInMeters());
            row.createCell(28).setCellValue(activityDetail.getSummary().getTotalElevationLossInMeters());
            row.createCell(29).setCellValue(activityDetail.getSummary().getIsParent());
            row.createCell(30).setCellValue(activityDetail.getSummary().getParentSummaryId());
            row.createCell(31).setCellValue(activityDetail.getSummary().getManual());
           // row.createCell(32).setCellValue(sampleJson);
           // row.createCell(33).setCellValue(lapJson);

        }

        int rowNo2 = 0;
       // XSSFSheet xsheet2 = xworkbook.createSheet("samples");		//시트 생성

        SXSSFSheet xsheet2 = sxssfWorkbook.createSheet("samples");		//시트 생성


        //Title
        //XSSFRow headerRow2 = xsheet2.createRow(rowNo2++);
        SXSSFRow headerRow2 = xsheet2.createRow(rowNo2++);


        headerRow2.createCell(0).setCellValue("fk_id");
        headerRow2.createCell(1).setCellValue("startTimeInSeconds");
        headerRow2.createCell(2).setCellValue("latitudeInDegree");
        headerRow2.createCell(3).setCellValue("longitudeInDegree");
        headerRow2.createCell(4).setCellValue("elevationInMeters");
        headerRow2.createCell(5).setCellValue("airTemperatureCelcius");
        headerRow2.createCell(6).setCellValue("heartrate");
        headerRow2.createCell(7).setCellValue("speedMetersPerSecond");
        headerRow2.createCell(8).setCellValue("stepsPerMinute");
        headerRow2.createCell(9).setCellValue("totalDistanceInMeters");
        headerRow2.createCell(10).setCellValue("timerDurationInSeconds");
        headerRow2.createCell(11).setCellValue("clockDurationInSeconds");
        headerRow2.createCell(12).setCellValue("movingDurationInSeconds");
        headerRow2.createCell(13).setCellValue("powerInWatts");
        headerRow2.createCell(14).setCellValue("bikeCadenceInRPM");
        headerRow2.createCell(15).setCellValue("swimCadenceInStrokesPerMinute");
        headerRow2.createCell(16).setCellValue("onDemand");





        for (ActivityDetails.ActivityDetail activityDetail : activityDetailList) {
            for (ActivityDetails.Samples sample : activityDetail.getSamples()) {
                //XSSFRow row = xsheet2.createRow(rowNo2++);
                SXSSFRow row = xsheet2.createRow(rowNo2++);

                row.createCell(0).setCellValue(activityDetail.getId());
                row.createCell(1).setCellValue(sample.getStartTimeInSeconds());
                row.createCell(2).setCellValue(sample.getLatitudeInDegree());
                row.createCell(3).setCellValue(sample.getLongitudeInDegree());
                row.createCell(4).setCellValue(sample.getElevationInMeters());
                row.createCell(5).setCellValue(sample.getAirTemperatureCelcius());
                row.createCell(6).setCellValue(sample.getHeartrate());
                row.createCell(7).setCellValue(sample.getSpeedMetersPerSecond());
                row.createCell(8).setCellValue(sample.getStepsPerMinute());
                row.createCell(9).setCellValue(sample.getTotalDistanceInMeters());
                row.createCell(10).setCellValue(sample.getTimerDurationInSeconds());
                row.createCell(11).setCellValue(sample.getClockDurationInSeconds());
                row.createCell(12).setCellValue(sample.getMovingDurationInSeconds());
                row.createCell(13).setCellValue(sample.getPowerInWatts());
                row.createCell(14).setCellValue(sample.getBikeCadenceInRPM());
                row.createCell(15).setCellValue(sample.getSwimCadenceInStrokesPerMinute());
                row.createCell(16).setCellValue(sample.getOnDemand());
            }

        }

        int rowNo3 = 0;
        //XSSFSheet xsheet3 = xworkbook.createSheet("laps");		//시트 생성
        SXSSFSheet xsheet3 = sxssfWorkbook.createSheet("laps");		//시트 생성


        //Title
        //XSSFRow headerRow3 = xsheet3.createRow(rowNo3++);
        SXSSFRow headerRow3 = xsheet3.createRow(rowNo3++);


        headerRow3.createCell(0).setCellValue("fk_id");
        headerRow3.createCell(1).setCellValue("startTimeInSeconds");


        for (ActivityDetails.ActivityDetail activityDetail : activityDetailList) {
            for (ActivityDetails.Laps laps : activityDetail.getLaps()) {
                //XSSFRow row = xsheet3.createRow(rowNo3++);
                SXSSFRow row = xsheet3.createRow(rowNo3++);

                row.createCell(0).setCellValue(activityDetail.getId());
                row.createCell(1).setCellValue(laps.getStartTimeInSeconds());
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
            response.setHeader("Content-Disposition", "attachment;filename=activityDetail"+ timeName +".xlsx");

            // Excel File Output
            //xworkbook.write(response.getOutputStream());
            //xworkbook.close();

            OutputStream fileOut = response.getOutputStream();
            sxssfWorkbook.write(fileOut);
            fileOut.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.info("Export Excel SUCCESS");

            logger.info("ActivityDetailController excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
