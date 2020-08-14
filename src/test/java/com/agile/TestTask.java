package com.agile;

import cloud.agileframework.kaptcha.kaptcha.KaptchaContextHolder;
import cloud.agileframework.kaptcha.properties.KaptchaConfigProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 佟盟
 * 日期 2020/7/14 18:03
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTask {
    private final Logger logger = LoggerFactory.getLogger(TestTask.class);

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void code() {
        String a = restTemplate.getForObject("/code", String.class);
        logger.info(a);
    }

}
