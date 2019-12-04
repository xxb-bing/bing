package com.fh.controller;

import com.fh.login.LoginAnnotation;
import com.fh.service.CartService;
import com.fh.utils.ResponseServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("cartOrder")//exposedHeaders允许返回的头部信息
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders="NOLOGIN")
public class CartOrderController {

    @Resource
    private CartService cartService;

    @GetMapping
    @LoginAnnotation
    public ResponseServer queryCartShopOrder(HttpServletRequest request){
        String userPhone = (String) request.getAttribute("phone");
        Map<String, Object> map = cartService.queryCartShopOrder(userPhone);
        return  ResponseServer.success(map);
    }

}
