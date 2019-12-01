package com.fh.service;

import com.fh.model.po.BrandTypePo;

public interface BrandTypeService {

    void addBrandType(BrandTypePo brandTypePo);

    void deleteByBrandId(Integer brandId);



}
