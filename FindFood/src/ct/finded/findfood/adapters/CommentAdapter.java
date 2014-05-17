package ct.finded.findfood.adapters;

import ct.finded.findfood.R;
import ct.finded.findfood.http.results.ShowGradeReplyResult.GradeReply;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 
 * @ClassName: CommentAdapter
 * @Description: 点评详情下的评论
 * @author Shy_You
 * @date 2014年5月15日
 */
public class CommentAdapter extends CommonListAdapter {

	private Context context;
	private LayoutInflater inflater;
	
	
	/**构造函数*/
	public CommentAdapter(Context ctx) {
		super();
		this.context = ctx;
		inflater = LayoutInflater.from(context);
	}



	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.comment_item, null);
			holder = new ViewHolder();
			holder.tvCommentItem = (TextView) convertView.findViewById(R.id.comment_item);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		initDataAndEvent(convertView, holder, position);
		return convertView;
	}
	
	/**初始化数据和事件监听*/
	private void initDataAndEvent(View convertView, ViewHolder holder,
			int position) {
		GradeReply data = (GradeReply) list.get(position);
		if(data != null){
			holder.tvCommentItem.setText(data.getRealname()+":"+data.getContent());
		}
	}

	/**临时视图容器*/
	class ViewHolder {
		public TextView tvCommentItem;
	}

}
