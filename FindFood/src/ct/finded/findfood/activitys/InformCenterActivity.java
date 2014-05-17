package ct.finded.findfood.activitys;

import ct.finded.findfood.R;
import ct.finded.findfood.control.FooterView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
/**
 * 
 * @ClassName: InformCenterActivity
 * @Description: 通知中心Aactivity,包括动态 通知和消息通知
 * @author Shy_You
 * @date 2014年5月5日
 */
public class InformCenterActivity extends Activity implements OnClickListener {
	
	/**动态通知标志*/
	public static final int TREND_INFORM = 1;
	/**系统消息标志*/
	public static final int SYSTEM_MSG = 2;
	
	/**当前选中的Tab标志*/
	private int curSelected;

	private LinearLayout llTrendInformTab;
	private ImageView ivTrendSelectedBg;
	private LinearLayout llSystemMsgTab;
	private ImageView ivSystemSelectedBg;
	private LinearLayout llTrendInformArea;
	private ListView lvTrendInformList;
	private LinearLayout llSystemMsgArea;
	private ListView lvSystemMsgList;
	
	private FooterView footerView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inform_center_layout);
		initViews();
		initEvents();
	}
	
	protected void initViews() {
		llTrendInformTab = (LinearLayout) findViewById(R.id.trend_inform_tab);
		ivTrendSelectedBg = (ImageView) findViewById(R.id.trend_selected_bg);
		llSystemMsgTab = (LinearLayout) findViewById(R.id.system_msg_tab);
		ivSystemSelectedBg = (ImageView) findViewById(R.id.system_selected_bg);
		llTrendInformArea = (LinearLayout) findViewById(R.id.trend_inform_area);
		lvTrendInformList = (ListView) findViewById(R.id.trend_inform_list);
		llSystemMsgArea = (LinearLayout) findViewById(R.id.system_msg_area);
		lvSystemMsgList = (ListView) findViewById(R.id.system_msg_list);
	
		footerView = new FooterView(this);
	
	}
	
	protected void initEvents() {
		llTrendInformTab.setOnClickListener(this);
		ivTrendSelectedBg.setOnClickListener(this);
		llSystemMsgTab.setOnClickListener(this);
		ivSystemSelectedBg.setOnClickListener(this);
		llTrendInformArea.setOnClickListener(this);
		llSystemMsgArea.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.trend_inform_tab://点击tab动态通知
				curSelected = TREND_INFORM;
				changeSelectedState(TREND_INFORM);
				break;
			case R.id.system_msg_tab://点击tab系统消息
				curSelected = SYSTEM_MSG;
				changeSelectedState(SYSTEM_MSG);
				break;
		}
	}
	
	
	/**
	 * Tab选择状态改变
	 * @param selectFlag
	 */
	private void changeSelectedState(int selectFlag){
		switch (selectFlag) {
		case TREND_INFORM :
			ivTrendSelectedBg.setVisibility(View.VISIBLE);
			llTrendInformArea.setVisibility(View.VISIBLE);
			llTrendInformTab.setClickable(false);
			ivSystemSelectedBg.setVisibility(View.GONE);
			llSystemMsgArea.setVisibility(View.GONE);
			llSystemMsgTab.setClickable(true);
			break;
		case SYSTEM_MSG :
			ivSystemSelectedBg.setVisibility(View.VISIBLE);
			llSystemMsgArea.setVisibility(View.VISIBLE);
			llSystemMsgTab.setClickable(true);
			break;
		default:
				break;
		}
	}
}
