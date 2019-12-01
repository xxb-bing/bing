package com.fh.service;

import com.fh.model.parameter.ShopParameter;
import com.fh.model.vo.ShopVo;
import com.fh.utils.PageBean;
import com.fh.utils.ResponseServer;

public interface ShopServive {
    void queryShopList(PageBean<ShopVo> page, ShopParameter shopParameter);

    ResponseServer getShopByShopId(Integer shopId);



   /* void queryShopPageListByTypeId(PageBean<ShopVo> page,Integer typeId);

    void queryShopPageListByBrandId(PageBean<ShopVo> page, Integer brandId);*/

}
