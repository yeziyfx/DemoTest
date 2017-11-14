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
    @Test
    public void woqu()
    {
        long totalLen=442012, sendLen=417990;
        float percent=sendLen*1.0f/(1.0f*totalLen);
        System.out.println("percent="+percent*100+"%");
    }
    @Test
    public void todayIsFriday()
    {
        System.out.println(keepDecimal(100.1245f,-1));
    }
    private   String keepDecimal(float f,int digit)
    {
        digit=digit<=0?2:digit;
        String str=f+"";
        int index=str.lastIndexOf(".");
        int len=str.length();
        int sub=len-1-index;
        if(sub<digit)
        {
            for (int i=0;i<digit-sub;i++)
            {
                str+="0";
            }
        }
        else {
            str=str.substring(0,index+digit+1);
        }
        return str;
    }
}