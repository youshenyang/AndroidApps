package ct.finded.findfood.commons;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;

/**
 * 搜索历史关键词
 * 
 * @author xiaoxh
 */
public class SearchHistory {
	
	/** 文件名 */
	private static final String FILE_NAME = "searchHistory.cache";
	/** 搜索历史 */
	private HistoryKey historyKey;
	private Context context;
	
	/** 构造函数 */
	public SearchHistory(Context context) {
		this.context = context;
		init();
	}
	
	/** 初始化 */
	private void init() {
		try {
			ObjectInputStream ois = new ObjectInputStream(context.openFileInput(FILE_NAME));
			historyKey = (HistoryKey)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			historyKey = new HistoryKey();
		} 
	}
	
	/** 获取搜索历史关键字 */
	public List<String> getKeys() {
		return historyKey.getKeys();
	}
	
	/** 增加关键字 */
	public void addKey(String key) {
		List<String> keys = historyKey.getKeys();
		if(keys == null)
			keys = new ArrayList<String>();
		if(!keys.contains(key))
		{
			keys.add(0, key);
			int size = keys.size();
			if(size > 10)
				keys.remove(size -1);
			historyKey.setKeys(keys);
			save();
		}
		else {
			keys.remove(key);
			keys.add(0, key);
			historyKey.setKeys(keys);
			save();
		}
	}
	
	/** 删除关键字 */
	public boolean delete(String key) {
		List<String> keys = historyKey.getKeys();
		if(keys == null || !keys.contains(key))
			return false;
		keys.remove(key);
		historyKey.setKeys(keys);
		save();
		return true;
	}
	
	/** 保存搜索历史 */
	private void save() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(context.openFileOutput(FILE_NAME, 0));
			oos.writeObject(historyKey);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
