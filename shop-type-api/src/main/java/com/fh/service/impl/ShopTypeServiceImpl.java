package com.fh.service.impl;

import com.fh.dao.ShopTypeDao;
import com.fh.model.po.ShopTypePo;
import com.fh.service.ShopTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopTypeServiceImpl implements ShopTypeService {

    @Resource
    private ShopTypeDao shopTypeDao;



    @Override
    public List<Map<String,Object>> queryTypeTree() {
        List<ShopTypePo> typeList = shopTypeDao.selectList(null);
        return getTypeTree(0,typeList);
    }

    private  List<Map<String,Object>>  getTypeTree(Integer pid,List<ShopTypePo> typeList){
        List<Map<String,Object>>  list = new ArrayList<Map<String,Object>>();
        typeList.forEach(type->{
            Map<String,Object>  map = null;
            if(pid.equals(type.getPid())){
                map = new HashMap<String,Object>();
                map.put("id",type.getId());
                map.put("name",type.getTypeName());
                map.put("pid",type.getPid());
                map.put("children",getTypeTree(type.getId(),typeList));
            }
            if(map != null){
                list.add(map) ;
            }
        });
        return list;
    }




    @Transactional
    @Override
    public void addShopType(ShopTypePo shopTypePo) {
        shopTypeDao.insert(shopTypePo);
    }

    @Override
    public ShopTypePo queryTypeById(Integer id) {
        return shopTypeDao.selectById(id);
    }

    @Transactional
    @Override
    public void updateShopType(ShopTypePo shopTypePo) {
        shopTypeDao.updateById(shopTypePo);
    }

    @Override
    public List<Map<String, Object>> queryChildrenTypeByParentId(Integer parentId) {
        return shopTypeDao.queryChildrenTypeByParentId(parentId);
    }



    /*

    */
/**
     * 根据品牌id查询类型树，将当前品牌拥有的类型回显到复选框
     * @return
     *//*

    @Override
    public List<Map<String, Object>> queryTypeTreeByBrandId(Integer brandId) {
        List<ShopTypeVo> typeList = shopTypeDao.queryTypeTreeByBrandId(brandId);
        return getTypeTreeByBrandId(0,typeList);
    }

    private  List<Map<String,Object>>  getTypeTreeByBrandId(Integer pid,List<ShopTypeVo> typeList){
        List<Map<String,Object>>  list = new ArrayList<Map<String,Object>>();
        typeList.forEach(type->{
            Map<String,Object>  map = null;
            if(pid.equals(type.getPid())){
                map = new HashMap<String,Object>();
                map.put("id",type.getId());
                map.put("name",type.getTypeName());
                map.put("checked",type.isChecked());
                map.put("children",getTypeTreeByBrandId(type.getId(),typeList));
            }
            if(map != null){
                list.add(map) ;
            }
        });
        return list;
    }
*/

  /*  *//**
     * 根据品牌id查询该品牌拥有的类型名称
     * @return
     *//*
    @Override
    public List<Map<String, Object>> queryTypeNameByBrandId(Integer brandId) {
        return shopTypeDao.queryTypeNameByBrandId(brandId);
    }*/

}
