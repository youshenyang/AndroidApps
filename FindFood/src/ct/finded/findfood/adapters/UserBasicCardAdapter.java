package ct.finded.findfood.adapters;

import javax.crypto.spec.IvParameterSpec;

import cn.com.wo.bitmap.ImageLoader;
import ct.finded.findfood.FoodApplication;
import ct.finded.findfood.R;
import ct.finded.findfood.http.results.LoginResult.Userinfo;
import ct.finded.findfood.utils.ActivityUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 
 * @ClassName: UserBasicCardAdapter
 * @Description: 用户中心的关注和粉丝列表适配器
 * @author Shy_You
 * @date 2014年5月3日
 */
public class UserBasicCardAdapter extends CommonListAdapter {
	
	private Context context;
	
	private LayoutInflater inflater;
	
	

	public UserBasicCardAdapter(Context ctx) {
		super();
		this.context = ctx;
		inflater = LayoutInflater.from(context);
	}



	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.focus_who_item, null);
			holder = new ViewHolder();
			holder.ivUserIcon = (ImageView) convertView.findViewById(R.id.user_icon);
			holder.tvUsername = (TextView) convertView.findViewById(R.id.username);
			holder.tvUserDesc = (TextView) convertView.findViewById(R.id.user_desc);
			holder.tvTrendCount = (TextView) convertView.findViewById(R.id.trend_count);
			holder.tvFocusCount = (TextView) convertView.findViewById(R.id.focus_count);
			holder.tvFansCount = (TextView) convertView.findViewById(R.id.fans_count);
			holder.llFocusBtn = (LinearLayout) convertView.findViewById(R.id.focus_btn);
			holder.tvFocusBtnTip = (TextView) convertView.findViewById(R.id.focus_btn_tip);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		initDataAndEvent(convertView, holder, position);
		return convertView;
	}
	
	/**初始化数据和监听事件*/
	private void initDataAndEvent(View convertView, ViewHolder holder,
			int position) {
		final Userinfo data = (Userinfo) list.get(position);
		if(data != null){
			ImageLoader.load(FoodApplication.getInstance().getImageFetcher(), data.getFace(), holder.ivUserIcon);
			holder.tvUsername.setText(data.getRealname());
			holder.tvUserDesc.setText(data.getSignature());
			holder.tvTrendCount.setText(data.getActiveNum());
			holder.tvFocusCount.setText(data.getFollowNum());
			holder.tvFansCount.setText(data.getFansNum());
			if(data.getIsFollow().equals("1")){//1表示已经关注
				holder.llFocusBtn.setBackgroundResource(R.drawable.gray_btn_bg);
				holder.tvFocusBtnTip.setText(context.getString(R.string.has_focus));
			}
			else if(data.getIsFollow().equals("2")){//2表示未关注
				holder.llFocusBtn.setBackgroundResource(R.drawable.green_btn_bg);
				holder.tvFocusBtnTip.setText(context.getString(R.string.add_focus));
			}
			else if(data.getIsFollow().equals("3")){//3表示是自己
				holder.llFocusBtn.setVisibility(View.GONE);
			}
			
			convertView.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {//点击卡片跳转到该用户的主页
					ActivityUtils.showUserCenterActivity(context, data.getUserId(), data.getRealname(), data.getFace());
				}
			});
		}
		
	}


	/**临时视图容器*/
	class ViewHolder{
		public ImageView ivUserIcon;
		public TextView tvUsername;
		public TextView tvUserDesc;
		public TextView tvTrendCount;
		public TextView tvFocusCount;
		public TextView tvFansCount;
		public LinearLayout llFocusBtn;
		public TextView tvFocusBtnTip;
	}

}
