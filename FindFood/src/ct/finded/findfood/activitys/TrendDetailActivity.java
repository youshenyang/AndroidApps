package ct.finded.findfood.activitys;



import cn.com.wo.bitmap.ImageLoader;
import ct.finded.findfood.FoodApplication;
import ct.finded.findfood.R;
import ct.finded.findfood.adapters.CommentAdapter;
import ct.finded.findfood.commons.AppConfigMrg;
import ct.finded.findfood.commons.AppConstant;
import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.control.CircleImageView;
import ct.finded.findfood.control.FooterView;
import ct.finded.findfood.control.MyListView;
import ct.finded.findfood.control.PageScrollView;
import ct.finded.findfood.control.ProgressDlg;
import ct.finded.findfood.control.PageScrollView.OnScrollListener;
import ct.finded.findfood.control.UserToast;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.RequestHelper;
import ct.finded.findfood.http.RequestHelper.OnRequestListener;
import ct.finded.findfood.http.requests.EvaluateReplyRequest;
import ct.finded.findfood.http.requests.MenuDetailRequest;
import ct.finded.findfood.http.requests.PraiseActionRequest;
import ct.finded.findfood.http.requests.ShowGradeReplyRequest;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;
import ct.finded.findfood.http.results.MenuDetailResult;
import ct.finded.findfood.http.results.MenuDetailResult.MenuInfo;
import ct.finded.findfood.http.results.ShowGradeReplyResult;
import ct.finded.findfood.utils.ActivityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;



/**
 * 
 * @ClassName: TrendDetailLayout
 * @Description: 
 * @author Shy_You
 * @date 2014年4月26日
 */
public class TrendDetailActivity extends Activity implements OnClickListener {
	// 生成属性定义方法：
	/**顶部栏返回按钮*/
	private LinearLayout llTopBackBtn;
	/**顶部栏更多按钮*/
	private LinearLayout llTopMoreBtn;
	/**顶部栏*/
	private RelativeLayout llTopNav;
	/**顶部栏背景图片*/
	private ImageView ivTopNavBg;
	/**自定义ScrollView*/
	private PageScrollView pageScrollview;
	/**静态菜式信息栏*/
	private LinearLayout llStaticFoodmsgBar;
	/**静态栏菜式名*/
	private TextView tvFoodNameSfb;
	/**静态栏店名*/
	private TextView tvFoodHouseNameSfb;
	/**静态栏位置*/
	private TextView tvLocationNameSfb;
	/**静态栏价格*/
	private TextView tvPriceSfb;
	/**静态栏电话按钮*/
	private LinearLayout llCallBtnSfb;
	/**菜式图片*/
	private ImageView ivFoodPic;
	
	/**动态菜式信息栏*/
	private LinearLayout llActiveFoodmsgBar;
	/**动态栏菜式名*/
	private TextView tvFoodNameAfb;
	/**动态栏店名*/
	private TextView tvFoodHouseNameAfb;
	/**动态栏位置*/
	private TextView tvLocationNameAfb;
	/**动态栏价格*/
	private TextView tvPriceAfb;
	/**动态栏电话按钮*/
	private LinearLayout llCallBtnAfb;
	/**评价用户头像*/
	private CircleImageView ivUserIcon;
	/**评价用户名*/
	private TextView tvUsername;
	/**菜式名*/
	private TextView tvFoodname;
	/**评分控件*/
	private RatingBar rbRatingBar;
	/**评价内容*/
	private TextView tvCommentContent;
	/**动态评价时间*/
	private TextView tvTrendTime;
	/**评论按钮*/
	private LinearLayout llCommentCountBtn;
	/**评论数量*/
	private TextView tvCommentCount;
	/**赞按钮*/
	private LinearLayout llGoodCountBtn;
	/**赞按钮图标*/
	private ImageView ivGoodBg;
	/**赞数量*/
	private TextView tvGoodCount;
	/**我要评论内容*/
	private EditText etCommentText;
	/**我要评论按钮*/
	private LinearLayout llCommentBtn;
	/**评论列表*/
	private MyListView lvCommentList;
	private ArrayAdapter<String> adapter;
	private CommentAdapter commentAdapter;
//	private List<String> data = new ArrayList<String>();
	/**列表底部提示框*/
	private FooterView footerView = null;
	
	/**动态菜式信息栏位置*/
	private int[] afbLocation = new int[2];
	
	/**静态菜式信息栏位置*/
	private int[] sfbLocation = new int[2];
	
	/**评价信息*/
	private Evaluate evaluate;
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
			
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			
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
			}
		}
		
		@Override
		public void onRequestCancel() {
			
		}
	};
	
	/**评论列表请求帮助类对象*/
	private RequestHelper showGradeReplyRequestHelper;
	/**评论列表请求*/
	private BaseRequest showGradeReplyRequest;
	/**评论列表请求监听器*/
	private OnRequestListener showGradeReplyRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			footerView.showLoading(commentAdapter, lvCommentList);
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			footerView.hide(lvCommentList);
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			ShowGradeReplyResult gradeReplyResult = (ShowGradeReplyResult) result;
			if(gradeReplyResult != null && gradeReplyResult.getData() != null){
				if(isCommentToRefresh){//如果是评论之后更新评论列表，则将评论列表先清空
					commentAdapter.clearDatas();
					isCommentToRefresh = false;
				}
			  commentAdapter.addDatas(gradeReplyResult.getData());
			}
			footerView.hide(lvCommentList);
		}
		
		@Override
		public void onRequestCancel() {
			footerView.hide(lvCommentList);			
		}
	};
	
	/**点赞请求帮助类*/
	private RequestHelper praiseActionRequestHelper;
	/**点赞请求*/
	private BaseRequest praiseActionRequest;
	/**点赞请求监听器*/
	private OnRequestListener praiseActionRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			UserToast.showToast(TrendDetailActivity.this, errorString);
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			if(evaluate.getHasPraise().equals(AppConstant.HAS_PRAISED)){//如果原先是已经点赞，则是取消赞成功
				evaluate.setHasPraise(AppConstant.NO_PRAISE);
				evaluate.setPraiseNum(String.valueOf(Integer.parseInt(evaluate.getPraiseNum())-1));
				tvGoodCount.setText(evaluate.getPraiseNum());
				ivGoodBg.setBackgroundResource(R.drawable.not_good_icon);
			}
			else if(evaluate.getHasPraise().equals(AppConstant.NO_PRAISE)){//如果原先是未点赞，则是点赞成功
				evaluate.setHasPraise(AppConstant.HAS_PRAISED);
				evaluate.setPraiseNum(String.valueOf(Integer.parseInt(evaluate.getPraiseNum())+1));
				tvGoodCount.setText(evaluate.getPraiseNum());
				ivGoodBg.setBackgroundResource(R.drawable.good_icon);
			}
		}
		
		@Override
		public void onRequestCancel() {
			
		}
	};
	
	/**等待对话框*/
	private ProgressDlg progressDlg;
	
	/**评论要发给的用户的id*/
	private String toId;
	/**回复给点评的,则为“f”;回复给某用户，则为“t”*/
	private String hasMore = "f";
	/**是否是评论然后刷新评论列表*/
	private Boolean isCommentToRefresh = false;
	/**评论回复请求帮助类*/
	private RequestHelper  evaluateReplyRequestHelper;
	/**评论回复请求*/
	private BaseRequest evaluateReplyRequest;
	/**评论回复请求监听器*/
	private OnRequestListener evaluateReplyRequestListener = new OnRequestListener() {
		
		@Override
		public void onRequestStart() {
			if(progressDlg == null){
				//创建进度等待对话框
				progressDlg = new ProgressDlg(TrendDetailActivity.this);
				progressDlg.setContent("");
				progressDlg.createDlg();
			}
			progressDlg.show();
		}
		
		@Override
		public void onRequestFailed(String errorString) {
			progressDlg.dismiss();
			UserToast.showToast(TrendDetailActivity.this, errorString);
			
		}
		
		@Override
		public void onRequestComplete(BaseResult result) {
			progressDlg.dismiss();
			etCommentText.setText("");//内容清空
			ActivityUtils.hideKeyBoard(TrendDetailActivity.this, etCommentText);//隐藏软键盘
			UserToast.showToast(TrendDetailActivity.this, TrendDetailActivity.this.getString(R.string.comment_success));
			isCommentToRefresh = true;
			//将评论数+1，并且更新视图
			evaluate.setReplyNum(String.valueOf(Integer.parseInt(evaluate.getReplyNum())+1));
			tvCommentCount.setText(evaluate.getReplyNum());
			doShowGradeReplyRequest();
		}
		
		@Override
		public void onRequestCancel() {
			progressDlg.dismiss();
		}
	};
	
private OnScrollListener scrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
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
		
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trend_detail_layout);
		initViews();
		bindData();
		initEvents();
	}





	/**初始化视图控件*/
	protected void initViews() {
		llTopNav = (RelativeLayout) this.findViewById(R.id.top_nav);
		ivTopNavBg = (ImageView) this.findViewById(R.id.top_nav_bg);
		ivTopNavBg.setAlpha(1f);
		llTopBackBtn = (LinearLayout) this.findViewById(R.id.top_back_btn);
		llTopMoreBtn = (LinearLayout) this.findViewById(R.id.top_more_btn);
		llStaticFoodmsgBar = (LinearLayout) findViewById(R.id.static_foodmsg_bar);
		tvFoodNameSfb = (TextView) findViewById(R.id.food_name_sfb);
		tvFoodHouseNameSfb = (TextView) findViewById(R.id.food_house_name_sfb);
		tvLocationNameSfb = (TextView) findViewById(R.id.location_name_sfb);
		tvPriceSfb = (TextView) findViewById(R.id.price_sfb);
		llCallBtnSfb = (LinearLayout) findViewById(R.id.call_btn_sfb);
		pageScrollview = (PageScrollView) findViewById(R.id.page_scrollview);
		ivFoodPic = (ImageView) findViewById(R.id.food_pic);
		llActiveFoodmsgBar = (LinearLayout) findViewById(R.id.active_foodmsg_bar);
		tvFoodNameAfb = (TextView) findViewById(R.id.food_name_afb);
		tvFoodHouseNameAfb = (TextView) findViewById(R.id.food_house_name_afb);
		tvLocationNameAfb = (TextView) findViewById(R.id.location_name_afb);
		tvPriceAfb = (TextView) findViewById(R.id.price_afb);
		llCallBtnAfb = (LinearLayout) findViewById(R.id.call_btn_afb);
		ivUserIcon = (CircleImageView) findViewById(R.id.user_icon);
		tvUsername = (TextView) findViewById(R.id.username);
		tvFoodname = (TextView) findViewById(R.id.foodname);
		rbRatingBar = (RatingBar) findViewById(R.id.rating_bar);
		tvCommentContent = (TextView) findViewById(R.id.comment_content);
		tvTrendTime = (TextView) findViewById(R.id.trend_time);
		llCommentCountBtn = (LinearLayout) findViewById(R.id.comment_count_btn);
		tvCommentCount = (TextView) findViewById(R.id.comment_count);
		llGoodCountBtn = (LinearLayout) findViewById(R.id.good_count_btn);
		ivGoodBg = (ImageView) findViewById(R.id.good_bg);
		tvGoodCount = (TextView) findViewById(R.id.good_count);
		etCommentText = (EditText) findViewById(R.id.comment_text);
		llCommentBtn = (LinearLayout) findViewById(R.id.comment_btn);
		
		lvCommentList = (MyListView) findViewById(R.id.comment_list);
//		data = TestData.getStringArray();
//	    adapter = new ArrayAdapter<String>(this, R.layout.comment_item, R.id.comment_item, data);
//	    lvCommentList.setAdapter(adapter);
		commentAdapter = new CommentAdapter(this);
		lvCommentList.setAdapter(commentAdapter);
		footerView = new FooterView(this);
	}
	
	
	/**初始化绑定视图数据*/
	private void bindData() {
		evaluate = (Evaluate) getIntent().getSerializableExtra(ParamNames.EVALUATE);
		if(evaluate != null){
			tvFoodNameSfb.setText(evaluate.getMenuName());
			tvFoodNameAfb.setText(evaluate.getMenuName());
			tvUsername.setText(evaluate.getRealname());
			tvFoodname.setText(evaluate.getMenuName());
			int score = evaluate.getGrade().intValue();
			rbRatingBar.setMax(100);
			rbRatingBar.setProgress(score);
			tvCommentContent.setText(evaluate.getContent());
			tvTrendTime.setText(evaluate.getTime());
			tvCommentCount.setText(evaluate.getReplyNum());
			tvGoodCount.setText(evaluate.getPraiseNum());
			ImageLoader.load(FoodApplication.getInstance().getImageFetcher(), evaluate.getFace(), ivUserIcon);
			
			doRequest();
		}
	}

	/**开始http请求*/
	private void doRequest() {
		if(menuDetailRequestHelper == null){
			menuDetailRequestHelper = new RequestHelper(menuDetailRequestListener);
			menuDetailRequest = new MenuDetailRequest(evaluate.getMenuId());
		}
		menuDetailRequestHelper.doRequest(menuDetailRequest);
		
		doShowGradeReplyRequest();
	}





	/**开始显示评论回复列表的请求*/
	public void doShowGradeReplyRequest() {
		if(showGradeReplyRequestHelper == null){
			showGradeReplyRequestHelper = new RequestHelper(showGradeReplyRequestListener);
			showGradeReplyRequest = new ShowGradeReplyRequest(evaluate.getGradeId(), AppConstant.REQUEST_START_PAGE, AppConstant.REQUEST_LENGTH);
		}
		showGradeReplyRequestHelper.doRequest(showGradeReplyRequest);
	}

	/**开始评论回复请求*/
	protected void doEvaluateReplyRequest() {
		if(evaluateReplyRequestHelper == null){
			evaluateReplyRequestHelper = new RequestHelper(evaluateReplyRequestListener);
		}
		toId = evaluate.getUserId();
		evaluateReplyRequest = new EvaluateReplyRequest(evaluate.getGradeId(), toId, etCommentText.getText().toString(), hasMore, AppConfigMrg.getAppConfig().getUserid());
		evaluateReplyRequestHelper.doRequest(evaluateReplyRequest);
	}




	/**初始化事件*/
	protected void initEvents() {
		llTopBackBtn.setOnClickListener(this);
		llStaticFoodmsgBar.setOnClickListener(this);
		tvFoodNameSfb.setOnClickListener(this);
		tvFoodHouseNameSfb.setOnClickListener(this);
		tvLocationNameSfb.setOnClickListener(this);
		tvPriceSfb.setOnClickListener(this);
		llCallBtnSfb.setOnClickListener(this);
		ivFoodPic.setOnClickListener(this);
		llActiveFoodmsgBar.setOnClickListener(this);
		tvFoodNameAfb.setOnClickListener(this);
		tvFoodHouseNameAfb.setOnClickListener(this);
		tvLocationNameAfb.setOnClickListener(this);
		tvPriceAfb.setOnClickListener(this);
		llCallBtnAfb.setOnClickListener(this);
		ivUserIcon.setOnClickListener(this);
		tvUsername.setOnClickListener(this);
		tvFoodname.setOnClickListener(this);
		tvCommentContent.setOnClickListener(this);
		tvTrendTime.setOnClickListener(this);
		llCommentCountBtn.setOnClickListener(this);
		llGoodCountBtn.setOnClickListener(this);
		etCommentText.setOnClickListener(this);
		llCommentBtn.setOnClickListener(this);
		
		pageScrollview.setOnScrollListener(scrollListener);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		    case R.id.top_back_btn:
			     //点击返回按钮
		    	ActivityUtils.shutdownActivity();
			case R.id.static_foodmsg_bar:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.litte_orange_rect_sfb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.food_name_sfb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.food_house_icon_sfb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.food_house_name_sfb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.location_icon_sfb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.location_name_sfb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.price_icon_sfb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.price_sfb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.call_btn_sfb:
				phoneNum = menuInfo.getTel().trim();
				ActivityUtils.showPhoneActivity(this, phoneNum);
				break;
			case R.id.page_scrollview:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.food_pic:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.active_foodmsg_bar:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.litte_orange_rect:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.food_name_afb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.food_house_icon:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.food_house_name_afb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.location_icon:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.location_name_afb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.price_icon:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.price_afb:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.line:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.call_btn_afb:
				phoneNum = menuInfo.getTel().trim();
				ActivityUtils.showPhoneActivity(this, phoneNum);
				break;
			case R.id.user_icon:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.title:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.username:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.foodname:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.rating_bar:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.comment_content:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.line1:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.trendcard_menu:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.trend_time:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.comment_count_btn:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.comment_count:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.v_line_1:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.good_count_btn:
				if(AppConfigMrg.getAppConfig().getIsLogin()){//如果登陆了，则进行点赞或取消赞操作
					if(praiseActionRequestHelper == null){
						praiseActionRequestHelper = new RequestHelper(praiseActionRequestListener);
					}
					praiseActionRequest = new PraiseActionRequest(evaluate.getGradeId(), evaluate.getUserId(), evaluate.getMenuId(),  AppConfigMrg.getAppConfig().getUserid());
					praiseActionRequestHelper.doRequest(praiseActionRequest);
				}else{
					UserToast.showToast(this, getString(R.string.please_login));
				}
				break;
				
			case R.id.v_line_2:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.line2:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.comment_list:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.focus_ta:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.comment_text:
//				IntentUtils.getInstance().startActivity(TrendDetailLayout.this, Object.class);
				break;
			case R.id.comment_btn:
				if(etCommentText.getText().toString() != null && !etCommentText.getText().toString().trim().equals("")){
					doEvaluateReplyRequest();
				}
				break;
		}
	}

	
}
