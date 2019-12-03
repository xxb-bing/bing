package com.fh.service;

import com.fh.model.po.AddressPo;
import com.fh.model.vo.AddressVo;

import java.util.List;

public interface AddressService {

    List<AddressVo> queryAddress(String phone);

    void addOrUpdateAddress(AddressPo addressPo, String phone);

    AddressPo getAddressById(Integer id, String phone);

    void deleteAddress(Integer id);

}
