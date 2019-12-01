package com.fh.controller;

import com.fh.model.parameter.ShopParameter;
import com.fh.model.vo.ShopVo;
import com.fh.service.ShopServive;
import com.fh.utils.PageBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("shop")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
public class ShopController {

    @Resource
    private ShopServive shopServive;

   /* @GetMapping
    @CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
    public PageBean<ShopVo> queryShopPageListByTypeId(PageBean<ShopVo> page, Integer typeId){
         shopServive.queryShopPageListByTypeId(page,typeId);
        return page;
    }

    @GetMapping("/{brandId}")
    @CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
    public PageBean<ShopVo> queryShopPageListByBrandId(PageBean<ShopVo> page,@PathVariable Integer brandId){
        shopServive.queryShopPageListByBrandId(page,brandId);
        return page;
    }*/

   @PostMapping
   public PageBean<ShopVo> queryShopList(PageBean<ShopVo> page, ShopParameter shopParameter){
       shopServive.queryShopList(page,shopParameter);
       return page;
   }

}
