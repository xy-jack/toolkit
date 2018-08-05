package com.yd.utils;

import com.alibaba.fastjson.JSON;
import com.tps.common.Encrypter;
import com.tps.common.MessageTemplateConfig;
import com.tps.common.Result;
import com.tps.common.helper.DateHelper;
import com.tps.exception.LogicException;
import com.tps.jedis.client.RedisClient;
import com.tps.model.SysUser;

import java.util.Date;

/**
 * (描述用途)
 *
 * @author zhengchao
 * @date 2017-12-29 下午 17:46
 */
public class AuthUtil {

    private static RedisClient redisClient;

    private static Integer expiredTime;

    public static synchronized void setRedisClient(RedisClient redisClient) {
        if (AuthUtil.redisClient == null) {
            AuthUtil.redisClient = redisClient;
        }
    }

    public static void setExpiredTime(Long expiredTime) {
        if (AuthUtil.expiredTime == null) {
            AuthUtil.expiredTime = expiredTime.intValue();
        }
    }

    /**
     * 返回token
     *
     * @param sysUser
     * @return
     */
    public static String createAuth(SysUser sysUser) {
        String token = Encrypter.md5(DateHelper.formatDate(new Date(), DateHelper.DATETIME_FORMAT1) + sysUser.getId());
        String redisKey = RedisKeyUtil.getRedisKey(token);
        if (redisClient.get(redisKey) == null) {
            // 保存用户信息 到Redis key=token
            redisClient.set(redisKey, JSON.toJSONString(sysUser), expiredTime);
        }
        return token;
    }


    /**
     * 删除指定token
     */
    public static Long removeAuth() {
        String redisKey = RedisKeyUtil.getRedisKey(LoginContext.getUserAuth());
        boolean exists = redisClient.exists(redisKey);
        if (!exists) {
            throw new LogicException(new Result().setCode(MessageTemplateConfig.get("sysUser.auth.logouted.code")).setMsg(MessageTemplateConfig.get("sysUser.auth.logouted")));
        }
        // 删除用户信息
        return redisClient.del(redisKey);
    }


    /**
     * 刷新token
     *
     * @param token
     */
    public static void refreshAuth(String token) {
        if (token == null) {
            token = LoginContext.getUserAuth();
        }
        String redisKey = RedisKeyUtil.getRedisKey(token);
        // 重新设置 用户信息 失效时间
        redisClient.expired(redisKey, expiredTime);
    }

    /**
     * 判断是否登录
     *
     * @param token
     */
    public static boolean isLogin(String token) {
        if (token == null) {
            token = LoginContext.getUserAuth();
        }
        return redisClient.exists(token);
    }


}
