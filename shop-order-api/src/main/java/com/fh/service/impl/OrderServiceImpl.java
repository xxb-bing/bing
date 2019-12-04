package com.fh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.dao.OrderDao;
import com.fh.dao.OrderDetailDao;
import com.fh.dao.PaylogDao;
import com.fh.httpclientUtils.HttpclientUtil;
import com.fh.model.CartBean;
import com.fh.model.po.OrderDetail;
import com.fh.model.po.OrderPo;
import com.fh.model.po.Paylog;
import com.fh.service.OrderService;
import com.fh.utils.ResponseServer;
import com.fh.utils.ServerEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderDetailDao orderDetailDao;

    @Resource
    private PaylogDao paylogDao;

    @Override
    @Transactional
    public ResponseServer createOrder(Integer addressId, String phone) {
        //获取购物车的id
        String cartId = (String) redisTemplate.opsForValue().get("cartid_" + phone);
        //获取用户id
        Integer userId = (Integer) redisTemplate.opsForValue().get("userId_" + phone);
        //取出购物车数据
       List<CartBean> cartList =redisTemplate.opsForHash().values(cartId);
        //从数据库获取选购商品
        List<CartBean>  selectingGoodsList= new ArrayList<CartBean>();
        for (CartBean cart:cartList){
            if(cart.getIsChecked()){
                selectingGoodsList.add(cart);
            }
        }
        //生成全局的订单号
        String orderId = IdWorker.getIdStr();
        //生成支付单号
        String outTradeNo = IdWorker.getIdStr();
        //定义库存不足的商品list
        List<CartBean> understockList = new ArrayList<CartBean>();
        //定义一个清空 购物车选中商品的list
        List<Integer> deleteCartShopList = new ArrayList<Integer>();
        //定义 订单的总金额
        BigDecimal totalMoney = new BigDecimal(0.00);
        //定义 订单的商品数量
        BigDecimal orderShopCount = new BigDecimal(0);
        ////将库存充足的商品保存到订单的详情表
        //循环选购商品list
        for(CartBean cartSelectShop : selectingGoodsList){
            //获取选购商品信息
            //根据商品id查询商品信息
            String result = HttpclientUtil.doGet("http://localhost:8090/shopSearch/" + cartSelectShop.getShopId());
            //将结果转化json对象
            JSONObject jsonObject = JSON.parseObject(result);
            //从responseserver中data取出商品数据
            JSONObject shopData = (JSONObject) JSON.parse(jsonObject.get("data").toString());
            Integer  stock = (Integer) shopData.get("stock");
            //选购商品的购买数量大于商品库存
            if (cartSelectShop.getCount() > stock ) {
                understockList.add(cartSelectShop);
            }else{
                //再次判断是否有库存 成功条数为零代表没有库存
                Integer successCount = orderDao.updateShopStock(cartSelectShop.getShopId(),cartSelectShop.getCount());
                if(successCount == 0){
                    understockList.add(cartSelectShop);
                }else{
                    totalMoney=totalMoney.add(cartSelectShop.getSubtotal());
                    orderShopCount=orderShopCount.add(new BigDecimal(cartSelectShop.getCount()));

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(orderId);
                    orderDetail.setCount(cartSelectShop.getCount());
                    orderDetail.setImage(cartSelectShop.getMainImg());
                    orderDetail.setPrice(cartSelectShop.getPrice());
                    orderDetail.setSubTotalPrice(cartSelectShop.getSubtotal());
                    orderDetail.setProductName(cartSelectShop.getShopName());
                    orderDetail.setProductId(cartSelectShop.getShopId());
                    orderDetail.setUserId(userId);
                    orderDetailDao.insert(orderDetail);
                    deleteCartShopList.add(cartSelectShop.getShopId());
                }
            }
        }
        if(understockList.size() == selectingGoodsList.size()){
            return ResponseServer.error(ServerEnum.STOCK_ALL_NULL);
        }
        //生成订单
        OrderPo orderPo = new OrderPo();
        orderPo.setAddressId(addressId);
        orderPo.setCreateTime(new Date());
        orderPo.setId(orderId);
        orderPo.setPayTime(null);
        orderPo.setPayType(1);//1 微信支付
        orderPo.setStatus(ServerEnum.ORDER_STATUS_NOPAY.getCode());
        orderPo.setUserId(userId);
        orderPo.setTotalCount(orderShopCount.intValue());
        orderPo.setTotalPrice(totalMoney);
        orderDao.insert(orderPo);

        //生成待支付记录
        Paylog paylog = new Paylog();
        paylog.setCreateTime(new Date());
        paylog.setOrderId(orderId);
        paylog.setPayMoney(totalMoney);
        paylog.setOutTradeNo(outTradeNo);
        paylog.setPayStatus(ServerEnum.ORDER_STATUS_NOPAY.getCode());
        paylog.setPayTime(null);
        paylog.setPayType(1);
        paylog.setTransactionId(null);
        paylog.setUserId(userId);
        paylogDao.insert(paylog);
        //将待支付记录存入redis中
         redisTemplate.opsForHash().put("pay_order_"+phone,outTradeNo,paylog);
         //将生成订单的商品在缓存中删除
        for(Integer cartShopId:deleteCartShopList){
            redisTemplate.opsForHash().delete(cartId,cartShopId);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("outTradeNo",outTradeNo);
        map.put("orderId",orderId);
        map.put("understockList",understockList);

        return ResponseServer.success(map);
    }

}
