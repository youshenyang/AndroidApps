package ct.finded.findfood.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.util.Log;
import ct.finded.findfood.BuildConfig;



/**
 * 字符串处理相关工具类
 * 
 */

public class StringUtils {
	
	/** 获取一个StringBuffer */
	public static StringBuffer getBuffer( int num ) {
		return new StringBuffer(num);
	}
	
	/** 获取一个StringBuffer */
	public static StringBuffer getBuffer() {
		return new StringBuffer();
	}
	
	/** 返回当前日期的格式化（yyyy-MM-dd）表示 */
	public static String getStrDate()
	{
		SimpleDateFormat dd = new SimpleDateFormat( "yyyy-MM-dd" );
		return dd.format( new Date() );
	}
	
	/** 返回当前日期的格式化（yyyy.MM.dd）表示 */
	public static String getFormatDate(String date)
	{
		try {
			date = date.trim();
			return date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8);
		}
		catch(Exception e) {
			if(BuildConfig.DEBUG)
				Log.i("App", e.getMessage());
			return date;
		}
	}
	
	/** 将字符串转移成布尔数*/
	public static boolean toBoolean( String num )
	{
		if ( isEmpty( num ) )
			return false;
		if ( num.equals( "true" ) )
			return true;
		return false;
	}
	
	/** 将列表转换成字符串 */
	public static String toStrings(List<Integer> list) {
		if(list == null || list.isEmpty())
			return null;
		StringBuffer sb = getBuffer();
		for(Integer id : list) {
			sb.append(",").append(id);
		}
		return sb.substring(1);
	}
	
	/** 将字符串转移成整数 */
	public static int toInt( String num )
	{
		try
		{
			return Integer.parseInt( num );
		} catch ( Exception e )
		{
			return 0;
		}
	}
	
	/** 转成整数 */
	public static int toInt( Integer num )
	{
		if(num == null)
			return 0;
		try
		{
			return num.intValue();
		} catch ( Exception e )
		{
			return 0;
		}
	}

	/** 将字符串转移成长整数 */
	public static long toLong( String num )
	{
		try
		{
			return Long.parseLong( num );
		} catch ( Exception e )
		{
			return 0;
		}
	}
	
	/** 将字符串转移成浮点数 */
	public static float toFloat( String num )
	{
		try
		{
			return Float.parseFloat( num );
		} catch ( Exception e )
		{
			return 0;
		}
	}
	
	/** utf8编码 */
	public static String encodeUtf8(String str)
	{
		if(isEmpty(str))
			return "";
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 获取文件的后缀名，返回大写
	 * */
	public static String getSuffix(String str) {
		int i = str.lastIndexOf('.');
		if (i != -1) {
			return str.substring(i + 1).toUpperCase();
		}
		return str;
	}
	
	/** 字符串是否为NULL或者为空串 */
	public static boolean isEmpty(String str)
	{
		if(str == null || str == "" || str.trim().equals(""))
			return true;
		return false;
	}
	
}