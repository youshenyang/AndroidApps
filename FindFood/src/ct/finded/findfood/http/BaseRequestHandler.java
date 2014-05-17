package ct.finded.findfood.http;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import ct.finded.findfood.BuildConfig;
import ct.finded.findfood.commons.ConnectionMgr;
import ct.finded.findfood.http.HttpDownload.DownloadListener;
import ct.finded.findfood.http.results.BaseResult;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;



/**
 * 请求处理抽象类。
 * 
 */
public abstract class BaseRequestHandler implements DownloadListener{

	private HttpRequestListener _listener;

	/**
	 * 底层http请求实例
	 */
	private HttpDownload _download = null;
	/** 输出流 */
	private ByteArrayOutputStream _os = null;

	private BaseRequest request;

	/** 缓存的离线数据 */
	private BaseResult cacheResult;

	protected String mUUid;

	private boolean _cancel = false;

	private Handler mHandler = new Handler();
	
	/** 页数 */
	protected int page;
	/** 每页记录数 */
	protected int length;

	/**
	 * 处理请求 post
	 * 
	 * @param request
	 * @param listener
	 */
	public void execute(BaseRequest request, HttpRequestListener listener, byte[] postContent, Context c) {
		this.request = request;

		String url = request.getUrl();
		// Logs.i(getClass().getSimpleName(), "request url:" + url);
		if(BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG, url);
		_listener = listener;
//		if (!ConnectionMgr.getInstance(c).isNetworkAvailable()) {
//			onError(ErrorCode.NETWORK_IO_EXCEPTION);
//		} else {
			/**
			 * 下载线程 在此次要处理请求包
			 * */
			_download = new HttpDownload(postContent);

			/** 输出流保存 */
			_os = new ByteArrayOutputStream();
			_os.reset();

//			_download.start(url, _os, this, postContent);
			_download.startInterfaceRequest(url, _os, this, postContent);
//		}
	}
	
	/**
	 * 处理上传请求 post
	 * 
	 * @param request
	 * @param listener
	 */
	public void upload(BaseRequest request, HttpRequestListener listener, byte[] postContent, String filePath, Context c) {
		this.request = request;

		String url = request.getUrl();
		// Logs.i(getClass().getSimpleName(), "request url:" + url);
		if(BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG, url);
		_listener = listener;
		if (!ConnectionMgr.getInstance(c).isNetworkAvailable()) {
			onError(ErrorCode.NETWORK_IO_EXCEPTION);
		} else {
			byte[] filePartByte = readFile(filePath);
			if(filePartByte == null) {
				onError(ErrorCode.NETWORK_IO_EXCEPTION);
				return ;
			}
			
			/**
			 * 下载线程 在此次要处理请求包
			 * */
			_download = new HttpDownload(postContent, filePartByte);

			/** 输出流保存 */
			_os = new ByteArrayOutputStream();
			_os.reset();

//			_download.start(url, _os, this, postContent);
			_download.startInterfaceRequest(url, _os, this, postContent);
		}
	}
	
	/**
	 * 处理上传请求 post
	 * 
	 * @param request
	 * @param listener
	 */
	public void upload(BaseRequest request, HttpRequestListener listener, byte[] postContent, String filePath, long time, Context c) {
		this.request = request;

		String url = request.getUrl();
		// Logs.i(getClass().getSimpleName(), "request url:" + url);
		if(BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG, url);
		_listener = listener;
		if (!ConnectionMgr.getInstance(c).isNetworkAvailable()) {
			onError(ErrorCode.NETWORK_IO_EXCEPTION);
		} else {
			byte[] filePartByte = readFile(filePath);
			if(filePartByte == null) {
				onError(ErrorCode.NETWORK_IO_EXCEPTION);
				return ;
			}
			
			/**
			 * 下载线程 在此次要处理请求包
			 * */
			_download = new HttpDownload(postContent, filePartByte, time);

			/** 输出流保存 */
			_os = new ByteArrayOutputStream();
			_os.reset();

//			_download.start(url, _os, this, postContent);
			_download.startInterfaceRequest(url, _os, this, postContent);
		}
	}
	
	/**
	 * 读取文件，返回字节数组
	 * @param filePath 文件路径
	 * @return
	 */
	private byte[] readFile(String filePath) {
		ByteArrayOutputStream baos = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			baos = new ByteArrayOutputStream(1024);
			byte[] data = new byte[512];
			int n = -1;
			while((n = fis.read(data)) != -1) {
				baos.write(data, 0, n);
			}
			byte result[] = baos.toByteArray();
			return result;
		} catch(Exception e) {
			return null;
		} finally {
			try {
				if(fis != null)
					fis.close();
				if(baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 采用post方式请求网络
	 */

	/**
	 * 取消请求
	 */
	public void cancel() {
		_cancel = true;
		if (_download != null) {
			_download.cancel();
		}
	}

	/** 实现下载监听器，下载开始时*/
	@Override
	public void onStart(String type, long length, String url) {
	}

	
	/** 实现下载监听器，下载完成时*/
	@Override
	public void onComplete() {
		if(BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG, "文件下载完成");
		// 解密
		doComplete(parse(_os));
	}

	/** 实现下载监听器，下载出错时*/
	@Override
	public void onError(final int errorCode) {
		if (_listener != null && !_cancel) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					if (request != null && request.needCache()) { // 请求需要离线
						_listener.onHttpRequestCompleted(cacheResult);
					}
					else
						_listener.onHttpRequestError(errorCode);
				}
			});
		}
		close();
	}

	/**
	 * 处理请求完成回调事件
	 * 
	 * @param result
	 */
	private void doComplete(final BaseResult result) {
		if (_listener != null && !_cancel) {
			mHandler.post(new Runnable() {
				public void run() {
					// 当请求响应成功，且需要使用离线数据，并服务器数据有更新时，保存离线数据
					if (result != null && result.requestSuccess()) // 请求成功
					{
						if (request != null && request.needCache()) { // 请求需要离线
							if (result.isUsecache()) // 使用缓存数据
								_listener.onHttpRequestCompleted(cacheResult);
							else { // 结果不使用缓存数据，有更新
								_listener.onHttpRequestCompleted(result);
								saveOfflineData();
							}
						} else
							// 不使用离线数据
							_listener.onHttpRequestCompleted(result);
					} else { // 请求失败
						if (request != null && request.needCache()) { // 请求需要离线
							_listener.onHttpRequestCompleted(cacheResult);
						}
						else
							_listener.onHttpRequestCompleted(result);
					}
					close();
				}
			});
		}
		else
			close();
	}

	/**保存数据到本地*/
	private void saveOfflineData() {
		//OfflineDataManager.getInstance().save(request, _os);
	}
	
	/**关闭输出流 */
	private void close() {
		try {
			if(_os != null) {
				_os.flush();
				_os.close();
				_os = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**设置mUUid值*/
	public void setUUID(String uuid) {
		mUUid = uuid;
	}

	/** 解析HTTP响应数据或者离线数据 */
	public BaseResult parse(ByteArrayOutputStream os) {
		return parseResult(os);
	}

	/**
	 * 解析返回结果
	 * 
	 * @param os
	 * @return
	 */
	protected abstract BaseResult parseResult(ByteArrayOutputStream os);

	/** 获取页数*/
	public int getPage() {
		return page;
	}
	/** 设置页数*/
	public void setPage(int page) {
		this.page = page;
	}
	/** 获取每页记录数*/
	public int getLength() {
		return length;
	}
	/** 设置每页记录数*/
	public void setLength(int length) {
		this.length = length;
	}

	/** 设置为下一页*/
	public void setNextPage() {
		page++;
	}

	/** 设置缓存结果cacheResult*/
	public void setCacheResult(BaseResult cacheResult) {
		this.cacheResult = cacheResult;
	}
	/** 设置mHandler*/
	public void setHandler(Handler handler){
		this.mHandler = handler;
	}

	/**实现下载监听器中，不支持断点续传事件 */
	@Override
	public void onNotSupportRange() {
		
	}
}
