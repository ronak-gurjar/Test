package com.test.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.service.MessageService;
import com.test.util.ApiResponse;
import com.test.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BaseController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ObjectMapper objectMapper;

    protected <T> ResponseEntity<?> okSuccessResponse(T t, String messageId) {
        return new ResponseEntity<>(new ApiResponse<>(t, messageService.getMessage(messageId), Constant.SUCCESS), HttpStatus.OK);
    }

    protected ResponseEntity okSuccessResponse(String messageId) {
        return new ResponseEntity<>(new ApiResponse<>(objectMapper.createObjectNode(), messageService.getMessage(messageId), Constant.SUCCESS), HttpStatus.OK);
    }

}
