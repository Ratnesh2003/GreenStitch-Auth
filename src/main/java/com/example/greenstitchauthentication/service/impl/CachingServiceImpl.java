package com.example.greenstitchauthentication.service.impl;

import com.example.greenstitchauthentication.payload.request.RegisterReq;
import com.example.greenstitchauthentication.service.CachingService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class CachingServiceImpl implements CachingService {

    private static final Integer EXPIRE_MINUTES = 5;
    private LoadingCache<String, Integer> otpCache;
    private LoadingCache<String, RegisterReq> userCache;

    public CachingServiceImpl() {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return null;
                    }
                });
        userCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<String, RegisterReq>() {
                    @Override
                    public RegisterReq load(String s) throws Exception {
                        return null;
                    }
                });
    }

    @Override
    public void cacheUserData(RegisterReq registerReq) {
        userCache.put(registerReq.getEmail(), registerReq);
    }

    @Override
    public RegisterReq getCachedUser(String key) throws ExecutionException {
        return userCache.get(key);
    }

    @Override
    public int generateOtp(String key) {
        Random random = new Random();
        int otp = random.nextInt(899999) + 100000;
        otpCache.put(key, otp);
        return otp;
    }

    @Override
    public int getOtp(String key) throws ExecutionException {
        return otpCache.get(key);
    }

    @Override
    public void clearOtp(String key) {
        otpCache.invalidate(key);

    }

    @Override
    public void clearUser(String key) {
        userCache.invalidate(key);
    }
}
