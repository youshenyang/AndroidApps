package ct.finded.findfood.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import ct.finded.findfood.BuildConfig;
import ct.finded.findfood.http.HttpPoster;



/**
 * 
 * @ClassName: JsonParser
 * @Description: JSON解析类
 * @author Shy_You
 * @date 2014年4月28日 
 * @param <T>
 */
public class JsonParser<T> {

	/**
	 * 将json字符串解析并返回对应的T类结果对象
	 * @param content  json字符串
	 * @param resultClass 解析json对应的结果类
	 * @return
	 */
	public T parse(String content, Class<T> resultClass) {
		if(BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG, content);
		return JSON.parseObject(content, resultClass);
	}

}