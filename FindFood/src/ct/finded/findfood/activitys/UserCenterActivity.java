package ct.finded.findfood.activitys;

import java.util.ArrayList;
import java.util.List;

import cn.com.wo.bitmap.ImageLoader;
import ct.finded.findfood.FoodApplication;
import ct.finded.findfood.R;
import ct.finded.findfood.adapters.UserBasicCardAdapter;
import ct.finded.findfood.adapters.UserTrendCardAdapter;
import ct.finded.findfood.commons.AppConfigMrg;
import ct.finded.findfood.commons.AppConstant;
import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.commons.TestData;
import ct.finded.findfood.control.CircleImageView;
import ct.finded.findfood.control.FooterView;
import ct.finded.findfood.control.PageScrollView;
import ct.finded.findfood.control.PageScrollView.OnScrollListener;
import ct.finded.findfood.control.UserToast;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.RequestHelper;
import ct.finded.findfood.http.RequestHelper.OnRequestListener;
import ct.finded.findfood.http.requests.ShowUserBasicRequest;
import ct.finded.findfood.http.requests.ShowUserFansRequest;
import ct.finded.findfood.http.requests.ShowUserFocusRequest;
import ct.finded.findfood.http.requests.ShowUserTrendRequest;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;
import ct.finded.findfood.http.results.ShowUserBasicResult;
import ct.finded.findfood.http.results.LoginResult.Userinfo;
import ct.finded.findfood.http.results.ShowUserFansResult;
import ct.finded.findfood.http.results.ShowUserFocusResult;
import ct.finded.findfood.http.results.ShowUserTrendResult;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @ClassName: UserCenterActivity
 * @Description: 用户主页Activity
 * @author Shy_You
 * @date 2014年4月26日
 */
public class UserCenterActivity extends Activity implements OnClickListener{

	/**用户动态标志*/
	public static final int USER_TREND_FLAG = 1;
	/**用户关注标志*/
	public static final int USER_FOCUS_FLAG = 2;
	/**用户粉丝标志*/
	public static final int USER_FANS_FLAG = 3;
	/**当前选中的Tab标志*/
	private int curSelected;
	
	private RelativeLayout rlTopNav;
	private ImageView ivTopNavBg;
	private LinearLayout llTopBackBtn;
	private LinearLayout llTopMoreBtn;
	private LinearLayout llInformRingBtn;
	
	private LinearLayout llFocusTa;
	private PageScrollView psvPageScrollview;
	private RelativeLayout rlPageHeader;
	private LinearLayout llUserIconArea;
	private CircleImageView ivUserIcon;
	private TextView tvUsername;
	private TextView tvUserDesc;
	private LinearLayout llUserTrendBtn;
	private TextView tvUserTrendCount;
	private ImageView ivTrendSelected;
	private LinearLayout llUserFocusBtn;
	private TextView tvUserFocusCount;
	private ImageView ivFocusSelected;
	private LinearLayout llUserFansBtn;
	private TextView tvUserFansCount;
	private ImageView ivFansSelected;
	private ListView lvTrendList;
	private LinearLayout llFocusArea;
	private ListView lvFocusWho;
	private LinearLayout llFansArea;
	private ListView lvFansList;
	
	private FooterView footerView = null;
	/**用户id*/
	private String userid;
	/**用户姓名*/
	private String username;
	/**用户头像地址*/
	private String face;
	/**用户基本信息对象*/
	private Userinfo userinfo;
	
	/**最新请求的动态列表*/
	private List<Evaluate> trendList;
	/**最新请求的关注列表*/
	private List<Userinfo> focusList;
	/**最新请求的粉丝列表*/
	private List<Userinfo> fansList;
	
	/**用户动态列表适配器*/
	private UserTrendCardAdapter trendCardAdapter;
	/**用户关注列表适配器*/
	private UserBasicCardAdapter focusCardAdapter;
	/**用户粉丝列表适配器*/
	private UserBasicCardAdapter fansCardAdapter;
	
	/**用户基本信息请求帮助类*/
	private RequestHelper showUserBasicReuqestHelper;
	/**用户基本信息请求*/
	private BaseRequest showUserBasicReuqest;
	/**用户基本信息请求监听器*/
	private OnRequestListener showUserBasicReuqestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			UserToast.showToast(UserCenterActivity.this, errorString);
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			ShowUserBasicResult userBasicResult = (ShowUserBasicResult) result;
			if(userBasicResult != null){
				userinfo = userBasicResult.getData();
				tvUserDesc.setText(userinfo.getSignature());
				tvUserTrendCount.setText(userinfo.getActiveNum());
				tvUserFocusCount.setText(userinfo.getFollowNum());
				tvUserFansCount.setText(userinfo.getFansNum());
			}
		}
		
		@Override
		public void onRequestCancel() {
			
		}
	};
	
	private RequestHelper showUserTrendRequestHelper;
	private BaseRequest showUserTrendRequest;
	private OnRequestListener showUserTrendRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			UserToast.showToast(UserCenterActivity.this, errorString);
			footerView.hide(lvTrendList);
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			ShowUserTrendResult trendResult = (ShowUserTrendResult) result;
			if(trendResult != null){
				trendList = trendResult.getData();
				if(trendList != null){
					trendCardAdapter.addDatas(trendList);
				}
			}
			footerView.hide(lvTrendList);
		}
		
		@Override
		public void onRequestCancel() {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**显示用户关注列表的请求帮助类*/
	private RequestHelper showUserFocusRequestHelper;
	/**显示用户关注列表请求*/
	private BaseRequest showUserFocusRequest;
	/**显示用户关注列表请求监听器*/
	private OnRequestListener showUserFocusRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
            footerView.showLoading(focusCardAdapter, lvFocusWho);
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			footerView.hide(lvFocusWho);
			UserToast.showToast(UserCenterActivity.this, errorString);
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			ShowUserFocusResult focusResult = (ShowUserFocusResult) result;
			if(focusResult != null){
				focusList = focusResult.getData();
				if(focusList != null){
					focusCardAdapter.addDatas(focusList);
				}
			}
			footerView.hide(lvFocusWho);
		}
		
		@Override
		public void onRequestCancel() {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**显示用户粉丝列表的请求帮助类*/
	private RequestHelper showUserFansRequestHelper;
	/**显示用户粉丝列表请求*/
	private BaseRequest showUserFansRequest;
	/**显示用户粉丝列表请求监听器*/
	private OnRequestListener showUserFansRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			footerView.showLoading(fansCardAdapter, lvFansList);
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			footerView.hide(lvFansList);
			UserToast.showToast(UserCenterActivity.this, errorString);
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			ShowUserFansResult fansResult = (ShowUserFansResult) result;
			if(fansResult != null){
				fansList = fansResult.getData();
				if(fansList != null){
					fansCardAdapter.addDatas(fansList);
				}
			}
			footerView.hide(lvFansList);
		}
		
		@Override
		public void onRequestCancel() {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	
	
	/**测试用的list*/
	private List list = new ArrayList();
	
	private OnScrollListener scrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
			int headerHeight  = rlPageHeader.getHeight() - rlTopNav.getHeight();
			float ratio =  (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
			ivTopNavBg.setAlpha(ratio);

		}
		
		@Override
		public void onBottom() {
			switch (curSelected) {
			
			case USER_TREND_FLAG:
				doTrendRequest();
				break;
				
			case USER_FOCUS_FLAG:
				doFocusRequest();
				break;
			case USER_FANS_FLAG:
				doFansRequest();
				break;

			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_center_layout);
		initViews();
		bindData();
		initEvents();
	}
	
	



	@Override
	protected void onResume() {
		super.onResume();
		llUserTrendBtn.performClick();
	}



	protected void initViews() {
		rlTopNav = (RelativeLayout) findViewById(R.id.top_nav);
		ivTopNavBg = (ImageView) findViewById(R.id.top_nav_bg);
		llTopBackBtn = (LinearLayout) findViewById(R.id.top_back_btn);
		llTopMoreBtn = (LinearLayout) findViewById(R.id.top_more_btn);
		llInformRingBtn = (LinearLayout) findViewById(R.id.inform_ring_btn);
		llFocusTa = (LinearLayout) findViewById(R.id.focus_ta);
		psvPageScrollview = (ct.finded.findfood.control.PageScrollView) findViewById(R.id.page_scrollview);
		rlPageHeader = (RelativeLayout) findViewById(R.id.page_header);
		llUserIconArea = (LinearLayout) findViewById(R.id.user_icon_area);
		ivUserIcon = (CircleImageView) findViewById(R.id.user_icon);
		tvUsername = (TextView) findViewById(R.id.username);
		tvUserDesc = (TextView) findViewById(R.id.user_desc);
		llUserTrendBtn = (LinearLayout) findViewById(R.id.user_trend_btn);
		tvUserTrendCount = (TextView) findViewById(R.id.user_trend_count);
		ivTrendSelected = (ImageView) findViewById(R.id.trend_selected);
		llUserFocusBtn = (LinearLayout) findViewById(R.id.user_focus_btn);
		tvUserFocusCount = (TextView) findViewById(R.id.user_focus_count);
		ivFocusSelected = (ImageView) findViewById(R.id.focus_selected);
		llUserFansBtn = (LinearLayout) findViewById(R.id.user_fans_btn);
		tvUserFansCount = (TextView) findViewById(R.id.user_fans_count);
		ivFansSelected = (ImageView) findViewById(R.id.fans_selected);
		lvTrendList = (ListView) findViewById(R.id.trend_list);
		llFocusArea = (LinearLayout) findViewById(R.id.focus_area);
		lvFocusWho = (ListView) findViewById(R.id.focus_who);
		llFansArea = (LinearLayout) findViewById(R.id.fans_area);
		lvFansList = (ListView) findViewById(R.id.fans_list);
		//动态列表添加适配器
		trendCardAdapter = new UserTrendCardAdapter(this);
		lvTrendList.setAdapter(trendCardAdapter);
		//关注列表添加适配器
		focusCardAdapter = new UserBasicCardAdapter(this);
		lvFocusWho.setAdapter(focusCardAdapter);
		//粉丝列表添加适配器
		fansCardAdapter = new UserBasicCardAdapter(this);
		lvFansList.setAdapter(fansCardAdapter);
		
		footerView = new FooterView(this);
	}
	
	

	/**初始化绑定视图数据*/
	private void bindData() {
		userid = getIntent().getStringExtra(ParamNames.USERID);
		username = getIntent().getStringExtra(ParamNames.USERNAME);
		face = getIntent().getStringExtra(ParamNames.FACE);
		if(userid != null){
			doUserBasicRequest();
		}
		if(username != null){
			tvUsername.setText(username);
		}
		if(face != null){
			ImageLoader.load(FoodApplication.getInstance().getImageFetcher(), face, ivUserIcon);
		}
	}

	/**开始http请求*/
	private void doUserBasicRequest() {
		if(showUserBasicReuqestHelper == null){
			showUserBasicReuqestHelper = new RequestHelper(showUserBasicReuqestListener);
			showUserBasicReuqest = new ShowUserBasicRequest(userid, AppConfigMrg.getAppConfig().getUserid());
		}
		showUserBasicReuqestHelper.doRequest(showUserBasicReuqest);
	}
	
	/**开始用户动态列表http请求*/
	private void doTrendRequest(){
		if(showUserTrendRequestHelper == null){
			showUserTrendRequestHelper = new RequestHelper(showUserTrendRequestListener);
			showUserTrendRequest = new ShowUserTrendRequest(userid,AppConfigMrg.getAppConfig().getUserid(),AppConstant.REQUEST_START_PAGE, AppConstant.REQUEST_LENGTH);
		}else{
			showUserTrendRequest.getRequestHandler().setNextPage();
		}
		showUserTrendRequestHelper.doRequest(showUserTrendRequest);
	}
	
	/**开始用户关注列表http请求*/
	private void doFocusRequest(){
		if(showUserFocusRequestHelper == null){
			showUserFocusRequestHelper = new RequestHelper(showUserFocusRequestListener);
			showUserFocusRequest = new ShowUserFocusRequest(userid,AppConfigMrg.getAppConfig().getUserid(),AppConstant.REQUEST_START_PAGE, AppConstant.REQUEST_LENGTH);
		}else{
			showUserFocusRequest.getRequestHandler().setNextPage();
		}
		showUserFocusRequestHelper.doRequest(showUserFocusRequest);
	}
	
	/**开始用户粉丝列表http请求*/
	private void doFansRequest(){
		if(showUserFansRequestHelper == null){
			showUserFansRequestHelper = new RequestHelper(showUserFansRequestListener);
			showUserFansRequest = new ShowUserFansRequest(userid,AppConfigMrg.getAppConfig().getUserid(),AppConstant.REQUEST_START_PAGE, AppConstant.REQUEST_LENGTH);
		}else{
			showUserFansRequest.getRequestHandler().setNextPage();
		}
		showUserFansRequestHelper.doRequest(showUserFansRequest);
	}
	





	/**
	 * Tab选择状态改变
	 * @param selectFlag
	 */
	private void changeSelectedState(int selectFlag) {
		switch (selectFlag) {
		case USER_TREND_FLAG:
			ivTrendSelected.setVisibility(View.VISIBLE);
			ivFocusSelected.setVisibility(View.INVISIBLE);
			ivFansSelected.setVisibility(View.INVISIBLE);
			llUserTrendBtn.setClickable(false);
			llUserFocusBtn.setClickable(true);
			llUserFansBtn.setClickable(true);
			lvTrendList.setVisibility(View.VISIBLE);
			llFocusArea.setVisibility(View.GONE);
			llFansArea.setVisibility(View.GONE);
			break;
		case USER_FOCUS_FLAG:
			ivTrendSelected.setVisibility(View.INVISIBLE);
			ivFocusSelected.setVisibility(View.VISIBLE);
			ivFansSelected.setVisibility(View.INVISIBLE);
			llUserTrendBtn.setClickable(true);
			llUserFocusBtn.setClickable(false);
			llUserFansBtn.setClickable(true);
			lvTrendList.setVisibility(View.GONE);
			llFocusArea.setVisibility(View.VISIBLE);
			llFansArea.setVisibility(View.GONE);
			break;
		case USER_FANS_FLAG:
			ivTrendSelected.setVisibility(View.INVISIBLE);
			ivFocusSelected.setVisibility(View.INVISIBLE);
			ivFansSelected.setVisibility(View.VISIBLE);
			llUserTrendBtn.setClickable(true);
			llUserFocusBtn.setClickable(true);
			llUserFansBtn.setClickable(false);
			lvTrendList.setVisibility(View.GONE);
			llFocusArea.setVisibility(View.GONE);
			llFansArea.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	protected void initEvents() {
		llTopBackBtn.setOnClickListener(this);
		llTopMoreBtn.setOnClickListener(this);
		llInformRingBtn.setOnClickListener(this);
		llFocusTa.setOnClickListener(this);
		psvPageScrollview.setOnClickListener(this);
		rlPageHeader.setOnClickListener(this);
		llUserIconArea.setOnClickListener(this);
		ivUserIcon.setOnClickListener(this);
		tvUsername.setOnClickListener(this);
		tvUserDesc.setOnClickListener(this);
		llUserTrendBtn.setOnClickListener(this);
		ivTrendSelected.setOnClickListener(this);
		llUserFocusBtn.setOnClickListener(this);
		ivFocusSelected.setOnClickListener(this);
		llUserFansBtn.setOnClickListener(this);
		ivFansSelected.setOnClickListener(this);
		llFocusArea.setOnClickListener(this);
		llFansArea.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.top_back_btn:
				break;
			case R.id.top_more_btn:
				break;
			case R.id.inform_ring_btn:
				break;
			case R.id.focus_ta:
				break;
			case R.id.page_header:
				break;
			case R.id.user_icon_area:
				break;
			case R.id.user_icon:
				break;
			case R.id.username:
				break;
			case R.id.user_desc:
				break;
			case R.id.user_trend_btn://点击动态tab时
				curSelected = 1;
				changeSelectedState(USER_TREND_FLAG);
				if(trendList == null){
					doTrendRequest();
				}
				break;
			case R.id.user_focus_btn://点击关注tab时
				curSelected =2;
				changeSelectedState(USER_FOCUS_FLAG);
				if(focusList == null){
					doFocusRequest();
				}
				break;
			case R.id.user_fans_btn://点击粉丝tab时
				curSelected = 3;
				changeSelectedState(USER_FANS_FLAG);
				if(fansList == null){
					doFansRequest();
				}
				break;
		}
	}

	protected void onDestroy() {
		super.onDestroy();
		llFocusTa = null;
		psvPageScrollview = null;
		rlPageHeader = null;
		llUserIconArea = null;
		ivUserIcon = null;
		tvUsername = null;
		tvUserDesc = null;
		llUserTrendBtn = null;
		tvUserTrendCount = null;
		ivTrendSelected = null;
		llUserFocusBtn = null;
		tvUserFocusCount = null;
		ivFocusSelected = null;
		llUserFansBtn = null;
		tvUserFansCount = null;
		ivFansSelected = null;
		lvTrendList = null;
		llFocusArea = null;
		lvFocusWho = null;
		llFansArea = null;
		lvFansList = null;
	}

}
