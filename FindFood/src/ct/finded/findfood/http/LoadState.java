package ct.finded.findfood.http;

/**
 * 请求加载状态
 * 
 */
public class LoadState {
	/** 加裁中 */
	public static final int LOADING = 1;
	/** 加载完成 */
	public static final int LOADED = 2;
	/** 加载失败 */
	public static final int LOAD_FAIL = 3;
	/** 加载无数据 */
	public static final int LOAD_NODATA = 4;
	/** 加载被取消 */
	public static final int LOAD_CANCEL = 5;

	/** 当前状态 */
	private int currentState = LOADED;
	
	/** 构造函数 */
	public LoadState() {
		currentState = LOADED;
	}
	
	/** 设置加载中 */
	public void setLoading() {
		currentState = LOADING;
	}
	
	/** 设置加载完成 */
	public void setLoaded() {
		currentState = LOADED;
	}
	
	/** 设置加载挫败 */
	public void setLoadFail() {
		currentState = LOAD_FAIL;
	}
	
	/** 设置加载无数据 */
	public void setLoadNoData() {
		currentState = LOAD_NODATA;
	}
	
	/** 设置加载被取消 */
	public void setLoadCancel() {
		currentState = LOAD_CANCEL;
	}
	
	/** 是否加载中 */
	public boolean isLoading() {
		return currentState == LOADING;
	}
	
	/** 是否加载完成 */
	public boolean isLoaded() {
		return currentState == LOADED;
	}
	
	/** 是否加载失败 */
	public boolean isLoadFail() {
		return currentState == LOAD_FAIL;
	}
	
	/** 是否无数据 */
	public boolean isLoadNoData() {
		return currentState == LOAD_NODATA;
	}
	
	/** 是否加载被取消 */
	public boolean isLoadCancel() {
		return currentState == LOAD_CANCEL;
	}
}
