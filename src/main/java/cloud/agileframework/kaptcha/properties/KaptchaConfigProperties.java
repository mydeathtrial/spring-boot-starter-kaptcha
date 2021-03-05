package cloud.agileframework.kaptcha.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Properties;

/**
 * @author by 佟盟 on 2018/2/1
 */
@ConfigurationProperties(prefix = "agile.kaptcha")
public class KaptchaConfigProperties implements InitializingBean {
    /**
     * 开关
     */
    private boolean enable = true;
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
    private Duration liveTime = Duration.ofMinutes(1);

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

    @Override
    public void afterPropertiesSet() throws Exception {
        if (properties == null) {
            properties = new Properties();
            properties.put("kaptcha.background.clear.from", "45,45,45");
            properties.put("kaptcha.background.clear.to", "45,45,45");
            properties.put("kaptcha.noise.color", "white");
            properties.put("kaptcha.border", "yes");
            properties.put("kaptcha.border.color", "white");
            properties.put("kaptcha.textproducer.font.color", "white");
            properties.put("kaptcha.textproducer.font.size", "40");
            properties.put("kaptcha.image.width", "125");
            properties.put("kaptcha.image.height", "45");
            properties.put("kaptcha.textproducer.char.length", "4");
            properties.put("kaptcha.textproducer.font.names", "微软雅黑");
            properties.put("kaptcha.textproducer.impl", "cloud.agileframework.kaptcha.kaptcha.AgileTextProducer");
            properties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        }
    }
}
