package com.tenkent.infrastructure.auth.jwt.core;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tenkent.infrastructure.auth.api.model.ISessionUser;
import com.tenkent.infrastructure.auth.jwt.api.IJwtProvider;
import com.tenkent.infrastructure.auth.jwt.api.model.JwtToken;
import com.tenkent.infrastructure.auth.util.SessionUserUtil;

/**
 * token提供者
 * @author  qinzhengliang
 * @version  [版本号, 2018年5月4日]
 */
public class JwtProvider implements IJwtProvider
{
    /**
     * 刷新时间
     */
    private static int REFRESH_TIME = 3 * 60;
    
    /**
     * yyyyMMddHHmmss
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    
    /**
     * token 过期时间 默认 1800秒
     */
    private int expTimeOut = 1800;
    
    /**
     * 时间格式
     */
    private TimeFormat timeFormat;
    
    /**
     * token 密钥
     */
    private String tokenSecret = "eyJhbGciOiJIUzI1fg55NiIsInR5cCI6IkpXVCJ9.234eyJuYW1lIjoicWluemhlbmdsaWFuZ2Fz";
    
    public JwtProvider()
    {
    }
    
    public JwtProvider(int expTimeOut)
    {
        this.expTimeOut = expTimeOut;
    }
    
    public JwtProvider(int expTimeOut, TimeFormat timeFormat)
    {
        this.expTimeOut = expTimeOut;
        this.timeFormat = timeFormat;
    }
    
    public JwtProvider(int expTimeOut, String tokenSecret)
    {
        this.expTimeOut = expTimeOut;
        this.tokenSecret = tokenSecret;
    }
    
    public JwtProvider(int expTimeOut, String tokenSecret, TimeFormat timeFormat)
    {
        this.expTimeOut = expTimeOut;
        this.tokenSecret = tokenSecret;
        this.timeFormat = timeFormat;
    }
    
    @Override
    public <T extends ISessionUser> JwtToken create(T user)
    {
        String strUser = SessionUserUtil.getStringSessionUser(user);
        
        // 签发时间
        Date iatDate = new Date();
        
        // token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(iatDate);
        calendar.add(Calendar.SECOND, expTimeOut);
        Date expDate = calendar.getTime();
        
        // 刷新时间
        calendar.setTime(iatDate);
        calendar.add(Calendar.SECOND, expTimeOut - REFRESH_TIME);
        Date refreshDate = calendar.getTime();
        
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        try
        {
            String token = JWT.create().withHeader(map).withClaim("user", strUser).withIssuedAt(iatDate).withExpiresAt(expDate).sign(
                Algorithm.HMAC256(tokenSecret));
            
            // 根据时间格式设置返回刷新时间
            if (TimeFormat.TIME_MILLIS_PATTERN.equals(timeFormat))
            {
                return new JwtToken(token, String.valueOf(refreshDate.getTime()));
            }
            return new JwtToken(token, formatDate(refreshDate, YYYYMMDDHHMMSS));
        }
        catch (IllegalArgumentException | JWTCreationException | UnsupportedEncodingException e)
        {
            throw new RuntimeException("生成JWT token异常", e);
        }
    }
    
    @Override
    public JwtToken refresh(String token)
    {
        return create(verifyToken(token));
    }
    
    @Override
    public ISessionUser verifyToken(String token)
    {
        JWTVerifier verifyer;
        try
        {
            verifyer = JWT.require(Algorithm.HMAC256(tokenSecret)).build();
        }
        catch (IllegalArgumentException | UnsupportedEncodingException e)
        {
            throw new RuntimeException("JWT认证失败", e);
        }
        
        DecodedJWT decoded = verifyer.verify(token);
        Claim claim = decoded.getClaim("user");
        
        return SessionUserUtil.getSessionUserFromJsonString(claim.asString());
    }
    
    private String formatDate(Date date, String format)
    {
        if ((null == date) || (StringUtils.isBlank(format)))
        {
            return null;
        }
        
        return new SimpleDateFormat(format).format(date);
    }
    
    public int getExpTimeOut()
    {
        return expTimeOut;
    }
    
    public void setExpTimeOut(int expTimeOut)
    {
        this.expTimeOut = expTimeOut;
    }
    
    public String getTokenSecret()
    {
        return tokenSecret;
    }
    
    public void setTokenSecret(String tokenSecret)
    {
        this.tokenSecret = tokenSecret;
    }
    
    /**
     * 过期时间格式枚举
     * @author  qinzhengliang
     * @version  [版本号, 2018年6月29日]
     */
    public enum TimeFormat
    {
        TIME_MILLIS_PATTERN, FULL_CHINESE_PATTERN
    }
}
