package com.example.greenstitchauthentication.service;

import com.example.greenstitchauthentication.payload.request.RegisterReq;

import java.util.concurrent.ExecutionException;

public interface CachingService {
    public void cacheUserData(RegisterReq registerReq);
    public RegisterReq getCachedUser(String key) throws ExecutionException;
    public int generateOtp(String key);
    public int getOtp(String key) throws ExecutionException;
    public void clearOtp(String key);
    public void clearUser(String key);

}
