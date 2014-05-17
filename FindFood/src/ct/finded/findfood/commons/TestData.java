package ct.finded.findfood.commons;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent.DispatcherState;
import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;
import ct.finded.findfood.http.results.TrendCardListResult.TrendCard;

/**
 * 
 * @ClassName: TestData
 * @Description: 获取测试数据
 * @author Shy_You
 * @date 2014年4月25日
 */
public class TestData {

	public static final String TAG = "TestData";
	/**
	 * 获取动态列表
	 * @return
	 */
	public static List getTrendCardListData() {
		List list = new ArrayList();
		for(int i = 0; i < 10; i++){
			Evaluate evaluate = new Evaluate();
			evaluate.setPraiseNum(String.valueOf(i));
			evaluate.setReplyNum(String.valueOf(i));
			evaluate.setMenuName("梅菜扣肉"+String.valueOf(i));
			evaluate.setRealname("游沈扬");
			evaluate.setGrade(50L);
			evaluate.setTime(String.valueOf(i)+"分钟前");
			evaluate.setContent("这家店的鱼香茄子饭简直就是一流的，我都加了好几次米饭了！可以说是这里最厉害的米饭杀手。");
			list.add(evaluate);
		}
		return list;
	}

	public static List<String> getStringArray() {
		List<String> list = new ArrayList<String>();
		list.add("朱伟文：我也觉得很好吃！吃了六碗饭了。");
		list.add("朱伟文：我也觉得很好吃！吃了六碗饭了。我也觉得很好吃！吃了六碗饭了。我也觉得很好吃！吃了六碗饭了。我也觉得很好吃！吃了六碗饭了。我也觉得很好吃！吃了六碗饭了。我也觉得很好吃！吃了六碗饭了。");
		for (int i = 0; i < 30; i++) {
			list.add("朱伟文：我也觉得很好吃！吃了六碗饭了。");
		}
		return list;
	}
	
	public static void printScreenMsg(Activity aty){
		DisplayMetrics dm = new DisplayMetrics();
		aty.getWindowManager().getDefaultDisplay().getMetrics(dm);
		Log.i(TAG, "***屏幕分辨率为："+String.valueOf(dm.heightPixels)+"X"+String.valueOf(dm.widthPixels)+"***");
	}
}
