package com.fakebilly.monet.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * TestRoot
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DisplayName("Junit单元测试")
public class TestRoot {

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Junit单元测试")
    void testEquals() {
        System.out.println("Junit单元测试");
    }

}

