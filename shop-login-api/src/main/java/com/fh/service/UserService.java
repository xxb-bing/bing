package com.fh.service;

import com.fh.model.po.UserPo;

public interface UserService {

/*
    UserPo queryUserByName(String loginName);

    void insert(UserPo userPo);

    UserPo queryUserByPhone(String phone);*/

    UserPo isRegisterPhone(String phone);

}
