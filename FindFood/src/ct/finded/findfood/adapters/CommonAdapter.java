package ct.finded.findfood.adapters;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * @ClassName: CommonAdapter
 * @Description: 通用的List数据列表适配器的基类，带有缓存
 * @author Shy_You
 * @date 2014年4月24日
 */
public abstract class CommonAdapter extends BaseAdapter {
	/** 当前适配器中的数据列表 */
	public List list = null;
	
	//获取list
	public List getDatas() {
		return list;
	}
	
	/**
	 * 构造函数
	 * @param list 初始化适配器中的数据
	 */
	public CommonAdapter(List list) {
		this.list = list;
	}
	
	/**
	 * 构造函数
	 * @param list 初始化适配器中的数据
	 */
	public CommonAdapter() {
	}
	
	//获取数量
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	//获取position位置的对象
	@Override
	public Object getItem(int position) {
		return list == null ? null : list.get(position);
	}

	//获取位置对象id
	@Override
	public long getItemId(int position) {
		return position;
	}

	//获取View
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
	
	/**
	 * 往当前ListView中增加数据列表
	 * @param datas 增加的数据列表
	 */
	public void addDatas(List datas)
	{
		if(this.list == null)
		{
			int size = datas == null ? 0 : datas.size();
			this.list = new ArrayList(size + 5);
		}
		this.list.addAll(datas);
		notifyDataSetChanged();
	}
	
	/**
	 * 往当前ListView中增加数据列表
	 * @param datas 增加的数据列表
	 * @param position 加入的位置
	 */
	public void addDatas(List datas, int position)
	{
		if(this.list == null)
			this.list = new ArrayList();
		this.list.addAll(position, datas);
		notifyDataSetChanged();
	}
	
	/**
	 * 更改当前ListView中的数据列表
	 * @param datas 新的数据列表
	 */
	public void changeDatas(List datas)
	{
		this.list = datas;
		notifyDataSetChanged();
	}
	
	//清空数据
	public void clearDatas() {
		if(this.list != null)
			this.list.clear();
		notifyDataSetChanged();
	}
}