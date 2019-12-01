package com.fh.redis.impl;

import com.fh.redis.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 判断  key是否存在缓存中
     * @param typeAll
     * @return
     */
    @Override
    public Boolean isExistKey(String typeAll) {
        return redisTemplate.hasKey(typeAll);
    }

    /**
     * 往 redis里放缓存
     * @param key
     * @param value
     */
    @Override
    public void setStringKeyAndValue(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 往 redis取数据
     * @param typeAll
     */
    @Override
    public Object getStringValueByKey(String typeAll) {
        return redisTemplate.opsForValue().get(typeAll);
    }

}
