package ct.finded.findfood.http.results;

import java.util.List;

import ct.finded.findfood.http.results.GetHotMenuResult.BaseMenuMsg;

/**
 * 
 * @ClassName: SearchResult
 * @Description: 搜索请求结果类
 * @author Shy_You
 * @date 2014年5月2日
 */
public class SearchResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = 2101477905833404479L;
	
	private List<BaseMenuMsg> data;

	public List<BaseMenuMsg> getData() {
		return data;
	}

	public void setData(List<BaseMenuMsg> data) {
		this.data = data;
	}
	
	
}
