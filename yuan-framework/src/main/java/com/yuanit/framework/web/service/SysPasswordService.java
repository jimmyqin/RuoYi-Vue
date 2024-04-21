package com.yuanit.framework.web.service;

import com.yuanit.common.constant.CacheConstants;
import com.yuanit.common.core.redis.RedisCache;
import com.yuanit.common.exception.user.UserPasswordNotMatchException;
import com.yuanit.common.exception.user.UserPasswordRetryLimitExceedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 登录密码方法
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Component
public class SysPasswordService {

    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    private final RedisCache redisCache;


    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(String username) {

        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount.compareTo(maxRetryCount) >= 0) {
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }
    }

    public void addFailCount(String username) {
        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));
        retryCount = retryCount + 1;
        redisCache.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
        throw new UserPasswordNotMatchException();
    }

    public void clearLoginRecordCache(String loginName) {
        if (redisCache.hasKey(getCacheKey(loginName))) {
            redisCache.deleteObject(getCacheKey(loginName));
        }
    }
}
