package ct.finded.findfood.adapters;

import java.util.List;
import android.view.View;
import android.view.ViewGroup;


/**
 * 
 * @ClassName: CommonListAdapter
 * @Description: 通用的List数据列表适配器的基类
 * @author Shy_You
 * @date 2014年4月24日
 */
public abstract class CommonListAdapter extends CommonAdapter{
	
	
	/**
	 * 无参构造函数
	 */
	public CommonListAdapter() {
		super();
	}

	/**
	 * 构造函数
	 * @param list 初始化适配器中的数据
	 */
	public CommonListAdapter(List list) {
		super(list);
	}
	
	//获取View
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return bindView(position, convertView, parent);
	}
	
	/**
	 * 子类实现此方法，初始化每一个Item的View，并且进行数据绑定到View上
	 * @param position 当前数据在list中的索引
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract View bindView(int position, View convertView, ViewGroup parent);
	
	//资源回收
	public void destroy() {
		changeDatas(null);
		list = null;
	}
	


}