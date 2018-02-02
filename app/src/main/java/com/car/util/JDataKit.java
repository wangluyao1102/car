package com.car.util;

import java.text.DecimalFormat;

/**
 * 数据格式化工具类
 * 
 * @author blue
 * 
 */
public class JDataKit
{

	/**
	 * 数据格式化（0和#都是占位符，不同的是用0表示占位符，不足的地方会用0补上，而#不会）
	 * 
	 * @param data
	 * @return
	 */
	public static String dataFormat(Object data)
	{
		return dataFormat("#.0", data);
	}

	/**
	 * 数据格式化（0和#都是占位符，不同的是用0表示占位符，不足的地方会用0补上，而#不会）
	 * 
	 * @param data
	 * @return
	 */
	public static String dataFormat(String pattern, Object data)
	{
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		String dataString = decimalFormat.format(data);
		return dataString;
	}

}
