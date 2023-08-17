package com.snuh.smile.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.snuh.smile.domain.*;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.BackfillRequestHeader;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.DailyService;
import com.snuh.smile.service.HealthSnapshotService;
import com.snuh.smile.service.OauthService;
import jdk.nashorn.internal.parser.JSONParser;
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
@RequestMapping("/healthSnapshot")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class HealthSnapshotController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${health.snapshot.url}")
    private String backfillURL;

    @NonNull
    private final AuthService authService;
    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final HealthSnapshotService healthSnapshotService;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView goHealthSnapshot(@PageableDefault(size = 10) Pageable pageable){

        logger.info("HealthSnapshotController goHealthSnapshot start");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("healthSnapshot");

        Page<HealthSnapshots.HealthSnapshot> healthSnapshotList = healthSnapshotService.getPagelist(pageable);


        mv.addObject("healthSnapshotList", healthSnapshotList );
        mv.addObject("pages", healthSnapshotList );
        mv.addObject("maxPage", 10 );

        logger.info("HealthSnapshotController goHealthSnapshot end");

        return mv;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView searchHealthSnapshot(@PageableDefault(size = 10) Pageable pageable, @RequestParam String token){

        logger.info("HealthSnapshotController searchHealthSnapshot start");

        logger.info("Search :: " + token);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("healthSnapshot");

        Page<HealthSnapshots.HealthSnapshot> healthSnapshotList = healthSnapshotService.getSearchPagelist(token, pageable);


        mv.addObject("healthSnapshotList", healthSnapshotList );
        mv.addObject("pages", healthSnapshotList );
        mv.addObject("maxPage", 10 );

        logger.info("HealthSnapshotController searchHealthSnapshot end");

        return mv;
    }


    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public void postHealthSnapshot(@RequestBody HealthSnapshots healthSnapshots){


        logger.info("HealthSnapshotController postHealthSnapshot start");

        responseOKEntity();
        logger.info("HealthSnapshotController Response send : 200");

        logger.info("Health Snapshots : {}", healthSnapshots.toString());

        for (HealthSnapshots.HealthSnapshot healthSnapshot : healthSnapshots.getHealthSnapshot()
        ) {
            healthSnapshotService.saveHealthSnapshot(healthSnapshot);
        }

        logger.info("HealthSnapshotController postHealthSnapshot end");
    }




    /*
    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity postHealthSnapshot(@RequestBody String jsonInfo){


        logger.info("HealthSnapshotController postHealthSnapshot start");

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@ json = " + jsonInfo);

        logger.info("HealthSnapshotController postHealthSnapshot end");

        return new ResponseEntity(HttpStatus.OK);
    }

     */


    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("HealthSnapshotController excel start");

        Auth getAuth = authService.findByEmail(auth.getEmail());

        if(getAuth == null) {
            response.sendRedirect("/healthSnapshot/");
            return;
        }

        logger.info("Excel Email :: " + auth.getEmail());

        List<HealthSnapshots.HealthSnapshot> healthSnapshotList = healthSnapshotService.getHealthSnapTokenshot(getAuth.getUserAccessToken());

        if(healthSnapshotList == null) {
            response.sendRedirect("/healthSnapshot/");
            return;

        }

        int rowNo = 0;


        //XSSFWorkbook xworkbook = new XSSFWorkbook();

        //XSSFSheet xsheet = xworkbook.createSheet("healthSnapshot");		//시트 생성

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxssfWorkbook.createSheet("healthSnapshot");

        //Title
        //XSSFRow headerRow = xsheet.createRow(rowNo++);
        SXSSFRow headerRow = sheet.createRow(rowNo++);


        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("userId");
        headerRow.createCell(2).setCellValue("userAccessToken");
        headerRow.createCell(3).setCellValue("summaryId");
        headerRow.createCell(4).setCellValue("calendarDate");
        headerRow.createCell(5).setCellValue("startTimeInSeconds");
        headerRow.createCell(6).setCellValue("durationInSeconds");
        headerRow.createCell(7).setCellValue("startTimeOffsetInSeconds");
        //headerRow.createCell(8).setCellValue("summaries");


        for (HealthSnapshots.HealthSnapshot healthSnapshot : healthSnapshotList) {

            /*
            ObjectMapper sampleMapper = new ObjectMapper();
            String summaryJson = sampleMapper.writeValueAsString(healthSnapshot.getSummaries());
*/
            //XSSFRow row = xsheet.createRow(rowNo++);
            SXSSFRow row = sheet.createRow(rowNo++);


            row.createCell(0).setCellValue(healthSnapshot.getId());
            row.createCell(1).setCellValue(healthSnapshot.getUserId());
            row.createCell(2).setCellValue(healthSnapshot.getUserAccessToken());
            row.createCell(3).setCellValue(healthSnapshot.getSummaryId());
            row.createCell(4).setCellValue(healthSnapshot.getCalendarDate());
            row.createCell(5).setCellValue(healthSnapshot.getStartTimeInSeconds());
            row.createCell(6).setCellValue(healthSnapshot.getDurationInSeconds());
            row.createCell(7).setCellValue(healthSnapshot.getStartTimeOffsetInSeconds());
            //row.createCell(3).setCellValue(summaryJson);

        }



        int rowNo2 = 0;

        //XSSFSheet xsheet2 = xworkbook.createSheet("summary");		//시트 생성
        SXSSFSheet sheet2 = sxssfWorkbook.createSheet("summary");		//시트 생성


        //Title
        //XSSFRow headerRow2 = xsheet2.createRow(rowNo2++);
        SXSSFRow headerRow2 = sheet2.createRow(rowNo2++);

        headerRow2.createCell(0).setCellValue("id");
        headerRow2.createCell(1).setCellValue("fk_id");
        headerRow2.createCell(2).setCellValue("summaryType");
        headerRow2.createCell(3).setCellValue("minValue");
        headerRow2.createCell(4).setCellValue("maxValue");
        headerRow2.createCell(5).setCellValue("avgValue");
    //    headerRow2.createCell(5).setCellValue("epochSummaries");

        for (HealthSnapshots.HealthSnapshot healthSnapshot : healthSnapshotList) {

            for (HealthSnapshots.SnapshotSummary summary : healthSnapshot.getSummaries()
                    ) {

                //XSSFRow row = xsheet2.createRow(rowNo2++);
                SXSSFRow row = sheet2.createRow(rowNo2++);


                //    ObjectMapper epochMapper = new ObjectMapper();
            //    String epochJson = epochMapper.writeValueAsString(summary.getEpochSummaries());
                row.createCell(0).setCellValue(summary.getId());
                row.createCell(1).setCellValue(healthSnapshot.getId());
                row.createCell(2).setCellValue(summary.getSummaryType());
                row.createCell(3).setCellValue(summary.getMinValue());
                row.createCell(4).setCellValue(summary.getMaxValue());
                row.createCell(5).setCellValue(summary.getAvgValue());
      //          row.createCell(5).setCellValue(epochJson);
            }

        }

        int rowNo3 = 0;

        //XSSFSheet xsheet3 = xworkbook.createSheet("health_snapshot_summary_epoch");		//시트 생성

        SXSSFSheet sheet3 = sxssfWorkbook.createSheet("health_snapshot_summary_epoch");		//시트 생성


        //Title
        //XSSFRow headerRow3 = xsheet3.createRow(rowNo3++);
        SXSSFRow headerRow3 = sheet3.createRow(rowNo3++);


        headerRow3.createCell(0).setCellValue("fk_id");
        headerRow3.createCell(1).setCellValue("key");
        headerRow3.createCell(2).setCellValue("value");


        for (HealthSnapshots.HealthSnapshot healthSnapshot : healthSnapshotList) {

            for (HealthSnapshots.SnapshotSummary summary : healthSnapshot.getSummaries()
            ) {
                Map<String, String> epochMap = summary.getEpochSummaries();

                Iterator<String> keys = epochMap.keySet().iterator();

                while (keys.hasNext()) {
                    String key = keys.next();

                    //XSSFRow row = xsheet3.createRow(rowNo3++);
                    SXSSFRow row = sheet3.createRow(rowNo3++);
                    row.createCell(0).setCellValue(summary.getId());
                    row.createCell(1).setCellValue(key);
                    row.createCell(2).setCellValue(epochMap.get(key));
                }
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
            response.setHeader("Content-Disposition", "attachment;filename=healthSnapshot"+ timeName +".xlsx");

            // Excel File Output
            //xworkbook.write(response.getOutputStream());
            //xworkbook.close();

            OutputStream fileOut = response.getOutputStream();
            sxssfWorkbook.write(fileOut);
            fileOut.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.info("Export Excel SUCCESS");

            logger.info("HealthSnapshotController excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
