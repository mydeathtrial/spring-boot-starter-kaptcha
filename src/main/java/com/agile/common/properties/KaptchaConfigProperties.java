package com.agile.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Properties;

/**
 * @author by 佟盟 on 2018/2/1
 */
@ConfigurationProperties(prefix = "agile.kaptcha")
public class KaptchaConfigProperties {
    /**
     * 开关
     */
    private boolean enable;
    /**
     * 验证码访问地址
     */
    private String url = "/code";
    /**
     * 依据的文字集
     */
    private String text;
    /**
     * 传输的头部信息
     */
    private String tokenHeader = "V-CODE";
    /**
     * 过期时间
     */
    private Duration liveTime;

    /**
     * 样式属性
     */
    private Properties properties;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public Duration getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(Duration liveTime) {
        this.liveTime = liveTime;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
