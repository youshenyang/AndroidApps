package ct.finded.findfood.activitys;

import java.util.Timer;
import java.util.TimerTask;

import ct.finded.findfood.R;
import ct.finded.findfood.commons.AppConfigMrg;
import ct.finded.findfood.commons.TestData;
import ct.finded.findfood.http.results.LoginResult.Userinfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * @ClassName: SplashActivity
 * @Description: 应用开启时的闪屏Activity，进行一些应用初始化操作
 * @author Shy_You
 * @date 2014年4月22日
 */
public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.splash_layout);
		//打印屏幕分辨率
		TestData.printScreenMsg(this);
		//定时器，一秒钟后关闭SplashActivity,跳转到首页
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
					startHomePage();
			}
		}, 1000);
	}

	/**
	 * 闪屏之后打开应用首页,
	 * 如果登陆了则跳转到已登陆的首页
	 * 否则跳转到未登陆的首页
	 */
	protected void startHomePage() {
		if(AppConfigMrg.getAppConfig().getIsLogin()){
			startLoginedHomePage();
		}else{
			startUnloginHomePage();
		}
	}

	/**打开未登陆的首页*/
	private void startUnloginHomePage() {
		Intent intent = new Intent(this, UnloginHomePageActivity.class);
		startActivity(intent);
		finish();
	}

	/**打开已登陆的首页*/
	private void startLoginedHomePage() {
		Intent intent = new Intent(this, LoginedHomePageActivity.class);
		Userinfo userinfo = new Userinfo();
		userinfo.setUserId(AppConfigMrg.getAppConfig().getUserid());
		userinfo.setRealname(AppConfigMrg.getAppConfig().getUsername());
		userinfo.setFace(AppConfigMrg.getAppConfig().getFace());
		userinfo.setSignature(AppConfigMrg.getAppConfig().getSignature());
		intent.putExtra("userinfo", userinfo);
		intent.putExtra("loginedStart", true);
		startActivity(intent);
		this.finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	
}
