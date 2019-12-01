package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.po.BrandPo;
import com.fh.model.vo.BrandVo;
import com.fh.utils.PageBean;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
@CacheNamespace(size = 200)
public interface BrandDao extends BaseMapper<BrandPo> {
    Long queryCount();

    List<BrandVo> queryBrandPageList(@Param("page") PageBean<BrandVo> page);

    List<Map<String,Object>> queryBrandLogoByTypeId(Integer id);

}
