package com.snuh.smile.controller;


import com.snuh.smile.domain.*;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.BackfillRequestHeader;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.BodyCompositionService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.snuh.smile.util.ResponseUtil.responseOKEntity;

@Controller
@RequestMapping("/bodyComps")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BodyCompositionContoller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${health.bodycomposition.url}")
    private String backfillURL;

    @NonNull
    private final AuthService authService;
    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final BodyCompositionService bodyCompositionService;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView goBodyComposition(@PageableDefault(size = 10) Pageable pageable){

        logger.info("BodyCompositionContoller goBodyComposition start");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("bodyComposition");

        Page<BodyCompositions.BodyComposition> bodyCompositionList = bodyCompositionService.getPagelist(pageable);


        mv.addObject("bodyCompositionList", bodyCompositionList );
        mv.addObject("pages", bodyCompositionList );
        mv.addObject("maxPage", 10 );

        logger.info("BodyCompositionContoller goBodyComposition end");

        return mv;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView searchBodyComposition(@PageableDefault(size = 10) Pageable pageable, @RequestParam String token){

        logger.info("BodyCompositionContoller searchBodyComposition start");

        logger.info("Search :: " + token);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("bodyComposition");

        Page<BodyCompositions.BodyComposition> bodyCompositionList = bodyCompositionService.getSearchPagelist(token, pageable);


        mv.addObject("bodyCompositionList", bodyCompositionList );
        mv.addObject("pages", bodyCompositionList );
        mv.addObject("maxPage", 10 );

        logger.info("BodyCompositionContoller searchBodyComposition end");

        return mv;
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public void postBodyComps(@RequestBody BodyCompositions bodyCompositions){


        logger.info("BodyCompositionContoller postBodyComps start");

        responseOKEntity();
        logger.info("BodyCompositionContoller Response send : 200");

        logger.info("Body Compositions : {}", bodyCompositions.toString());

        for (BodyCompositions.BodyComposition bodyComposition : bodyCompositions.getBodyComps()
        ) {
            bodyCompositionService.save(bodyComposition);
        }

        logger.info("BodyCompositionContoller postBodyComps end");

    }



    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("ActivityController excel start");


        Auth getAuth = authService.findByEmail(auth.getEmail());

        if(getAuth == null) {
            response.sendRedirect("/bodyComps/");
            return;
        }

        logger.info("Excel Email :: " + auth.getEmail());

        List<BodyCompositions.BodyComposition> bodyCompositionList = bodyCompositionService.getBodyCompositionTokenList(getAuth.getUserAccessToken());


        if(bodyCompositionList == null) {
            response.sendRedirect("/bodyComps/");
            return;
        }

        int rowNo = 0;


        //XSSFWorkbook xworkbook = new XSSFWorkbook();

        //XSSFSheet xsheet = xworkbook.createSheet("bodyComps");		//시트 생성

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        SXSSFSheet sheet = sxssfWorkbook.createSheet("bodyComps");

        //Title
        //XSSFRow headerRow = xsheet.createRow(rowNo++);

        SXSSFRow headerRow = sheet.createRow(rowNo++);


        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("userId");
        headerRow.createCell(2).setCellValue("userAccessToken");
        headerRow.createCell(3).setCellValue("summaryId");
        headerRow.createCell(4).setCellValue("measurementTimeInSeconds");
        headerRow.createCell(5).setCellValue("measurementTimeOffsetInSeconds");
        headerRow.createCell(6).setCellValue("muscleMassInGrams");
        headerRow.createCell(7).setCellValue("boneMassInGrams");
        headerRow.createCell(8).setCellValue("bodyWaterInPercent");
        headerRow.createCell(9).setCellValue("bodyFatInPercent");
        headerRow.createCell(10).setCellValue("bodyMassIndex");
        headerRow.createCell(11).setCellValue("weightInGrams");

        for (BodyCompositions.BodyComposition bodyComposition : bodyCompositionList) {
            //XSSFRow row = xsheet.createRow(rowNo++);
            SXSSFRow row = sheet.createRow(rowNo++);

            row.createCell(0).setCellValue(bodyComposition.getId());
            row.createCell(1).setCellValue(bodyComposition.getUserId());
            row.createCell(2).setCellValue(bodyComposition.getUserAccessToken());
            row.createCell(3).setCellValue(bodyComposition.getSummaryId());
            row.createCell(4).setCellValue(bodyComposition.getMeasurementTimeInSeconds());
            row.createCell(5).setCellValue(bodyComposition.getMeasurementTimeOffsetInSeconds());
            row.createCell(6).setCellValue(bodyComposition.getMuscleMassInGrams());
            row.createCell(7).setCellValue(bodyComposition.getBoneMassInGrams());
            row.createCell(8).setCellValue(bodyComposition.getBodyWaterInPercent());
            row.createCell(9).setCellValue(bodyComposition.getBodyFatInPercent());
            row.createCell(10).setCellValue(bodyComposition.getBodyMassIndex());
            row.createCell(11).setCellValue(bodyComposition.getWeightInGrams());

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
            response.setHeader("Content-Disposition", "attachment;filename=bodyComps"+ timeName +".xlsx");

            // Excel File Output
            //xworkbook.write(response.getOutputStream());
            //xworkbook.close();

            OutputStream fileOut = response.getOutputStream();
            sxssfWorkbook.write(fileOut);
            fileOut.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.info("Export Excel SUCCESS");

            logger.info("BodyCompositionContoller excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
