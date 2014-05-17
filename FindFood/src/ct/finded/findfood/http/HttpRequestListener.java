package ct.finded.findfood.http;

import ct.finded.findfood.http.results.BaseResult;



/**
 * http请求监听类
 * 
 */
public interface HttpRequestListener {

	/**
	 * 请求处理完成
	 */
	void onHttpRequestCompleted(BaseResult result);

	/**
	 * 请求处理出错
	 */
	void onHttpRequestError(int errorCode);
}
