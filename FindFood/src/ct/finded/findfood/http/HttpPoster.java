package ct.finded.findfood.http;

import ct.finded.findfood.http.results.BaseResult;
import android.content.Context;


/**
 * http模块统一入口。 命令模式:该类接收各业务模块下发的http请求对象，通过java反射机制交给具体的请求处理类去执行，最后将结果回调给上层。
 * 
 */
public class HttpPoster {

	public static final String TAG = "HttpPoster";

	/**
	 * 接收并执行请求 post
	 * 
	 * @param request 具体的请求对象
	 * @param listener 请求监听对象
	 * @return 处理此请求的对象,一次请求对应一个唯一的处理对象
	 */
	public static BaseRequestHandler execute(BaseRequest request, HttpRequestListener listener, String postContent, BaseResult cacheResult, Context c) {
		if (postContent == null) {
			return null;
		}
		try {
			BaseRequestHandler handler = request.getRequestHandler();
			//handler.setCacheResult(cacheResult);
			handler.execute(request, listener, postContent.getBytes("utf-8"), c);
			return handler;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 接收并执行请求 post
	 * 
	 * @param request 具体的请求对象
	 * @param listener 请求监听对象
	 * @return 处理此请求的对象,一次请求对应一个唯一的处理对象
	 */
	public static BaseRequestHandler upload(BaseRequest request, HttpRequestListener listener, String postContent, String filePath, Context c) {
		if (postContent == null) {
			return null;
		}
		try {
			// 设置时间戳
			BaseRequestHandler handler = request.getRequestHandler();
			handler.upload(request, listener, postContent.getBytes("utf-8"), filePath, c);
			return handler;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 接收并执行请求 post
	 * 
	 * @param request 具体的请求对象
	 * @param listener 请求监听对象
	 * @return 处理此请求的对象,一次请求对应一个唯一的处理对象
	 */
	public static BaseRequestHandler upload(BaseRequest request, HttpRequestListener listener, String postContent, String filePath, long time, Context c) {
		if (postContent == null) {
			return null;
		}
		try {
			// 设置时间戳
			BaseRequestHandler handler = request.getRequestHandler();
			handler.upload(request, listener, postContent.getBytes("utf-8"), filePath, time, c);
			return handler;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 取消请求
	 * 
	 * @param handler
	 */
	public static void cancel(BaseRequestHandler handler) {
		handler.cancel();
	}
}
