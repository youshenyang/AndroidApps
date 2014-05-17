package ct.finded.findfood.http.results;

import java.util.List;

import ct.finded.findfood.http.results.GetHotMenuResult.BaseMenuMsg;

/**
 * 
 * @ClassName: GetRandMenu
 * @Description: 随机获取9个菜式
 * @author Shy_You
 * @date 2014年4月28日
 */
public class GetRandMenuResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = -8461711188869917168L;

	private List<BaseMenuMsg> data;

	public List<BaseMenuMsg> getData() {
		return data;
	}

	public void setData(List<BaseMenuMsg> data) {
		this.data = data;
	}
	
}
