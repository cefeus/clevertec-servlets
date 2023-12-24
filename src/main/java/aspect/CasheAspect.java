package aspect;

import cache.Cache;
import cache.ICache;
import entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import util.ReadProperties;

import java.util.Optional;

/**
 * Aspect for caching User-related operations within UserDao.
 */
@Aspect
public class CasheAspect {

    private final ICache cache = Cache.getCache(ReadProperties.getPropertyByKey("CACHE"));

    /**
     * Intercepts the 'get' method in UserDao to check and retrieve data from the cache if available.
     * If data is not found in the cache, retrieves from the UserDao and caches it.
     *
     * @param joinPoint The JoinPoint containing information about the intercepted method call.
     * @return Optional<User> representing the retrieved user (if found) or an empty Optional.
     * @throws Throwable if an error occurs during the method execution.
     */
    @Around("execution(* dao.impl.UserDao.get(..))")
    public Optional<User> aroundGet(ProceedingJoinPoint joinPoint) throws Throwable {
        Optional<User> user = cache.get(joinPoint.getArgs()[0]);
        Object[] args = joinPoint.getArgs();
        if (user.isEmpty()) {
            user = (Optional<User>) joinPoint.proceed(joinPoint.getArgs());
            if (user.isPresent())
                cache.put(user.get().getId(), user.get());
        }
        return user;
    }

    /**
     * Intercepts the 'save' method in UserDao to cache the saved user data.
     *
     * @param joinPoint The JoinPoint containing information about the intercepted method call.
     * @throws Throwable if an error occurs during the method execution.
     */
    @Around("execution(* dao.impl.UserDao.save(..))")
    public void aroundSave(ProceedingJoinPoint joinPoint) throws Throwable {
         joinPoint.proceed(joinPoint.getArgs());
         User user = (User) joinPoint.getArgs()[0];
         cache.put(user.getId(), user);

    }

    /**
     * Intercepts the 'delete' method in UserDao to remove data from the cache.
     *
     * @param joinPoint The JoinPoint containing information about the intercepted method call.
     * @throws Throwable if an error occurs during the method execution.
     */
    @Around("execution(* dao.impl.UserDao.delete(..))")
    public void aroundDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed(joinPoint.getArgs());
        cache.pop(joinPoint.getArgs()[0]);
    }

}
