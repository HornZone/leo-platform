package com.leo.platform.upms.service.extra.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.leo.platform.server.cache.BaseCacheAspect;
import com.leo.platform.upms.entity.user.User;

/**
 * 缓存及清理菜单缓存
 * <p>
 * User: Zhouqian
 * <p>
 * Date: 13-5-16 下午3:49
 * <p>
 * Version: 1.0
 */
@Component
@Aspect
public class ResourceMenuCacheAspect extends BaseCacheAspect {

    public ResourceMenuCacheAspect() {
        setCacheName("sys-menuCache");
    }

    private String menusKeyPrefix = "menus-";

    @Pointcut(value = "target(com.leo.platform.upms.service.resource.impl.ResourceServiceImpl)")
    private void resourceServicePointcut() {}

    @Pointcut(value = "execution(* save(..)) || execution(* update(..)) || execution(* delete(..))")
    private void resourceCacheEvictAllPointcut() {}

    @Pointcut(value = "execution(* findMenus(*)) && args(arg)", argNames = "arg")
    private void resourceCacheablePointcut(User arg) {}

    /**
     * 在保存，更新和删除时，先清除缓存，防止缓存脏数据
     * 
     * @Title: findRolesCacheableAdvice
     * @author: Administrator
     * @data: 2018年9月18日
     * @throws Throwable
     * @return: void
     */
    @Before(value = "resourceServicePointcut() && resourceCacheEvictAllPointcut()")
    public void findRolesCacheableAdvice() throws Throwable {
        clear();
    }

    /**
     * 在查找菜单时，先用环绕通知去查找缓存中是否存在已经缓存的菜单
     * 
     * @Title: findRolesCacheableAdvice
     * @author: Administrator
     * @data: 2018年9月18日
     * @param pjp
     * @param arg
     * @return
     * @throws Throwable
     * @return: Object
     */
    @Around(value = "resourceServicePointcut() && resourceCacheablePointcut(arg)", argNames = "pjp,arg")
    public Object findRolesCacheableAdvice(ProceedingJoinPoint pjp, User arg) throws Throwable {

        User user = arg;

        String key = menusKey(user.getId());
        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findRolesCacheableAdvice, hit key:{}", cacheName, key);
            return retVal;
        }
        log.debug("cacheName:{}, method:findRolesCacheableAdvice, miss key:{}", cacheName, key);

        /* 启动目标方法执行 */
        retVal = pjp.proceed();

        /* 目标方法执行结果，再缓存起来 */
        put(key, retVal);

        return retVal;
    }

    public void evict(Long userId) {
        evict(menusKey(userId));
    }

    private String menusKey(Long userId) {
        return this.menusKeyPrefix + userId;
    }
}
