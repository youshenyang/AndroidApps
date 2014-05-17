package ct.finded.findfood.http;

import android.util.Log;
import ct.finded.findfood.BuildConfig;
import ct.finded.findfood.FoodApplication;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.utils.StringUtils;


/**
 * 接口请求帮助类，所有的请求通过RequestHelper类处理
 * 
 */
public class RequestHelper {
	
	private LoadState loadState = new LoadState();

	/**
	 * 
	 * @ClassName: OnRequestListener
	 * @Description: 接口请求监听器
	 * @author Shy_You
	 * @date 2014年4月28日
	 */
	public interface OnRequestListener {
		/** 接口请求开始，可实现此方法显示加载提示对话框等 */
		void onRequestStart();

		/** 接口请求被取消 */
		void onRequestCancel();

		/** 接口请求成功，子类可实现此方法显示数据 */
		void onRequestComplete(BaseResult result);

		/** 接口请求失败，子类可实现此方法做一些提示 */
		void onRequestFailed(String errorString);
	}

	/**
	 * 构造函数
	 * 
	 * @param requestListener
	 */
	public RequestHelper(OnRequestListener requestListener) {
		this.requestListener = requestListener;
//		WoApplication.getInstance().getApplicationContext() = WoApplication.getInstance().getApplicationContext();
	}

	private BaseRequestHandler requestHandler = null;
	private OnRequestListener requestListener = null;
//	private Context WoApplication.getInstance().getApplicationContext();

	/**Http请求监听器*/
	private HttpRequestListener httpRequestListener = new HttpRequestListener() {

		@Override
		public void onHttpRequestCompleted(BaseResult result) {//请求完成时
			if (result == null) {
				doRequestFailed("获取异常");
				return;
			}
			if (result.requestSuccess()) {//请求成功时
				doReuqetsSuccess(result);
			} else {
				//请求失败时
				String failedString = result.getDesc();
				if (failedString == null || failedString.trim().length() == 0) {
					failedString ="网络链接异常，请重试";
				}
				doRequestFailed(failedString);
			}
		}

		@Override
		public void onHttpRequestError(int errorCode) { //请求出错时
			doRequestFailed("网络链接异常，请重试");
		}
	};

	/**
	 * Http接口请求
	 * 
	 * @return void
	 * 
	 * @Author xiaoxh@lenovo-cw.com
	 * @timer 2013-8-20 下午3:28:45
	 */
	public synchronized void doRequest(BaseRequest request) {
		if(loadState.isLoading())
			return ;

		String postContent = request.getPostContent();
		if(BuildConfig.DEBUG) {
			Log.i(HttpPoster.TAG, "请求URL：" + request.getUrl());
			Log.i(HttpPoster.TAG, "Post Data：" + postContent);
		}
				
		BaseResult cache = null;
		// 判断该请求是否使用缓存
		if (request.needCache()) {
			// 从缓存中取数据
			cache = null;//OfflineDataManager.getInstance().get(request, postContent);
			if (cache != null && cache.requestSuccess()) {
				long time = cache.getTime();
				StringBuffer postBuffer = StringUtils.getBuffer().append(postContent).append("&time=").append(time);
				requestHandler = HttpPoster.execute(request, httpRequestListener, postBuffer.toString(), cache, FoodApplication.getInstance().getApplicationContext());
			} else {
				// 缓存中不存在数据，则直接从网络获取
				requestHandler = HttpPoster.execute(request, httpRequestListener, postContent, null, FoodApplication.getInstance().getApplicationContext());
			}
		} else {
			requestHandler = HttpPoster.execute(request, httpRequestListener, postContent, null, FoodApplication.getInstance().getApplicationContext());
		}
		doRequestStart();
	}
	
	/**
	 * Http上传文件请求
	 */
	public void doUpload(BaseRequest request, String filePath) {

		String postContent = request.getPostContent();
		if(BuildConfig.DEBUG)
		{
			Log.i(HttpPoster.TAG, "请求URL：" + request.getUrl());
			Log.i(HttpPoster.TAG, "Post Data：" + postContent);
		}
		
		requestHandler = HttpPoster.upload(request, httpRequestListener, postContent, filePath, FoodApplication.getInstance().getApplicationContext());
		doRequestStart();
	}
	
	/**
	 * Http上传文件请求
	 * 
	 * @return void
	 * 
	 * @Author xiaoxh@lenovo-cw.com
	 * @timer 2013-8-20 下午3:28:45
	 */
	public void doUpload(BaseRequest request, String filePath, long time) {

		String postContent = request.getPostContent();
		if(BuildConfig.DEBUG)
		{
			Log.i(HttpPoster.TAG, "请求URL：" + request.getUrl());
			Log.i(HttpPoster.TAG, "Post Data：" + postContent);
		}
		
		requestHandler = HttpPoster.upload(request, httpRequestListener, postContent, filePath, time, FoodApplication.getInstance().getApplicationContext());
		doRequestStart();
	}

	/**
	 * 取消请求
	 * 
	 * @return void
	 * 
	 * @Author xiaoxh@lenovo-cw.com
	 * @timer 2013-8-20 下午3:31:37
	 */
	public void cancelRequest() {
		loadState.setLoaded();
		if (requestHandler != null) {
			requestHandler.cancel();
			requestHandler = null;
			if (requestListener != null) {
				requestListener.onRequestCancel();
			}
		}
	}

	/**请求成功时*/
	private void doReuqetsSuccess(BaseResult result) {
		loadState.setLoaded();
		if (requestListener != null) {
			requestListener.onRequestComplete(result);
		}
	}

	/**请求失败时*/
	private void doRequestFailed(String errString) {
		loadState.setLoadFail();
		if (requestListener != null) {
			requestListener.onRequestFailed(errString);
		}
	}

	/**请求开始时*/
	private void doRequestStart() {
		loadState.setLoading();
		if (requestListener != null) {
			requestListener.onRequestStart();
		}
	}
}