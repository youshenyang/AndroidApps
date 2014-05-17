package ct.finded.findfood.control;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * ListView滚动数据加载器
 * 
 * @author xiaoxh
 * @version 1.00
 */
public abstract class PageScrollListener implements OnScrollListener{
	
	/** 列表数量 */
	protected int totalCount = 0;
	/** 每次加载数量 */
	protected int pageCount = 20;
	/** 是否载入结束 */
//	protected boolean loadover = false;
	
	/** 构造函数 */
	public PageScrollListener() { }
	
	/** 设置列表数量 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	/** 设置一页显示数量 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			synchronized (PageScrollListener.this) {
				if (view.getLastVisiblePosition() > view.getCount() - 2) { // -5
					if (view.getCount() < totalCount) {
//						Logs.i(TAG, view.getCount() + " | " + (index + pageSize) + " | " + index);
						return;
					}
					totalCount += pageCount;
					loadingNextPageTask();
				}
			}
		}
	}
	
	/**
	 * 重新执行加载任务
	 */
	public abstract void loadingNextPageTask();
}
