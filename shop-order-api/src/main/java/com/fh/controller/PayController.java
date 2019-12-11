package com.fh.controller;

import com.fh.login.LoginAnnotation;
import com.fh.service.PaylogService;
import com.fh.utils.ResponseServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("pay")//exposedHeaders允许返回的头部信息
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders="NOLOGIN")
public class PayController {

    @Resource
    private PaylogService paylogService;

    @GetMapping("{outTradeNo}")
    @LoginAnnotation
    public ResponseServer createPayQRCode(@PathVariable String outTradeNo, HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        return paylogService.createPayQRCode(phone,outTradeNo);
    }

    @PostMapping()
    @LoginAnnotation
    public  ResponseServer checkPayStatus(String outTradeNo, HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        return paylogService.checkPayStatus(phone,outTradeNo);
    }

}
