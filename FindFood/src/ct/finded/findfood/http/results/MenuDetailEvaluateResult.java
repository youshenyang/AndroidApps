package ct.finded.findfood.http.results;

import java.util.List;

import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;

/**
 * 
 * @ClassName: MenuDetailEvaluateResult
 * @Description: 菜式详情页，用户的点评请求结果类
 * @author Shy_You
 * @date 2014年5月2日
 */
public class MenuDetailEvaluateResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = 4139430716718263443L;
	
	private List<Evaluate> data;

	public List<Evaluate> getData() {
		return data;
	}

	public void setData(List<Evaluate> data) {
		this.data = data;
	}
	

}
