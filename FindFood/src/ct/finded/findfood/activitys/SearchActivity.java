package ct.finded.findfood.activitys;

import ct.finded.findfood.R;
import ct.finded.findfood.adapters.SearchResultAadapter;
import ct.finded.findfood.commons.AppConstant;
import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.control.FooterView;
import ct.finded.findfood.control.UserToast;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.RequestHelper;
import ct.finded.findfood.http.RequestHelper.OnRequestListener;
import ct.finded.findfood.http.requests.SearchRequest;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.SearchResult;
import ct.finded.findfood.utils.ActivityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 
 * @ClassName: SearchActivity
 * @Description: 搜索Activity,搜索和展示搜索结果
 * @author Shy_You
 * @date 2014年4月26日
 */
public class SearchActivity extends Activity implements OnClickListener{

	
	/**顶部栏*/
	private RelativeLayout llTopNav;
	/**顶部栏返回按钮*/
	private LinearLayout llTopBackBtn;
	/**顶部栏更多按钮*/
	private LinearLayout llTopMoreBtn;
	/**顶部栏通知按钮*/
	private LinearLayout llInformRingBtn;
	/**顶部栏背景图片*/
	private ImageView ivTopNavBg;
	
	private AutoCompleteTextView actvSearchKey;
	private LinearLayout llSearchResultArea;
	private ListView lvSearchResultList;
//	private ListView lvRelatedSuggestList;
	/**搜索结果列表适配器*/
	private SearchResultAadapter resultAadapter;
	
	private FooterView footerView = null;
	
	private String searchKey = "";
	
	/**搜索请求帮助类对象*/
	private RequestHelper searchRequestHelper;
	private BaseRequest searchRequest;
	private OnRequestListener searchRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			footerView.showLoading(resultAadapter, lvSearchResultList);
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			footerView.hide(lvSearchResultList);
			UserToast.showToast(SearchActivity.this, errorString);
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			SearchResult searchResult = (SearchResult) result;
			if(searchResult != null && searchResult.getData() != null){
				resultAadapter.addDatas(searchResult.getData());
			}
			footerView.hide(lvSearchResultList);
		}
		
		@Override
		public void onRequestCancel() {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);
		searchKey = getIntent().getStringExtra(ParamNames.SEARCH_KEY);
		initViews();
		initEvents();
	}
	
	/**初始化视图组件*/
	protected void initViews() {
		
		llTopNav = (RelativeLayout) this.findViewById(R.id.top_nav);
		ivTopNavBg = (ImageView) this.findViewById(R.id.top_nav_bg);
		ivTopNavBg.setAlpha(1f);
		llTopBackBtn = (LinearLayout) this.findViewById(R.id.top_back_btn);
		llTopMoreBtn = (LinearLayout) this.findViewById(R.id.top_more_btn);
		llInformRingBtn = (LinearLayout) this.findViewById(R.id.inform_ring_btn);
		actvSearchKey = (AutoCompleteTextView) findViewById(R.id.search_key);
		llSearchResultArea = (LinearLayout) findViewById(R.id.search_result_area);
		lvSearchResultList = (ListView) findViewById(R.id.search_result_list);
//		lvRelatedSuggestList = (ListView) findViewById(R.id.related_suggest_list);
		resultAadapter = new SearchResultAadapter(this);
		lvSearchResultList.setAdapter(resultAadapter);
	
		footerView = new FooterView(this);
		
		if(searchKey != null){
			actvSearchKey.setText(searchKey);
			actvSearchKey.setSelection(searchKey.length());
			doSearchRequest();
		}
		
	
	}
	

	/**初始化监听事件*/
	protected void initEvents() {
		actvSearchKey.setOnClickListener(this);
		llSearchResultArea.setOnClickListener(this);
		//设置滚动监听
		lvSearchResultList.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(view.getLastVisiblePosition() == (view.getCount()-1)){
					doSearchRequest();
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		
		actvSearchKey.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_SEARCH 
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
					searchKey = actvSearchKey.getText().toString().trim();
					if(searchKey == null ||searchKey.equals("")){
						return false;
					}else{
						resultAadapter.clearDatas();
						searchRequest = new SearchRequest(searchKey, AppConstant.REQUEST_START_PAGE, AppConstant.REQUEST_LENGTH);
						if(searchRequestHelper == null){
							searchRequestHelper = new RequestHelper(searchRequestListener);
						}
						searchRequestHelper.doRequest(searchRequest);
						return true;
					}
				}
				return false;
			}
		});
	}
	
	/**开始搜索请求*/
	private void doSearchRequest() {
		if(searchRequestHelper == null){
			searchRequestHelper = new RequestHelper(searchRequestListener);
			searchRequest = new SearchRequest(searchKey, AppConstant.REQUEST_START_PAGE, AppConstant.REQUEST_LENGTH);
		}else{
			searchRequest.getRequestHandler().setNextPage();
		}
		searchRequestHelper.doRequest(searchRequest);
	}
	
	@Override
	public void onClick(View v) {
	
	}
}
