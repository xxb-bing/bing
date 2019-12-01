package com.fh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.httpclientUtils.HttpclientUtil;
import com.fh.model.CartBean;
import com.fh.service.CartService;
import com.fh.utils.ResponseServer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public ResponseServer addCarts(Integer shopId, String userPhone) {
        //获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartid_" + userPhone);
        //根据商品id查询商品信息
        String result = HttpclientUtil.doGet("http://localhost:8090/shopSearch/" + shopId);

        //将结果转化json对象
        JSONObject jsonObject = JSON.parseObject(result);
        //从responseserver中data取出商品数据
        JSONObject shopData = (JSONObject) JSON.parse(jsonObject.get("data").toString());

        //为购物车bean赋值
        CartBean cartBean = new CartBean();
        cartBean.setShopId(shopData.getInteger("id"));
        cartBean.setMainImg(shopData.getString("mainImg"));
        cartBean.setShopName(shopData.getString("name"));
        cartBean.setPrice(shopData.getBigDecimal("price"));

        if(redisTemplate.opsForHash().hasKey(cartId,shopId)){
            CartBean cart = (CartBean) redisTemplate.opsForHash().get(cartId,shopId);
            cartBean.setCount(cart.getCount()+1);
        }else{
            cartBean.setCount(1);
        }

        //根据该商品的个数算出商品 总价钱
        BigDecimal  bigDecimal = new BigDecimal(0.00);
        BigDecimal  shopCount = new  BigDecimal(cartBean.getCount());
        BigDecimal  subtotal = bigDecimal.add(cartBean.getPrice().multiply(shopCount));
        cartBean.setSubtotal(subtotal);
        //新加入商品要被选中
        cartBean.setIsChecked(true);

        //将查询到的商品数据存放到redis中
        redisTemplate.opsForHash().put(cartId,shopId,cartBean);

        //统计购物车商品的数量
        Long count = redisTemplate.opsForHash().size(cartId);

        return ResponseServer.success(count);
    }

    /**
     * 查询  购物车商品
     * @param phone
     * @return
     */
    @Override
    public Map<String, Object> getCartsShop(String phone){
        //获取购物车的id
        String cartId = (String) redisTemplate.opsForValue().get("cartid_" + phone);
        //取出购物车数据
        List<CartBean> cartList =redisTemplate.opsForHash().values(cartId);
        BigDecimal bigDecimal = BigDecimal.valueOf(0.00);
        for (CartBean cart:cartList){
            if(cart.getIsChecked()){
                bigDecimal = bigDecimal.add(cart.getSubtotal());
            }
        }
        Map<String, Object> map=new HashMap<>();
        map.put("cartList",cartList);
        map.put("total",bigDecimal);
        return map;
    }

    @Override
    public ResponseServer deleteCartShop(String phone,Integer shopId) {
        //获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartid_" + phone);
        redisTemplate.opsForHash().delete(cartId,shopId);
        return ResponseServer.success();
    }

    /**
     * 改变商品复选框状态
     * @param phone
     * @param shopId
     * @return
     */
    @Override
    public ResponseServer changeCheck(String phone, Integer shopId) {
        //获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartid_" + phone);
        //获取商品信息
        CartBean cart = (CartBean) redisTemplate.opsForHash().get(cartId,shopId);
        cart.setIsChecked(!cart.getIsChecked());
        redisTemplate.opsForHash().put(cartId,shopId,cart);
        return ResponseServer.success();
    }


    @Override
    public void changeNumByAdd(String phone, Integer shopId) {
        //获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartid_" + phone);
        //获取商品信息
        CartBean cart = (CartBean) redisTemplate.opsForHash().get(cartId,shopId);
        cart.setCount(cart.getCount()+1);

        BigDecimal bigDecimal = new BigDecimal(0.00);
        BigDecimal  shopCount = new  BigDecimal(cart.getCount());
        cart.setSubtotal(cart.getPrice().multiply(shopCount));
        redisTemplate.opsForHash().put(cartId,shopId,cart);
    }

    @Override
    public void changeNumByMinus(String phone, Integer shopId) {
        //获取购物车id
        String cartId = (String) redisTemplate.opsForValue().get("cartid_" + phone);
        //获取商品信息
        CartBean cart = (CartBean) redisTemplate.opsForHash().get(cartId,shopId);
        if(cart.getCount() != 1){
            cart.setCount(cart.getCount()-1);
            BigDecimal bigDecimal = new BigDecimal(0.00);
            BigDecimal  shopCount = new  BigDecimal(cart.getCount());
            cart.setSubtotal(cart.getPrice().multiply(shopCount));
        }
        redisTemplate.opsForHash().put(cartId,shopId,cart);
    }

    @Override
    public void allCheck(String phone, Integer shopId) {
        String cartId = (String) redisTemplate.opsForValue().get("cartid_" + phone);
        CartBean cart= (CartBean) redisTemplate.opsForHash().get(cartId,shopId);
        if(cart.getIsChecked()){
            cart.setIsChecked(false);
        }else {
            cart.setIsChecked(true);
        }
        redisTemplate.opsForHash().put(cartId,shopId,cart);
    }

}
