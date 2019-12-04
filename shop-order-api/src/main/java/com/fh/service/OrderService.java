package com.fh.service;

import com.fh.utils.ResponseServer;

public interface OrderService {

    ResponseServer createOrder(Integer addressId, String phone);

}
