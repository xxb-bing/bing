package com.fh.controller;

import com.fh.login.LoginAnnotation;
import com.fh.service.PaylogService;
import com.fh.utils.ResponseServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RequestMapping("pay")//exposedHeaders允许返回的头部信息
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders="NOLOGIN")
public class PayController {

    @Resource
    private PaylogService paylogService;

    @PostMapping()
    @LoginAnnotation
    public ResponseServer getPayPrice(String orderId){
      BigDecimal price =  paylogService.getPayPrice(orderId);
      return ResponseServer.success(price);
    }

}
