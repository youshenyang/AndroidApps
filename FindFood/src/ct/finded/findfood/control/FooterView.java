package ct.finded.findfood.control;

import ct.finded.findfood.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

/**
 * 
 * @ClassName: FooterView
 * @Description: 通用的ListView footer，包含列表底部提示数据加载，未加载到数据时显示重新加载，无数据时显示“无数据”字样等。
 * @author Shy_You
 * @date 2014年4月25日
 */
public class FooterView {
	/** 列表底部提示框 */
	private LinearLayout rlFooterLayout;
	/** 加载中 */
	private LinearLayout llFooterLoadingLayout;
	/** 重新加载 */
	private LinearLayout llFooterReloadLayout;
	/** 重新加载按钮 */
	private Button btnFooterReload;
	/** 无数据 */
	private LinearLayout llFooterNodataLayout;

	/**
	 * 初始化View
	 * @param ctx
	 */
	public FooterView(Activity app, boolean isIncluded) {
		if(isIncluded) {
			init(app);
		} else {
			this.rlFooterLayout = (LinearLayout) LayoutInflater.from(app).inflate(R.layout.footer_loading_progress_tip, null);
			llFooterLoadingLayout = (LinearLayout) rlFooterLayout.findViewById( R.id.footer_loading_layout );
			llFooterReloadLayout = (LinearLayout) rlFooterLayout.findViewById( R.id.footer_reload_layout );
			btnFooterReload = (Button) rlFooterLayout.findViewById( R.id.footer_reload );
			llFooterNodataLayout = (LinearLayout) rlFooterLayout.findViewById( R.id.footer_nodata_layout );

			hide();
		}
	}
	
	/**
	 * 初始化View
	 * @param ctx
	 */
	public FooterView(Activity app) {
		init(app);
	}
	
	private void init(Activity app) {
		this.rlFooterLayout = (LinearLayout) app.findViewById(R.id.footer_root_layout);
		llFooterLoadingLayout = (LinearLayout) rlFooterLayout.findViewById( R.id.footer_loading_layout );
		llFooterReloadLayout = (LinearLayout) rlFooterLayout.findViewById( R.id.footer_reload_layout );
		btnFooterReload = (Button) rlFooterLayout.findViewById( R.id.footer_reload );
		llFooterNodataLayout = (LinearLayout) rlFooterLayout.findViewById( R.id.footer_nodata_layout );

		hide();
	}
		
	/**
	 * 初始化View
	 * @param ctx
	 */
	public FooterView(View view) {
		this.rlFooterLayout = (LinearLayout) view.findViewById(R.id.footer_root_layout);
		llFooterLoadingLayout = (LinearLayout) rlFooterLayout.findViewById( R.id.footer_loading_layout);
		llFooterReloadLayout = (LinearLayout) rlFooterLayout.findViewById( R.id.footer_reload_layout );
		btnFooterReload = (Button) rlFooterLayout.findViewById( R.id.footer_reload );
		
		llFooterNodataLayout = (LinearLayout) rlFooterLayout.findViewById( R.id.footer_nodata_layout );
		
		hide();
	}

	/**
	 * 隐藏底部加载提示
	 */
	public FootState hide() {
//		Logs.i("FooterView", "=======hide==========");
		this.rlFooterLayout.setVisibility(View.GONE);
//		this.rlFooterLayout.setVisibility(View.INVISIBLE);
		return FootState.Hide;
	}
	
	/**
	 * 隐藏底部加载提示
	 */
	public FootState invisible() {
//		Logs.i("FooterView", "=======hide==========");
		this.rlFooterLayout.setVisibility(View.INVISIBLE);
//		this.rlFooterLayout.setVisibility(View.INVISIBLE);
		return FootState.Hide;
	}
	
	/**
	 * 隐藏底部加载提示
	 */
	public FootState hide(View view) {
//		Logs.i("FooterView", "=======hide view==========");
		this.rlFooterLayout.setVisibility(View.GONE);
//		this.rlFooterLayout.setVisibility(View.INVISIBLE);
		view.setVisibility(View.VISIBLE);
		return FootState.Hide;
	}

	/**
	 * 显示重新加载按钮和提示
	 */
	public FootState showReload() {  //重新加载
//		Logs.i("FooterView", "=======showReload==========");
		this.rlFooterLayout.setVisibility(View.VISIBLE);
		llFooterLoadingLayout.setVisibility(View.GONE);
		llFooterReloadLayout.setVisibility(View.VISIBLE);
		llFooterNodataLayout.setVisibility(View.GONE);
		return FootState.Reload;
	}

	/**
	 * 显示数据加载中的进度和提示
	 */
	public FootState showLoading(BaseAdapter adapter, View view) {
//		Logs.i("FooterView", "=======showLoading view==========");
		if(adapter == null || adapter.getCount() == 0)
			view.setVisibility(View.GONE);
		this.rlFooterLayout.setVisibility(View.VISIBLE);
		llFooterLoadingLayout.setVisibility(View.VISIBLE);
		llFooterReloadLayout.setVisibility(View.GONE);
		llFooterNodataLayout.setVisibility(View.GONE);
		return FootState.Loading;
	}
	
	/**
	 * 显示数据加载中的进度和提示
	 */
	public FootState showLoading() {
//		Logs.i("FooterView", "=======showLoading==========");
		this.rlFooterLayout.setVisibility(View.VISIBLE);
		llFooterLoadingLayout.setVisibility(View.VISIBLE);
		llFooterReloadLayout.setVisibility(View.GONE);
		llFooterNodataLayout.setVisibility(View.GONE);
		return FootState.Loading;
	}
	
	/**
	 * 显示无数据的提示
	 */
	public FootState showNoData(View view){
		this.rlFooterLayout.setVisibility(View.VISIBLE);
		llFooterLoadingLayout.setVisibility(View.GONE);
		llFooterReloadLayout.setVisibility(View.GONE);
		llFooterNodataLayout.setVisibility(View.VISIBLE);
		view.setVisibility(View.GONE);
		return FootState.NoData;
	}
	
	/**
	 * 显示无数据的提示
	 */
	public FootState showNoData(){
		this.rlFooterLayout.setVisibility(View.VISIBLE);
		llFooterLoadingLayout.setVisibility(View.GONE);
		llFooterReloadLayout.setVisibility(View.GONE);
		llFooterNodataLayout.setVisibility(View.VISIBLE);
		return FootState.NoData;
	}
	
	/**
	 * 返回底部加载Layout布局的View－RelativeLayout
	 */
	public LinearLayout getFooter(){
		return this.rlFooterLayout;
	}
	
	/** 设置重新加载按钮的事件 */
	public void setReloadButtonListener(View.OnClickListener listener){
		this.btnFooterReload.setOnClickListener(listener);
	}
	
	/** 将对象置空 */
	public void clear(){
		btnFooterReload = null;
		llFooterLoadingLayout = null;
		llFooterNodataLayout = null;
		llFooterReloadLayout = null;
		rlFooterLayout = null;
	}
	
	/** 通过footview的状态显示footview的显示 */
	public void showVia(FootState state)
	{
		if(state == FootState.Loading)
			showLoading();
		else if(state == FootState.Reload)
			showReload();
		else if(state == FootState.Hide)
			hide();
		else if(state == FootState.NoData)
			showNoData();
	}
	
	public enum FootState {
		Loading, Reload, Hide, NoData, None
	}

	/** 获取高度 */
	public int getHeight() {
		int height = rlFooterLayout.getMeasuredHeight();
		return height;
//		ViewGroup.LayoutParams params = rlFooterLayout.getLayoutParams();
//		return params.height;
	}
	
	public void toogle(boolean isTop) {
		if(isTop) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			params.alignWithParent=true;
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			rlFooterLayout.setLayoutParams(params);
		}
		else {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			params.alignWithParent=true;
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			rlFooterLayout.setLayoutParams(params);
		}
	}
}
