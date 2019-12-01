package com.fh.service.impl;

import com.fh.dao.ShopDao;
import com.fh.model.parameter.ShopParameter;
import com.fh.model.vo.ShopVo;
import com.fh.service.ShopServive;
import com.fh.utils.PageBean;
import com.fh.utils.ResponseServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShopServiveImpl implements ShopServive {

    @Resource
    private ShopDao shopDao;

    @Override
    public void queryShopList(PageBean<ShopVo> page, ShopParameter shopParameter) {
        Long count = shopDao.queryShopCount(shopParameter);
        page.setRecordsTotal(count);
        page.setRecordsFiltered(count);

        List<ShopVo> list =  shopDao.queryShopList(page,shopParameter);
        page.setData(list);
    }

    @Override
    public ResponseServer getShopByShopId(Integer shopId) {
        ShopVo shopVo = shopDao.getShopByShopId(shopId);
        return ResponseServer.success(shopVo);
    }

  /*  @Override
    public void queryShopPageListByTypeId(PageBean<ShopVo> page,Integer typeId) {
        Long count = shopDao.queryCount(typeId);
        page.setRecordsTotal(count);
        page.setRecordsFiltered(count);

        List<ShopVo> list =  shopDao.queryShopPageListByTypeId(page,typeId);
        page.setData(list);
    }

    @Override
    public void queryShopPageListByBrandId(PageBean<ShopVo> page, Integer brandId) {
        Long count = shopDao.queryShopCountByBrand(brandId);
        page.setRecordsTotal(count);
        page.setRecordsFiltered(count);

        List<ShopVo> list =  shopDao.queryShopPageListByBrandId(page,brandId);
        page.setData(list);
    }*/


}
