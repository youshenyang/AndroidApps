package ct.finded.findfood.adapters;

import cn.com.wo.bitmap.ImageLoader;
import ct.finded.findfood.FoodApplication;
import ct.finded.findfood.R;
import ct.finded.findfood.activitys.FoodDetailActivity;
import ct.finded.findfood.activitys.TrendDetailActivity;
import ct.finded.findfood.activitys.UserCenterActivity;
import ct.finded.findfood.adapters.TrendCardAdapter.ViewHolder;
import ct.finded.findfood.commons.AppConfigMrg;
import ct.finded.findfood.control.UserToast;
import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;
import ct.finded.findfood.http.results.TrendCardListResult.TrendCard;
import ct.finded.findfood.utils.ActivityUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @ClassName: NewEvaluateAdapter
 * @Description: 最新点评卡片Adapter
 * @author Shy_You
 * @date 2014年4月25日
 */
public class NewEvaluateAdapter extends CommonListAdapter {

	private LayoutInflater inflater = null;
	protected Context context = null;
	
	
    
	public NewEvaluateAdapter(Context ctx) {
		super();
		this.context = ctx;
		inflater = LayoutInflater.from(context);
	}



	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.newest_evaluate_item, null);
			holder = new ViewHolder();
			holder.ivUserIcon = (ImageView) convertView.findViewById(R.id.user_icon);
			holder.tvUsername = (TextView) convertView.findViewById(R.id.username);
			holder.tvFoodname = (TextView) convertView.findViewById(R.id.foodname);
			holder.tvCommentContent = (TextView) convertView.findViewById(R.id.comment_content);
			holder.rbRatingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
			holder.tvTrendTime = (TextView) convertView.findViewById(R.id.trend_time);
			holder.llCommentCountBtn = (LinearLayout) convertView.findViewById(R.id.comment_count_btn);
			holder.llGoodCountBtn = (LinearLayout) convertView.findViewById(R.id.good_count_btn);
			holder.tvCommentCount = (TextView) convertView.findViewById(R.id.comment_count);
			holder.tvGoodCount = (TextView) convertView.findViewById(R.id.good_count);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		initDataAndEvent(convertView, holder, position);
		return convertView;
	}
	
	
	private void initDataAndEvent(View convertView, ViewHolder holder,
			int position) {
		final Evaluate data = (Evaluate) list.get(position);
		if(data != null){
			holder.tvUsername.setText(data.getRealname());
			holder.tvFoodname.setText(data.getMenuName());
			holder.tvCommentContent.setText(data.getContent());
			holder.tvCommentCount.setText(data.getReplyNum());
			holder.tvGoodCount.setText(data.getPraiseNum());
			holder.tvTrendTime.setText(data.getTime());
			holder.rbRatingBar.setMax(100);
			holder.rbRatingBar.setProgress(data.getGrade().intValue());
			ImageLoader.load(FoodApplication.getInstance().getImageFetcher(), data.getFace(), holder.ivUserIcon);
			
			OnClickListener clickListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.foodname:
						Intent intentFood = new Intent(context, FoodDetailActivity.class);
						intentFood.putExtra("menuid", data.getMenuId());
						intentFood.putExtra("menuname", data.getMenuName());
						intentFood.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intentFood);
						break;
						
					case R.id.username:
						if(AppConfigMrg.getAppConfig().getIsLogin()){
							ActivityUtils.showUserCenterActivity(context, data.getUserId(), data.getRealname(), data.getFace());
						   }else{
							UserToast.showToast(context, context.getString(R.string.please_login));
						}
						break;
						
					case R.id.comment_count_btn:
						if(!AppConfigMrg.getAppConfig().getIsLogin()){
							UserToast.showToast(context, context.getString(R.string.please_login));
						}
						break;
					case R.id.good_count_btn:
						if(!AppConfigMrg.getAppConfig().getIsLogin()){
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
					Intent intentTrendDetail = new Intent(context, TrendDetailActivity.class);
					intentTrendDetail.putExtra("evaluate", data);
					intentTrendDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intentTrendDetail);
				}
			});
		}
	}
	
	
	/**临时View存储器*/
	class ViewHolder{
		public ImageView ivUserIcon;//用户头像
		public TextView tvUsername;//用户名
		public TextView tvFoodname;//评价菜式名
		public TextView tvCommentContent;//评价内容
		public RatingBar rbRatingBar;//评分控件
		public TextView tvTrendTime;//时间
		public LinearLayout llCommentCountBtn;//评论按钮
		public TextView tvCommentCount;//评论数
		public LinearLayout llGoodCountBtn;//点赞按钮
		public TextView tvGoodCount;//点赞数
	}

}
