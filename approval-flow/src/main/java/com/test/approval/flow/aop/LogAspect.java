package com.test.approval.flow.aop;

import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.tenkent.infrastructure.log.LoggerManager;
import com.tenkent.infrastructure.utility.DateUtility;

@Aspect
@Component
public class LogAspect
{
    private static AtomicInteger UNIQUE_ID = new AtomicInteger(0);
    
    /**
     * 线程级ID
     */
    private ThreadLocal<LogIdInfo> THREADLOCAL_ID = new ThreadLocal<LogIdInfo>();
    
    @Pointcut("execution(public * com.test.approval.flow.controller..*.*(..))")
    public void controllerLog()
    {
    }
    
    /** 
     * 在切入点前后切入内容，并自己控制何时执行切入点自身的内容 
     * 可以实现拦截器的功能 
     */
    @Around("controllerLog()")
    public Object doControllerAround(ProceedingJoinPoint pjp)
        throws Throwable
    {
        // 请求唯一
        Integer unqId = UNIQUE_ID.incrementAndGet();
        LogIdInfo log = new LogIdInfo(unqId, 0);
        // 线程设置
        THREADLOCAL_ID.set(log);
        
        return doAround(pjp, log.getCurrentReqId());
    }
    
    @Pointcut("execution(public * com.test.approval.flow.service..*.*(..))")
    public void serviceLog()
    {
    }
    
    @Around("serviceLog()")
    public Object doServiceAround(ProceedingJoinPoint pjp)
        throws Throwable
    {
        // 请求唯一
        LogIdInfo log = getLogIdInfo();
        return doAround(pjp, log.getNextReqId());
    }
    
    @Pointcut("execution(public * com.test.approval.flow.repository.dao..*.*(..))")
    public void daoLog()
    {
    }
    
    @Around("daoLog()")
    public Object doDaoAround(ProceedingJoinPoint pjp)
        throws Throwable
    {
        LogIdInfo log = getLogIdInfo();
        return doAround(pjp, log.getNextReqId());
    }
    
    private Object doAround(ProceedingJoinPoint pjp, String uniqueId)
        throws Throwable
    {
        // 请求时间
        long start = System.currentTimeMillis();
        LoggerManager.info(pjp.getSignature().getDeclaringType(),
            uniqueId + " " + pjp.getSignature().getName() + " starting when " + DateUtility.getFullChinesePatternNow());
        Object result = pjp.proceed();
        
        LoggerManager.info(pjp.getSignature().getDeclaringType(),
            uniqueId + " " + pjp.getSignature().getName() + " cost {} ms",
            System.currentTimeMillis() - start);
        return result;
    }
    
    private LogIdInfo getLogIdInfo()
    {
        LogIdInfo log = THREADLOCAL_ID.get();
        if (log == null)
        {
            return new LogIdInfo(UNIQUE_ID.incrementAndGet(), 0);
        }
        return log;
    }
    
}

class LogIdInfo
{
    private static final int MOD_BASE = 1000000;
    
    private Integer transactionId;
    
    private Integer flowId;
    
    public LogIdInfo(Integer transactionId, Integer flowId)
    {
        this.transactionId = transactionId;
        this.flowId = flowId;
    }
    
    public Integer getTransactionId()
    {
        return transactionId;
    }
    
    public void setTransactionId(Integer transactionId)
    {
        this.transactionId = transactionId;
    }
    
    public Integer getFlowId()
    {
        return flowId;
    }
    
    public void setFlowId(Integer flowId)
    {
        this.flowId = flowId;
    }
    
    public String getCurrentReqId()
    {
        return getLogId();
    }
    
    public String getNextReqId()
    {
        flowId += 1;
        return getLogId();
    }
    
    private String getLogId()
    {
        return getStringTransactionId() + getStringFlowId();
    }
    
    private String getStringTransactionId()
    {
        int mod = transactionId % MOD_BASE;
        return String.format("%0" + 8 + "d", mod);
    }
    
    private String getStringFlowId()
    {
        return String.format("%03d", flowId);
    }
}