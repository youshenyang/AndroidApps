package ct.finded.findfood.adapters;


import java.util.ArrayList;
import java.util.List;

import ct.finded.findfood.R;
import ct.finded.findfood.commons.SearchHistory;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 搜索历史记录列表适配器
 * @author shy_you  2013年8月29日 
 *
 */
public class SearchHistoryAdapter extends BaseAdapter implements Filterable{
	private Context context;
	private List<String> mOriginalValues;//所有的Item
	private final Object mLock = new Object();
	//private List<String> mObjects;
	private ArrayFilter mFilter;
	private SearchHistory history;
	
	public SearchHistoryAdapter(Context context, List<String> mOriginalValues, SearchHistory history){
		this.context=context;
		this.mOriginalValues=mOriginalValues;
		this.history = history;
	}
	
	
	
    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(String object) {
        if (mOriginalValues != null) {
            synchronized (mLock) {
                mOriginalValues.remove(object);
            }
        } else {
        	mOriginalValues.remove(object);
        }
        notifyDataSetChanged();
    }
    
    
    /**
     * Remove all elements from the list.
     */
    public void clear() {
        if (mOriginalValues != null) {
            synchronized (mLock) {
                mOriginalValues.clear();
            }
        } else {
        	mOriginalValues.clear();
        }
         notifyDataSetChanged();
    }
	
	@Override
	public Filter getFilter() {
		if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
	}
	


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mOriginalValues != null){
			return mOriginalValues.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		//此方法有误，尽量不要使用
		return mOriginalValues.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void changeOriginalValues(List<String> mOriginalValues){

		this.mOriginalValues = mOriginalValues;
		notifyDataSetChanged();
	}
	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if( convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.search_local_recommend_layout, null);
			holder = new ViewHolder();
			holder.tvRecommendKey = (TextView) convertView.findViewById(R.id.search_recommend_key);
		    holder.ivDelete = (ImageView) convertView.findViewById(R.id.local_recommend_delete_btn);
		    holder.llLocalRecommendLayout = (LinearLayout) convertView.findViewById(R.id.local_recommend_layout);
		    convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		View[] view = new View[]{holder.ivDelete,holder.llLocalRecommendLayout};
		
		holder.tvRecommendKey.setText(mOriginalValues.get(position));
		holder.ivDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				boolean isDelete = content.deleteByName(holder.tvRecommendKey.getText().toString());
				boolean isDelete = history.delete(holder.tvRecommendKey.getText().toString());
				if(isDelete){
//					 mOriginalValues.remove(position);  
		             notifyDataSetChanged(); 
				}
				else {
					Toast.makeText(context, "历史记录删除失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return convertView;
	}

	
	public List<String> getAllItems(){
		return mOriginalValues;
	}
	
	class ViewHolder{
		TextView tvRecommendKey;
		ImageView ivDelete;
		LinearLayout llLocalRecommendLayout;
	}
	
	
	
	   private class ArrayFilter extends Filter{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
		      FilterResults results = new FilterResults();

	                synchronized (mLock) {
	                    ArrayList<String> list = new ArrayList<String>(mOriginalValues);
	                    results.values = list;
	                    results.count = list.size();
	                }
	           
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
	          //noinspection unchecked
//			mOriginalValues = (ArrayList<String>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
			
		}
		   
	   }
}

