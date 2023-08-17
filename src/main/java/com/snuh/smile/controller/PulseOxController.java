package com.snuh.smile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snuh.smile.domain.*;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.BackfillRequestHeader;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.OauthService;
import com.snuh.smile.service.PulseOxService;
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
@RequestMapping("/pulseOx")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PulseOxController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${health.pulse.ox.url}")
    private String backfillURL;

    @NonNull
    private final AuthService authService;
    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final PulseOxService pulseOxService;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView goPulseOx(@PageableDefault(size = 10) Pageable pageable){

        logger.info("PulseOxController goPulseOx start");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("pulseOx");

        Page<PulseOxs.PulseOx> pulseOxList = pulseOxService.getPagelist(pageable);

        mv.addObject("pulseOxList", pulseOxList );
        mv.addObject("pages", pulseOxList );
        mv.addObject("maxPage", 10 );

        logger.info("PulseOxController goPulseOx end");

        return mv;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView searchPulseOx(@PageableDefault(size = 10) Pageable pageable, @RequestParam String token){

        logger.info("PulseOxController searchPulseOx start");

        logger.info("Search :: " + token);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("pulseOx");

        Page<PulseOxs.PulseOx> pulseOxList = pulseOxService.getSearchPagelist(token, pageable);

        mv.addObject("pulseOxList", pulseOxList );
        mv.addObject("pages", pulseOxList );
        mv.addObject("maxPage", 10 );

        logger.info("PulseOxController searchPulseOx end");

        return mv;
    }


    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public void postPulseOx(@RequestBody PulseOxs pulseOxs){


        logger.info("PulseOxController postPulseOx start");

        responseOKEntity();
        logger.info("PulseOxController Response send : 200");

        logger.info("PulseOxes : {}", pulseOxs.toString());

        for (PulseOxs.PulseOx pulseOx : pulseOxs.getPulseox()
        ) {
            pulseOxService.save(pulseOx);
        }

        logger.info("PulseOxController postPulseOx end");
    }


    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("PulseOxController excel start");

        Auth getAuth = authService.findByEmail(auth.getEmail());

        if(getAuth == null) {
            response.sendRedirect("/pulseOx/");
            return;
        }

        logger.info("Excel Email :: " + auth.getEmail());

        List<PulseOxs.PulseOx> pulseOxList = pulseOxService.getPulseOxTokenList(getAuth.getUserAccessToken());

        if(pulseOxList == null) {
            response.sendRedirect("/pulseOx/");
            return;
        }



        int rowNo = 0;


        //XSSFWorkbook xworkbook = new XSSFWorkbook();

        //XSSFSheet xsheet = xworkbook.createSheet("pulseox");		//시트 생성

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
        headerRow.createCell(7).setCellValue("durationInSeconds");
      //  headerRow.createCell(8).setCellValue("timeOffsetSpo2Values");
        headerRow.createCell(8).setCellValue("onDemand");


        for (PulseOxs.PulseOx pulseOx : pulseOxList) {
            //XSSFRow row = xsheet.createRow(rowNo++);
            SXSSFRow row = sheet.createRow(rowNo++);


            //    ObjectMapper sampleMapper = new ObjectMapper();
        //    String timeJson = sampleMapper.writeValueAsString(pulseOx.getTimeOffsetSpo2Values());

            row.createCell(0).setCellValue(pulseOx.getId());
            row.createCell(1).setCellValue(pulseOx.getUserId());
            row.createCell(2).setCellValue(pulseOx.getUserAccessToken());
            row.createCell(3).setCellValue(pulseOx.getSummaryId());
            row.createCell(4).setCellValue(pulseOx.getCalendarDate());
            row.createCell(5).setCellValue(pulseOx.getStartTimeInSeconds());
            row.createCell(6).setCellValue(pulseOx.getStartTimeOffsetInSeconds());
            row.createCell(7).setCellValue(pulseOx.getDurationInSeconds());
        //    row.createCell(8).setCellValue(timeJson);
            row.createCell(8).setCellValue(pulseOx.getOnDemand());
        }



        int rowNo2 = 0;

        //XSSFSheet xsheet2 = xworkbook.createSheet("time_offset_spo2_values");		//시트 생성
        SXSSFSheet sheet2 = sxssfWorkbook.createSheet("time_offset_spo2_values");		//시트 생성


        //Title
        //XSSFRow headerRow2 = xsheet2.createRow(rowNo2++);
        SXSSFRow headerRow2 = sheet2.createRow(rowNo2++);


        headerRow2.createCell(0).setCellValue("fk_id");
        headerRow2.createCell(1).setCellValue("key");
        headerRow2.createCell(2).setCellValue("value");


        for (PulseOxs.PulseOx pulseOx : pulseOxList) {
            Map<String, String> timeMap = pulseOx.getTimeOffsetSpo2Values();

            Iterator<String> keys = timeMap.keySet().iterator();

            while (keys.hasNext()) {
                String key = keys.next();

                //XSSFRow row = xsheet2.createRow(rowNo2++);
                SXSSFRow row = sheet2.createRow(rowNo2++);

                row.createCell(0).setCellValue(pulseOx.getId());
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
            response.setHeader("Content-Disposition", "attachment;filename=pulseox"+ timeName +".xlsx");

            // Excel File Output
            //xworkbook.write(response.getOutputStream());
            //xworkbook.close();

            OutputStream fileOut = response.getOutputStream();
            sxssfWorkbook.write(fileOut);
            fileOut.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.info("Export Excel SUCCESS");

            logger.info("PulseOxController excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
