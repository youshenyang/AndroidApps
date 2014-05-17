package ct.finded.findfood.activitys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ct.finded.findfood.R;
import ct.finded.findfood.adapters.GuessYouLikeAadapter;
import ct.finded.findfood.adapters.NewEvaluateAdapter;
import ct.finded.findfood.adapters.SearchHistoryAdapter;
import ct.finded.findfood.commons.AppConfigMrg;
import ct.finded.findfood.commons.SearchHistory;
import ct.finded.findfood.control.CircleImageView;
import ct.finded.findfood.control.FooterView;
import ct.finded.findfood.control.HorizontalListView;
import ct.finded.findfood.control.PageScrollView;
import ct.finded.findfood.control.PageScrollView.OnScrollListener;
import ct.finded.findfood.control.Search_AutoCompleteTextView;
import ct.finded.findfood.control.UserToast;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.RequestHelper;
import ct.finded.findfood.http.RequestHelper.OnRequestListener;
import ct.finded.findfood.http.requests.GetHotMenuRequest;
import ct.finded.findfood.http.requests.GetNewCommentRequest;
import ct.finded.findfood.http.requests.GetRandMenuRequest;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.GetHotMenuResult;
import ct.finded.findfood.http.results.GetHotMenuResult.BaseMenuMsg;
import ct.finded.findfood.http.results.GetNewCommentResult;
import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;
import ct.finded.findfood.http.results.GetRandMenuResult;
import ct.finded.findfood.utils.ActivityUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
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
 * @ClassName: UnloginHomePageActivity
 * @Description: 未登陆的首页
 * @author Shy_You
 * @date 2014年4月23日
 */
public class UnloginHomePageActivity extends Activity implements OnClickListener{
	
	public static final String TAG = "UnloginHomePageActivity";
	
	/**滚动ScrollView*/
	PageScrollView pageScrollView;
	
	/**顶部栏*/
	private RelativeLayout llTopNav;
	/**顶部栏背景图片*/
	private ImageView ivTopNavBg;
	
	/**顶部栏返回按钮*/
	private LinearLayout llTopBackBtn;
	/**顶部栏更多按钮*/
	private LinearLayout llTopMoreBtn;
	
	/**头像区域*/
	private LinearLayout llUserIconArea;
	/**头像*/
	private CircleImageView ivUserIcon;
	
	/**页头部个人资料背景*/
	private RelativeLayout rlPageHeader;
	
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
	private Search_AutoCompleteTextView actvActiveSearch;
	
//	/**搜索历史列表适配器 */
//	private SearchHistoryAdapter activeSearchHistoryAdapter = null;
//	
//	/** 搜索历史 */
//	private SearchHistory history;
//	
	
	
	/**静态搜索区域*/
	private LinearLayout llStaticSearchBar;
	/**静态搜索框*/
	private Search_AutoCompleteTextView actvStaticSearch;
	
	/**动态搜索区域位置*/
	private int[] asbLocation = new int[2];
	
	/**静态搜索区域位置*/
	private int[] ssbLocation = new int[2];
	
	/**猜你喜欢*/
	private HorizontalListView hlvGuessYouLikeList;
	/**猜你喜欢列表适配器*/
	private GuessYouLikeAadapter likeAadapter;
	
	
	/**最新点评列表*/
	private ListView lvNewestEvaluateList;
	/**最新点评列表适配器*/
	private NewEvaluateAdapter evaluateAdapter;
	
	
	/**列表底部提示视图*/
	private FooterView footView;
	
	/**测试用的list*/
	private List list = new ArrayList();
	
	/**获取热门菜式请求*/
	private RequestHelper getHotMenuRequestHelper;
	private BaseRequest getHotMenuRequest;
	/**热门菜式列表*/
	private List<BaseMenuMsg> hotMenuList;
	
	/**猜你喜欢请求帮助类对象*/
	private RequestHelper guessYouLikeRequestHelper;
	/**猜你喜欢请求*/
	private BaseRequest guessYouLikeRequest;
	/**猜你喜欢请求列表*/
	private List<BaseMenuMsg> guessYouLikeList;
	
	/**最新点评请求帮助类对象*/
	private RequestHelper newestEvaluateRequestHelper;
	/**最新点评请求*/
	private BaseRequest newestEvaluateRequest;
	/**最新点评请求列表*/
	private List<Evaluate> newestEvaluateList;
	
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
	
	/**猜你喜欢请求监听器*/
	private OnRequestListener guessYouLikeRequestListener = new OnRequestListener() {
		
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
			if(result != null){
				GetRandMenuResult guessYouLikeResult = (GetRandMenuResult) result;
				guessYouLikeList = guessYouLikeResult.getData();
				if(guessYouLikeList != null){
					likeAadapter.addDatas(guessYouLikeList);
				}
			}
		}
		
		@Override
		public void onRequestCancel() {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**最新点评请求监听器*/
	private OnRequestListener newestEvaluateRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			footView.showLoading(evaluateAdapter, lvNewestEvaluateList);
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			//footView.showReload();
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			GetNewCommentResult newestEvaluateResult = (GetNewCommentResult) result;
			if(newestEvaluateResult != null){
				newestEvaluateList = newestEvaluateResult.getData();
				if(newestEvaluateList != null){
					evaluateAdapter.addDatas(newestEvaluateList);
				}
			}
			footView.hide(lvNewestEvaluateList);
			
		}
		
		@Override
		public void onRequestCancel() {
			
		}
	};
	
	

	/**搜索关键词*/
	private String searchKey;
	/**静态和动态搜索框的内容是否已经同步*/
	private Boolean searchKeyHasBeSame = true;
	/**显示的搜索框标志，1为动态的，2为静态的*/
	private static final int SHOWING_ACTIVE_SEARCH_BAR = 1;
	private static final int SHOWING_STATIC_SEARCH_BAR = 2;
	/**当前显示的搜索框标志*/
	private int curShowingSearchBar = SHOWING_ACTIVE_SEARCH_BAR;
	
	
	
private OnScrollListener scrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
			int headerHeight  = rlPageHeader.getHeight() - llTopNav.getHeight();
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
			//测试
//			footView.showLoading(evaluateAdapter, lvNewestEvaluateList);//测试底部提示框
//			list = TestData.getTrendCardListData();
//			footView.hide(lvNewestEvaluateList);//测试底部提示框
//			evaluateAdapter.addDatas(list);
		}
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.homepage_unlogin_layout);
//		history = new SearchHistory(getApplicationContext());
		initViews();
		initEvents();
	}

	

	/**初始化视图*/
	private void initViews() {
		llTopNav = (RelativeLayout) this.findViewById(R.id.top_nav);
		ivTopNavBg = (ImageView) this.findViewById(R.id.top_nav_bg);
		pageScrollView = (PageScrollView) this.findViewById(R.id.page_scrollview);
		llTopBackBtn = (LinearLayout) this.findViewById(R.id.top_back_btn);
		llTopMoreBtn = (LinearLayout) this.findViewById(R.id.top_more_btn);
		rlPageHeader = (RelativeLayout) this.findViewById(R.id.page_header);
		llUserIconArea = (LinearLayout) this.findViewById(R.id.user_icon_area);
		ivUserIcon = (CircleImageView) this.findViewById(R.id.user_icon);
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
		actvActiveSearch = (Search_AutoCompleteTextView) this.findViewById(R.id.active_search);
		actvStaticSearch = (Search_AutoCompleteTextView) this.findViewById(R.id.static_search);
		
		
		hlvGuessYouLikeList = (HorizontalListView) this.findViewById(R.id.guest_youlike_list);
		likeAadapter = new GuessYouLikeAadapter(getApplicationContext());
		hlvGuessYouLikeList.setAdapter(likeAadapter);
		
		footView = new FooterView(this);
		lvNewestEvaluateList = (ListView) this.findViewById(R.id.newest_evaluate_list);
		evaluateAdapter = new NewEvaluateAdapter(getApplicationContext());
		lvNewestEvaluateList.setAdapter(evaluateAdapter);
		
//		activeSearchHistoryAdapter = new SearchHistoryAdapter(this, history.getKeys(), history);
//		actvActiveSearch.setAdapter(activeSearchHistoryAdapter);
//		actvStaticSearch.setAdapter(activeSearchHistoryAdapter);
		//测试
//		list = TestData.getTrendCardListData();
//		evaluateAdapter.addDatas(list);
		
		doRequest();
	}
	
	/**开始请求*/
	private void doRequest() {
		if(getHotMenuRequestHelper == null){
			getHotMenuRequestHelper = new RequestHelper(getHotMenuRequestListener);
			getHotMenuRequest = new GetHotMenuRequest();
		}
		getHotMenuRequestHelper.doRequest(getHotMenuRequest);
		
		if(guessYouLikeRequestHelper == null){
			guessYouLikeRequestHelper = new RequestHelper(guessYouLikeRequestListener);
			guessYouLikeRequest = new GetRandMenuRequest();
		}
		guessYouLikeRequestHelper.doRequest(guessYouLikeRequest);
		
		if(newestEvaluateRequestHelper == null){
			newestEvaluateRequestHelper = new RequestHelper(newestEvaluateRequestListener);
			newestEvaluateRequest = new GetNewCommentRequest();
		}
		newestEvaluateRequestHelper.doRequest(newestEvaluateRequest);
		
	}



	private void initEvents() {
		llTopBackBtn.setOnClickListener(this);
		llTopMoreBtn.setOnClickListener(this);
		llUserIconArea.setOnClickListener(this);
		llSuggestFood1Btn.setOnClickListener(this);
		llSuggestFood2Btn.setOnClickListener(this);
		llSuggestFood3Btn.setOnClickListener(this);
		llSuggestFood4Btn.setOnClickListener(this);
		
		pageScrollView.setOnScrollListener(scrollListener);
		
//		/**焦点变化监听器*/
//		actvActiveSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				Search_AutoCompleteTextView view = (Search_AutoCompleteTextView) v;
//				if(hasFocus){//如果获得焦点
//					if(view.getText().toString().trim().length() == 0){//如果关键字为空，则显示本地搜索历史
//						if(view.getAdapter().getCount() == 0) {
//							view.dismissDropDown();
//						}
//						showLocalSearchHistory();
//					}else{//如果关键字不为空，则根据关键字请求获取提示
//						//待补充
//					}
//				}else{//如果失去焦点，则去除关闭提示下拉框
//					view.setText(UnloginHomePageActivity.this.getString(R.string.search_hint));
//					view.dismissDropDown();
//					view.clearFocus();
//				}
//			}
//		});
//		
//		actvStaticSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				Search_AutoCompleteTextView view = (Search_AutoCompleteTextView) v;
//				if(hasFocus){//如果获得焦点
//					if(view.getText().toString().trim().length() == 0){//如果关键字为空，则显示本地搜索历史
//						if(view.getAdapter().getCount() == 0) {
//							view.dismissDropDown();
//						}
//						showLocalSearchHistory();
//					}else{//如果关键字不为空，则根据关键字请求获取提示
//						//待补充
//					}
//				}else{//如果失去焦点，则去除关闭提示下拉框
//					view.setText(UnloginHomePageActivity.this.getString(R.string.search_hint));
//					view.dismissDropDown();
//					view.clearFocus();
//				}
//			}
//		});
		
		//设置搜索框关键字改变时监听事件
		actvActiveSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			
//				if(s == null || s.toString().trim().equals("")){
//					showLocalSearchHistory();
//				}
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
//				if(s == null || s.toString().trim().equals("")){
//					showLocalSearchHistory();
//				}
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
//						history.addKey(searchKey);
						ActivityUtils.showSearchActivity(UnloginHomePageActivity.this, searchKey);
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
//					    history.addKey(searchKey);
						ActivityUtils.showSearchActivity(UnloginHomePageActivity.this, searchKey);
						return true;
					}
				}
				return false;
			}
		});
	}
	
	

	/**显示本地搜索记录*/
//	protected void showLocalSearchHistory() {
//
//		if(activeSearchHistoryAdapter == null){
//			activeSearchHistoryAdapter = new SearchHistoryAdapter(this, history.getKeys(), history);
//			actvActiveSearch.setAdapter(activeSearchHistoryAdapter);
//			actvStaticSearch.setAdapter(activeSearchHistoryAdapter);
//		}else{
//			actvActiveSearch.setAdapter(activeSearchHistoryAdapter);
//			actvStaticSearch.setAdapter(activeSearchHistoryAdapter);
//			activeSearchHistoryAdapter.changeOriginalValues(history.getKeys());
//		}
//		if(history.getKeys() == null || history.getKeys().isEmpty()){
//			dismissDropDown(curShowingSearchBar);
//		}else{
//			showDropDown(curShowingSearchBar);
//		}
//		
//	}



	@Override
	protected void onResume() {
		super.onResume();
		if(AppConfigMrg.getAppConfig().getIsLogin()){
			this.finish();
		}
//		actvActiveSearch.clearFocus();//清除焦点，避免弹出搜索提示下拉框
//		actvStaticSearch.clearFocus();
		actvActiveSearch.setDropDownWidth(actvActiveSearch.getWidth());
		actvActiveSearch.setDropDownHeight(500);
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
		case R.id.user_icon_area:
			//点击头像区域,跳转到登陆页面
			showLoginActivity();
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



	



	/**跳转到登陆界面*/
	private void showLoginActivity() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
	
	
//	/**显示提示下拉框*/
//	protected void showDropDown(int curShowing) {
//		if(curShowing == SHOWING_ACTIVE_SEARCH_BAR){
//			actvActiveSearch.showDropDown();
//		}
//		else if(curShowing == SHOWING_STATIC_SEARCH_BAR){
//			actvStaticSearch.showDropDown();
//		}
//	}
//	
//	/**关闭提示下拉框*/
//	protected void dismissDropDown(int curShowing) {
//		if(curShowing == SHOWING_ACTIVE_SEARCH_BAR){
//			actvActiveSearch.dismissDropDown();
//		}
//		else if(curShowing == SHOWING_STATIC_SEARCH_BAR){
//			actvStaticSearch.dismissDropDown();
//		}
//	}

	
}
