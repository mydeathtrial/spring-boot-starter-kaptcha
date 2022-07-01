package cloud.agileframework.kaptcha.config;

import cloud.agileframework.kaptcha.kaptcha.AgileTextProducer;
import cloud.agileframework.kaptcha.kaptcha.KaptchaContextHolder;
import cloud.agileframework.kaptcha.kaptcha.KaptchaServlet;
import cloud.agileframework.kaptcha.kaptcha.KaptchaUndertowServlet;
import cloud.agileframework.kaptcha.properties.KaptchaConfigProperties;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;

/**
 * @author 佟盟 on 2017/10/7
 */
@Configuration
@EnableConfigurationProperties(value = {KaptchaConfigProperties.class})
@ConditionalOnClass({DefaultKaptcha.class})
@ConditionalOnProperty(name = "enable", prefix = "agile.kaptcha", matchIfMissing = true)
public class KaptchaAutoConfiguration implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(KaptchaAutoConfiguration.class);

    private final KaptchaConfigProperties kaptchaConfigProperties;

    public KaptchaAutoConfiguration(KaptchaConfigProperties kaptchaConfigProperties) {
        this.kaptchaConfigProperties = kaptchaConfigProperties;
        AgileTextProducer.setKaptchaConfigProperties(kaptchaConfigProperties);
    }
    
    @Bean
    public ServletRegistrationBean<HttpServlet> kaptchaServlet() {
        if (logger.isDebugEnabled()) {
            logger.debug("完成初始化登录验证码");
        }

        ServletRegistrationBean<HttpServlet> reg = new ServletRegistrationBean<>();
        reg.setServlet(new KaptchaServlet());
        reg.addUrlMappings(kaptchaConfigProperties.getUrl());
        return reg;
    }

    @ConditionalOnClass(name = "org.apache.catalina.servlets.DefaultServlet")
    @Bean
    public ServletRegistrationBean<HttpServlet> kaptchaServlet2() {
        if (logger.isDebugEnabled()) {
            logger.debug("完成初始化登录验证码");
        }

        ServletRegistrationBean<HttpServlet> reg = new ServletRegistrationBean<>();
        reg.setServlet(new KaptchaUndertowServlet());
        reg.addUrlMappings(kaptchaConfigProperties.getUrl());
        return reg;
    }
    
    

    @Bean
    DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(new Config(kaptchaConfigProperties.getProperties()));
        return defaultKaptcha;
    }

    @Override
    public void afterPropertiesSet() {
        KaptchaContextHolder.initConfig(kaptchaConfigProperties);
    }
}
