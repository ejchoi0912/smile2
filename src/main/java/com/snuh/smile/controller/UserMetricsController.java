package com.snuh.smile.controller;

import com.snuh.smile.domain.*;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.BackfillRequestHeader;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.DailyService;
import com.snuh.smile.service.OauthService;
import com.snuh.smile.service.UserMetricsService;
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
@RequestMapping("/userMetrics")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserMetricsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${health.user.metriecs.url}")
    private String backfillURL;

    @NonNull
    private final AuthService authService;
    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final UserMetricsService userMetricsService;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView goUserMetrics(@PageableDefault(size = 10) Pageable pageable){
        logger.info("UserMetricsController goUserMetrics start");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("userMetrics");

        Page<UserMetrics.UserMetric> userMetricList = userMetricsService.getPagelist(pageable);

        mv.addObject("userMetricList", userMetricList );
        mv.addObject("pages", userMetricList );
        mv.addObject("maxPage", 10 );

        logger.info("UserMetricsController goUserMetrics end");
        return mv;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView searchUserMetrics(@PageableDefault(size = 10) Pageable pageable,  @RequestParam String token){
        logger.info("UserMetricsController searchUserMetrics start");

        logger.info("Search :: " + token);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("userMetrics");

        Page<UserMetrics.UserMetric> userMetricList = userMetricsService.getSearchPagelist(token, pageable);

        mv.addObject("userMetricList", userMetricList );
        mv.addObject("pages", userMetricList );
        mv.addObject("maxPage", 10 );

        logger.info("UserMetricsController searchUserMetrics end");
        return mv;
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public void postUserMetrics(@RequestBody UserMetrics userMetrics){


        logger.info("UserMetricsController postUserMetrics start");

        responseOKEntity();
        logger.info("UserMetricsController Response send : 200");

        logger.info("User Metrics : {}", userMetrics.toString());

        for (UserMetrics.UserMetric userMetric : userMetrics.getUserMetrics()
        ) {
            userMetricsService.save(userMetric);
        }

        logger.info("UserMetricsController postUserMetrics end");
    }


    @RequestMapping(value = "/excel", method = {RequestMethod.POST})
    public void excel(HttpServletResponse response, Auth auth) throws IOException {

        logger.info("UserMetricsController excel start");

        Auth getAuth = authService.findByEmail(auth.getEmail());

        if(getAuth == null) {
            response.sendRedirect("/userMetrics/");
            return;
        }

        logger.info("Excel Email :: " + auth.getEmail());

        List<UserMetrics.UserMetric> userMetricList = userMetricsService.getUserMetricTokenList(getAuth.getUserAccessToken());

        if(userMetricList == null) {
            response.sendRedirect("/userMetrics/");
            return;
        }

        int rowNo = 0;


        //XSSFWorkbook xworkbook = new XSSFWorkbook();

        //XSSFSheet xsheet = xworkbook.createSheet("userMetrics");		//시트 생성

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
        headerRow.createCell(5).setCellValue("vo2Max");
        headerRow.createCell(6).setCellValue("enhanced");
        headerRow.createCell(7).setCellValue("fitnessAge");


        for (UserMetrics.UserMetric userMetric : userMetricList) {
            //XSSFRow row = xsheet.createRow(rowNo++);
            SXSSFRow row = sheet.createRow(rowNo++);

            row.createCell(0).setCellValue(userMetric.getId());
            row.createCell(1).setCellValue(userMetric.getUserId());
            row.createCell(2).setCellValue(userMetric.getUserAccessToken());
            row.createCell(3).setCellValue(userMetric.getSummaryId());
            row.createCell(4).setCellValue(userMetric.getCalendarDate());
            row.createCell(5).setCellValue(userMetric.getVo2Max());
            row.createCell(6).setCellValue(userMetric.getEnhanced());
            row.createCell(7).setCellValue(userMetric.getFitnessAge());

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
            response.setHeader("Content-Disposition", "attachment;filename=userMetrics"+ timeName +".xlsx");

            // Excel File Output
            //xworkbook.write(response.getOutputStream());
            //xworkbook.close();

            OutputStream fileOut = response.getOutputStream();
            sxssfWorkbook.write(fileOut);
            fileOut.close();

            response.getOutputStream().flush();
            response.getOutputStream().close();

            logger.info("Export Excel SUCCESS");

            logger.info("UserMetricsController excel end");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
