package ct.finded.findfood.control;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;

/**
 * 搜索页面自动匹配框继承AutoCompleteTextView 功能：使输入框为空时也能弹出提示框
 * 
 * @author xuhw 2013-09-23
 * */
public class Search_AutoCompleteTextView extends AutoCompleteTextView {

	/** 构造函数 */
	public Search_AutoCompleteTextView(Context context) {
		super(context);
	}

	/** 构造函数 */
	public Search_AutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/** 构造函数 */
	public Search_AutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean enoughToFilter() {
		return true;
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);

	}

}
