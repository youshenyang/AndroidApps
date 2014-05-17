package ct.finded.findfood.control;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * 
 * @ClassName: UserToast
 * @Description: 自定义Toast框，控制每次弹框时间，及时显示当前的提示框
 * @author Shy_You
 * @date 2014年4月25日
 */
public class UserToast {

	/** 单例实例 */
	private static Toast mToast;
	
	/** 内置处理handler */
	private static Handler mHandler = new Handler();
	/** toast取消线程 */
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
		}
	};

	/**
	 * 显示Toast框
	 * 
	 * @param context 上下文
	 * 
	 * @param text 提示语
	 */
	public static void showToast(Context context, String text) {
		mHandler.removeCallbacks(r);
		if (mToast != null)
			mToast.setText(text);
		else
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		mHandler.postDelayed(r, 2000);
		mToast.show();
	}
	
	/**
	 * 显示Toast框
	 * 
	 * @param context 上下文
	 * 
	 * @param text 提示语
	 */
	public static void showTip(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}
