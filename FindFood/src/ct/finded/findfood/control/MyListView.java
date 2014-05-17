package ct.finded.findfood.control;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * 
 * @ClassName: MyListView
 * @Description: 自定义ListView
 * @author Shy_You
 * @date 2014年4月24日
 */
public class MyListView extends ListView {

	public MyListView(Context context) {
		// TODO Auto-generated method stub
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		super(context, attrs);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		// TODO Auto-generated method stub
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
