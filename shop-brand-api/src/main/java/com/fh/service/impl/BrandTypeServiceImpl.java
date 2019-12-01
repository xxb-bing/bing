package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.dao.BrandTypeDao;
import com.fh.model.po.BrandTypePo;
import com.fh.service.BrandTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class BrandTypeServiceImpl implements BrandTypeService {

     @Resource
     private BrandTypeDao brandTypeDao;

    @Transactional
    @Override
    public void addBrandType(BrandTypePo brandTypePo) {
        brandTypeDao.insert(brandTypePo);
    }

    @Transactional
    @Override
    public void deleteByBrandId(Integer brandId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("brandId",brandId);
        brandTypeDao.delete(wrapper);
    }


}
