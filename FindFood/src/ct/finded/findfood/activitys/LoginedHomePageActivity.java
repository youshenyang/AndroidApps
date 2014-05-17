package ct.finded.findfood.activitys;

import java.util.ArrayList;
import java.util.List;

import cn.com.wo.bitmap.ImageLoader;
import ct.finded.findfood.FoodApplication;
import ct.finded.findfood.R;
import ct.finded.findfood.adapters.TrendCardAdapter;
import ct.finded.findfood.commons.AppConfigMrg;
import ct.finded.findfood.commons.AppConstant;
import ct.finded.findfood.commons.TestData;
import ct.finded.findfood.control.CircleImageView;
import ct.finded.findfood.control.FooterView;
import ct.finded.findfood.control.MyListView;
import ct.finded.findfood.control.PageScrollView;
import ct.finded.findfood.control.PageScrollView.OnScrollListener;
import ct.finded.findfood.control.UserToast;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.RequestHelper;
import ct.finded.findfood.http.RequestHelper.OnRequestListener;
import ct.finded.findfood.http.requests.CurUserFocusTrendRequest;
import ct.finded.findfood.http.requests.GetHotMenuRequest;
import ct.finded.findfood.http.requests.ShowUserBasicRequest;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.CurUserFocusTrendResult;
import ct.finded.findfood.http.results.GetHotMenuResult;
import ct.finded.findfood.http.results.GetHotMenuResult.BaseMenuMsg;
import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;
import ct.finded.findfood.http.results.LoginResult.Userinfo;
import ct.finded.findfood.http.results.ShowUserBasicResult;
import ct.finded.findfood.http.results.TrendCardListResult.TrendCard;
import ct.finded.findfood.utils.ActivityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
/**
 * 
 * @ClassName: LogindeHomePageActivity
 * @Description: 已登陆的首页
 * @author Shy_You
 * @date 2014年4月23日
 */
public class LoginedHomePageActivity extends Activity implements OnClickListener {

	/**滚动ScrollView*/
	PageScrollView pageScrollView;
	/**顶部栏返回按钮*/
	private LinearLayout llTopBackBtn;
	/**顶部栏更多按钮*/
	private LinearLayout llTopMoreBtn;
	/**顶部栏*/
	private RelativeLayout rlTopNav;
	/**顶部栏背景图片*/
	private ImageView ivTopNavBg;
	/**页头部个人资料背景*/
	private RelativeLayout rlPageHeader;
	/**头像区域*/
	private LinearLayout llUserIconArea;
	/**头像*/
	private CircleImageView ivUserIcon;
	/**用户名*/
	private TextView tvUserName;
	/**用户描述*/
	private TextView tvUserDesc;
	
	/**热门菜式列表*/
	private List<BaseMenuMsg> hotMenuList;
	/**推荐菜式1按钮*/
	private LinearLayout llSuggestFood1Btn;
	/**推荐菜式1*/
	private TextView tvSuggestFood1;
	/**推荐菜式2按钮*/
	private LinearLayout llSuggestFood2Btn;
	/**推荐菜式2*/
	private TextView tvSuggestFood2;
	/**推荐菜式3按钮*/
	private LinearLayout llSuggestFood3Btn;
	/**推荐菜式3*/
	private TextView tvSuggestFood3;
	/**推荐菜式4按钮*/
	private LinearLayout llSuggestFood4Btn;
	/**推荐菜式4*/
	private TextView tvSuggestFood4;
	
	/**动态搜索区域*/
	private LinearLayout llActiveSearchBar;
	/**动态搜索框*/
	private AutoCompleteTextView actvActiveSearch;
	
	/**静态搜索区域*/
	private LinearLayout llStaticSearchBar;
	/**静态搜索框*/
	private AutoCompleteTextView actvStaticSearch;
	
	/**动态搜索区域位置*/
	private int[] asbLocation = new int[2];
	
	/**静态搜索区域位置*/
	private int[] ssbLocation = new int[2];
	
	/**用户动态列表*/
	private MyListView lvUserTrendList;
	/**动态列表适配器*/
	private TrendCardAdapter cardAdapter;
	
	/**列表底部提示视图*/
	private FooterView footView;
	
	/**测试用的list*/
	private List list = new ArrayList();
	
	/**搜索关键词*/
	private String searchKey;
	/**静态和动态搜索框的内容是否已经同步*/
	private Boolean searchKeyHasBeSame = true;
	/**显示的搜索框标志，1为动态的，2为静态的*/
	private static final int SHOWING_ACTIVE_SEARCH_BAR = 1;
	private static final int SHOWING_STATIC_SEARCH_BAR = 2;
	/**当前显示的搜索框标志*/
	private int curShowingSearchBar = SHOWING_ACTIVE_SEARCH_BAR;
	
	/**用户信息*/
	private Userinfo userinfo;
	/**用户信息请求帮助类*/
	private RequestHelper showUserBasicRequestHelper;
	/**用户信息请求*/
	private BaseRequest showUserBasicRequest;
	/**用户信息请求监听器*/
	private OnRequestListener showUserBasicRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			ShowUserBasicResult userBasicResult = (ShowUserBasicResult) result; 
			userinfo = userBasicResult.getData();
			tvUserName.setText(userinfo.getRealname());
			tvUserDesc.setText(userinfo.getSignature());
			ImageLoader.load(FoodApplication.getInstance().getImageFetcher(), userinfo.getFace(), ivUserIcon);
		}
		
		@Override
		public void onRequestCancel() {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**获取热门菜式请求*/
	private RequestHelper getHotMenuRequestHelper;
	private BaseRequest getHotMenuRequest;
	/**热门菜式请求监听器*/
	private OnRequestListener getHotMenuRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			if( result != null ){
				GetHotMenuResult menuResult = (GetHotMenuResult) result;
				hotMenuList = menuResult.getData();
				BaseMenuMsg bmm = null;
				if(hotMenuList != null && hotMenuList.size() != 0){
					for(int i = 0; i < hotMenuList.size(); i++){
						bmm = hotMenuList.get(i);
						switch (i) {
						case 0:
							tvSuggestFood1.setText(bmm.getMenuName());
							break;
						case 1:
							tvSuggestFood2.setText(bmm.getMenuName());
							break;
						case 2:
							tvSuggestFood3.setText(bmm.getMenuName());
							break;
						case 3:
							tvSuggestFood4.setText(bmm.getMenuName());
							break;
						default:
							break;
						}
					}
				}
			}
		}
		
		@Override
		public void onRequestCancel() {
			
		}
	};
	
	/**当前登陆用户关注的人的动态请求帮助类*/
	private RequestHelper curUserFocusTrendRequestHelper;
	/**当前登陆用户关注的人的动态请求*/
	private BaseRequest curUserFocusTrendRequest;
	/**当前登陆用户关注的人的动态请求监听器*/
	private OnRequestListener curUserFocusTrendRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			footView.showLoading(cardAdapter, lvUserTrendList);
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			footView.hide(lvUserTrendList);	
			UserToast.showToast(LoginedHomePageActivity.this, errorString);
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			CurUserFocusTrendResult curUserFocusTrendResult = (CurUserFocusTrendResult) result;
			if(curUserFocusTrendResult != null){
				List<Evaluate> curUserFocusTrendList = curUserFocusTrendResult.getData();
				if(curUserFocusTrendList != null){
					cardAdapter.addDatas(curUserFocusTrendList);
				}
				footView.hide(lvUserTrendList);
			}
			
		}
		
		@Override
		public void onRequestCancel() {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	private OnScrollListener scrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
			int headerHeight  = rlPageHeader.getHeight() - rlTopNav.getHeight();
			float ratio =  (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
			ivTopNavBg.setAlpha(ratio);
			
			llStaticSearchBar.getLocationOnScreen(ssbLocation);
			llActiveSearchBar.getLocationOnScreen(asbLocation);
			if(asbLocation[1] <= ssbLocation[1]){
				if(!searchKeyHasBeSame && curShowingSearchBar == SHOWING_ACTIVE_SEARCH_BAR){
					actvStaticSearch.setText(actvActiveSearch.getText().toString());
					actvStaticSearch.setSelection(actvActiveSearch.getSelectionEnd());;
					searchKeyHasBeSame = true;
					curShowingSearchBar = SHOWING_STATIC_SEARCH_BAR;
				}
				llStaticSearchBar.setVisibility(View.VISIBLE);
				actvStaticSearch.requestFocus();
			}else{
				if(!searchKeyHasBeSame && curShowingSearchBar == SHOWING_STATIC_SEARCH_BAR){
					actvActiveSearch.setText(actvStaticSearch.getText().toString());
					actvActiveSearch.setSelection(actvStaticSearch.getSelectionEnd());
					searchKeyHasBeSame = true;
					curShowingSearchBar = SHOWING_ACTIVE_SEARCH_BAR;
				}
				llStaticSearchBar.setVisibility(View.INVISIBLE);
				actvActiveSearch.requestFocus();
			}
		}
		
		@Override
		public void onBottom() {
//			//测试
//			footView.showLoading(cardAdapter, lvUserTrendList);//测试底部提示框
//			list = TestData.getTrendCardListData();
//			footView.hide(lvUserTrendList);//测试底部提示框
//			cardAdapter.addDatas(list);
			doCurUserFocusTrendRequest();
			
		}

	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage_logined_layout);
		initViews();
		bindData();
		initEvents();
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
	
	/**初始化视图*/
	private void initViews() {
		rlTopNav = (RelativeLayout) this.findViewById(R.id.top_nav);
		ivTopNavBg = (ImageView) this.findViewById(R.id.top_nav_bg);
		pageScrollView = (PageScrollView) this.findViewById(R.id.page_scrollview);
		llTopBackBtn = (LinearLayout) this.findViewById(R.id.top_back_btn);
		llTopMoreBtn = (LinearLayout) this.findViewById(R.id.top_more_btn);
		rlPageHeader = (RelativeLayout) this.findViewById(R.id.page_header);
		llUserIconArea = (LinearLayout) this.findViewById(R.id.user_icon_area);
		ivUserIcon = (CircleImageView) this.findViewById(R.id.user_icon);
		tvUserName = (TextView) this.findViewById(R.id.username);
		tvUserDesc = (TextView) this.findViewById(R.id.user_desc);
		llSuggestFood1Btn = (LinearLayout) this.findViewById(R.id.suggest_food1_btn);
		llSuggestFood2Btn = (LinearLayout) this.findViewById(R.id.suggest_food2_btn);
		llSuggestFood3Btn = (LinearLayout) this.findViewById(R.id.suggest_food3_btn);
		llSuggestFood4Btn = (LinearLayout) this.findViewById(R.id.suggest_food4_btn);
		tvSuggestFood1 = (TextView) this.findViewById(R.id.suggest_food1);
		tvSuggestFood2 = (TextView) this.findViewById(R.id.suggest_food2);
		tvSuggestFood3 = (TextView) this.findViewById(R.id.suggest_food3);
		tvSuggestFood4 = (TextView) this.findViewById(R.id.suggest_food4);
		llActiveSearchBar = (LinearLayout) this.findViewById(R.id.active_search_bar);
		llStaticSearchBar = (LinearLayout) this.findViewById(R.id.static_search_bar);
		actvActiveSearch = (AutoCompleteTextView) this.findViewById(R.id.active_search);
		actvStaticSearch = (AutoCompleteTextView) this.findViewById(R.id.static_search);
		
		footView = new FooterView(this);
		
		lvUserTrendList = (MyListView) this.findViewById(R.id.user_trend_list);
		cardAdapter = new TrendCardAdapter(getApplicationContext());
		lvUserTrendList.setAdapter(cardAdapter);
		
		//测试
//		footView.showLoading(cardAdapter, lvUserTrendList);//测试底部提示框
//		list = TestData.getTrendCardListData();
//		footView.hide(lvUserTrendList);//测试底部提示框
//		cardAdapter.addDatas(list);
		
	}
	
	/**绑定视图数据*/
	private void bindData() {
		userinfo = (Userinfo) getIntent().getSerializableExtra("userinfo");
		if(userinfo != null){
			ImageLoader.load(FoodApplication.getInstance().getImageFetcher(), userinfo.getFace(), ivUserIcon);
			tvUserName.setText(userinfo.getRealname());
			tvUserDesc.setText(userinfo.getSignature());
			if(getIntent().getBooleanExtra("loginedStart", false)){
				doShowUserBasicRequest();
			}
			doHotMenuRequest();
			doCurUserFocusTrendRequest();
		}
	}

	






	/**开始热门菜式请求*/
	private void doHotMenuRequest() {
		if(getHotMenuRequestHelper == null){
			getHotMenuRequestHelper = new RequestHelper(getHotMenuRequestListener);
			getHotMenuRequest = new GetHotMenuRequest();
		}
		getHotMenuRequestHelper.doRequest(getHotMenuRequest);
	}
	
	/**开始当前用户关注的用户的动态请求*/
	public void doCurUserFocusTrendRequest() {
		if(curUserFocusTrendRequestHelper == null){
			curUserFocusTrendRequestHelper = new RequestHelper(curUserFocusTrendRequestListener);
			curUserFocusTrendRequest = new CurUserFocusTrendRequest(AppConfigMrg.getAppConfig().getUserid(),AppConstant.REQUEST_START_PAGE,AppConstant.REQUEST_LENGTH);
		}else{
			curUserFocusTrendRequest.getRequestHandler().setNextPage();
		}
		curUserFocusTrendRequestHelper.doRequest(curUserFocusTrendRequest);
	}



	/**开始获取用户基本信息请求*/
	private void doShowUserBasicRequest() {
		if(showUserBasicRequestHelper == null){
			showUserBasicRequestHelper = new RequestHelper(showUserBasicRequestListener);
			showUserBasicRequest = new ShowUserBasicRequest(userinfo.getUserId(),AppConfigMrg.getAppConfig().getUserid());
		}
		showUserBasicRequestHelper.doRequest(showUserBasicRequest);
	}



	/**初始化事件*/
	private void initEvents() {
		pageScrollView.setOnScrollListener(scrollListener);
		llTopBackBtn.setOnClickListener(this);
		llSuggestFood1Btn.setOnClickListener(this);
		llSuggestFood2Btn.setOnClickListener(this);
		llSuggestFood3Btn.setOnClickListener(this);
		llSuggestFood4Btn.setOnClickListener(this);
actvActiveSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				searchKeyHasBeSame = false;
			}
		});
		
		
		actvStaticSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				searchKeyHasBeSame = false;
				
			}
		});
		
		//设置软键盘回车跳转到搜索界面
		actvActiveSearch.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_SEARCH 
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
					searchKey = actvActiveSearch.getText().toString().trim();
					if(searchKey == null ||searchKey.equals("")){
						return false;
					}else{
						ActivityUtils.showSearchActivity(LoginedHomePageActivity.this, searchKey);
						return true;
					}
				}
				return false;
			}
		});
		
		actvStaticSearch.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_SEARCH 
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
					searchKey = actvStaticSearch.getText().toString().trim();
					if(searchKey == null ||searchKey.equals("")){
						return false;
					}else{
					ActivityUtils.showSearchActivity(LoginedHomePageActivity.this, searchKey);
					return true;
					}
				}
				return false;
			}
		});
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
		case R.id.user_icon_area:
			//点击头像区域,跳转到个人主页
			break;
		case R.id.suggest_food1_btn:
			if(hotMenuList != null){
				ActivityUtils.showFoodDetailActivity(this, hotMenuList.get(0).getMenuId(), hotMenuList.get(0).getMenuName());
			}
			break;
		case R.id.suggest_food2_btn:
			//点击推荐菜式2按钮
			if(hotMenuList != null){
				ActivityUtils.showFoodDetailActivity(this, hotMenuList.get(1).getMenuId(), hotMenuList.get(1).getMenuName());
			}
			break;
		case R.id.suggest_food3_btn:
			//点击推荐菜式3按钮
			if(hotMenuList != null){
				ActivityUtils.showFoodDetailActivity(this, hotMenuList.get(2).getMenuId(), hotMenuList.get(2).getMenuName());
			}
			break;
		case R.id.suggest_food4_btn:
			//点击推荐菜式4按钮
			if(hotMenuList != null){
				ActivityUtils.showFoodDetailActivity(this, hotMenuList.get(3).getMenuId(), hotMenuList.get(3).getMenuName());
			}
			break;

		default:
			break;
		}
	}

}
