package ct.finded.findfood.adapters;


import cn.com.wo.bitmap.ImageLoader;
import ct.finded.findfood.FoodApplication;
import ct.finded.findfood.R;
import ct.finded.findfood.activitys.FoodDetailActivity;
import ct.finded.findfood.activitys.TrendDetailActivity;
import ct.finded.findfood.activitys.UserCenterActivity;
import ct.finded.findfood.commons.AppConfigMrg;
import ct.finded.findfood.commons.AppConstant;
import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.control.CircleImageView;
import ct.finded.findfood.control.UserToast;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.RequestHelper;
import ct.finded.findfood.http.RequestHelper.OnRequestListener;
import ct.finded.findfood.http.requests.PraiseActionRequest;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;
import ct.finded.findfood.utils.ActivityUtils;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
/**
 * 
 * @ClassName: TrendCardAdapter
 * @Description: 已登陆首页和菜式详情页的点评动态列表适配器
 * @author Shy_You
 * @date 2014年5月17日
 */
public class TrendCardAdapter extends CommonListAdapter {

	private LayoutInflater inflater = null;
	protected Context context = null;
	
	protected RequestHelper praiseActionRequestHelper;
	protected BaseRequest praiseActionRequest;
	/**
	 * 构造函数
	 * @param ctx
	 */
	public TrendCardAdapter(Context ctx) {
		super();
		this.context = ctx;
		inflater = LayoutInflater.from(context);
	}



	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.trend_card_item, null);
			holder = new ViewHolder();
			holder.ivUserIcon = (CircleImageView) convertView.findViewById(R.id.user_icon);
			holder.tvUsername = (TextView) convertView.findViewById(R.id.username);
			holder.tvFoodname = (TextView) convertView.findViewById(R.id.foodname);
			holder.tvCommentContent = (TextView) convertView.findViewById(R.id.comment_content);
			holder.rbRatingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
			holder.tvTrendTime = (TextView) convertView.findViewById(R.id.trend_time);
			holder.llCommentCountBtn = (LinearLayout) convertView.findViewById(R.id.comment_count_btn);
			holder.llGoodCountBtn = (LinearLayout) convertView.findViewById(R.id.good_count_btn);
			holder.tvCommentCount = (TextView) convertView.findViewById(R.id.comment_count);
			holder.tvGoodCount = (TextView) convertView.findViewById(R.id.good_count);
			holder.ivGoodBg = (ImageView) convertView.findViewById(R.id.good_bg);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		initDataAndEvent(convertView, holder, position);
		return convertView;
	}
	
	private void initDataAndEvent(View convertView, final ViewHolder holder,
			int position) {
		final Evaluate data = (Evaluate) list.get(position);
		holder.tvUsername.setText(data.getRealname());
		holder.tvFoodname.setText(data.getMenuName());
		holder.tvCommentContent.setText(data.getContent());
		holder.tvCommentCount.setText(data.getReplyNum());
		holder.tvGoodCount.setText(data.getPraiseNum());
		holder.tvTrendTime.setText(data.getTime());
		holder.rbRatingBar.setMax(100);
		holder.rbRatingBar.setProgress(data.getGrade().intValue());
		ImageLoader.load(FoodApplication.getInstance().getImageFetcher(), data.getFace(), holder.ivUserIcon);
		//设置点赞图标背景
		if(data.getHasPraise().equals(AppConstant.HAS_PRAISED)){
			holder.ivGoodBg.setBackgroundResource(R.drawable.good_icon);
		}
		else if(data.getHasPraise().equals(AppConstant.NO_PRAISE)){
			holder.ivGoodBg.setBackgroundResource(R.drawable.not_good_icon);
		}
		
		OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.foodname:
					ActivityUtils.showFoodDetailActivity(context, data.getMenuId(), data.getMenuName());
					break;
					
				case R.id.username:
					if(AppConfigMrg.getAppConfig().getIsLogin()){
						Intent intentUserCenter = new Intent(context, UserCenterActivity.class);
						intentUserCenter.putExtra(ParamNames.USERID, data.getUserId());
						intentUserCenter.putExtra(ParamNames.USERNAME, data.getRealname());
						intentUserCenter.putExtra(ParamNames.FACE, data.getFace());
						intentUserCenter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intentUserCenter);
					   }else{
						UserToast.showToast(context, context.getString(R.string.please_login));
					}
					break;
					
				case R.id.comment_count_btn:
					if(AppConfigMrg.getAppConfig().getIsLogin()){//如果登陆了，则跳转到点评详情
						ActivityUtils.showTrendDetailActivity(context, data);
					}else{//如果未登录，则提示登录
						UserToast.showToast(context, context.getString(R.string.please_login));
					}
					break;
					
				case R.id.good_count_btn:  //点赞时
					if(AppConfigMrg.getAppConfig().getIsLogin()){//如果登陆了，则进行点赞或取消赞操作
							if(praiseActionRequestHelper == null){
								praiseActionRequestHelper = new RequestHelper(new OnRequestListener() {
									
									@Override
									public void onRequestStart() {
										
									}
									
									@Override
									public void onRequestFailed(String errorString) {
										UserToast.showToast(context, errorString);
									}
									
									@Override
									public void onRequestComplete(BaseResult result) {
										if(data.getHasPraise().equals(AppConstant.HAS_PRAISED)){//如果原先是已经点赞，则是取消赞成功
											data.setHasPraise(AppConstant.NO_PRAISE);
											data.setPraiseNum(String.valueOf(Integer.parseInt(data.getPraiseNum())-1));
											holder.tvGoodCount.setText(data.getPraiseNum());
											holder.ivGoodBg.setBackgroundResource(R.drawable.not_good_icon);
										}
										else if(data.getHasPraise().equals(AppConstant.NO_PRAISE)){//如果原先是未点赞，则是点赞成功
											data.setHasPraise(AppConstant.HAS_PRAISED);
											data.setPraiseNum(String.valueOf(Integer.parseInt(data.getPraiseNum())+1));
											holder.tvGoodCount.setText(data.getPraiseNum());
											holder.ivGoodBg.setBackgroundResource(R.drawable.good_icon);
										}
									}
									
									@Override
									public void onRequestCancel() {
										// TODO Auto-generated method stub
										
									}
								});
							}
							praiseActionRequest = new PraiseActionRequest(data.getGradeId(), data.getUserId(), data.getMenuId(), AppConfigMrg.getAppConfig().getUserid());
							praiseActionRequestHelper.doRequest(praiseActionRequest);
						
					}else{//如果未登录，则提示登录
						UserToast.showToast(context, context.getString(R.string.please_login));
					}
					break;
					
				default:
					break;
				}
			}
		};
		holder.tvFoodname.setOnClickListener(clickListener);
		holder.tvUsername.setOnClickListener(clickListener);
		holder.llCommentCountBtn.setOnClickListener(clickListener);
		holder.llGoodCountBtn.setOnClickListener(clickListener);
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityUtils.showTrendDetailActivity(context, data);
			}
		});
	}

	/**临时View存储器*/
	class ViewHolder{
		public CircleImageView ivUserIcon;//用户头像
		public TextView tvUsername;//用户名
		public TextView tvFoodname;//评价菜式名
		public TextView tvCommentContent;//评价内容
		public RatingBar rbRatingBar;//评分控件
		public TextView tvTrendTime;//时间
		public LinearLayout llCommentCountBtn;//评论按钮
		public TextView tvCommentCount;//评论数
		public LinearLayout llGoodCountBtn;//点赞按钮
		public TextView tvGoodCount;//点赞数
		public ImageView ivGoodBg;
	}

}
