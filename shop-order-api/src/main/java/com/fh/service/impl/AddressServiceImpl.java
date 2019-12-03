package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.dao.AddressDao;
import com.fh.model.po.AddressPo;
import com.fh.model.vo.AddressVo;
import com.fh.service.AddressService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressDao addressDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<AddressVo> queryAddress(String phone) {
        Integer userId = (Integer) redisTemplate.opsForValue().get("userId_" + phone);
        Boolean isExists = (Boolean) redisTemplate.hasKey("address");
        List<AddressVo> list = null;
        if(!isExists){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.orderByDesc("addTime");
            queryWrapper.eq("userId",userId);
            list = addressDao.selectList(queryWrapper);
            redisTemplate.opsForValue().set("address",list);
        }else{
            list = (List<AddressVo>) redisTemplate.opsForValue().get("address");
        }

        return list;
    }


    @Override
    public void addOrUpdateAddress(AddressPo addressPo, String phone) {
        Integer userId = (Integer) redisTemplate.opsForValue().get("userId_" + phone);
        if(addressPo.getId() == null){
            addressPo.setUserId(userId);
            addressPo.setAddTime(new Date());
            addressDao.insert(addressPo);
            redisTemplate.delete("address");
        }else{
            addressPo.setAddTime(new Date());
            addressPo.setUserId(userId);
            addressDao.updateById(addressPo);
            redisTemplate.delete("address");
        }
    }

    @Override
    public AddressPo getAddressById(Integer id, String phone) {
        AddressPo addressPo = addressDao.selectById(id);
        return addressPo;
    }

    @Override
    public void deleteAddress(Integer id) {
        addressDao.deleteById(id);
        redisTemplate.delete("address");
    }

}
