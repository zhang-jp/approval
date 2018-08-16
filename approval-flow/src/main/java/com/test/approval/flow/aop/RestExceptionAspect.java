package com.test.approval.flow.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.tenkent.infrastructure.constant.GlobalConstant;
import com.tenkent.infrastructure.exception.InfrastructureException;
import com.tenkent.infrastructure.exception.RepositoryException;
import com.tenkent.infrastructure.exception.ServiceException;
import com.tenkent.infrastructure.exception.util.RspUtil;
import com.tenkent.infrastructure.log.LoggerManager;
import com.tenkent.infrastructure.utility.JsonUtility;

@Aspect
@Component
public class RestExceptionAspect
{
    @Pointcut("execution(public * com.test.approval.flow.controller..*.*(..))")
    public void rest()
    {
    }
    
    /**
     * 拦截异常
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("rest()")
    public Object exceptionAroundAdvice(ProceedingJoinPoint proceedingJoinPoint)
        throws Throwable
    {
        try
        {
            return proceedingJoinPoint.proceed();
        }
        catch (InfrastructureException e)
        {
            LoggerManager.error(getClass(),
                e,
                "Infrastructure" + GlobalConstant.GLOBAL_EXCEPTION_LOGGER,
                e.getMessage(),
                JsonUtility.toJSONString(proceedingJoinPoint.getArgs()));
            return RspUtil.buildErrorRsp(e);
        }
        catch (RepositoryException e)
        {
            LoggerManager.error(getClass(),
                e,
                "Repository DB" + GlobalConstant.GLOBAL_EXCEPTION_LOGGER,
                e.getMessage(),
                JsonUtility.toJSONString(proceedingJoinPoint.getArgs()));
            return RspUtil.buildErrorRsp(e);
        }
        catch (ServiceException e)
        {
            LoggerManager.error(getClass(),
                e,
                "Service" + GlobalConstant.GLOBAL_EXCEPTION_LOGGER,
                e.getMessage(),
                JsonUtility.toJSONString(proceedingJoinPoint.getArgs()));
            return RspUtil.buildErrorRsp(e);
        }
        catch (Exception e)
        {
            LoggerManager.error(getClass(),
                e,
                GlobalConstant.GLOBAL_EXCEPTION_LOGGER,
                e.getMessage(),
                JsonUtility.toJSONString(proceedingJoinPoint.getArgs()));
            return RspUtil.buildErrorRsp();
        }
    }
}