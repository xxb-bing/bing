package com.fh.controller;

import com.fh.login.LoginAnnotation;
import com.fh.service.OrderService;
import com.fh.utils.ResponseServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("order")//exposedHeaders允许返回的头部信息
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders="NOLOGIN")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PutMapping
    @LoginAnnotation
    public ResponseServer createOrder(Integer addressId, HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        return  orderService.createOrder(addressId,phone);
    }


}
