package ct.finded.findfood.utils;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import ct.finded.findfood.R;
import ct.finded.findfood.activitys.FoodDetailActivity;
import ct.finded.findfood.activitys.SearchActivity;
import ct.finded.findfood.activitys.TrendDetailActivity;
import ct.finded.findfood.activitys.UnloginHomePageActivity;
import ct.finded.findfood.activitys.UserCenterActivity;
import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.control.UserToast;
import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;
import ct.finded.findfood.http.results.LoginResult.Userinfo;

/**
 * 
 * @ClassName: ActivityUtils
 * @Description: Activity相关操作，比如跳转
 * @author Shy_You
 * @date 2014年5月3日
 */
public class ActivityUtils {
	
	/**打开菜式详情Activity*/
	public static void showFoodDetailActivity(Activity activity,String menuid, String menuname) {
		Intent intentFood = new Intent(activity, FoodDetailActivity.class);
		intentFood.putExtra(ParamNames.MENU_ID,menuid);
		intentFood.putExtra(ParamNames.MENU_NAME,menuname);
		activity.startActivity(intentFood);
	}
	
	/**打开菜式详情Activity*/
	public static void showFoodDetailActivity(Context context,String menuid, String menuname) {
		Intent intentFood = new Intent(context, FoodDetailActivity.class);
		intentFood.putExtra(ParamNames.MENU_ID,menuid);
		intentFood.putExtra(ParamNames.MENU_NAME,menuname);
		intentFood.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intentFood);
	}
	
	/**打开点评动态详情*/
	public static void showTrendDetailActivity(Context context, Evaluate evaluate){
		Intent intentTrendDetail = new Intent(context, TrendDetailActivity.class);
		intentTrendDetail.putExtra(ParamNames.EVALUATE, evaluate);
		intentTrendDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intentTrendDetail);
	}
	/**打开用户主页Activity*/
	public static void showUserCenterActivity(Context context, String userid, String username, String face){
		Intent intentUserCenter = new Intent(context, UserCenterActivity.class);
		intentUserCenter.putExtra(ParamNames.USERID, userid);
		intentUserCenter.putExtra(ParamNames.USERNAME, username);
		intentUserCenter.putExtra(ParamNames.FACE, face);
		intentUserCenter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intentUserCenter);
	}
	
	/**打开搜索Activity*/
	public static void showSearchActivity(Activity activity, String searchKey){
		Intent searchIntent = new Intent(activity, SearchActivity.class);
		searchIntent.putExtra(ParamNames.SEARCH_KEY, searchKey);
		activity.startActivity(searchIntent);
	}
	
	/**模拟返回键，关闭当前Activity*/
	public static void shutdownActivity(){
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**调用打电话*/
	public static void showPhoneActivity(Activity activity, String phoneNum){
	    if(phoneNum != null && !phoneNum.equals("")){
		      Intent phoneIntent = new Intent("android.intent.action.CALL", 
		                 Uri.parse("tel:" + phoneNum)); 
		      //启动 
		      activity.startActivity(phoneIntent);    
		    }else{
		    	UserToast.showToast(activity, activity.getString(R.string.fail_get_phone));
		    }
	}
	
	
	/**隐藏软键盘*/
	public static void hideKeyBoard(Context context, EditText edit){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE); 
        imm.hideSoftInputFromWindow(edit.getWindowToken(),0);
	}

}
