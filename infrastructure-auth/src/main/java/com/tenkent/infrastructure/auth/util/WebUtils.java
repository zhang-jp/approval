package com.tenkent.infrastructure.auth.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple utility class for operations used across multiple class hierarchies in the web framework code.
 * <p/>
 * Some methods in this class were copied from the Spring Framework so we didn't have to re-invent the wheel,
 * and in these cases, we have retained all license, copyright and author information.
 *
 * @since 0.9
 */
public class WebUtils
{
    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);
    
    public static final String SERVLET_REQUEST_KEY = ServletRequest.class.getName() + "_SHIRO_THREAD_CONTEXT_KEY";
    
    public static final String SERVLET_RESPONSE_KEY = ServletResponse.class.getName() + "_SHIRO_THREAD_CONTEXT_KEY";
    
    /**
     * {@link org.apache.shiro.session.Session Session} key used to save a request and later restore it, for example when redirecting to a
     * requested page after login, equal to {@code shiroSavedRequest}.
     */
    public static final String SAVED_REQUEST_KEY = "shiroSavedRequest";
    
    /**
     * Standard Servlet 2.3+ spec request attributes for include URI and paths.
     * <p>If included via a RequestDispatcher, the current resource will see the
     * originating request. Its own URI and paths are exposed as request attributes.
     */
    public static final String INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";
    
    public static final String INCLUDE_CONTEXT_PATH_ATTRIBUTE = "javax.servlet.include.context_path";
    
    public static final String INCLUDE_SERVLET_PATH_ATTRIBUTE = "javax.servlet.include.servlet_path";
    
    public static final String INCLUDE_PATH_INFO_ATTRIBUTE = "javax.servlet.include.path_info";
    
    public static final String INCLUDE_QUERY_STRING_ATTRIBUTE = "javax.servlet.include.query_string";
    
    /**
     * Standard Servlet 2.4+ spec request attributes for forward URI and paths.
     * <p>If forwarded to via a RequestDispatcher, the current resource will see its
     * own URI and paths. The originating URI and paths are exposed as request attributes.
     */
    public static final String FORWARD_REQUEST_URI_ATTRIBUTE = "javax.servlet.forward.request_uri";
    
    public static final String FORWARD_CONTEXT_PATH_ATTRIBUTE = "javax.servlet.forward.context_path";
    
    public static final String FORWARD_SERVLET_PATH_ATTRIBUTE = "javax.servlet.forward.servlet_path";
    
    public static final String FORWARD_PATH_INFO_ATTRIBUTE = "javax.servlet.forward.path_info";
    
    public static final String FORWARD_QUERY_STRING_ATTRIBUTE = "javax.servlet.forward.query_string";
    
    /**
     * Default character encoding to use when <code>request.getCharacterEncoding</code>
     * returns <code>null</code>, according to the Servlet spec.
     *
     * @see javax.servlet.ServletRequest#getCharacterEncoding
     */
    public static final String DEFAULT_CHARACTER_ENCODING = "ISO-8859-1";
    
    /**
     * Return the path within the web application for the given request.
     * Detects include request URL if called within a RequestDispatcher include.
     * <p/>
     * For example, for a request to URL
     * <p/>
     * <code>http://www.somehost.com/myapp/my/url.jsp</code>,
     * <p/>
     * for an application deployed to <code>/mayapp</code> (the application's context path), this method would return
     * <p/>
     * <code>/my/url.jsp</code>.
     *
     * @param request current HTTP request
     * @return the path within the web application
     */
    public static String getPathWithinApplication(HttpServletRequest request)
    {
        String contextPath = getContextPath(request);
        String requestUri = getRequestUri(request);
        if (startsWithIgnoreCase(requestUri, contextPath))
        {
            // Normal case: URI contains context path.
            String path = requestUri.substring(contextPath.length());
            return (hasText(path) ? path : "/");
        }
        else
        {
            // Special case: rather unusual.
            return requestUri;
        }
    }
    
    public static boolean startsWithIgnoreCase(String str, String prefix)
    {
        if (str == null || prefix == null)
        {
            return false;
        }
        if (str.startsWith(prefix))
        {
            return true;
        }
        if (str.length() < prefix.length())
        {
            return false;
        }
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }
    
    public static boolean hasText(String str)
    {
        if (!hasLength(str))
        {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++)
        {
            if (!Character.isWhitespace(str.charAt(i)))
            {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasLength(String str)
    {
        return (str != null && str.length() > 0);
    }
    
    /**
     * Return the request URI for the given request, detecting an include request
     * URL if called within a RequestDispatcher include.
     * <p>As the value returned by <code>request.getRequestURI()</code> is <i>not</i>
     * decoded by the servlet container, this method will decode it.
     * <p>The URI that the web container resolves <i>should</i> be correct, but some
     * containers like JBoss/Jetty incorrectly include ";" strings like ";jsessionid"
     * in the URI. This method cuts off such incorrect appendices.
     *
     * @param request current HTTP request
     * @return the request URI
     */
    public static String getRequestUri(HttpServletRequest request)
    {
        String uri = (String)request.getAttribute(INCLUDE_REQUEST_URI_ATTRIBUTE);
        if (uri == null)
        {
            uri = request.getRequestURI();
        }
        return normalize(decodeAndCleanUriString(request, uri));
    }
    
    /**
     * Normalize a relative URI path that may have relative values ("/./",
     * "/../", and so on ) it it.  <strong>WARNING</strong> - This method is
     * useful only for normalizing application-generated paths.  It does not
     * try to perform security checks for malicious input.
     * Normalize operations were was happily taken from org.apache.catalina.util.RequestUtil in
     * Tomcat trunk, r939305
     *
     * @param path Relative path to be normalized
     * @return normalized path
     */
    public static String normalize(String path)
    {
        return normalize(path, true);
    }
    
    /**
     * Normalize a relative URI path that may have relative values ("/./",
     * "/../", and so on ) it it.  <strong>WARNING</strong> - This method is
     * useful only for normalizing application-generated paths.  It does not
     * try to perform security checks for malicious input.
     * Normalize operations were was happily taken from org.apache.catalina.util.RequestUtil in
     * Tomcat trunk, r939305
     *
     * @param path             Relative path to be normalized
     * @param replaceBackSlash Should '\\' be replaced with '/'
     * @return normalized path
     */
    private static String normalize(String path, boolean replaceBackSlash)
    {
        
        if (path == null)
            return null;
        
        // Create a place for the normalized path
        String normalized = path;
        
        if (replaceBackSlash && normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');
        
        if (normalized.equals("/."))
            return "/";
        
        // Add a leading "/" if necessary
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;
        
        // Resolve occurrences of "//" in the normalized path
        while (true)
        {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 1);
        }
        
        // Resolve occurrences of "/./" in the normalized path
        while (true)
        {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 2);
        }
        
        // Resolve occurrences of "/../" in the normalized path
        while (true)
        {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null); // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
        }
        
        // Return the normalized path that we have completed
        return (normalized);
        
    }
    
    /**
     * Decode the supplied URI string and strips any extraneous portion after a ';'.
     *
     * @param request the incoming HttpServletRequest
     * @param uri     the application's URI string
     * @return the supplied URI string stripped of any extraneous portion after a ';'.
     */
    private static String decodeAndCleanUriString(HttpServletRequest request, String uri)
    {
        uri = decodeRequestString(request, uri);
        int semicolonIndex = uri.indexOf(';');
        return (semicolonIndex != -1 ? uri.substring(0, semicolonIndex) : uri);
    }
    
    /**
     * Return the context path for the given request, detecting an include request
     * URL if called within a RequestDispatcher include.
     * <p>As the value returned by <code>request.getContextPath()</code> is <i>not</i>
     * decoded by the servlet container, this method will decode it.
     *
     * @param request current HTTP request
     * @return the context path
     */
    public static String getContextPath(HttpServletRequest request)
    {
        String contextPath = (String)request.getAttribute(INCLUDE_CONTEXT_PATH_ATTRIBUTE);
        if (contextPath == null)
        {
            contextPath = request.getContextPath();
        }
        if ("/".equals(contextPath))
        {
            // Invalid case, but happens for includes on Jetty: silently adapt it.
            contextPath = "";
        }
        return decodeRequestString(request, contextPath);
    }
    
    /**
     * Decode the given source string with a URLDecoder. The encoding will be taken
     * from the request, falling back to the default "ISO-8859-1".
     * <p>The default implementation uses <code>URLDecoder.decode(input, enc)</code>.
     *
     * @param request current HTTP request
     * @param source  the String to decode
     * @return the decoded String
     * @see #DEFAULT_CHARACTER_ENCODING
     * @see javax.servlet.ServletRequest#getCharacterEncoding
     * @see java.net.URLDecoder#decode(String, String)
     * @see java.net.URLDecoder#decode(String)
     */
    @SuppressWarnings({"deprecation"})
    public static String decodeRequestString(HttpServletRequest request, String source)
    {
        String enc = determineEncoding(request);
        try
        {
            return URLDecoder.decode(source, enc);
        }
        catch (UnsupportedEncodingException ex)
        {
            if (log.isWarnEnabled())
            {
                log.warn("Could not decode request string [" + source + "] with encoding '" + enc
                    + "': falling back to platform default encoding; exception message: " + ex.getMessage());
            }
            return URLDecoder.decode(source);
        }
    }
    
    /**
     * Determine the encoding for the given request.
     * Can be overridden in subclasses.
     * <p>The default implementation checks the request's
     * {@link ServletRequest#getCharacterEncoding() character encoding}, and if that
     * <code>null</code>, falls back to the {@link #DEFAULT_CHARACTER_ENCODING}.
     *
     * @param request current HTTP request
     * @return the encoding for the request (never <code>null</code>)
     * @see javax.servlet.ServletRequest#getCharacterEncoding()
     */
    protected static String determineEncoding(HttpServletRequest request)
    {
        String enc = request.getCharacterEncoding();
        if (enc == null)
        {
            enc = DEFAULT_CHARACTER_ENCODING;
        }
        return enc;
    }
    
    /**
     * A convenience method that merely casts the incoming <code>ServletRequest</code> to an
     * <code>HttpServletRequest</code>:
     * <p/>
     * <code>return (HttpServletRequest)request;</code>
     * <p/>
     * Logic could be changed in the future for logging or throwing an meaningful exception in
     * non HTTP request environments (e.g. Portlet API).
     *
     * @param request the incoming ServletRequest
     * @return the <code>request</code> argument casted to an <code>HttpServletRequest</code>.
     */
    public static HttpServletRequest toHttp(ServletRequest request)
    {
        return (HttpServletRequest)request;
    }
    
    /**
     * A convenience method that merely casts the incoming <code>ServletResponse</code> to an
     * <code>HttpServletResponse</code>:
     * <p/>
     * <code>return (HttpServletResponse)response;</code>
     * <p/>
     * Logic could be changed in the future for logging or throwing an meaningful exception in
     * non HTTP request environments (e.g. Portlet API).
     *
     * @param response the outgoing ServletResponse
     * @return the <code>response</code> argument casted to an <code>HttpServletResponse</code>.
     */
    public static HttpServletResponse toHttp(ServletResponse response)
    {
        return (HttpServletResponse)response;
    }
    
    /**
     * <p>Checks to see if a request param is considered true using a loose matching strategy for
     * general values that indicate that something is true or enabled, etc.</p>
     * <p/>
     * <p>Values that are considered "true" include (case-insensitive): true, t, 1, enabled, y, yes, on.</p>
     *
     * @param request   the servlet request
     * @param paramName @return true if the param value is considered true or false if it isn't.
     * @return true if the given parameter is considered "true" - false otherwise.
     */
    public static boolean isTrue(ServletRequest request, String paramName)
    {
        String value = getCleanParam(request, paramName);
        return value != null && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("t") || value.equalsIgnoreCase("1")
            || value.equalsIgnoreCase("enabled") || value.equalsIgnoreCase("y") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("on"));
    }
    
    /**
     * Convenience method that returns a request parameter value, first running it through
     *
     * @param request   the servlet request.
     * @param paramName the parameter name.
     * @return the clean param value, or null if the param does not exist or is empty.
     */
    public static String getCleanParam(ServletRequest request, String paramName)
    {
        return clean(request.getParameter(paramName));
    }
    
    public static String clean(String in)
    {
        String out = in;
        if (in != null)
        {
            out = in.trim();
            if (out.equals(""))
            {
                out = null;
            }
        }
        return out;
    }
}