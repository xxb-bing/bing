package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.po.ShopTypePo;
import com.fh.model.vo.ShopTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ShopTypeDao extends BaseMapper<ShopTypePo> {
    /**
     * 根据品牌id查询类型树，将当前品牌拥有的类型回显到复选框
     * @return
     */
    List<ShopTypeVo> queryTypeTreeByBrandId(Integer brandId);

    /**
     * 根据品牌id查询该品牌拥有的类型名称
     * @return
     */
    List<Map<String,Object>> queryTypeNameByBrandId(Integer brandId);

    List<Map<String,Object>> queryBrandLogoByTypeId(Integer id);

    List<Map<String,Object>> queryChildrenTypeByParentId(Integer parentId);
}
