package com.fh.service;

import com.fh.utils.ResponseServer;

import java.util.Map;

public interface CartService {

    ResponseServer addCarts(Integer shopId, String userPhone);

    Map<String, Object> getCartsShop(String phone);

    ResponseServer deleteCartShop(String phone,Integer shopId);

    ResponseServer changeCheck(String phone, Integer shopId);

    void changeNumByAdd(String phone, Integer shopId);

    void changeNumByMinus(String phone, Integer shopId);

    void allCheck(String phone, Integer shopId);

}
