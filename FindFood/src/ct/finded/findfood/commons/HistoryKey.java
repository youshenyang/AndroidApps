package ct.finded.findfood.commons;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索历史数据
 * 
 * @author xiaoxh
 */
public class HistoryKey implements Serializable {
	private static final long serialVersionUID = 7591041270053944323L;
	private List<String> keys;
	public List<String> getKeys() {
		return keys;
	}
	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
}
