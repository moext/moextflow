package com.moext.flowservice.inteceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.moext.flowservice.common.BaseResponse;
import com.moext.flowservice.common.ReturnCodeConstant;

@RestControllerAdvice
public class ControllerExceptionHandleAdvice {

	private static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandleAdvice.class);
	
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse<Object> handleException(Exception e) {
    	logger.error("", e);
    	return new BaseResponse.Builder<Object>(ReturnCodeConstant.NORMAL_ERROR).setMessage(e.getMessage()).build();
    }
}
