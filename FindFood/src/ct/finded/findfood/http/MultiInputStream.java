package ct.finded.findfood.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ct.finded.findfood.BuildConfig;
import ct.finded.findfood.utils.StringUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;


/**
 * 分段下载请求类
 */
public class MultiInputStream extends InputStream
{
	
	private URL _url = null; // http请求URL
	private HttpURLConnection _conn = null; // http连接
	
	private int _contentLength = 0; // 请求文件返回的长度
	private String _contentType = null; // 请求文件的类型
	private String _realUrl = null;	//重定向后真正请求的URL
	private int _responseCode = 0; // 请求的返回码
	private InputStream _is = null; // 当前连接打开的流
	private long _readPos = 0; // 以读取文件的位置
	private long _startPos = 0; // 缓冲区的开始位置
	private long _endPos = 0; // 缓冲区的结束位置
	
	private int _requestCount = 0; // 请求的次数
	
	private final int DEFAULT_CONNECT_TIME = 10 * 1000; // 默认连接时间
	private final int UPLOAD_CONNECT_TIME = 60 * 1000; // 上传文件连接时间
	private final int DEFAULT_READ_TIMEOUT = 20 * 1000; // 读取超时时间
	// private final static int DEFAULT_READ_TIME = 30 * 1000;
	private final long DEFAULT_BLOCK_SIZE = 200 * 1024; // 默认缓冲块大小
	private final long INTERFACE_BLOCK_SIZE = 150 * 1024; // 默认缓冲块大小
	/** 是否使用断点续传 */
	private boolean mUseRange = true;
	/** POST内容 */
	private byte[] postContent = null;
	/** 文件上传字节内容 */
	private byte[] filePartByte = null;
	/** 缓存时间 */
	private long time = 0l;
	/** 是否支持断点续传 */
	private boolean isSupportRange = false;
	/**若干构造函数 */
	public MultiInputStream(String url) throws MalformedURLException
	{
		super();
		this._url = new URL(url);
		this._startPos = 0;
	}
	
	/** 构造函数 */
	public MultiInputStream(URL url)
	{
		super();
		this._url = url;
		this._startPos = 0;
	}
	
	/** 构造函数 */
	public MultiInputStream(String url, long startPosition) throws MalformedURLException
	{
		super();
		this._url = new URL(url);
		this._startPos = startPosition;
	}
	
	/** 构造函数 */
	public MultiInputStream(URL url, long startPosition)
	{
		super();
		this._url = url;
		this._startPos = startPosition;
	}
	
	/** 构造函数 */
	public MultiInputStream(URL url, long startPosition, byte[] postContent, byte[] filePartByte)
	{
		super();
//		Log.e("********url", "" + url);
		this._url = url;
		this._startPos = startPosition;
//		Log.e("********postContent", "" + postContent);
		this.postContent = postContent;
		this.filePartByte = filePartByte;
	}
	
	/** 构造函数 */
	public MultiInputStream(URL url, long startPosition, byte[] postContent, byte[] filePartByte, long time)
	{
		super();
//		Log.e("********url", "" + url);
		this._url = url;
		this._startPos = startPosition;
//		Log.e("********postContent", "" + postContent);
		this.postContent = postContent;
		this.filePartByte = filePartByte;
		this.time = time;
	}
	
	/** 接口请求 */
	public void openInterfaceRequest() throws IOException
	{
		int retryCnt = 0;
		do {
			try {
				closeConnection();
				preOpen(INTERFACE_BLOCK_SIZE);
			}
			catch(Exception e) {
				if (e.getMessage() != null && e.getMessage().contains("Connection reset by peer")) {
					//WifiStateManager.getInstance().setIsLimit(true);
				}
			}
			retryCnt++;
		}
		while (_contentLength < 0 && retryCnt < 3);
		
		 // 取得文件类型
		_realUrl = _conn.getURL().toString();
		String fileType = MimeTypeMap.getFileExtensionFromUrl(_realUrl);
		if(fileType != null && fileType.trim().length() > 0)
		{
			_contentType = fileType;
		}
		else
		{
			_contentType = _conn.getContentType();
		}
		
		_readPos = _startPos;
		
		if (_contentLength > _startPos + INTERFACE_BLOCK_SIZE)
		{
			_endPos = _startPos + INTERFACE_BLOCK_SIZE - 1;
		}
		else
		{
			_endPos = _contentLength - 1;
		}
	}
	
	/** 打开连接 */
	public boolean open() throws IOException
	{
		return open(DEFAULT_BLOCK_SIZE);
	}
	
	/** 打开连接 */
	public boolean open(long readSize) throws IOException
	{
		int retryCnt = 0;
		do {
			try {
				closeConnection();
				preOpen(readSize);
			}
			catch(Exception e) {
				if (e.getMessage() != null && e.getMessage().contains("Connection reset by peer")) {
					//WifiStateManager.getInstance().setIsLimit(true);
				}
			}
			retryCnt++;
		}
		while (_contentLength < 0 && retryCnt < 10);
		
		 // 取得文件类型
		_realUrl = _conn.getURL().toString();
		String fileType = MimeTypeMap.getFileExtensionFromUrl(_realUrl);
		if(fileType != null && fileType.trim().length() > 0)
		{
			_contentType = fileType;
		}
		else
		{
			_contentType = _conn.getContentType();
		}
		
		_readPos = _startPos;
		
		String range = _conn.getHeaderField("Content-Range");
		isSupportRange = (range == null ? false : true);
		if(BuildConfig.DEBUG) {
			Log.i(HttpPoster.TAG, "是否支持断点续传=" + isSupportRange);
		}
		if (_contentLength > _startPos + DEFAULT_BLOCK_SIZE && isSupportRange)
		{
			_endPos = _startPos + DEFAULT_BLOCK_SIZE - 1;
		}
		else
		{
			_startPos = 0;
			_endPos = _contentLength - 1;
		}
		return isSupportRange;
	}
	
	/** 打开连接 */
	private void preOpen(long readSize) throws IOException {
		
		_requestCount = 0;
		//这个地方有问题
		openConnection(_startPos, _startPos + readSize - 1,postContent);
		
		/*取得文件类型，判断是否是wml*/
		_contentType = _conn.getContentType();
		if (_contentType != null && _contentType.startsWith("text/vnd.wap.wml"))
		{
			// 若返回资费信息的WML，则再请求一次
			_conn.disconnect();
			openConnection(_startPos, _startPos + readSize - 1,postContent);
		}
		
		/*取得返回码，确定是否为重定向*/
		_responseCode = _conn.getResponseCode();
		while (_responseCode == HttpURLConnection.HTTP_MOVED_TEMP)
		{
			_url = new URL(_conn.getHeaderField("Location"));
			openConnection(0, DEFAULT_BLOCK_SIZE - 1,postContent);
			_responseCode = _conn.getResponseCode();
		}
		
		// 取得文件总长度
		String range = _conn.getHeaderField("Content-Range");
		if(BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG, "当前range = " + range);
		if (range == null)
		{
			_contentLength = _conn.getContentLength();
		}
		else
		{
			_contentLength = Integer.parseInt(range.substring(range.indexOf("/") + 1));
		}
		if(BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG, "当前contentLength = " + _contentLength);
	}
	
	/** 获取连接地址 */
	public String getRealUrl()
	{
		return _realUrl;
	}
	
	/** 获取contentType */
	public String getContentType()
	{
		return _contentType;
	}
	
	/** 获取内容长度 */
	public int getContentLength()
	{
		return _contentLength;
	}
	
	/** 获取响应状态码 */
	public int getResponseCode() throws IOException
	{
		return _responseCode;
	}
	
	@Override
	public int read() throws IOException
	{
		if (_startPos >= _contentLength)
		{
			// 流已经读完，
			return -1;
		}
		else
		{
			int n = _is.read();
			if (n != -1)
			{
				return n;
			}
			else
			{
				if(BuildConfig.DEBUG)
					Log.i(HttpPoster.TAG, "openNextHttpConnection begin");
				openNextHttpConnection();
				if(BuildConfig.DEBUG)
					Log.i(HttpPoster.TAG, "openNextHttpConnection end");
				return read();
			}
		}
	}
	
	@Override
	public int available() throws IOException
	{
		// TODO Auto-generated method stub
		return super.available();
	}
	
	@Override
	public void close() throws IOException
	{
		closeConnection();
	}
	
	@Override
	public void mark(int readlimit)
	{
		super.mark(readlimit);
	}
	
	@Override
	public boolean markSupported()
	{
		return super.markSupported();
	}
	
	/** 处理读取错误 */
	private int handleReadError(byte[] b, int offset, int length, byte[] tempBuf, int fillLen) throws IOException {
		if (_readPos >= _contentLength) {
			return -1;
		}
		System.arraycopy(tempBuf, 0, b, offset, fillLen);
		_endPos = _readPos - 1;
		if(BuildConfig.DEBUG)
		{
			Log.i(HttpPoster.TAG, "ERROR: _readPos = " + _readPos);
			Log.i(HttpPoster.TAG, "openNextHttpConnection begin");
		}
		openNextHttpConnection();
		if(BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG, "openNextHttpConnection end");
		
		int num = read(b, offset + fillLen, length - fillLen);
		// 由于是上一个http的分段文件的的末尾,我们返回的是 0;
		if (num != -1)
		{
			// 有数据
			return num + fillLen;
		}
		else
		{
			// 无数据
			return fillLen;
		}
	}
	
	/** 不使用断点续传读取 */
	public int unableRangeRead(byte[] b, int length, long bytesRead) throws IOException{
		int requestLength = length;
		// 当前连接剩余的字节数
		int leftCurrentDataLength = (int) (this._contentLength - bytesRead);
		// 保存从流中读取的数据的临时数组
		byte[] tempBuf = null;
		// 条件都是从是否要新的http的请求来制定的
		if (_startPos >= _contentLength)
		{
			return -1;
		}
		if (leftCurrentDataLength >= requestLength)
		{
			// 当前的流的字节数足够返回
			tempBuf = new byte[requestLength];
			int fillLen = this.fillData(tempBuf);
			if (fillLen < requestLength) {
				System.arraycopy(tempBuf, 0, b, 0, fillLen);
				return fillLen;
			} else {
				System.arraycopy(tempBuf, 0, b, 0, requestLength);
				return requestLength;
			}
		}
		else
		{
			// 当前的流的字节数不足
			tempBuf = new byte[leftCurrentDataLength];
			int fillLen = this.fillData(tempBuf);
			if (fillLen < leftCurrentDataLength) {
				System.arraycopy(tempBuf, 0, b, 0, fillLen);
				return fillLen;
			}
			System.arraycopy(tempBuf, 0, b, 0, leftCurrentDataLength);
			return leftCurrentDataLength;
		}
	}
	
	@Override
	public int read(byte[] b, int offset, int length) throws IOException
	{
		// 请求的byte字节数
//		if(BuildConfig.DEBUG)
//			Log.i(HttpPoster.TAG, "offset: " + offset);
		int requestLength = length;
		// 当前连接剩余的字节数
		int leftCurrentDataLength = (int) (this._endPos - this._readPos + 1);
		// 保存从流中读取的数据的临时数组
		byte[] tempBuf = null;
		// 条件都是从是否要新的http的请求来制定的
		if (_startPos >= _contentLength)
		{
			return -1;
		}
		if (leftCurrentDataLength >= requestLength)
		{
			// 当前的流的字节数足够返回
			tempBuf = new byte[requestLength];
			int fillLen = this.fillData(tempBuf);
			if (fillLen < requestLength) {
				return handleReadError(b, offset, length, tempBuf, fillLen);
//				// 开启另一个HTTP请求
//				System.arraycopy(tempBuf, 0, b, offset, fillLen);
//				_endPos = _readPos;
//				openNextHttpConnection();
//				
//				int num = read(b, offset + fillLen, length - fillLen);
//				// 由于是上一个http的分段文件的的末尾,我们返回的是 0;
//				if (num != -1)
//				{
//					// 有数据
//					return num + fillLen;
//				}
//				else
//				{
//					// 无数据
//					return fillLen;
//				}
			} else {
				System.arraycopy(tempBuf, 0, b, offset, requestLength);
				return requestLength;
			}
		}
		else
		{
			// 当前流的字节数不足请求的
			// 拷贝当前数据到buf[],并打开一个新的连接
			tempBuf = new byte[leftCurrentDataLength];
			int fillLen = this.fillData(tempBuf);
			if (fillLen < leftCurrentDataLength) {
				return handleReadError(b, offset, length, tempBuf, fillLen);
			}
			System.arraycopy(tempBuf, 0, b, offset, leftCurrentDataLength);
			if(BuildConfig.DEBUG)
				Log.i(HttpPoster.TAG, "openNextHttpConnection begin");
			openNextHttpConnection();
			if(BuildConfig.DEBUG)
				Log.i(HttpPoster.TAG, "openNextHttpConnection end");
			
			int num = read(b, offset + leftCurrentDataLength, length - leftCurrentDataLength);
			// 由于是上一个http的分段文件的的末尾,我们返回的是 0;
			if (num != -1)
			{
				// 有数据
				return num + leftCurrentDataLength;
			}
			else
			{
				// 无数据
				return leftCurrentDataLength;
			}
		}
	}
	
	@Override
	public int read(byte[] b) throws IOException
	{
		return read(b, 0, b.length);
	}
	
	@Override
	public synchronized void reset() throws IOException
	{
		super.reset();
	}
	
	@Override
	public long skip(long n) throws IOException
	{
		return super.skip(n);
	}
	
	public long skipBytes(long n) throws IOException {
		if (_is != null) {
			long i = skipBytesFromStream(_is, n);
			return i;
		}
		return -1;
	}
	
	/*重写了Inpustream 中的skip(long n) 方法，将数据流中起始的n 个字节跳过*/  
	 private long skipBytesFromStream(InputStream inputStream, long n) {  
	  long remaining = n;  
	  // SKIP_BUFFER_SIZE is used to determine the size of skipBuffer  
	  int SKIP_BUFFER_SIZE = 2048;  
	  // skipBuffer is initialized in skip(long), if needed.  
	  byte[] skipBuffer = null;  
	  int nr = 0;  
	  if (skipBuffer == null) {  
	   skipBuffer = new byte[SKIP_BUFFER_SIZE];  
	  }  
	  byte[] localSkipBuffer = skipBuffer;  
	  if (n <= 0) {  
	   return 0;  
	  }  
	  while (remaining > 0) {  
	   try {  
		if (inputStream != null) {
			nr = inputStream.read(localSkipBuffer, 0,  
					(int) Math.min(SKIP_BUFFER_SIZE, remaining));  
		} else {
			nr = -1;
		}
	   } catch (IOException e) {  
	    e.printStackTrace();  
	   }  
	   if (nr < 0) {  
	    break;  
	   }  
	   remaining -= nr;  
	  }  
	  return n - remaining;  
	 }
	
	/**
	 * 填充当前缓冲块数据到buf[]中
	 * 
	 * @param buf
	 * @return
	 * @throws IOException
	 */
	private synchronized int fillData(byte[] buf) throws IOException
	{
		int size = buf.length;
		int readDataSize = 0;
		int remainDataSize = size;
		int alreadyReadDataSize = 0;
		// 确保缓冲区数据下载满
		while (alreadyReadDataSize < size && readDataSize != -1 && _is != null)
		{
			readDataSize = _is.read(buf, alreadyReadDataSize, remainDataSize);
			if (readDataSize == -1)
			{
				break;
			}
			alreadyReadDataSize += readDataSize;
			remainDataSize = size - alreadyReadDataSize;
		}
		this._readPos += alreadyReadDataSize;
		return alreadyReadDataSize;
	}
	
	
	/**
	 * 当前连接的数据已经读完，打开下一个http连接
	 * 
	 * @throws IOException
	 */
	private void openNextHttpConnection() throws IOException
	{
		// 关闭上一次连接
		closeConnection();
		
		if(BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG,  "_endPos = " + _endPos + ", _readPos = " + _readPos);
		_startPos = _endPos + 1;
		if(BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG, "_startPos = " + _startPos);
		if (_startPos >= _contentLength && _startPos != 0)
		{
			_startPos = _contentLength;
			return;
		}
		
		_endPos = _startPos + DEFAULT_BLOCK_SIZE - 1;
		if (_endPos >= _contentLength)
		{
			// 最后一次的数据不够一个缓冲块
			_endPos = _contentLength - 1;
		}
		
		int maxTryCnt = 3;
		int tryCnt = 0;
		while (tryCnt <= maxTryCnt) {
			try {
				openConnection(_startPos, _endPos,postContent);
				break;
			} catch (IOException e) {
				if (tryCnt < maxTryCnt) {
					e.printStackTrace();
					continue;
				} else {
					throw e;
				}
			}
		}
	}
	
	
	
	/**
	 * 打开http连接    
	 * 
	 * @param start
	 *            开始字节位置
	 * @param end
	 *            结束字节位置
	 * @throws IOException
	 */
	private void openConnection(long start, long end, byte[] postContent) throws IOException
	{
		if(BuildConfig.DEBUG)
			Log.i(HttpPoster.TAG, "start=" + start + "; end=" + end);
		
		_requestCount++;
		
		//xiaoxh added, 设置3.0版本的请求头部信息
		if(filePartByte != null) {
			if(time > 0)
			{
				setErrorLogProperties();
			}
			else {
				setUploadProperties();
			}
			
			int code = _conn.getResponseCode();
			if(BuildConfig.DEBUG)
				Log.i(HttpPoster.TAG, "code = " + code);
			_is = _conn.getInputStream();
//			dos.close();
		}
		else {
			_conn = (HttpURLConnection) _url.openConnection();
			_conn.setConnectTimeout(DEFAULT_CONNECT_TIME);
			_conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
			if(postContent == null)
			{
				// _conn.setReadTimeout(DEFAULT_READ_TIME);
				_conn.setRequestProperty("Connection", "Keep-Alive");
				if (mUseRange) {
					_conn.setRequestProperty("RANGE", "bytes=" + start + "-" + end);
				}
				
//				_conn.addRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
//				_conn.setRequestProperty( "Connection", "close" );
				
				//setHeaders(_conn);
				
				_conn.setDoOutput( true );
				_conn.setDoInput( true );
				_conn.setRequestMethod( "POST" );
				
				_conn.connect();
				_is = _conn.getInputStream();
			}else{
				_conn.setRequestProperty("RANGE", "bytes=" + start + "-" + end);
				
				_conn.addRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
				
				//setHeaders(_conn);
				
				_conn.setRequestProperty( "Connection", "close" );
				_conn.setDoOutput( true );
				_conn.setDoInput( true );
				_conn.setRequestMethod( "POST" );
				
				_conn.getOutputStream().write(postContent);
				_conn.connect();
				
				_is = _conn.getInputStream();
			}
		}
		
	}
	
	/** 设置上传参数 */
	private void setUploadProperties() throws IOException {
		_conn = (HttpURLConnection) _url.openConnection();
		_conn.setConnectTimeout(UPLOAD_CONNECT_TIME);
		_conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
		
		String endHyphens = "\r\n";
		String twoHyphens = "--";
		String boundary = "****";
		
		_conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
		
		//setHeaders(_conn);
		
		_conn.setRequestProperty( "Connection", "Keep-Alive" );
		_conn.setDoOutput( true );
		_conn.setDoInput( true );
		_conn.setRequestMethod( "POST" );
		
		DataOutputStream dos = new DataOutputStream(_conn.getOutputStream());
		
		StringBuffer buffer = StringUtils.getBuffer(1000);
		StringBuffer sb = StringUtils.getBuffer(200);
		sb.append(twoHyphens).append(boundary).append(endHyphens);
		//传appid
		sb.append("Content-Disposition: form-data; name=\"appid\"").append(endHyphens).append(endHyphens);
		//sb.append(Config.APPID);
		sb.append(endHyphens);
		
		//传validatecode
		sb.append(twoHyphens).append(boundary).append(endHyphens);
		sb.append("Content-Disposition: form-data; name=\"validatecode\"").append(endHyphens).append(endHyphens);
		//sb.append(MD5.toMd5(DES3.encryptThreeDESECB(Config.APPID+Config.APP_SECRET)));
		sb.append(endHyphens);
		buffer.append(sb.toString());
		dos.write(sb.toString().getBytes());
		
		sb = StringUtils.getBuffer(200);
		sb.append(twoHyphens).append(boundary).append(endHyphens);
		sb.append("Content-Disposition: form-data; name=\"contractfile\"; filename=\"my.png\"").append(endHyphens);
		sb.append("Content-Type: image/png").append(endHyphens);
		sb.append(endHyphens);
		
		dos.write(sb.toString().getBytes());
		
		dos.write(filePartByte);
		
		sb = StringUtils.getBuffer(30);
		sb.append(endHyphens);
		sb.append(twoHyphens).append(boundary).append(twoHyphens).append(endHyphens);
		
		dos.write(sb.toString().getBytes());
		
		dos.flush();
//		_conn.connect();
	}
	
	/** 设置错误日志上传参数 */
	private void setErrorLogProperties() throws IOException {
		_conn = (HttpURLConnection) _url.openConnection();
		_conn.setConnectTimeout(UPLOAD_CONNECT_TIME);
		_conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
		
		String endHyphens = "\r\n";
		String twoHyphens = "--";
		String boundary = "****";
		
		_conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
		
		//setHeaders(_conn);
		
		_conn.setRequestProperty( "Connection", "Keep-Alive" );
		_conn.setDoOutput( true );
		_conn.setDoInput( true );
		_conn.setRequestMethod( "POST" );
		
		DataOutputStream dos = new DataOutputStream(_conn.getOutputStream());
		
		StringBuffer buffer = StringUtils.getBuffer(1000);
		StringBuffer sb = StringUtils.getBuffer(200);
		sb.append(twoHyphens).append(boundary).append(endHyphens);
		//传appid
		sb.append("Content-Disposition: form-data; name=\"appid\"").append(endHyphens).append(endHyphens);
		//sb.append(Config.APPID);
		sb.append(endHyphens);
		
		//传time
		sb.append(twoHyphens).append(boundary).append(endHyphens);
		sb.append("Content-Disposition: form-data; name=\"errortime\"").append(endHyphens).append(endHyphens);
		sb.append(time);
		sb.append(endHyphens);
		
		//传validatecode
		sb.append(twoHyphens).append(boundary).append(endHyphens);
		sb.append("Content-Disposition: form-data; name=\"validatecode\"").append(endHyphens).append(endHyphens);
		String str = time + ""; //+Config.APPID+Config.APP_SECRET;
		//sb.append(MD5.toMd5(DES3.encryptThreeDESECB(str)));
		sb.append(endHyphens);
		buffer.append(sb.toString());
		dos.write(sb.toString().getBytes());
		
		sb = StringUtils.getBuffer(200);
		sb.append(twoHyphens).append(boundary).append(endHyphens);
		sb.append("Content-Disposition: form-data; name=\"contractfile\"; filename=\"errorlog.txt\"").append(endHyphens);
		sb.append("Content-Type: text/html").append(endHyphens);
		sb.append(endHyphens);
		
		dos.write(sb.toString().getBytes());
		
		dos.write(filePartByte);
		
		sb = StringUtils.getBuffer(30);
		sb.append(endHyphens);
		sb.append(twoHyphens).append(boundary).append(twoHyphens).append(endHyphens);
		
		dos.write(sb.toString().getBytes());
		
		dos.flush();
//		_conn.connect();
	}
	
	/**
	 * 关闭当前连接和流
	 * 
	 * @throws IOException
	 */
	private void closeConnection() throws IOException
	{
		// if(_conn != null){
		// _conn.disconnect();
		// _conn = null;
		// }
		if (_is != null)
		{
			_is.close();
			_is = null;
		}
	}
	
	/** 设置是否使用断点续传 */
	public void setIsUseRange(boolean isRange) {
		mUseRange = isRange;
	}
	
	/** 设置请求头部 */
/*	private void setHeaders(HttpURLConnection _conn) {
		_conn.addRequestProperty(ParamNames.HEADER_IMSI, RequestHeader.getImsi());
		_conn.addRequestProperty(ParamNames.HEADER_UID, RequestHeader.getUid());
		_conn.addRequestProperty(ParamNames.HEADER_USERID, ConfigMgr.getAppConfig().getUserid());
		_conn.addRequestProperty(ParamNames.HEADER_UA, RequestHeader.getUa());
		_conn.addRequestProperty(ParamNames.HEADER_OSID, RequestHeader.getOsid());
		_conn.addRequestProperty(ParamNames.HEADER_OSVERSION, RequestHeader.getOsversion());
		_conn.addRequestProperty(ParamNames.HEADER_OSWOVERSION, RequestHeader.getOswoversion());
		_conn.addRequestProperty(ParamNames.HEADER_PROTOCOLVER, RequestHeader.getProtocolver());
		_conn.addRequestProperty(ParamNames.HEADER_APN, RequestHeader.getApn());
		_conn.addRequestProperty(ParamNames.HEADER_SCREEN, RequestHeader.getScreen());
	}*/
}
