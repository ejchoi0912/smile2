package com.snuh.smile.controller;

import com.snuh.smile.domain.*;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.BackfillRequestHeader;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.DailyService;
import com.snuh.smile.service.EpochService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.snuh.smile.util.ResponseUtil.responseOKEntity;

@Controller
@RequestMapping("/epoch")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class EpochController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${health.epoch.url}")
    private String backfillURL;

    @NonNull
    private final AuthService authService;
    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final EpochService epochService;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView getEpoch(@PageableDefault(size = 10) Pageable pageable){

        logger.info("EpochController getEpoch start");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("epoch");

        Page<Epochs.Epoch> epochList = epochService.getPagelist(pageable);


        mv.addObject("epochList", epochList );
        mv.addObject("pages", epochList );
        mv.addObject("maxPage", 10 );

        logger.info("EpochController getEpoch end");

        return mv;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView searchEpoch(@PageableDefault(size = 10) Pageable pageable, @RequestParam String token){

        logger.info("EpochController searchEpoch start");

        logger.info("Search :: " + token);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("epoch");

        Page<Epochs.Epoch> epochList = epochService.getSearchPagelist(token, pageable);


        mv.addObject("epochList", epochList );
        mv.addObject("pages", epochList );
        mv.addObject("maxPage", 10 );

        logger.info("EpochController searchEpoch end");

        return mv;
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity postEpoch(@RequestBody Epochs epochs){


        logger.info("EpochController postEpoch start");


        responseOKEntity();
        logger.info("EpochController Response send : 200");

        logger.info("Epoch : {}", epochs.toString());

        for (Epochs.Epoch epoch : epochs.getEpochs()
        ) {

            epochService.save(epoch);

        }

        logger.info("EpochController postEpoch end");

        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("EpochController excel start");

        Auth getAuth = authService.findByEmail(auth.getEmail());

        if(getAuth == null) {
            response.sendRedirect("/epoch/");
            return;
        }

        logger.info("Excel Email :: " + auth.getEmail());

        List<Epochs.Epoch> epochList = epochService.getEpochTokenList(getAuth.getUserAccessToken());

        if(epochList == null) {
            response.sendRedirect("/epoch/");
            return;
        }

        int rowNo = 0;


        //XSSFWorkbook xworkbook = new XSSFWorkbook();

        //XSSFSheet xsheet = xworkbook.createSheet("epoch");		//시트 생성

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxssfWorkbook.createSheet("epoch");

        //Title
        //XSSFRow headerRow = xsheet.createRow(rowNo++);
        SXSSFRow headerRow = sheet.createRow(rowNo++);


        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("userId");
        headerRow.createCell(2).setCellValue("userAccessToken");
        headerRow.createCell(3).setCellValue("summaryId");
        headerRow.createCell(4).setCellValue("startTimeInSeconds");
        headerRow.createCell(5).setCellValue("startTimeOffsetInSeconds");
        headerRow.createCell(6).setCellValue("activityType");
        headerRow.createCell(7).setCellValue("durationInSeconds");
        headerRow.createCell(8).setCellValue("activeTimeInSeconds");
        headerRow.createCell(9).setCellValue("Steps");
        headerRow.createCell(10).setCellValue("distanceInMeters");
        headerRow.createCell(11).setCellValue("activeKilocalories");
        headerRow.createCell(12).setCellValue("Met");
        headerRow.createCell(13).setCellValue("intensity");
        headerRow.createCell(14).setCellValue("meanMotionIntensity");
        headerRow.createCell(15).setCellValue("maxMotionIntensity");


        for (Epochs.Epoch epoch : epochList) {
            //XSSFRow row = xsheet.createRow(rowNo++);
            SXSSFRow row = sheet.createRow(rowNo++);

            row.createCell(0).setCellValue(epoch.getId());
            row.createCell(1).setCellValue(epoch.getUserId());
            row.createCell(2).setCellValue(epoch.getUserAccessToken());
            row.createCell(3).setCellValue(epoch.getSummaryId());
            row.createCell(4).setCellValue(epoch.getStartTimeInSeconds());
            row.createCell(5).setCellValue(epoch.getStartTimeOffsetInSeconds());
            row.createCell(6).setCellValue(epoch.getActivityType());
            row.createCell(7).setCellValue(epoch.getDurationInSeconds());
            row.createCell(8).setCellValue(epoch.getActiveTimeInSeconds());
            row.createCell(9).setCellValue(epoch.getSteps());
            row.createCell(10).setCellValue(epoch.getDistanceInMeters());
            row.createCell(11).setCellValue(epoch.getActiveKilocalories());
            row.createCell(12).setCellValue(epoch.getMet());
            row.createCell(13).setCellValue(epoch.getIntensity());
            row.createCell(14).setCellValue(epoch.getMeanMotionIntensity());
            row.createCell(15).setCellValue(epoch.getMaxMotionIntensity());

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
            response.setHeader("Content-Disposition", "attachment;filename=epoch"+ timeName +".xlsx");

            // Excel File Output
            //xworkbook.write(response.getOutputStream());
            //xworkbook.close();

            OutputStream fileOut = response.getOutputStream();
            sxssfWorkbook.write(fileOut);
            fileOut.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.info("Export Excel SUCCESS");

            logger.info("EpochController excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
