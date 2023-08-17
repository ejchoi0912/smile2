package com.snuh.smile.controller;


import com.snuh.smile.domain.Auth;
import com.snuh.smile.domain.Backfill;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.BackfillRequestHeader;
import com.snuh.smile.service.ActivityService;
import com.snuh.smile.service.AuthService;
import com.snuh.smile.service.OauthService;
import com.snuh.smile.vo.AuthVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/backfill")
@Slf4j
public class BackfillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${activity.url}")
    private String activityURL;

    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${activity.backfill.url}")
    private String activityBackfillURL;

    @Value("${activity.detail.backfill.url}")
    private String activityDetailBackfillURL;

    @Value("${health.daily.url}")
    private String DailyBackfillURL;

    @Value("${health.epoch.url}")
    private String EpochBackfillURL;

    @Value("${health.sleep.url}")
    private String SleepBackfillURL;

    @Value("${health.bodycomposition.url}")
    private String BodyCompsBackfillURL;

    @Value("${health.stress.details.url}")
    private String stressDetailBackfillURL;

    @Value("${health.user.metriecs.url}")
    private String userMetricsBackfillURL;

    @Value("${health.pulse.ox.url}")
    private String PulseOxBackfillURL;

    @Value("${health.respiration.url}")
    private String RespirationBackfillURL;

    @Value("${health.snapshot.url}")
    private String healthSnapshotBackfillURL;


    @NonNull
    private final AuthService authService;
    @NonNull
    private final OauthService oauthService;
    @NonNull
    private final ActivityService activityService;

    @GetMapping("/")
    public ModelAndView goBackfill() {

        logger.info("BackfillController goBackfill start");


        ModelAndView mv = new ModelAndView();
        mv.setViewName("backfill");

        logger.info("BackfillController goBackfill end");

        return mv;
    }

    @RequestMapping(value = "/activity", method = {RequestMethod.POST})
    @ResponseBody
    public String getActivityBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getActivityBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new BackfillRequestHeader(
                            this.activityBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            logger.info("### BackfillRequestHeader tcHeader :" + tcHeader.toString());

            final ResponseEntity<String> response =
                    this.oauthService.getBackfill(tcHeader);
            logger.info("Backfill Process: SUCCESS");

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getActivityBackfill end");
        return "success";
    }

    @RequestMapping(value = "/activityDetail", method = {RequestMethod.POST})
    @ResponseBody
    public String getActivityDetailBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getActivityDetailBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new BackfillRequestHeader(
                            this.activityDetailBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            logger.info("### BackfillRequestHeader tcHeader ####" + tcHeader.toString());

            final ResponseEntity<String> response =
                    this.oauthService.getBackfill(tcHeader);



            logger.info("Backfill Process: SUCCESS");

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getActivityDetailBackfill end");
        return "success";
    }

    @RequestMapping(value = "/daily", method = {RequestMethod.POST})
    @ResponseBody
    public String getDailyBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getDailyBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new BackfillRequestHeader(
                            this.DailyBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            logger.info("### BackfillRequestHeader tcHeader ####" + tcHeader.toString());

            final ResponseEntity<String> response =
                    this.oauthService.getBackfill(tcHeader);

            logger.info("Backfill Process: SUCCESS");

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getDailyBackfill end");
        return "success";
    }

    @RequestMapping(value = "/epoch", method = {RequestMethod.POST})
    @ResponseBody
    public String getEpochBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getEpochBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new BackfillRequestHeader(
                            this.EpochBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            logger.info("### BackfillRequestHeader tcHeader ####" + tcHeader.toString());

            final ResponseEntity<String> response =
                    this.oauthService.getBackfill(tcHeader);

            logger.info("Backfill Process: SUCCESS");

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getEpochBackfill end");
        return "success";
    }

    @RequestMapping(value = "/sleep", method = {RequestMethod.POST})
    @ResponseBody
    public String getSleepBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getSleepBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new BackfillRequestHeader(
                            this.SleepBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            logger.info("### BackfillRequestHeader tcHeader ####" + tcHeader.toString());

            final ResponseEntity<String> response =
                    this.oauthService.getBackfill(tcHeader);

            logger.info("Backfill Process: SUCCESS");

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getSleepBackfill end");
        return "success";
    }

    @RequestMapping(value = "/bodyComps", method = {RequestMethod.POST})
    @ResponseBody
    public String getBodyCompsBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getBodyCompsBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new BackfillRequestHeader(
                            this.BodyCompsBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            logger.info("### BackfillRequestHeader tcHeader ####" + tcHeader.toString());

            final ResponseEntity<String> response =
                    this.oauthService.getBackfill(tcHeader);

            logger.info("Backfill Process: SUCCESS");

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getBodyCompsBackfill end");
        return "success";
    }

    @RequestMapping(value = "/stressDetail", method = {RequestMethod.POST})
    @ResponseBody
    public String getStressDetailBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getStressDetailBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new BackfillRequestHeader(
                            this.stressDetailBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            logger.info("### BackfillRequestHeader tcHeader ####" + tcHeader.toString());

            final ResponseEntity<String> response =
                    this.oauthService.getBackfill(tcHeader);

            logger.info("Backfill Process: SUCCESS");

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getStressDetailBackfill end");
        return "success";
    }

    @RequestMapping(value = "/UserMetrics", method = {RequestMethod.POST})
    @ResponseBody
    public String getUserMetricsBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getUserMetricsBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new BackfillRequestHeader(
                            this.userMetricsBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            logger.info("### BackfillRequestHeader tcHeader ####" + tcHeader.toString());

            final ResponseEntity<String> response =
                    this.oauthService.getBackfill(tcHeader);

            logger.info("Backfill Process: SUCCESS");

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getUserMetricsBackfill end");
        return "success";
    }

    @RequestMapping(value = "/pulseOx", method = {RequestMethod.POST})
    @ResponseBody
    public String getPulseOxBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getPulseOxBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new BackfillRequestHeader(
                            this.PulseOxBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            logger.info("### BackfillRequestHeader tcHeader ####" + tcHeader.toString());

            final ResponseEntity<String> response =
                    this.oauthService.getBackfill(tcHeader);

            logger.info("Backfill Process: SUCCESS");

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getPulseOxBackfill end");
        return "success";
    }

    @RequestMapping(value = "/respiration", method = {RequestMethod.POST})
    @ResponseBody
    public String getRespirationBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getRespirationBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new BackfillRequestHeader(
                            this.RespirationBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            logger.info("### BackfillRequestHeader tcHeader ####" + tcHeader.toString());

            final ResponseEntity<String> response =
                    this.oauthService.getBackfill(tcHeader);

            logger.info("Backfill Process: SUCCESS");

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getRespirationBackfill end");
        return "success";
    }

    @RequestMapping(value = "/healthSnapshot", method = {RequestMethod.POST})
    @ResponseBody
    public String getHealthSnapshotBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getHealthSnapshotBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader tcHeader =
                    new BackfillRequestHeader(
                            this.healthSnapshotBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            logger.info("### BackfillRequestHeader tcHeader ####" + tcHeader.toString());

            final ResponseEntity<String> response =
                    this.oauthService.getBackfill(tcHeader);

            logger.info("### Backfill response : " + response.toString());
            logger.info("Backfill Process: SUCCESS");

        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getHealthSnapshotBackfill end");
        return "success";
    }


    @RequestMapping(value = "/all", method = {RequestMethod.POST})
    @ResponseBody
    public String getAllBackfill(@RequestBody Backfill backfill){

        logger.info("BackfillController getAllBackfill start");
        logger.info("Backfill : {}", backfill.toString());

        try {
            Auth auth = authService.findByEmail(backfill.getEmail());
            logger.info("Request Auth : " +auth.toString());

            final AbstractOAuth10aRequestHeader activityHeader =
                    new BackfillRequestHeader(
                            this.activityBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            final ResponseEntity<String> activityResponse =
                    this.oauthService.getBackfill(activityHeader);

            logger.info("Activity Backfill Prossess: SUCCESS " + activityResponse);

            final AbstractOAuth10aRequestHeader activityDetailHeader =
                    new BackfillRequestHeader(
                            this.activityDetailBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            final ResponseEntity<String> activityDetailresponse =
                    this.oauthService.getBackfill(activityDetailHeader);

            logger.info("ActivityDetail Backfill Prossess: SUCCESS " + activityDetailresponse);

            final AbstractOAuth10aRequestHeader dailyHeader =
                    new BackfillRequestHeader(
                            this.DailyBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            final ResponseEntity<String> dailyResponse =
                    this.oauthService.getBackfill(dailyHeader);

            logger.info("Daily Backfill Prossess: SUCCESS " + dailyResponse);

            final AbstractOAuth10aRequestHeader epochHeader =
                    new BackfillRequestHeader(
                            this.EpochBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            final ResponseEntity<String> epochResponse =
                    this.oauthService.getBackfill(epochHeader);

            logger.info("Epoch Backfill Prossess: SUCCESS "+ epochResponse);

            final AbstractOAuth10aRequestHeader sleepHeader =
                    new BackfillRequestHeader(
                            this.SleepBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            final ResponseEntity<String> sleepResponse =
                    this.oauthService.getBackfill(sleepHeader);

            logger.info("Sleep Backfill Prossess: SUCCESS " + sleepResponse);

            final AbstractOAuth10aRequestHeader bodycompsHeader =
                    new BackfillRequestHeader(
                            this.BodyCompsBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            final ResponseEntity<String> bodycompsResponse =
                    this.oauthService.getBackfill(bodycompsHeader);

            logger.info("BodyComps Backfill Prossess: SUCCESS " + bodycompsResponse);

            final AbstractOAuth10aRequestHeader stressDetailHeader =
                    new BackfillRequestHeader(
                            this.stressDetailBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            final ResponseEntity<String> stressDetailResponse =
                    this.oauthService.getBackfill(stressDetailHeader);

            logger.info("Backfill Prossess: SUCCESS " + stressDetailResponse);

            final AbstractOAuth10aRequestHeader userMetricsHeader =
                    new BackfillRequestHeader(
                            this.userMetricsBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            final ResponseEntity<String> userMetricsResponse =
                    this.oauthService.getBackfill(userMetricsHeader);

            logger.info("UserMetrics Backfill Prossess: SUCCESS " + userMetricsResponse);

            final AbstractOAuth10aRequestHeader pulseOxHeader =
                    new BackfillRequestHeader(
                            this.PulseOxBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            final ResponseEntity<String> pulseOxResponse =
                    this.oauthService.getBackfill(pulseOxHeader);

            logger.info("Pulse OX Backfill Prossess: SUCCESS " + pulseOxResponse);

            final AbstractOAuth10aRequestHeader respirationHeader =
                    new BackfillRequestHeader(
                            this.RespirationBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            final ResponseEntity<String> respirationResponse =
                    this.oauthService.getBackfill(respirationHeader);

            logger.info("Respiration Backfill Prossess: SUCCESS " + respirationResponse);

            final AbstractOAuth10aRequestHeader healthSnapshotHeader =
                    new BackfillRequestHeader(
                            this.healthSnapshotBackfillURL,
                            this.consumerKey,
                            this.consumerSecret,
                            auth.getUserAccessToken(),
                            auth.getUserAccessTokenSecret(),
                            "",
                            backfill.getSummaryStartTimeInSeconds(),
                            backfill.getSummaryEndTimeInSeconds());

            final ResponseEntity<String> healthSnapshotResponse =
                    this.oauthService.getBackfill(healthSnapshotHeader);

            logger.info("HealthSnapshot Backfill Prossess: SUCCESS " + healthSnapshotResponse);


        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        logger.info("BackfillController getAllBackfill end");
        return "success";
    }

}
