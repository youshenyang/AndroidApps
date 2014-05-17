package ct.finded.findfood.activitys;

import java.util.ArrayList;
import java.util.List;

import cn.com.wo.bitmap.ImageLoader;
import ct.finded.findfood.FoodApplication;
import ct.finded.findfood.R;
import ct.finded.findfood.adapters.TrendCardAdapter;
import ct.finded.findfood.commons.AppConfigMrg;
import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.commons.TestData;
import ct.finded.findfood.control.FooterView;
import ct.finded.findfood.control.MyListView;
import ct.finded.findfood.control.PageScrollView;
import ct.finded.findfood.control.UserToast;
import ct.finded.findfood.control.PageScrollView.OnScrollListener;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.RequestHelper;
import ct.finded.findfood.http.RequestHelper.OnRequestListener;
import ct.finded.findfood.http.requests.MenuDetailEvaluateRequest;
import ct.finded.findfood.http.requests.MenuDetailRequest;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;
import ct.finded.findfood.http.results.MenuDetailEvaluateResult;
import ct.finded.findfood.http.results.MenuDetailResult;
import ct.finded.findfood.http.results.MenuDetailResult.MenuInfo;
import ct.finded.findfood.utils.ActivityUtils;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
/**
 * 
 * @ClassName: FoodDetailActivity
 * @Description: 菜式详情Activity
 * @author Shy_You
 * @date 2014年4月25日
 */
public class FoodDetailActivity extends Activity implements OnClickListener{

	/**菜式id*/
	private String menuid = "";
	/**菜式名称*/
	private String menuname = "";
	

	/**滚动ScrollView*/
	PageScrollView pageScrollView;
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
	
	
	/**菜式图片*/
	private ImageView ivFoodPic;
	
	/**静态菜式信息栏*/
	private LinearLayout llStaticFoodmsgBar;
	/**静态栏菜式名称*/
	private TextView tvFoodNameSfb;
	/**静态栏餐馆名称*/
	private TextView tvFoodHouseNameSfb;
	/**静态栏餐馆位置*/
	private TextView tvLocationNameSfb;
	/**静态栏菜式价格*/
	private TextView tvPriceSfb;
	/**静态栏电话按钮*/
	private LinearLayout llCallBtnSbf;
	
	/**动态菜式信息栏*/
	private LinearLayout llActiveFoodmsgBar;
	/**动态栏菜式名称*/
	private TextView tvFoodNameAfb;
	/**动态栏餐馆名称*/
	private TextView tvFoodHouseNameAfb;
	/**动态栏餐馆位置*/
	private TextView tvLocationNameAfb;
	/**动态栏菜式价格*/
	private TextView tvPriceAfb;
	/**动态栏电话按钮*/
	private LinearLayout llCallBtnAbf;
	
	/**动态菜式信息栏位置*/
	private int[] afbLocation = new int[2];
	
	/**静态菜式信息栏位置*/
	private int[] sfbLocation = new int[2];
	
	/**平均分*/
	private TextView tvAvgScore;
	/**多少人评价*/
	private TextView tvNumToEvaluate;
	
	/**四星率进度条*/
	private ProgressBar pbRatePgFourstars;
	/**三星率进度条*/
	private ProgressBar pbRatePgThreestars;
	/**二心率进度条*/
	private ProgressBar pbRatePgTwostars;
	/**一心率进度条*/
	private ProgressBar pbRatePgOnestars;
	/**四星百分比*/
	private TextView tvRatePercFourstars;
	/**三星百分比*/
	private TextView tvRatePercThreestars;
	/**二星百分比*/
	private TextView tvRatePercTwostars;
	/**一星百分比*/
	private TextView tvRatePercOnestars;
	
	/**动态列表*/
	private MyListView lvTrendCardList;
	/**动态列表适配器*/
	private TrendCardAdapter cardAdapter;
	
	/**列表底部提示框*/
	private FooterView footerView;
	
	private List list = new ArrayList();
	
	private List<Evaluate> detailEvaluateList;
	
	/**菜式信息*/
	private MenuInfo menuInfo;
	/**商家电话号码*/
	private String phoneNum;
	
	/**菜式详情请求帮助类*/
	private RequestHelper menuDetailRequestHelper;
	/**菜式详情请求*/
	private BaseRequest menuDetailRequest;
	/**菜式详情请求监听类*/
	private OnRequestListener menuDetailRequestListener = new OnRequestListener() {
		
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
			MenuDetailResult detailResult = (MenuDetailResult) result;
			if(detailResult != null){
				menuInfo = detailResult.getData().get(0);
				ImageLoader.load(FoodApplication.getInstance().getImageFetcher(), menuInfo.getImgPath(), ivFoodPic, 1);
				tvFoodHouseNameAfb.setText(menuInfo.getShopName());
				tvFoodHouseNameSfb.setText(menuInfo.getShopName());
				tvLocationNameAfb.setText(menuInfo.getAddress());
				tvLocationNameSfb.setText(menuInfo.getAddress());
				tvPriceAfb.setText(menuInfo.getPrice());
				tvPriceSfb.setText(menuInfo.getPrice());
				tvAvgScore.setText(menuInfo.getShowScore());
				tvNumToEvaluate.setText(menuInfo.getCommentNum()+getResources().getString(R.string.people_to_evaluate));
				tvRatePercFourstars.setText(menuInfo.getGrade4Percent());
				tvRatePercThreestars.setText(menuInfo.getGrade3Percent());
				tvRatePercTwostars.setText(menuInfo.getGrade2Percent());
				tvRatePercOnestars.setText(menuInfo.getGrade1Percent());
				
				int rate1 = Integer.parseInt(menuInfo.getGrade1Percent().replace("%", ""));
				int rate2 = Integer.parseInt(menuInfo.getGrade2Percent().replace("%", ""));
				int rate3 = Integer.parseInt(menuInfo.getGrade3Percent().replace("%", ""));
				int rate4 = Integer.parseInt(menuInfo.getGrade4Percent().replace("%", ""));
				pbRatePgOnestars.setProgress(rate1);
				pbRatePgTwostars.setProgress(rate2);
				pbRatePgThreestars.setProgress(rate3);
				pbRatePgFourstars.setProgress(rate4);
				
			}
		}
		
		@Override
		public void onRequestCancel() {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**菜式详情下的用户点评请求帮助类*/
	private RequestHelper menuDetailEvaluateRequestHelper;
	/**菜式详情下的用户点评请求*/
	private BaseRequest menuDetailEvaluateRequest;
	/**菜式详情下的用户点评请求监听器*/
	private OnRequestListener menuDetailEvaluateRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			footerView.showLoading(cardAdapter, lvTrendCardList);
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			footerView.hide(lvTrendCardList);
			UserToast.showToast(FoodDetailActivity.this, errorString);
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			MenuDetailEvaluateResult detailEvaluateResult = (MenuDetailEvaluateResult) result;
			if(detailEvaluateResult != null){
				detailEvaluateList = detailEvaluateResult.getData();
				if(detailEvaluateList != null){
					cardAdapter.addDatas(detailEvaluateList);
				}
			}
			footerView.hide(lvTrendCardList);
		}
		
		@Override
		public void onRequestCancel() {
			// TODO Auto-generated method stub
			
		}
	};
	
	
private OnScrollListener scrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
			int headerHeight  = ivFoodPic.getHeight() - llTopNav.getHeight();
			float ratio =  (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
			ivTopNavBg.setAlpha(ratio);
			
			llStaticFoodmsgBar.getLocationOnScreen(sfbLocation);
			llActiveFoodmsgBar.getLocationOnScreen(afbLocation);
			if(afbLocation[1] <= sfbLocation[1]){
				llStaticFoodmsgBar.setVisibility(View.VISIBLE);
			}else{
				llStaticFoodmsgBar.setVisibility(View.GONE);
			}
		}
		
		@Override
		public void onBottom() {
//			//测试
//			footerView.showLoading(cardAdapter, lvTrendCardList);//测试底部提示框
//			list = TestData.getTrendCardListData();
//			footerView.hide(lvTrendCardList);//测试底部提示框
//			cardAdapter.addDatas(list);
			if(AppConfigMrg.getAppConfig().getIsLogin()){//如果用户登录了
				doMenuDetailEvaluateRequest();
			}else{
				UserToast.showToast(FoodDetailActivity.this, FoodDetailActivity.this.getString(R.string.please_login));
			}
			
		}

	};
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_detail_layout);
		menuid = getIntent().getStringExtra(ParamNames.MENU_ID);
		menuname = getIntent().getStringExtra(ParamNames.MENU_NAME);
		
		initViews();
		initEvents();
	}

	

	private void initViews() {
		llTopNav = (RelativeLayout) this.findViewById(R.id.top_nav);
		ivTopNavBg = (ImageView) this.findViewById(R.id.top_nav_bg);
		pageScrollView = (PageScrollView) this.findViewById(R.id.page_scrollview);
		llTopBackBtn = (LinearLayout) this.findViewById(R.id.top_back_btn);
		llTopMoreBtn = (LinearLayout) this.findViewById(R.id.top_more_btn);
		llInformRingBtn = (LinearLayout) this.findViewById(R.id.inform_ring_btn);
		ivFoodPic = (ImageView) this.findViewById(R.id.food_pic);
		llStaticFoodmsgBar = (LinearLayout) this.findViewById(R.id.static_foodmsg_bar);
		llActiveFoodmsgBar = (LinearLayout) this.findViewById(R.id.active_foodmsg_bar);
		tvFoodNameAfb = (TextView) this.findViewById(R.id.food_name_afb);
		tvFoodNameSfb = (TextView) this.findViewById(R.id.food_name_sfb);
		tvFoodHouseNameAfb = (TextView) this.findViewById(R.id.food_house_name_afb);
		tvFoodHouseNameSfb = (TextView) this.findViewById(R.id.food_house_name_sfb);
	    tvLocationNameAfb = (TextView) this.findViewById(R.id.location_name_afb);
	    tvLocationNameSfb = (TextView) this.findViewById(R.id.location_name_sfb);
	    tvPriceAfb = (TextView) this.findViewById(R.id.price_afb);
	    tvPriceSfb = (TextView) this.findViewById(R.id.price_sfb);
	    llCallBtnAbf = (LinearLayout) this.findViewById(R.id.call_btn_afb);
	    llCallBtnSbf = (LinearLayout) this.findViewById(R.id.call_btn_sfb);
	    tvAvgScore = (TextView) this.findViewById(R.id.avg_score);
	    tvNumToEvaluate = (TextView) this.findViewById(R.id.num_to_evaluate);
	    pbRatePgFourstars = (ProgressBar) this.findViewById(R.id.rate_pg_fourstars);
	    pbRatePgThreestars = (ProgressBar) this.findViewById(R.id.rate_pg_threestars);
	    pbRatePgTwostars = (ProgressBar) this.findViewById(R.id.rate_pg_twostars);
	    pbRatePgOnestars = (ProgressBar) this.findViewById(R.id.rate_pg_onestars);
	    tvRatePercFourstars = (TextView) this.findViewById(R.id.rate_perc_fourstars);
	    tvRatePercThreestars = (TextView) this.findViewById(R.id.rate_perc_threestars);
	    tvRatePercTwostars = (TextView) this.findViewById(R.id.rate_perc_twostars);
	    tvRatePercOnestars = (TextView) this.findViewById(R.id.rate_perc_onestars);
	    
	    lvTrendCardList = (MyListView) this.findViewById(R.id.trend_card_list);
	    cardAdapter = new TrendCardAdapter(getApplicationContext());
	    lvTrendCardList.setAdapter(cardAdapter);
//	    cardAdapter.addDatas(TestData.getTrendCardListData());
	    
	    footerView = new FooterView(this);
	    
	    if(menuname != null){
	    	tvFoodNameAfb.setText(menuname);
	    	tvFoodNameSfb.setText(menuname);
	    }
	    
	    if(menuid != null){
	    	doRequest();
	    }
	    
	}
	
	/**http请求*/
	protected void doRequest() {
		if(menuDetailRequestHelper == null){
			menuDetailRequestHelper = new RequestHelper(menuDetailRequestListener);
			menuDetailRequest = new MenuDetailRequest(menuid);
		}
		menuDetailRequestHelper.doRequest(menuDetailRequest);
		
		doMenuDetailEvaluateRequest();
	}
	
	/**开始菜式详情点评的请求*/
	public void doMenuDetailEvaluateRequest() {
		if(menuDetailEvaluateRequestHelper == null){
			menuDetailEvaluateRequestHelper = new RequestHelper(menuDetailEvaluateRequestListener);
			menuDetailEvaluateRequest = new MenuDetailEvaluateRequest(menuid,AppConfigMrg.getAppConfig().getUserid(), 1, 20);
		}else{
			menuDetailEvaluateRequest.getRequestHandler().setNextPage();
		}
		menuDetailEvaluateRequestHelper.doRequest(menuDetailEvaluateRequest);
	}



	/**初始化事件*/
	private void initEvents() {
		pageScrollView.setOnScrollListener(scrollListener);
		llTopBackBtn.setOnClickListener(this);
		llCallBtnSbf.setOnClickListener(this);
		llCallBtnAbf.setOnClickListener(this);
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



	//点击事件处理
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
		case R.id.top_back_btn:
			ActivityUtils.shutdownActivity();
			break;
		case R.id.call_btn_afb:
			 phoneNum = menuInfo.getTel().trim();
		    ActivityUtils.showPhoneActivity(this, phoneNum);
		    break;
		case R.id.call_btn_sfb:
			 phoneNum = menuInfo.getTel().trim();
		    ActivityUtils.showPhoneActivity(this, phoneNum);
		    break;
			
		}
		
	}
	

}
