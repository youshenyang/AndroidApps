package ct.finded.findfood.http.results;

import java.util.List;

import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;

/**
 * 
 * @ClassName: CurUserFocusTrendResult
 * @Description: 当前登陆用户关注的人的动态展示请求结果
 * @author Shy_You
 * @date 2014年5月2日
 */
public class CurUserFocusTrendResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = 139845641295138630L;
	
	private List<Evaluate> data;

	public List<Evaluate> getData() {
		return data;
	}

	public void setData(List<Evaluate> data) {
		this.data = data;
	}

}
