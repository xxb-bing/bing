package com.fh.redis;

public interface RedisService {
    Boolean isExistKey(String typeAll);

    void setStringKeyAndValue(String key, Object value);

    Object getStringValueByKey(String typeAll);
}
