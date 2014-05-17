package ct.finded.findfood.adapters;

import ct.finded.findfood.R;
import ct.finded.findfood.activitys.FoodDetailActivity;
import ct.finded.findfood.activitys.TrendDetailActivity;
import ct.finded.findfood.activitys.UserCenterActivity;
import ct.finded.findfood.commons.AppConfigMrg;
import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.control.UserToast;
import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;
import ct.finded.findfood.utils.ActivityUtils;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.TextView;
/**
 * 
 * @ClassName: UserTrendCardAdapter
 * @Description: 用户主页动态列表适配器
 * @author Shy_You
 * @date 2014年5月18日
 */
public class UserTrendCardAdapter extends CommonListAdapter {

	private LayoutInflater inflater = null;
	protected Context context = null;
	
	public UserTrendCardAdapter(Context ctx) {
		super();
		this.context = ctx;
		inflater = LayoutInflater.from(context);
	}



	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.trend_comment_item, null);
			holder = new ViewHolder();
			holder.tvFoodname = (TextView) convertView.findViewById(R.id.foodname);
			holder.tvCommentContent = (TextView) convertView.findViewById(R.id.comment_content);
			holder.rbRatingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
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
		holder.tvFoodname.setText(data.getMenuName());
		holder.tvCommentContent.setText(data.getContent());
		holder.rbRatingBar.setMax(100);
		holder.rbRatingBar.setProgress(data.getGrade().intValue());
		
		OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				
				case R.id.foodname:
					ActivityUtils.showFoodDetailActivity(context, data.getMenuId(), data.getMenuName());
					break;
					
				default:
					break;
				}
			}
		};
		
		holder.tvFoodname.setOnClickListener(clickListener);
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityUtils.showTrendDetailActivity(context, data);
			}
		});
	}

	/**临时View存储器*/
	class ViewHolder{
		public TextView tvFoodname;//评价菜式名
		public TextView tvCommentContent;//评价内容
		public RatingBar rbRatingBar;//评分控件
	}
}
