package ct.finded.findfood.http.results;

/**
 * 
 * @ClassName: BaseResult
 * @Description: http请求返回结果基础类
 * @author Shy_You
 * @date 2014年4月24日
 */
public class BaseResult implements java.io.Serializable {
	/** 请求成功标志 */
	private static final String REQUEST_SUCCESS_TAG = "100";
	
	private static final long serialVersionUID = 4147065395635120227L;
	
	/** 结果代码 */
	private String status;
	/** 结果消息 */
	private String desc;
	/** 时间截 */
	private long time;
	/** 是否使用缓存中的数据 */
	private boolean usecache;
	
	public boolean isUsecache() {
		return usecache;
	}
	public void setUsecache(boolean usecache) {
		this.usecache = usecache;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/** 请求是否成功 */
	public boolean requestSuccess() {
		return REQUEST_SUCCESS_TAG.equals(status);
	}
}