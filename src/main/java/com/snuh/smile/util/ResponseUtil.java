package com.snuh.smile.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResponseUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @ResponseBody
    @Async
    @ResponseStatus(HttpStatus.OK)
    public static ResponseEntity responseOKEntity(){

        return new ResponseEntity(HttpStatus.OK);
    }
}
