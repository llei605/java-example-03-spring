package com.example;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Test
    public void hello() {

        log.info("The first test method");

    }
}
