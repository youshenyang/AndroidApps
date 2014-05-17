package ct.finded.findfood.http.results;

import java.util.List;

import ct.finded.findfood.http.results.GetNewCommentResult.Evaluate;
import ct.finded.findfood.http.results.LoginResult.Userinfo;

/**
 * 
 * @ClassName: ShowUserFansResult
 * @Description: 用户中心，用户的粉丝请求结果类
 * @author Shy_You
 * @date 2014年5月3日
 */
public class ShowUserFansResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = 296926274985773030L;
	
	private List<Userinfo> data;

	public List<Userinfo> getData() {
		return data;
	}

	public void setData(List<Userinfo> data) {
		this.data = data;
	}

}
