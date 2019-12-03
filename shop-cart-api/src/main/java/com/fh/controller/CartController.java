package com.fh.controller;

import com.fh.login.LoginAnnotation;
import com.fh.service.CartService;
import com.fh.utils.ResponseServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("cart")//exposedHeaders允许返回的头部信息
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders="NOLOGIN")
public class CartController {

    @Resource
    private CartService cartService;

    @PostMapping()
    @LoginAnnotation
    public ResponseServer  addCarts(Integer shopId, HttpServletRequest request){
        String userPhone = (String) request.getAttribute("phone");
        return  cartService.addCarts(shopId,userPhone);
    }

    @GetMapping
    @LoginAnnotation
    public  ResponseServer  toCartsShow(){
         return ResponseServer.success();
    }


    @GetMapping("cartShopShow")
    @LoginAnnotation
    public ResponseServer  cartShopShow(HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        Map<String, Object> cartShops = cartService.getCartsShop(phone);
        return  ResponseServer.success(cartShops);
    }

    @DeleteMapping("{shopId}")
    @LoginAnnotation
    public ResponseServer  deleteCartShop(@PathVariable  Integer shopId,HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        return   cartService.deleteCartShop(phone,shopId);
    }

    @PostMapping("changeCheck")
    @LoginAnnotation
    public ResponseServer  changeCheck(Integer shopId,HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        return   cartService.changeCheck(phone,shopId);
    }

    @PostMapping("changeNumByAdd")
    @LoginAnnotation
    public ResponseServer  changeNumByAdd(Integer shopId,HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        cartService.changeNumByAdd(phone,shopId);
        return  ResponseServer.success();
    }

    @PostMapping("changeNumByMinus")
    @LoginAnnotation
    public ResponseServer  changeNumByMinus(Integer shopId,HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        cartService.changeNumByMinus(phone,shopId);
        return  ResponseServer.success();
    }


    @PostMapping("allCheck")
    @LoginAnnotation
    public ResponseServer allCheck(Integer shopId,HttpServletRequest request){
        String  phone= (String) request.getAttribute("phone");

        cartService.allCheck(phone,shopId);
        return ResponseServer.success();
    }
}
