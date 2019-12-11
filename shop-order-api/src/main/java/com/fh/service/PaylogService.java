package com.fh.service;

import com.fh.utils.ResponseServer;

public interface PaylogService {

    ResponseServer createPayQRCode(String phone, String outTradeNo);

    ResponseServer checkPayStatus(String phone, String outTradeNo);

}
