package ct.finded.findfood.http;

/**
 * 业务请求数据抽象类，方法由子类实现
 */
public interface BaseRequest {

	/** 构造请求POST数据，封装具体的每个接口的请求数据 */
	String getPostContent();

	/**
	 * 获取请求url
	 */
	String getUrl();

	/** 是否需要缓存 */
	boolean needCache();

	/**获取请求处理对象*/
	BaseRequestHandler getRequestHandler();
}
