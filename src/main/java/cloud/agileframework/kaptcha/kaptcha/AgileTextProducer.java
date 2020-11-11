package cloud.agileframework.kaptcha.kaptcha;

import cloud.agileframework.kaptcha.properties.KaptchaConfigProperties;
import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.Configurable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author 佟盟
 * 日期 2020/7/17 13:51
 * 描述 验证码文字生成
 * @version 1.0
 * @since 1.0
 */
public class AgileTextProducer extends Configurable implements TextProducer {
    @Autowired
    private static KaptchaConfigProperties kaptchaConfigProperties;

    public AgileTextProducer() throws NoSuchAlgorithmException {
        super();
    }

    public static void setKaptchaConfigProperties(KaptchaConfigProperties kaptchaConfigProperties) {
        AgileTextProducer.kaptchaConfigProperties = kaptchaConfigProperties;
    }

    private final Random random = new Random();

    @Override
    public String getText() {
        int length = getConfig().getTextProducerCharLength();
        assert kaptchaConfigProperties != null;
        String text = kaptchaConfigProperties.getText();
        if (StringUtils.isEmpty(text)) {
            return defaultGetText();
        }
        char[] s = text.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int ind = random.nextInt(s.length);
            sb.append(s[ind]);
        }
        return sb.toString();
    }

    private String defaultGetText() {
        int length = this.getConfig().getTextProducerCharLength();
        char[] chars = this.getConfig().getTextProducerCharString();
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < length; ++i) {
            text.append(chars[random.nextInt(chars.length)]);
        }

        return text.toString();
    }
}
