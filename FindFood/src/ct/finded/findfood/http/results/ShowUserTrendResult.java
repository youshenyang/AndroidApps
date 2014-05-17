package ct.finded.findfood.http.results;

import java.util.List;

import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;

/**
 * 
 * @ClassName: ShowUserTrendResult
 * @Description: 用户中心，用户动态请求结果
 * @author Shy_You
 * @date 2014年5月3日
 */
public class ShowUserTrendResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = -2866571790366879828L;
	
	private List<Evaluate> data;

	public List<Evaluate> getData() {
		return data;
	}

	public void setData(List<Evaluate> data) {
		this.data = data;
	}
	
	

}
