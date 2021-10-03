package org.example.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * by Iskortsev S.V.
 */
@Component
@Aspect
public class AuthenticationAspects {
    private final AuthenticationUtils authenticationUtils;

    @Autowired
    public AuthenticationAspects (AuthenticationUtils authenticationUtils) {
        this.authenticationUtils = authenticationUtils;
    }

    //срез
    @Pointcut("execution(@org.example.annotations.Authentication * *(..))")
    public void annotatedByAuthentication() {}

    //совет
    @Around("annotatedByAuthentication()")
    public Object around(ProceedingJoinPoint joinPoint) { // информация об аргументах
        HttpServletRequest request = findRequest(joinPoint);
        if (request == null) {
            throw new RuntimeException("Method annotated by @Authentication must have HttpServletRequest argument");
        }

        Object target = joinPoint.getTarget();

        return authenticationUtils.performAfterAuthentication(request, (userId) -> {
            try {
                Field userIdField = target.getClass().getDeclaredField("userId");
                userIdField.setAccessible(true); // разрешаем доступ
                userIdField.set(target, userId);

                ResponseEntity<Object> result = (ResponseEntity<Object>) joinPoint.proceed();

                userIdField.set(target, null);
                userIdField.setAccessible(false); // запретить доступ

                return result;
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Class with method annotated by @Authentication must have userId field", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });

    }
//получить доступ к http request'y
    private HttpServletRequest findRequest(ProceedingJoinPoint joinPoint) {
        return (HttpServletRequest) Stream.of(joinPoint.getArgs())
                .filter(arg -> HttpServletRequest.class.isAssignableFrom(arg.getClass())) // isAssignableFrom является ли переменная указанного типа либо дочернего
                .findFirst()
                .orElse(null);
    }
}
