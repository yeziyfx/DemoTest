package com.demo.demotest.junit;

import java.util.Arrays;

import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.SysConstant;

import android.test.AndroidTestCase;
import junit.framework.TestCase;


public class ArrayCopyTest extends AndroidTestCase {
	public void testSave()
	{
		int[] ids = { 1, 2, 3, 4, 5 };  
		System.out.println(Arrays.toString(ids)); // [1, 2, 3, 4, 5]  
		 
		int[] ids2 = new int[6];  
		System.arraycopy(ids, 1, ids2, 0, 3); 
		LogSuperUtil.i(SysConstant.Log.exception, ""+Arrays.toString(ids2));
		System.out.println(Arrays.toString(ids2)); // [2, 3, 1, 0, 0, 0]  
	}
	public void testFinalize()
	{
		new ClassA();
		System.gc();
	}
	class ClassA
	{
		
	}
	@Override
	public void finalize() throws Throwable
	{
		LogSuperUtil.i(SysConstant.Log.exception, "执行了finalize方法");
	}
}
