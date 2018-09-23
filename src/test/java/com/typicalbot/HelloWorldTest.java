package com.typicalbot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HelloWorldTest {
    private HelloWorld hw;

    @Before
    public void setup() {
        hw = new HelloWorld();
    }

    @Test
    public void testGetMessage() {
        Assert.assertEquals("Hello World", hw.getMessage());
    }
}
