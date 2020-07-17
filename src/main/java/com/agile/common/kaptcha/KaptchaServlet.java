package com.agile.common.kaptcha;

import com.agile.common.cache.AgileCacheManager;
import com.agile.common.properties.KaptchaConfigProperties;
import com.agile.common.util.CacheUtil;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author 佟盟 on 2018/9/6
 */
public class KaptchaServlet extends HttpServlet implements Servlet {

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private KaptchaConfigProperties kaptchaConfigProperties;

    @Override
    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        if(applicationContext!=null){
            applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (
                ServletOutputStream out = resp.getOutputStream();
        ) {
            initResponse(resp);
            String capText = createCode(req, resp);
            BufferedImage bi = this.kaptchaProducer.createImage(capText);
            ImageIO.write(bi, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initResponse(HttpServletResponse resp) {
        resp.setDateHeader("Expires", 0L);
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        resp.setHeader("Pragma", "no-cache");
        resp.setContentType("image/jpeg");
    }

    private String createCode(HttpServletRequest req, HttpServletResponse resp) {
        String capText = this.kaptchaProducer.createText();

        String codeToken = codeToken(req);
        if (codeToken == null) {
            codeToken = UUID.randomUUID().toString();
        }
        CacheUtil.put(codeToken, capText, Duration.ofSeconds(kaptchaConfigProperties.getLiveTime().getSeconds()));
        setOutParam(kaptchaConfigProperties.getTokenHeader(), codeToken, resp);
        return capText;
    }

    private String codeToken(HttpServletRequest req) {
        String codeToken;

        codeToken = req.getHeader(kaptchaConfigProperties.getTokenHeader());
        if (codeToken == null && req.getCookies() != null) {
            codeToken = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(kaptchaConfigProperties.getTokenHeader()))
                    .map(Cookie::getValue)
                    .findFirst().orElse(null);
        }
        if (codeToken == null) {
            codeToken = (String) req.getAttribute(kaptchaConfigProperties.getTokenHeader());
        }
        return codeToken;
    }

    private void setOutParam(String codeToken, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(codeToken, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setHeader(codeToken, value);
    }
}
