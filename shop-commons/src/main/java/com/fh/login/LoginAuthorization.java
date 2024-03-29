package com.fh.login;

import com.fh.exception.AuthenticateException;
import com.fh.jwt.JwtUtils;
import com.fh.utils.ResponseServer;
import com.fh.utils.ServerEnum;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//登录认证
@Aspect
@Component
@Order(5)
public class LoginAuthorization {

    @Around(value = "execution(* com.fh.controller.*.*(..)) && @annotation(loginAnnotation)")
    public Object loginAround(ProceedingJoinPoint joinPoint,LoginAnnotation loginAnnotation){
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String token = request.getHeader("token");

        if(StringUtils.isBlank(token)){
            //抛异常 自定义异常
            throw new AuthenticateException(ServerEnum.TOKEN_TIMEOUT);
        }

        //解析 token
        ResponseServer responseServer = JwtUtils.resolverToken(token);
        if(responseServer.getCode() != 200){
            throw new AuthenticateException(ServerEnum.TOKEN_TIMEOUT);
        }

        Claims claims =  (Claims) responseServer.getData();
        String phone = (String) claims.get("phone");

        request.setAttribute("phone",phone);

        Object obj=null;
        try {
            obj=joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }

}
