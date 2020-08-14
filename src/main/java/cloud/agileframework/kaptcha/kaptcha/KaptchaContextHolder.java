package cloud.agileframework.kaptcha.kaptcha;

import cloud.agileframework.cache.util.CacheUtil;
import cloud.agileframework.kaptcha.properties.KaptchaConfigProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author 佟盟
 * 日期 2020/8/00014 10:01
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
public class KaptchaContextHolder {
    private KaptchaContextHolder() {
    }

    private static KaptchaConfigProperties kaptchaConfigProperties;

    public static void initConfig(KaptchaConfigProperties kaptchaConfigProperties) {
        KaptchaContextHolder.kaptchaConfigProperties = kaptchaConfigProperties;
    }

    public static String code(HttpServletRequest request) {
        String codeToken = codeToken(request);
        return CacheUtil.get(codeToken, String.class);
    }

    public static String codeToken(HttpServletRequest req) {
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
}
