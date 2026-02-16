package com.inventory.inventory_management.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    public <T> T get(String key, Class<T> entityClass) {
        try{
            Object obj = redisTemplate.opsForValue().get(key);
            if(obj==null){
                log.info("Cache miss for key {}",key);
                return null;}

            return objectMapper.readValue(obj.toString(), entityClass);
        }
        catch (Exception e){
            log.error("Redis got error trying to get {}", key, e);
            return null;
        }
    }

    public void set(String key, Object value, long ttl){
        try{
            String json=objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key,json,ttl, TimeUnit.MINUTES);
        }
        catch (Exception e){
            log.error("Redis got error trying to get {}", key, e);
        }
    }

    public void evict(String key){
        try {
            redisTemplate.delete(key);
        }
        catch (Exception e){
            log.error("Reddis got error trying to delete {}", key, e);
        }
    }
}
