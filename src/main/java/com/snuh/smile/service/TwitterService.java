package com.snuh.smile.service;

import com.snuh.smile.domain.NextAction;
import com.snuh.smile.domain.credentials.AbstractOAuth10aCredentials;
import com.snuh.smile.domain.header.AbstractOAuth10aRequestHeader;
import com.snuh.smile.domain.header.OAuth10aProtectedResourcesRequestHeader;
import com.snuh.smile.exception.OAuth10aException;
import com.snuh.smile.util.OAuth10aConstants;
import com.snuh.smile.util.OAuth10aSignatureSupport;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-08-18.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TwitterService {

    @Value("${oauth10a.provider.temporary.credentials.url}")
    private String temporaryCredentialsUrl;

    @Value("${oauth10a.provider.token.credentials.url}")
    private String tokenCredentialsUrl;

    @NonNull
    private RestTemplate restTemplate;

    @NonNull
    private OAuth10aSignatureSupport oAuth10ASignatureSupport;

    public <T extends AbstractOAuth10aCredentials> ResponseEntity<T> getCredentials(AbstractOAuth10aRequestHeader oAuthRequestHeader, Class<T> clazz) {

        this.oAuth10ASignatureSupport.fillNonce(oAuthRequestHeader);
        this.oAuth10ASignatureSupport.accessTokenSignature(oAuthRequestHeader);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(OAuth10aConstants.AUTHORIZATION, oAuthRequestHeader.getRequestHeader());
        log.info("### {} request header: {}", clazz.getSimpleName(), oAuthRequestHeader.getRequestHeader());
        final HttpEntity<String> reqEntity = new HttpEntity<>(httpHeaders);

        final ResponseEntity<String> response = this.restTemplate.exchange(
                oAuthRequestHeader.getServerUrl(),
                HttpMethod.POST,
                reqEntity,
                String.class
        );

        if (response.getStatusCode().equals(HttpStatus.OK)) {

            AbstractOAuth10aCredentials oAuthCredentials;
            try {
                oAuthCredentials = clazz.getConstructor(String.class).newInstance(response.getBody());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            return new ResponseEntity<>((T) oAuthCredentials, HttpStatus.OK);
        } else {
            throw new OAuth10aException("Response from Server: " + response.getStatusCode() + " " + response.getBody());
        }
    }


    public <T extends AbstractOAuth10aCredentials> ResponseEntity<T> getTempCredentials(AbstractOAuth10aRequestHeader oAuthRequestHeader, Class<T> clazz) {
        this.oAuth10ASignatureSupport.fillNonce(oAuthRequestHeader);
        this.oAuth10ASignatureSupport.requestTokenSignature(oAuthRequestHeader);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(OAuth10aConstants.AUTHORIZATION, oAuthRequestHeader.getRequestHeader());
        log.info("### {} request header: {}", clazz.getSimpleName(), oAuthRequestHeader.getRequestHeader());
        final HttpEntity<String> reqEntity = new HttpEntity<>(httpHeaders);

        final ResponseEntity<String> response = this.restTemplate.exchange(
                oAuthRequestHeader.getServerUrl(),
                HttpMethod.POST,
                reqEntity,
                String.class
        );

        if (response.getStatusCode().equals(HttpStatus.OK)) {

            AbstractOAuth10aCredentials oAuthCredentials;
            try {
                oAuthCredentials = clazz.getConstructor(String.class).newInstance(response.getBody());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            return new ResponseEntity<>((T) oAuthCredentials, HttpStatus.OK);
        } else {
            throw new OAuth10aException("Response from Server: " + response.getStatusCode() + " " + response.getBody());
        }
    }


    /**
     * @see <a href="https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/post-statuses-update">Twitter API Doc</a>
     * @param header
     * @param action
     * @return
     */
    public ResponseEntity<Object> doNextAction(OAuth10aProtectedResourcesRequestHeader header, NextAction action) {
        this.oAuth10ASignatureSupport.fillNonce(header);
        this.oAuth10ASignatureSupport.requestTokenSignature(header);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(OAuth10aConstants.AUTHORIZATION, header.getRequestHeader());
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, header.getContentType());
        log.info("### Protected resources request header: {}", header.getRequestHeader());

        final RequestEntity<String> requestEntity =
                new RequestEntity<>(
                        action.getRequestBody(),
                        httpHeaders,
                        action.getHttpMethod(),
                        action.getUri());

        ResponseEntity<Object> response = null;
        try {
            response = this.restTemplate.exchange(requestEntity, Object.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return response;
    }
}
