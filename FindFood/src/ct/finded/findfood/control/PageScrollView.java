package ct.finded.findfood.control;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * @ClassName: PageScrollView
 * @Description: 结合ListView的ScrollView，支持滚动底部监听事件
 * @author Shy_You
 * @date 2014年4月24日
 */
public class PageScrollView extends ScrollView {
	
	/** 内容视图 */
    private View contentView;
    /** 上次onBottom时间 */
    private long lastOnBottomTime = 0l;
	
	/**
     * 滚动监听接口
     */
    public interface OnScrollListener {
    	/** 当ScrollView滚动至底部时触发 */
        void onBottom();
        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
    }
    
    
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if(onScrollListener != null){
        	onScrollListener.onScrollChanged(this, l, t, oldl, oldt);
        }
        if (contentView != null && contentView.getMeasuredHeight() <= getScrollY() + getHeight()) {
            if(isFastScroll())
            	return ;
        	if (onScrollListener != null) {
            	onScrollListener.onBottom();
            }
        }
    }
    
    /** 判断是否重复快速滚动 */
    private boolean isFastScroll() {
        long time = System.currentTimeMillis();
        long timeD = time - lastOnBottomTime;
        if ( 0 < timeD && timeD < 800) {
            return true;   
        }   
        lastOnBottomTime = time;   
        return false;   
    }

    /** 构造函数 */
    public PageScrollView(Context context) {
        super(context);
    }

    /** 构造函数 */
    public PageScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** 构造函数 */
    public PageScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /** 滚动监听器 */
	private OnScrollListener onScrollListener;

    /**
     * 设置滚动监听器
     * 
     * @return void
     *
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
        if (contentView == null) {
            contentView = getChildAt(0);
        }
    }
    
    /** 底部高度 */
    private int footHeight;
    
    /**
     * 设置FooterView的高度
     */
    public void setFootHeight(int footHeight) {
    	this.footHeight = footHeight;
    }

    
}
