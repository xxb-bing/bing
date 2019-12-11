package com.fh.service.impl;

import com.fh.dao.OrderDao;
import com.fh.dao.PaylogDao;
import com.fh.model.po.OrderPo;
import com.fh.model.po.Paylog;
import com.fh.service.PaylogService;
import com.fh.utils.ResponseServer;
import com.fh.utils.ServerEnum;
import com.fh.wxPayUtil.DateUtil;
import com.fh.wxPayUtil.MyWxConfig;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaylogServiceImpl implements PaylogService {

    @Resource
    private PaylogDao paylogDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private OrderDao orderDao;

    @Override
    public ResponseServer createPayQRCode(String phone, String outTradeNo) {
        //根据手机号查询未支付的订单信息
        Paylog paylog = (Paylog) redisTemplate.opsForHash().get("pay_order_" + phone,outTradeNo);
        if(paylog == null){
            ResponseServer.error(ServerEnum.NO_ORDER_PAY);
        }
        //调用微信支付接口
        MyWxConfig myWxConfig = new MyWxConfig();
        try {
            WXPay wxPay = new WXPay(myWxConfig);
            //定义存放参数的map
            Map<String,String> data = new HashMap<String,String>();
            data.put("body","鹏昌集团-洗浴服务一条龙支付");
            //设置支付单号
            data.put("out_trade_no",outTradeNo);
            //设置币种
            data.put("fee_type","CNY");
            //设置二维码的失效时间
            Date date = DateUtils.addMinutes(new Date(), 2);
            data.put("time_expire",DateUtil.getYyyyMMddhhmmss(date,DateUtil.yyyyMMddhhmmss));
            //设置支付金额
            /*int payMoney = BigDecimalUtil.mul(paylog.getPayMoney() + "", "100").intValue();*/
            int payMoney = 1;
            data.put("total_fee",payMoney+"");
            //设置付款方式
            data.put("trade_type","NATIVE");
            //设置接口调用路径
            data.put("notify_url","http://www.example.com/wxpay/notify");
            //查询未支付的
            Map<String,String> resultMap = wxPay.unifiedOrder(data);
            //验证接口能否正常访问
            String returnCode = resultMap.get("return_code");
            String returnMessage = resultMap.get("return_msg");
            if(!"SUCCESS".equalsIgnoreCase(returnCode)){
                return ResponseServer.error(5000,returnMessage);
            }
            //验证业务是否成功
            String resultCode = resultMap.get("result_code");
            //错误描述
            String errCodeDes = resultMap.get("err_code_des");
            if(!"SUCCESS".equalsIgnoreCase(resultCode)){
                return ResponseServer.error(5000,errCodeDes);
            }

            String codeUrl = resultMap.get("code_url");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("codeUrl",codeUrl);
            map.put("outTradeNo",outTradeNo);
            map.put("payMoney",paylog.getPayMoney());
           return ResponseServer.success(map);
        }catch (Exception e){
            return ResponseServer.error(ServerEnum.CREATE_PAY_QRCODE_ERROR);
        }
    }

    /**
     * 查看支付状态 支付成功之后修改支付记录表支付状态
     * @param phone
     * @param outTradeNo
     * @return
     */
    @Transactional
    @Override
    public ResponseServer checkPayStatus(String phone, String outTradeNo) {
        //根据手机号查询未支付的订单信息
        Paylog paylog = (Paylog) redisTemplate.opsForHash().get("pay_order_" + phone,outTradeNo);
        if(paylog == null){
            ResponseServer.error(ServerEnum.NO_ORDER_PAY);
        }
        //调用微信支付接口
        MyWxConfig myWxConfig = new MyWxConfig();
        int count = 0;
        while (true){
            count++;
            try {
                WXPay wxPay = new WXPay(myWxConfig);
                //定义存放参数的map
                Map<String,String> data = new HashMap<String,String>();
                data.put("out_trade_no",outTradeNo);
                //查询已支付的
                Map<String,String> responsResult = wxPay.orderQuery(data);
                //验证接口能否正常访问
                String returnCode = responsResult.get("return_code");
                String returnMessage = responsResult.get("return_msg");
                if(!"SUCCESS".equalsIgnoreCase(returnCode)){
                    return ResponseServer.error(5000,returnMessage);
                }
                //验证业务是否成功
                String resultCode = responsResult.get("result_code");
                //错误描述
                String errCodeDes = responsResult.get("err_code_des");
                if(!"SUCCESS".equalsIgnoreCase(resultCode)){
                    return ResponseServer.error(5000,errCodeDes);
                }
                //交易状态 SUCCESS成功
                String tradeState = responsResult.get("trade_state");
                if("SUCCESS".equalsIgnoreCase(tradeState)){
                    //更新支付记录表记录
                    Paylog paylog1 = new Paylog();
                    paylog1.setPayTime(new Date());
                    paylog1.setPayStatus(ServerEnum.ORDER_STATUS_SUCCESS.getCode());
                    //微信支付订单号
                    String transactionId = responsResult.get("transaction_id");
                    paylog1.setTransactionId(transactionId);
                    paylog1.setOutTradeNo(outTradeNo);
                    paylogDao.updateById(paylog1);
                    //更新订单记录
                    OrderPo orderPo = new OrderPo();
                    orderPo.setId(paylog.getOrderId());
                    orderPo.setPayTime(new Date());
                    orderPo.setStatus(ServerEnum.ORDER_STATUS_SUCCESS.getCode());
                    orderDao.updateById(orderPo);
                    //删除redis中待支付记录
                    redisTemplate.opsForHash().delete("pay_order_" + phone,outTradeNo);
                    return ResponseServer.success();
                }
                 Thread.sleep(3000L);
                 if(count > 50){
                     return ResponseServer.error(ServerEnum.ORDER_PAY_TIMEOUT);
                 }
            }catch (Exception e){
                return ResponseServer.error(ServerEnum.CREATE_PAY_QRCODE_ERROR);
            }
        }
    }

}
