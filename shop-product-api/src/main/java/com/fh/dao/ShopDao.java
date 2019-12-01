package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.parameter.ShopParameter;
import com.fh.model.po.ShopPo;
import com.fh.model.vo.ShopVo;
import com.fh.utils.PageBean;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@CacheNamespace(size = 200)
public interface ShopDao extends BaseMapper<ShopPo> {
    Long queryShopCount(@Param("shopParameter") ShopParameter shopParameter);

    List<ShopVo> queryShopList(@Param("page") PageBean<ShopVo> page, @Param("shopParameter") ShopParameter shopParameter);

    ShopVo getShopByShopId(Integer shopId);



  /*  Long queryCount(Integer typeId);

    List<ShopVo> queryShopPageListByTypeId(@Param("page") PageBean<ShopVo> page, @Param("typeId") Integer typeId);

    Long queryShopCountByBrand(Integer brandId);

    List<ShopVo> queryShopPageListByBrandId(@Param("page") PageBean<ShopVo> page,@Param("brandId") Integer brandId);*/

}
