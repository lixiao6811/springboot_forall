package com.example.springbootpro.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

//@Configuration
@WebFilter(filterName="FilterController",urlPatterns="/user/*")
@Slf4j
public class FilterController implements Filter {

    @Value("${sign.app_secret}")
    private String appSecret;

    @Value("${sign.app_key}")
    private String appKey;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("自定义拦截器");
        log.info(appKey+":::::::::::::::"+appSecret);
        HttpServletResponse rsp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        servletResponse.setContentType("text/html;charset=utf-8");// 页面字符的设置
        String rappkey = req.getParameter("appkey");
        String timestamp = req.getParameter("timestamp");
        String sign = req.getParameter("sign");
        //若仍无信息，则报错，否则开始鉴权
        if (rappkey == null || "".equals(rappkey) || timestamp == null || "".equals(timestamp) ||
                sign == null || "".equals(sign)) {
            PrintWriter out = rsp.getWriter();
            log.info(req.getRequestURI() + " 未正确设定系统级鉴权参数!");
            out.write("{\"is_success\":false,\"message\":\"未正确设定系统级鉴权参数!\"}");
            return;
        } else {
            if (checkSign(rappkey, timestamp, sign)) {//签名有效
                log.info(req.getRequestURI() + "签名有效!");
                filterChain.doFilter(req, rsp);
            } else {//签名无效
                PrintWriter out = rsp.getWriter();
                log.info(req.getRequestURI() + "调用签名失败!");
                out.write("{\"is_success\":false,\"message\":\"调用签名失败\"}");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    @Override
    public void destroy() {
        log.info("过滤器销毁");
    }
    /**
     * 验证签名
     *
     * @param timestamp 时间戳
     * @param sign      签名
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    private boolean checkSign(String rappKey, String timestamp, String sign) throws UnsupportedEncodingException {
        long now = (new Date()).getTime();
        if (Math.abs(now - Long.valueOf(timestamp)) > 1800000) { //3*60*1000毫秒=180000毫秒=3分钟
            log.info("时间戳不在合法范围内. Serv:" + now + "  Cli:" + timestamp);
            return false;//时间戳不在合法范围内（±3分钟）
        }
        String vsign = createSign(appSecret, timestamp);
        if (!rappKey.equals(appKey)){
            return false;
        }
        if (!sign.equals(vsign)) {
            log.info("sign=" + sign + " vsign=" + vsign);
            return false;
        }
        return true;
    }

    /**
     * 生成签名算法
     *
     * @param appSecret 应用密钥
     * @param timestamp 时间戳
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String createSign(String appSecret, String timestamp) {
        String str = timestamp + "&" + appSecret;
        String strUrlencode = null;
        try {
            strUrlencode = URLEncoder.encode(str.toLowerCase(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String strMd5 = DigestUtils.md5Hex(strUrlencode.toLowerCase());
        return strMd5.toLowerCase();
    }
}