package com.example.botheat.controller;

import com.example.botheat.bean.MessageFactory;
import com.example.botheat.entity.Operator;
import com.example.botheat.service.OperatorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.UUID;

/**
 * @author haya
 */
@Log4j2
@RestController
public class OperatorController {
    @Autowired
    private OperatorService operatorService;

    @PostMapping(value = "/operator/login")
    public Object login(@RequestBody Operator operator) {
        UUID uuid = operatorService.login( operator );
        return MessageFactory.instance( uuid );
    }

    @GetMapping(value = "/operator")
    public Object getInfo(@CookieValue(value = "token", required = false) Cookie cookie) {
        log.warn( cookie.getValue() );
        return MessageFactory.instance( true );
    }
}
