package com.nahwu.base.controller;

import com.nahwu.base.entity.TestObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @PostMapping("/v1/test/echo")
    @Operation(summary = "Test API and reply with the same request payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API working",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TestObject.class))})})
    public ResponseEntity<?> testApiEcho(
            @RequestBody TestObject testObject) {
        return new ResponseEntity<>(testObject, HttpStatus.OK);
    }

    @PostMapping("/v1/test/length")
    @Operation(summary = "Test API and reply with the request length")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API working",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))})})
    public ResponseEntity<?> testApiLength(
            @RequestBody TestObject testObject) {
        logger.warn("__Received an API call on /v1/test/length");
        return new ResponseEntity<>(testObject.getPayload().length(), HttpStatus.OK);
    }
}
