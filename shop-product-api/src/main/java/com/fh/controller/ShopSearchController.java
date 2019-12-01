package com.fh.controller;

import com.fh.service.ShopServive;
import com.fh.utils.ResponseServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("shopSearch")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8094")
public class ShopSearchController {

    @Resource
    private ShopServive shopServive;

    @GetMapping("{shopId}")
    public ResponseServer getShopByShopId(@PathVariable  Integer shopId){
       return shopServive.getShopByShopId(shopId);
    }

}
