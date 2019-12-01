package com.fh.service;

import com.fh.model.vo.BrandVo;
import com.fh.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface BrandService {

    void queryBrandPageList(PageBean<BrandVo> page);

  /*  void addBrand(BrandPo brandPo);

    void deleteBrand(Integer brandId);

    BrandPo queryBrandById(Integer id);

    void updateBrand(BrandPo brandPo);*/

    List<Map<String,Object>> queryBrandLogoByTypeId(Integer id);


    /*  List<BrandExcelBean> queryExportBrandExcel();*/

}
