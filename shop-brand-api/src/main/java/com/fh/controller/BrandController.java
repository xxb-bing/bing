package com.fh.controller;

import com.fh.model.vo.BrandVo;
import com.fh.service.BrandService;
import com.fh.service.BrandTypeService;
import com.fh.utils.PageBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Resource
    private BrandService brandService;

    @Resource
    private BrandTypeService brandTypeService;

    @GetMapping
    @CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
    public PageBean<BrandVo> queryBrandPageList(PageBean<BrandVo> page){
        brandService.queryBrandPageList(page);
       return page;
    }

    @GetMapping("{id}")
    @CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
    public List<Map<String,Object>> queryBrandLogoByTypeId(@PathVariable Integer id){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        list = brandService.queryBrandLogoByTypeId(id);
        return  list;
    }

 /*   @RequestMapping("uploadFile")
    @ResponseBody
    public  Map<String,Object>  uploadFile( @RequestParam("logoPhoto") MultipartFile logoPhoto){
        String url = copyFile.copyFile(logoPhoto, "brand-logo");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",url);
        return  map;
    }
*/

   /* @RequestMapping("addBrand")
    @ResponseBody
    public  String  addBrand(BrandPo brandPo,String typeIds){
        brandService.addBrand(brandPo);
        BrandTypePo brandTypePo = new BrandTypePo();
        String[] typeIdArr = typeIds.split(",");
        brandTypePo.setBrandId(brandPo.getId());
        for(int i=0;i<typeIdArr.length;i++){
            brandTypePo.setTypeId(Integer.valueOf(typeIdArr[i]));
            brandTypeService.addBrandType(brandTypePo);
        }
        return "ok";
    }

    @RequestMapping("deleteBrand")
    @ResponseBody
    public  String deleteBrand(Integer  brandId){
        brandTypeService.deleteByBrandId(brandId);
        brandService.deleteBrand(brandId);
        return "ok";
    }

    @RequestMapping("toUpdateBrand")
    public ModelAndView toUpdateBrand(Integer id){
        BrandPo brand  = brandService.queryBrandById(id);
        ModelAndView mav = new ModelAndView("brand/brand-update");
        mav.addObject("brand",brand);
        return  mav;
    }

    @RequestMapping("updateBrand")
    @ResponseBody
    public  String  updateBrand(BrandPo brandPo,String typeIds){
        if(typeIds != ""){
            brandTypeService.deleteByBrandId(brandPo.getId());
            BrandTypePo brandTypePo = new BrandTypePo();
            String[] typeIdArr = typeIds.split(",");
            brandTypePo.setBrandId(brandPo.getId());
            for(int i=0;i<typeIdArr.length;i++) {
                brandTypePo.setTypeId(Integer.valueOf(typeIdArr[i]));
                brandTypeService.addBrandType(brandTypePo);
            }
        }
        brandPo.setUpdateTime(new Date());
        brandService.updateBrand(brandPo);
        return "ok";
    }*/


}
