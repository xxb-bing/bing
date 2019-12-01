package com.fh.service.impl;

import com.fh.dao.BrandDao;
import com.fh.model.vo.BrandVo;
import com.fh.service.BrandService;
import com.fh.utils.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandDao brandDao;

    @Override
    public void  queryBrandPageList(PageBean<BrandVo> page) {
        Long count = brandDao.queryCount();
        page.setRecordsFiltered(count);
        page.setRecordsTotal(count);

        List<BrandVo> list = brandDao.queryBrandPageList(page);
        page.setData(list);
    }

  /*  @Transactional
    @Override
    public void addBrand(BrandPo brandPo) {
        brandDao.insert(brandPo);
    }

    @Transactional
    @Override
    public void deleteBrand(Integer brandId) {
        brandDao.deleteById(brandId);
    }


    @Override
    public BrandPo queryBrandById(Integer id) {
        BrandPo brandPo  = brandDao.selectById(id);
        return brandPo;
    }

    @Transactional
    @Override
    public void updateBrand(BrandPo brandPo) {
        brandDao.updateById(brandPo);
    }*/

    @Override
    public List<Map<String,Object>> queryBrandLogoByTypeId(Integer id) {
        return brandDao.queryBrandLogoByTypeId(id);
    }

}
