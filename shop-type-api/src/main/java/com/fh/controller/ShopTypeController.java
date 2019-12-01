package com.fh.controller;

import com.fh.model.po.ShopTypePo;
import com.fh.redis.RedisService;
import com.fh.service.ShopTypeService;
import com.fh.utils.ResponseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("type")
public class ShopTypeController {

    @Resource
    private ShopTypeService shopTypeService;

    @Autowired
    private RedisService redisService;


    //redis缓存
    @GetMapping
    @CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
    public ResponseServer queryTypeTree(){
        Boolean isExistKey=redisService.isExistKey("typeAll");
        Object typeList=null;
        if(!isExistKey){
              typeList=shopTypeService.queryTypeTree();
             redisService.setStringKeyAndValue("typeAll",typeList);
         }else{
             typeList= redisService.getStringValueByKey("typeAll");
        }
        return ResponseServer.success(typeList);
    }




    @GetMapping("{parentId}")
    @CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
    public List<Map<String,Object>> queryChildrenTypeByParentId(@PathVariable  Integer parentId){
        return  shopTypeService.queryChildrenTypeByParentId(parentId);
    }

    @PutMapping
    @CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
    public ShopTypePo addShopType(ShopTypePo shopTypePo){
        shopTypeService.addShopType(shopTypePo);
        return shopTypePo;
    }



   /* @GetMapping("{id}")
    @CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
    public ModelAndView toUpdateShopType(Integer  id){
        ModelAndView mav = new ModelAndView("/view/shopType/update-type");
        ShopTypePo  shopTypePo = shopTypeService.queryTypeById(id);
        mav.addObject("shopTypePo",shopTypePo);
        return  mav;
    }

    @PostMapping
    @CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
    public  ShopTypePo updateShopType(ShopTypePo shopTypePo){
        shopTypeService.updateShopType(shopTypePo);
        return shopTypePo;
    }*/

  /*  *//**
     * 根据品牌id查询类型树，将当前品牌拥有的类型回显到复选框
     * @return
     *//*
    @RequestMapping("queryTypeTreeByBrandId")
    @ResponseBody
    public List<Map<String,Object>> queryTypeTreeByBrandId(Integer brandId){
        return  shopTypeService.queryTypeTreeByBrandId(brandId);
    }

    *//**
     * 根据品牌id查询该品牌拥有的类型名称
     * @return
     *//*
    @RequestMapping("queryTypeNameByBrandId")
    @ResponseBody
    public List<Map<String,Object>> queryTypeNameByBrandId(Integer brandId){
        List<Map<String,Object>> list = shopTypeService.queryTypeNameByBrandId(brandId);
        return list;
    }*/

}
