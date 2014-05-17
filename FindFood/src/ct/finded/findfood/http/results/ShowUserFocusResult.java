package ct.finded.findfood.http.results;

import java.util.List;

import ct.finded.findfood.http.results.LoginResult.Userinfo;

/**
 * 
 * @ClassName: ShowUserFocusResult
 * @Description: 用户中心，用户关注的人请求结果类
 * @author Shy_You
 * @date 2014年5月3日
 */
public class ShowUserFocusResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = 4101881860656239236L;
	
	private List<Userinfo> data;

	public List<Userinfo> getData() {
		return data;
	}

	public void setData(List<Userinfo> data) {
		this.data = data;
	}
	

}
