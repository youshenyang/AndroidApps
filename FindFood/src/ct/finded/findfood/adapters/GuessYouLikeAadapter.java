package ct.finded.findfood.adapters;

import cn.com.wo.bitmap.ImageLoader;
import ct.finded.findfood.FoodApplication;
import ct.finded.findfood.R;
import ct.finded.findfood.http.results.GetHotMenuResult.BaseMenuMsg;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
/**
 * 
 * @ClassName: GuessYouLikeAadapter
 * @Description: 未登陆首页——猜你喜欢列表适配器
 * @author Shy_You
 * @date 2014年4月30日
 */
public class GuessYouLikeAadapter extends CommonListAdapter {

	private LayoutInflater inflater = null;
	protected Context context = null;
	
	
	
	public GuessYouLikeAadapter(Context ctx) {
		super();
		this.context = ctx;
		inflater = LayoutInflater.from(context);
	}



	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.guess_youlike_item, null);
			holder = new ViewHolder();
			holder.ivFoodPic = (ImageView) convertView.findViewById(R.id.food_pic);
			holder.tvFoodName = (TextView) convertView.findViewById(R.id.food_name);
			holder.tvFoodScore = (TextView) convertView.findViewById(R.id.food_score);
			holder.rbRatingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		initDataAndEvent(convertView, holder, position);
		return convertView;
	}
	
	/**初始化item数据和点击事件*/
	private void initDataAndEvent(View convertView, ViewHolder holder,
			int position) {
		BaseMenuMsg data = (BaseMenuMsg) list.get(position);
		if(data.getMenuName() != null){
			holder.tvFoodName.setText(data.getMenuName());
		}
		if(data.getShowScore() != null){
			holder.tvFoodScore.setText(data.getShowScore());
		}
		if(data.getShowScore() != null){
			holder.rbRatingBar.setMax(100);
			holder.rbRatingBar.setProgress(Integer.parseInt(data.getShowScorePercent()));
		}
		if(data.getImgPath() != null){
			ImageLoader.load(FoodApplication.getInstance().getImageFetcher(), data.getImgPath(), holder.ivFoodPic);
		}
	}


	/**临时View存储器*/
	class ViewHolder{
		public ImageView ivFoodPic;
		public TextView tvFoodName;
		public RatingBar rbRatingBar;
		public TextView tvFoodScore;
	}

}
