package ct.finded.findfood.adapters;

import cn.com.wo.bitmap.ImageLoader;
import ct.finded.findfood.FoodApplication;
import ct.finded.findfood.R;
import ct.finded.findfood.http.results.GetHotMenuResult.BaseMenuMsg;
import ct.finded.findfood.utils.ActivityUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 
 * @ClassName: SearchResultAadapter
 * @Description: 搜索请求结果列表适配器
 * @author Shy_You
 * @date 2014年5月4日
 */
public class SearchResultAadapter extends CommonListAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	
	

	public SearchResultAadapter(Context ctx) {
		this.context = ctx;
		inflater = LayoutInflater.from(context);
	}



	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.search_result_item, null);
			holder = new ViewHolder();
			holder.ivFoodPic = (ImageView) convertView.findViewById(R.id.food_pic);
			holder.tvFoodname = (TextView) convertView.findViewById(R.id.foodname);
			holder.tvAvgScore = (TextView) convertView.findViewById(R.id.avg_score);
			holder.tvNumToEvaluate = (TextView) convertView.findViewById(R.id.num_to_evaluate);
			holder.tvFoodHouseName= (TextView) convertView.findViewById(R.id.food_house_name);
			holder.rbRatingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
			holder.tvFoodPrice = (TextView) convertView.findViewById(R.id.food_price);
			holder.rlMenuBtn = (RelativeLayout) convertView.findViewById(R.id.menu_btn);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		initDataAndEvent(convertView, holder, position);
		return convertView;
	}
	
	/**初始化数据和监听器*/
	private void initDataAndEvent(View convertView, ViewHolder holder,
			int position) {
		final BaseMenuMsg data = (BaseMenuMsg) list.get(position);
		if(data != null){
			ImageLoader.load(FoodApplication.getInstance().getImageFetcher(), data.getImgPath(), holder.ivFoodPic, 1);
			holder.tvFoodname.setText(data.getMenuName());
			holder.tvAvgScore.setText(data.getShowScore());
			holder.tvFoodHouseName.setText(data.getShopName());
			holder.tvNumToEvaluate.setText(data.getCommentNum()+context.getString(R.string.people_to_evaluate));
			holder.tvFoodPrice.setText(data.getPrice()+context.getString(R.string.yuan));
			holder.rbRatingBar.setMax(100);
			holder.rbRatingBar.setProgress(Integer.parseInt(data.getShowScorePercent().replace("%", "")));
			
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ActivityUtils.showFoodDetailActivity(context, data.getMenuId(), data.getMenuName());
				}
			});
		}
	}

	/**临时视图容器*/
	class ViewHolder{
		ImageView ivFoodPic;
		TextView tvFoodname;
		TextView tvAvgScore;
		RatingBar rbRatingBar;
		TextView tvNumToEvaluate;
		TextView tvFoodHouseName;
		RelativeLayout rlMenuBtn;
		TextView tvFoodPrice;
	}

}
