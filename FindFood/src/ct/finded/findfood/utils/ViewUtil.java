package ct.finded.findfood.utils;

import ct.finded.findfood.adapters.CommonListAdapter;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;

/**
 * 界面视图工具类
 * 
 */
public class ViewUtil {
	/**
	 * Android之ScrollView嵌套ListView
	 * 问题所在：在ScrollView中嵌套使用ListView，ListView只会显示一行多一点
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		CommonListAdapter listAdapter = (CommonListAdapter)listView.getAdapter();
		if (listAdapter == null) {
			return ;
		}
		int totalHeight = 0;
		int count = listAdapter.getCount();
		if(count == 0)
			return ;
		for (int i = 0; i < count; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			//int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
			listItem.measure(0, 0);// 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight();// 统计所有子项的总高度
		}
		
		LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
	
	/**
	 * Android之ScrollView嵌套ListView
	 * 问题所在：在ScrollView中嵌套使用ListView，ListView只会显示一行多一点
	 * 
	 * @param listView
	 */
	public static int computeListViewHeight(ListView listView, int singleHeight, int count) {
		if(singleHeight == 0) {
			CommonListAdapter listAdapter = (CommonListAdapter)listView.getAdapter();
			if (count == 0) {
				return 0;
			}
			int totalHeight = 0;
//			singleHeight = getSingleHeight(listView, listAdapter);
			for (int i = 0; i < (count); i++) {
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);// 计算子项View 的宽高
				totalHeight += listItem.getMeasuredHeight();// 统计所有子项的总高度
			}
			
			totalHeight = totalHeight + (listView.getDividerHeight() * (count - 1));
			
			singleHeight = totalHeight / count;
			
			LayoutParams params = listView.getLayoutParams();
			int height = params.height;
			height = height + totalHeight;
			params.height = height;
			listView.setLayoutParams(params);
			return params.height;
		} else {
			LayoutParams params = listView.getLayoutParams();
			int height = params.height;
			height = height + singleHeight * count;
			params.height = height;
			listView.setLayoutParams(params);
			return params.height;
		}
	}
	
	/** 上次点击时间 */
	private static long lastClickTime;
	/** 是否快速点击 */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 600) {
            return true;   
        }   
        lastClickTime = time;   
        return false;   
    }
    
    /** 等待10毫秒 */
    public static void sleep() {
    	try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
    }
    
    /** 等待一定时间，单位为毫秒数 */
    public static void sleep(int time) {
    	try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
    }
}
