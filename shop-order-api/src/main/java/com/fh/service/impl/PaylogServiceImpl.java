package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.dao.PaylogDao;
import com.fh.model.po.Paylog;
import com.fh.service.PaylogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class PaylogServiceImpl implements PaylogService {

    @Resource
    private PaylogDao paylogDao;

    @Override
    public BigDecimal getPayPrice(String orderId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("orderId",orderId);
        Paylog paylog = paylogDao.selectOne(queryWrapper);
        return paylog.getPayMoney();
    }

}
