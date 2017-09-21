package com.yfx.demotest;
import android.util.Log;

import com.demo.demotest.util.LogSuperUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        MyThread myThread=new MyThread();
        myThread.start();
        Thread.sleep(3000);
        System.out.println("woca");
    }
    private class MyThread extends Thread
    {
        @Override
        public void run()
        {
            System.out.println("fuck this");
        }
    }
}