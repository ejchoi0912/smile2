package com.snuh.smile.controller;

import com.snuh.smile.domain.Mention;
import com.snuh.smile.domain.NextAction;
import com.snuh.smile.domain.VerifierResponse;
import com.snuh.smile.domain.credentials.TemporaryCredentials;
import com.snuh.smile.domain.credentials.TokenCredentials;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.OAuth10aTemporaryCredentialRequestHeader;
import com.snuh.smile.domain.header.OAuth10aTokenCredentialsRequestHeader;
import com.snuh.smile.service.OauthService;
import com.snuh.smile.util.OAuth10aConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.snuh.smile.util.EncodingUtils.getPercentEncoded;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-08-18.
 */
@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class OauthController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${oauth10a.provider.temporary.credentials.url}")
    private String temporaryCredentialsUrl;

    @Value("${oauth10a.provider.authorize.url}")
    private String authorizeUrl;

    @Value("${oauth10a.provider.token.credentials.url}")
    private String tokenCredentialsUrl;

    @Value("${oauth10a.provider.post.url}")
    private String postUrl;

    @Value("${oauth10a.consumer.key}")
    private String consumerKey;

    @Value("${oauth10a.consumer.secret}")
    private String consumerSecret;

    @Value("${oauth10a.consumer.callback.url}")
    private String callbackUrl;

    @Value("${domain.url}")
    private String domain;

    @NonNull
    private final OauthService oauthService;




    @GetMapping("/mentions")
    public ModelAndView showMentionForm(ModelAndView mav) {

        logger.info("OauthController showMentionForm start");

        mav.setViewName("req");

        logger.info("OauthController showMentionForm end");

        return mav;
    }

    @PostMapping("/mentions")
    public String requestTemporaryCredentials(HttpServletRequest request, Mention mention) {

        logger.info("OauthController requestTemporaryCredentials start");


        final AbstractOAuth10aRequestHeader tcHeader =
                new OAuth10aTemporaryCredentialRequestHeader(this.temporaryCredentialsUrl, this.consumerKey, this.consumerSecret, this.callbackUrl);

        final ResponseEntity<TemporaryCredentials> response =
                this.oauthService.getTempCredentials(tcHeader, TemporaryCredentials.class);
        final TemporaryCredentials temporaryCredentials = response.getBody();

        HttpSession session = request.getSession();
        session.setAttribute(OAuth10aConstants.NEXT_ACTION,
                // https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/post-statuses-update
                // 에 따라 POST로 보내더라도 mention 내용을 queryString으로 붙여보내야만 정상 동작함
                new NextAction(HttpMethod.POST, postUrl + "?status=" + getPercentEncoded(mention.getMention()), null));
//                new NextAction(HttpMethod.POST, postUrl, "status=" + getPercentEncoded(mention.getMention())));
        // RequestTokenSecret is better be stored in cache like Redis
        // If it is to be stored in the session, it needs to be encrypted
        session.setAttribute("RTS", temporaryCredentials.getOauth_token_secret());

        logger.info("OauthController requestTemporaryCredentials end");


        return "redirect:" + this.authorizeUrl + "?" +
                OAuth10aConstants.OAUTH_TOKEN + "=" + temporaryCredentials.getOauth_token();
    }


    @GetMapping("/callback")
    public String requestTokenCredentials(HttpServletRequest request, VerifierResponse verifierResponse) {

        logger.info("OauthController requestTokenCredentials start");

        HttpSession session = request.getSession();
        // RequestTokenSecret is better be fetched from cache like Redis
        // If it is to be read from the session, it needs to be decrypted
        final String requestTokenSecret = (String) session.getAttribute("RTS");
        final AbstractOAuth10aRequestHeader tcHeader =
                new OAuth10aTokenCredentialsRequestHeader(
                        this.tokenCredentialsUrl,
                        this.consumerKey,
                        this.consumerSecret,
                        verifierResponse.getOauth_token(),
                        requestTokenSecret,
                        verifierResponse.getOauth_verifier());

            final ResponseEntity<TokenCredentials> responseEntity =
                    this.oauthService.getCredentials(tcHeader, TokenCredentials.class);

            log.info("access_token: {}", Objects.requireNonNull(responseEntity.getBody()).toString());

        String token = responseEntity.getBody().getOauth_token();
        String secret = responseEntity.getBody().getOauth_token_secret();

        session.removeAttribute("RTS");

        logger.info("OauthController requestTokenCredentials end");


        return "redirect:/auth/regi?token=" + token + "&secret=" + secret;



    }

}
