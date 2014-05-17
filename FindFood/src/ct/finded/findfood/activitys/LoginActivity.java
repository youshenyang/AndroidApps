package ct.finded.findfood.activitys;

import ct.finded.findfood.FoodApplication;
import ct.finded.findfood.R;
import ct.finded.findfood.commons.AppConfigMrg;
import ct.finded.findfood.control.ProgressDlg;
import ct.finded.findfood.control.UserToast;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.RequestHelper;
import ct.finded.findfood.http.RequestHelper.OnRequestListener;
import ct.finded.findfood.http.requests.LoginRequest;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.LoginResult;
import ct.finded.findfood.http.results.LoginResult.Userinfo;
import ct.finded.findfood.utils.ActivityUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
/**
 * 
 * @ClassName: LoginActivity
 * @Description: 登陆Activity,进行登陆
 * @author Shy_You
 * @date 2014年4月23日
 */
public class LoginActivity extends Activity implements OnClickListener{

	/**顶部栏返回按钮*/
	private LinearLayout llTopBackBtn;
	/**顶部栏更多按钮*/
	private LinearLayout llTopMoreBtn;
	
	/**登陆用户id*/
	private EditText edtUserid;
	/**登陆密码*/
	private EditText edtPassword;
	/**登陆按钮*/
	private Button btnLogin;
	/**登陆账号*/
	private String userid;
	/**登陆密码*/
	private String password;
	/**用户信息*/
	private Userinfo userinfo;
	
	/**等待对话框*/
	private ProgressDlg progressDlg;
	
	/**登陆请求帮助类*/
	private RequestHelper loginRequestHelper;
	/**登陆请求*/
	private BaseRequest loginRequest;
	/**登陆请求监听器*/
	private OnRequestListener loginRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			if(progressDlg != null){
				progressDlg.show();
			}
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			UserToast.showToast(LoginActivity.this, errorString);
			progressDlg.dismiss();
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			LoginResult loginResult = (LoginResult) result;
			if(loginResult != null){
				userinfo = loginResult.getData().get(0);
				if(userinfo != null){
					UserToast.showToast(LoginActivity.this, getString(R.string.login_success));
					AppConfigMrg.getAppConfig().setIsLogin(true);
					AppConfigMrg.getAppConfig().setUserid(userinfo.getUserId());
					AppConfigMrg.getAppConfig().setUsername(userinfo.getRealname());
					AppConfigMrg.getAppConfig().setFace(userinfo.getFace());
					AppConfigMrg.getAppConfig().setSignature(userinfo.getSignature());
					AppConfigMrg.save(FoodApplication.getInstance().getApplicationContext());
					progressDlg.dismiss();
					showLoginedHomePageActivity();
				}
				
			}
			
		}
		
		@Override
		public void onRequestCancel() {
			progressDlg.dismiss();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login_layout);
		initViews();
		initEvents();
	}

	
	
	/**跳转到已登陆的首页*/
	private void showLoginedHomePageActivity() {
		Intent intent = new Intent(this, LoginedHomePageActivity.class);
		intent.putExtra("userinfo", userinfo);
		startActivity(intent);
		this.finish();
	}






	/**初始化视图*/
	private void initViews() {
		llTopBackBtn = (LinearLayout) this.findViewById(R.id.top_back_btn);
		llTopMoreBtn = (LinearLayout) this.findViewById(R.id.top_more_btn);
		edtUserid = (EditText) this.findViewById(R.id.userid);
		edtPassword = (EditText) this.findViewById(R.id.password);
		btnLogin = (Button) this.findViewById(R.id.login);
		//创建进度等待对话框
		progressDlg = new ProgressDlg(this);
		progressDlg.setContent(getString(R.string.logining));
		progressDlg.createDlg();
	}
	
	/**初始化事件*/
	private void initEvents() {
		llTopBackBtn.setOnClickListener(this);
		llTopMoreBtn.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.top_back_btn: 
			//点击返回按钮
			ActivityUtils.shutdownActivity();
			break;
		case R.id.top_more_btn: 
			//点击更多按钮
			break;
		case R.id.login:
			//如果登陆成功，跳转到首页
			checkLoginInfo();
			doRequest();
			break;
			
		default:
			break;
		}
	}



	/**开始http请求*/
	private void doRequest() {
		if(loginRequestHelper == null){
			loginRequestHelper = new RequestHelper(loginRequestListener);
		}
		loginRequest = new LoginRequest(userid, password);
		loginRequestHelper.doRequest(loginRequest);
	}



	/**校验登陆信息*/
	private void checkLoginInfo() {
		//判断用户账号是否为空
		userid = edtUserid.getText().toString();
		if(userid == null || userid.trim().equals("")){
			UserToast.showToast(this, getString(R.string.userid_not_null));
		}
		
		//判断密码是否为空
		password = edtPassword.getText().toString();
		if(password == null || password.trim().equals("")){
			UserToast.showToast(this, getString(R.string.pwd_not_null));
		}
		
	}
	

}
