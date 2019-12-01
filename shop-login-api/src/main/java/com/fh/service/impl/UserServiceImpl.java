package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.dao.UserDao;
import com.fh.model.po.UserPo;
import com.fh.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;


  /*  @Override
    public UserPo queryUserByName(String loginName) {
        QueryWrapper<UserPo> queryWrapper=new QueryWrapper<UserPo>();
        queryWrapper.eq("userName",loginName);
        return userDao.selectOne(queryWrapper);
    }

    @Override
    public void insert(UserPo userPo) {
        userDao.insert(userPo);
    }

    @Override
    public UserPo queryUserByPhone(String phone) {
        QueryWrapper<UserPo> queryWrapper = new QueryWrapper<UserPo>();
        queryWrapper.eq("phone",phone);
        return userDao.selectOne(queryWrapper);
    }*/

    @Override
    public UserPo isRegisterPhone(String phone) {
        QueryWrapper<UserPo> queryWrapper = new QueryWrapper<UserPo>();
        queryWrapper.eq("phone",phone);
        UserPo userPo = userDao.selectOne(queryWrapper);
        if(userPo == null){
            UserPo userPo1 = new UserPo();
            userPo1.setPhone(phone);
            userPo1.setLoginDate(new Date());
            userPo1.setCartId(UUID.randomUUID().toString().replace("-",""));
            userDao.insert(userPo1);
        }else{
            userPo.setLoginDate(new Date());
            userDao.updateById(userPo);
        }
        return userPo;
    }

}
