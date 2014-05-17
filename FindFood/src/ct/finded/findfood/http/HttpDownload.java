package ct.finded.findfood.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;
import ct.finded.findfood.BuildConfig;
import ct.finded.findfood.utils.StringUtils;
/**
 * Http下载线程类
 * 
 * @author kuncheng
 * 
 */
public class HttpDownload implements Runnable {
	private final int DEFAULT_BUFFER_SIZE = 5 * 1024; // 默认缓冲区大小

	private long _bytesRead; // 已下载的字节数
	private URL _downloadUrl; // 下载url
	private MultiInputStream _is = null; // 写入流 get方式得到
	private OutputStream _save = null; // 写出流
	private byte[] _buffer; // 下载缓冲区
	private boolean _stop; // 停止下载标志
	private boolean _cancel; // 取消下载标致
	/** 下载线程 */
	private Thread _downloadThread; // 下载线程
//	private ExecutorService _downloadThread;
	/** 是否做限速处理 */
	private boolean _isSlow = false; // 是否做限速处理
	private int mDelayTime = 100;

	private DownloadListener _downloadListener = null;
	/** 进度监听 */
	private ProgressListener _progressListener = null;
	private DivideDownloadListener _divideListener = null;
	private FileInfoListener _fileInfoListener = null;
	/** 是否使用断点续传 */
	private boolean mUseRange = true;
	/** POST内容 */
	private byte[] postContent = null;
	/** 上传文件字节 */
	private byte[] filePartByte = null;
	/** 内容缓存时间 */
	private long time = 0l;
	/** 是否为接口请求 */
	private boolean isInterfaceRequest = false;
	/** 是否支持断点续传 */
	private boolean isSupportRange = false;
	/** 请求时间 */
	private long requestTime = 0l;
	/** 连接时间 */
	private long connectionTime = 0l;
	/** 读取时间 */
	private long readTime = 0l;
	/** 解析时间 */
	private long parseTime = 0l;

	/**
	 * 构造函数，初始化变量，初始化线程
	 */
	public HttpDownload() {
		_bytesRead = 0;
		_stop = false;
		_cancel = false;

		_buffer = new byte[DEFAULT_BUFFER_SIZE];

		_downloadThread = new Thread(this);
//		_downloadThread = Executors.newFixedThreadPool(1);
	}

	/**
	 * 构造函数，初始化变量，初始化线程
	 */
	public HttpDownload(byte[] postContent) {
		_bytesRead = 0;
		_stop = false;
		_cancel = false;

		_buffer = new byte[DEFAULT_BUFFER_SIZE];

		/** 这个干嘛用的 */
		_downloadThread = new Thread(this);
//		_downloadThread = Executors.newFixedThreadPool(1);
		// Log.i(HttpPoster.TAG, "" + postContent);

		this.postContent = postContent;
	}

	/**
	 * 构造函数，初始化变量，初始化线程
	 */
	public HttpDownload(byte[] postContent, byte[] filePartByte) {
		_bytesRead = 0;
		_stop = false;
		_cancel = false;

		_buffer = new byte[DEFAULT_BUFFER_SIZE];

		/** 这个干嘛用的 */
		_downloadThread = new Thread(this);
//		_downloadThread = Executors.newFixedThreadPool(1);
		// Log.i(HttpPoster.TAG, "" + postContent);

		this.postContent = postContent;
		this.filePartByte = filePartByte;
	}
	
	/**
	 * 构造函数，初始化变量，初始化线程
	 */
	public HttpDownload(byte[] postContent, byte[] filePartByte, long time) {
		_bytesRead = 0;
		_stop = false;
		_cancel = false;

		_buffer = new byte[DEFAULT_BUFFER_SIZE];

		/** 这个干嘛用的 */
		_downloadThread = new Thread(this);
//		_downloadThread = Executors.newFixedThreadPool(1);
		// Log.i(HttpPoster.TAG, "" + postContent);

		this.postContent = postContent;
		this.filePartByte = filePartByte;
		this.time = time;
	}

	/**
	 * 设置下载进度事件监听
	 * 
	 * @param e
	 */
	public void setProgressListener(ProgressListener e) {
		this._progressListener = e;
	}

	/**
	 * 设置分段保存事件监听
	 * 
	 * @param e
	 */
	public void setDivideDownloadListener(DivideDownloadListener e) {
		this._divideListener = e;
	}

	/**
	 * 设置下载限速
	 */
	public void setSpeedDown() {
		_isSlow = true;
	}

	/** 设置延时时间 */
	public void setDelayTime(int t) {
		mDelayTime = t;
	}

	/**
	 * 设置停止下载标致
	 * 
	 * @param stop
	 */
	private void setStopped(boolean stop) {
		_stop = stop;
	}

	/**
	 * 取得停止下载标志
	 * 
	 * @return
	 */
	public boolean isStopped() {
		return _stop;
	}

	/**
	 * 取消获得文件信息请求
	 */
	public void cancelOpen() {
		_cancel = true;
	}

	/**
	 * 取消下载
	 */
	public void cancel() {
		// 停止下载
		stopDownload();
	}

	/**
	 * 启动下载 get
	 * 
	 * @param url 下载的url
	 * @param save 下载返回的流
	 * @param e 下载监听
	 */
	public void start(String url, OutputStream save, DownloadListener e) {
		this.start(url, save, 0, e);
	}

	public void start(String url, OutputStream save, DownloadListener e, String encode) {
		this.start(url, save, 0, e, encode);
	}

	/**
	 * 启动下载 post
	 * 
	 * @param url 下载的url
	 * @param save 下载返回的流
	 * @param e 下载监听
	 */
	public void start(String url, OutputStream save, DownloadListener e, byte[] postContent) {
		this.postContent = postContent;
		this.start(url, save, 0, e);
	}

	/**
	 * 启动下载 post
	 * 
	 * @param url 下载的url
	 * @param save 下载返回的流
	 * @param e 下载监听
	 */
	public void startInterfaceRequest(String url, OutputStream save, DownloadListener e, byte[] postContent) {
		isInterfaceRequest = true;
		this.postContent = postContent;
		this.start(url, save, 0, e);
	}

	/**
	 * 启动下载 get
	 * 
	 * @param url 下载url
	 * @param save 下载返回的流
	 * @param startPosition 下载开始的位置
	 */
	public void start(String url, OutputStream save, long startPosition, DownloadListener e) {
		/** 有些url里面存在中文，在请求网络时候需要将中文编码 */
		String strUrl = null;

		// Log.i(HttpPoster.TAG, url);
		strUrl = encode(url);
		// strUrl = url;
		/** 下载监听 */
		_downloadListener = e;
		/** 输出流 */
		_save = save;
		/** 开始位置 */
		_bytesRead = startPosition;

		if (strUrl == null) {
			downloadError(ErrorCode.NETWORK_IO_EXCEPTION);
			return;
		}

		try {
			// Log.i(Config.TAG_PLAYER, strUrl);
			_downloadUrl = new URL(strUrl);

			// 启动下载线程
			_downloadThread.start();
//			_downloadThread.execute(this);
		} catch (MalformedURLException ex) {
			downloadError(ErrorCode.NETWORK_INVALID_URL);
			ex.printStackTrace();
		}
	}

	public void start(String url, OutputStream save, long startPosition, DownloadListener e, String code) {
		/** 有些url里面存在中文，在请求网络时候需要将中文编码 */
		String strUrl = null;
		// Log.e("000000000000000000+++++++++++++", "" + url);
		strUrl = encode(url, code);
		/** 下载监听 */
		_downloadListener = e;
		/** 输出流 */
		_save = save;
		/** 开始位置 */
		_bytesRead = startPosition;

		if (strUrl == null) {
			downloadError(ErrorCode.NETWORK_IO_EXCEPTION);
			return;
		}

		try {
			// Log.i(Config.TAG_PLAYER, strUrl);
			_downloadUrl = new URL(strUrl);

			// 启动下载线程
			_downloadThread.start();
//			_downloadThread.execute(this);
		} catch (MalformedURLException ex) {
			downloadError(ErrorCode.NETWORK_INVALID_URL);
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		performDownload();
	}

	/**
	 * 执行下载函数，从is中读，然后写入_save 设置停止标志_stop，为true是退出循环
	 */
	private void performDownload() {
		// 建立下载连接
		try {
			if(BuildConfig.DEBUG) {
//				StringUtils.requestBegin(_downloadUrl.getPath());
				requestTime = System.currentTimeMillis();
			}
			
			// 此处有点问题
			if (openConnection(true)) {
				if(BuildConfig.DEBUG)
				{
//					StringUtils.requestTask("打开连接", System.currentTimeMillis() - requestTime);
					connectionTime = System.currentTimeMillis() - requestTime;
					requestTime = System.currentTimeMillis();
				}
				// 取得文件大小
				long length = _is.getContentLength();
				
				//不支持断点续传
				if(!isSupportRange) {
					if(_downloadListener != null)
						_downloadListener.onNotSupportRange();
				}

				if (BuildConfig.DEBUG)
					Log.i(HttpPoster.TAG, ">>> total length: " + length);

				if (length > 0) {
					// 文件大于0，则开始下载
					this.start(_is.getContentType(), length, _is.getRealUrl());
					if (!mUseRange && _bytesRead > 0) {
						long i = _is.skipBytes(_bytesRead);
					}
					this.readInputStream(length);
				} else {
					// 否则，资源不存在错误
					downloadError(ErrorCode.NETWORK_IO_EXCEPTION);
				}
			}
		} catch (IOException e) {
			if (BuildConfig.DEBUG) {
				Log.i(HttpPoster.TAG, "HttpDownload:" + e);
				e.printStackTrace();
			}
			downloadError(ErrorCode.NETWORK_IO_EXCEPTION);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.i(HttpPoster.TAG, "HttpDownload:" + e);
				e.printStackTrace();
			}
			downloadError(ErrorCode.NETWORK_IO_EXCEPTION);
		}
	}

	/**
	 * 打开下载链接
	 * 
	 * @param readyDownload 打开后是否准备下载
	 * @return 打开成功true，否则返回false
	 * @throws IOException 打开是出现异常
	 */
	private boolean openConnection(boolean readyDownload) throws IOException {
		// 分段下载
		_is = new MultiInputStream(_downloadUrl, _bytesRead, postContent, filePartByte, time);
		_is.setIsUseRange(mUseRange);
		if (isInterfaceRequest) {
			_is.openInterfaceRequest();
		} else {
			if (readyDownload) {
				// 此处有问题
				isSupportRange = _is.open();
			} else {
				// 不准备下载时，只需打开长度为1的连接
				_is.open(1);
			}
		}

		// 取得返回码
		int responseCode = _is.getResponseCode();

		if (BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG, "返回码是-->" + responseCode);

		if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_PARTIAL) {
			if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
				// 404错误，资源不存在
				if (readyDownload) {
					downloadError(ErrorCode.NETWORK_RESOURCE_NOT_FIND);
				} else {
					openError(ErrorCode.NETWORK_RESOURCE_NOT_FIND);
				}
			} else {
				// 其他错误码，则直接报网络异常错误
				if (readyDownload) {
					downloadError(ErrorCode.NETWORK_IO_EXCEPTION);
				} else {
					openError(ErrorCode.NETWORK_IO_EXCEPTION);
				}
			}
			return false;
		}

		return true;
	}

	/** 读取响应内容 */
	private void readInputStream(long length) {
		int bytesCount = 0;
		// 开始下载
		while (!isStopped()) {
			if (_bytesRead < length) {
				try {
					if (!mUseRange) {
						bytesCount = _is.unableRangeRead(_buffer, _buffer.length, _bytesRead);
					} else {
						bytesCount = _is.read(_buffer);
					}
					if (bytesCount == -1) {
						break;
					} else {
						_bytesRead = _bytesRead + bytesCount;
						_save.flush();
						_save.write(_buffer, 0, bytesCount);
						this.progress(_bytesRead);
						this.divide();
					}
				} catch (IOException e) {
					downloadError(ErrorCode.NETWORK_IO_EXCEPTION);
					e.printStackTrace();
				}

				if (_isSlow && mDelayTime > 0) {
					try {
						Thread.sleep(mDelayTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			} else {
				complete();
				break;
			}
		}
	}

	/**
	 * 获得下载文件信息
	 * 
	 * @param type 文件类型
	 * @param length 文件大小
	 */
	private void fileInfo(String type, long length, String url) {
		if (!this._cancel && this._fileInfoListener != null) {
			this._fileInfoListener.onFileInfo(type, length, url);
		}
	}

	/**
	 * 返回开始下载消息
	 * 
	 * @param length 文件大小
	 */
	private void start(String type, long length, String url) {
		if (!this._cancel && this._downloadListener != null) {
			this._downloadListener.onStart(type, length, url);
		}
	}

	/**
	 * 返回下载进度
	 * 
	 * @param savedLength
	 */
	private void progress(long savedLength) {
		if (!this._cancel && this._progressListener != null) {
			this._progressListener.onProgress(savedLength);
		}
	}

	/**
	 * 返回分段保存文件消息
	 */
	private void divide() {
		if (!this._cancel && this._divideListener != null) {
			this._divideListener.onDivide();
		}
	}

	/**
	 * 返回下载完成消息
	 */
	private void complete() {
		if(BuildConfig.DEBUG)
		{
			readTime = System.currentTimeMillis() - requestTime;
			requestTime = System.currentTimeMillis();
		}
		if (!this._cancel && this._downloadListener != null) {
			this.closeStream();
			this._downloadListener.onComplete();
			
			if(BuildConfig.DEBUG)
			{
				parseTime = System.currentTimeMillis() - requestTime;
				requestTime = System.currentTimeMillis();
			}
		}
	}

	/**
	 * 下载时异常，返回下载出错消息
	 * 
	 * @param errorCode 出错码
	 */
	private void downloadError(int errorCode) {
		if (!this._cancel && this._downloadListener != null) {
			this._downloadListener.onError(errorCode);
			stopDownload();
		}
	}

	/**
	 * 取文件头信息异常，返回出错信息
	 * 
	 * @param errorCode 出错码
	 */
	private void openError(int errorCode) {
		if (!this._cancel && this._fileInfoListener != null) {
			this._fileInfoListener.onError(errorCode);
			stopDownload();
		}
	}

	/**
	 * 停止下载
	 */
	private void stopDownload() {
		this._cancel = true;
		this._downloadListener = null;
		this._progressListener = null;
		this._divideListener = null;

//		try {
//			_downloadThread.shutdownNow();
//		}
//		catch(Exception e) {
//			
//		}
		
		setStopped(true);

		// 关闭流
		closeStream();
	}

	/**
	 * 关闭流
	 */
	private void closeStream() {
		try {
			if (_is != null) {
				_is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			if (_save != null) {
				_save.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载通用事件接口
	 * 
	 * @author kuncheng
	 * 
	 */
	public interface DownloadListener {
		
		void onStart(String type, long length, String url); // 下载开始事件
		
		void onNotSupportRange(); // 不支持断点续传事件

		void onComplete(); // 下载完成事件

		void onError(int errorCode); // 下载出错事件
	}

	/**
	 * 下载大文件分段保存事件接口
	 * 
	 * @author kuncheng
	 * 
	 */
	public interface DivideDownloadListener {
		/** 分段 */
		void onDivide();
	}

	/**
	 * 下载进度事件接口
	 * 
	 * @author kuncheng
	 * 
	 */
	public interface ProgressListener {
		/** 进度 */
		void onProgress(long savedBytes);
	}

	/**
	 * 下载文件信息事件接口
	 * 
	 * @author kuncheng
	 * 
	 */
	public interface FileInfoListener {
		/** 文件信息 */
		void onFileInfo(String type, long length, String url);
		/** 出错 */
		void onError(int errorCode);
	}

	/**
	 * 对url进行编码，处理中文
	 * 
	 * @param url
	 * @return
	 */
	private String encode(String url) {
		// Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(url);
		// while(matcher.find())
		// {
		// String tmp = matcher.group();
		// try
		// {
		// url = url.replaceAll(tmp, URLEncoder.encode(tmp, "gbk"));
		// }
		// catch (UnsupportedEncodingException e)
		// {
		// e.printStackTrace();
		// }
		// }
		return url;
	}

	/** 编码UTF－8 */
	private String encode(String url, String code) {
		Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(url);
		while (matcher.find()) {
			String tmp = matcher.group();
			try {
				url = url.replaceAll(tmp, URLEncoder.encode(tmp, code));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return url;
	}

	/**
	 * 获取响应长度
	 */
	public long getContentLength() {
		return _is.getContentLength();
	}

	/** 设置是否支持断点续传 */
	public void setUseRange(boolean isUseRange) {
		mUseRange = isUseRange;
	}
}
