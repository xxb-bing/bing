package com.fh.service;

import com.fh.model.po.ShopTypePo;

import java.util.List;
import java.util.Map;

public interface ShopTypeService {
    List<Map<String,Object>>  queryTypeTree();

    void addShopType(ShopTypePo shopTypePo);

    ShopTypePo queryTypeById(Integer id);

    void updateShopType(ShopTypePo shopTypePo);

    List<Map<String,Object>> queryChildrenTypeByParentId(Integer parentId);


    /**
     * 根据品牌id查询类型树，将当前品牌拥有的类型回显到复选框
     * @return
     */
  //  List<Map<String,Object>> queryTypeTreeByBrandId(Integer brandId);

    /**
     * 根据品牌id查询该品牌拥有的类型名称
     * @return
     */
//    List<Map<String,Object>> queryTypeNameByBrandId(Integer brandId);

}
