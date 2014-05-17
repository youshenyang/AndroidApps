package ct.finded.findfood.control;

import ct.finded.findfood.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
/**
 * 
 * @ClassName: ProgressDlg
 * @Description: 自定义等待进度对话框
 * @author Shy_You
 * @date 2014年5月2日
 */
public class ProgressDlg extends Dialog {
	
	private Dialog mTipDlg = null;
	private LayoutInflater mLayoutInflater = null;
	private Context mContext = null;
	private View rootView = null;
//	private Button btnOk = null;
//	private Button btnCancel = null;
	private TextView tvContent = null;
	
	private int mId = -1;
	
	/** 构造函数 */
	public ProgressDlg(Context context) {
		super(context);
		mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
		initTipDialogLayout();
	}
	
	/** 设置是否显示提示内容，默认为true */
	public void setContentVisible(boolean visible) {
		if(visible) 
			tvContent.setVisibility(View.VISIBLE);
		else
			tvContent.setVisibility(View.GONE);
	}
	
	
	/** 获取对话框 */
	public Dialog getDialog() {
		return mTipDlg;
	}
	
	/** 初始化视图组件 */
	private void initTipDialogLayout() {
		rootView = mLayoutInflater.inflate(R.layout.progress_dlg, null);
		tvContent = (TextView) rootView.findViewById(R.id.content);
	}
	
	/**
	 * 设置对话框提示内容
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		if (tvContent != null) {
			tvContent.setText(content);
		}
	}
	
	/** 取消事件监听 */
	public void setOnDismissListener(OnDismissListener listener) {
		if (mTipDlg != null) {
			mTipDlg.setOnDismissListener(listener);
		}
		else
		{
			createDlg();
			mTipDlg.setOnDismissListener(listener);
		}
	}
	
	/**
	 * 创建对话框
	 */
	public void createDlg() {
		if(mTipDlg == null) {
			WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
			Display display = manager.getDefaultDisplay();
			int width = display.getWidth();
			mTipDlg = new Dialog(mContext, R.style.MyDialog);
			mTipDlg.setContentView(rootView);
			WindowManager.LayoutParams lp = mTipDlg.getWindow().getAttributes();
			lp.width = (int)(width * 0.75); //设置宽度
			mTipDlg.getWindow().setAttributes(lp); 
		}
	}
	
	
	/** 显示对话框 */
	public void show() {
		if (mTipDlg != null && !mTipDlg.isShowing()) {
			mTipDlg.show();
		}
	}
	
	/** 取消显示对话框 */
	public void dismiss() {
		if (mTipDlg != null && mTipDlg.isShowing()) {
			mTipDlg.dismiss();
		}
	} 
	
	/** 提示框是否可以取消 */
	public void setCancelable(boolean isCancelable) {
		if(mTipDlg == null)
			createDlg();
		mTipDlg.setCancelable(isCancelable);
	}
	
	/**
	 * 对话框的序列号的标志量
	 * @param id 对话框的编号
	 */
	public void setId(int id) {
		mId = id;
	}
	
	
	

}
