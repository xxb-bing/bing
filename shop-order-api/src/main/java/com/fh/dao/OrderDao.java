package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.po.OrderPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OrderDao extends BaseMapper<OrderPo> {

    Integer updateShopStock(@Param("shopId") Integer shopId, @Param("count") Integer count);

}
