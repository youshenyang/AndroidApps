package ct.finded.findfood.http.results;

import java.util.List;

import ct.finded.findfood.http.results.LoginResult.Userinfo;

/**
 * 
 * @ClassName: ShowUserBasicResult
 * @Description: 用户中心，用户基本资料请求结果类
 * @author Shy_You
 * @date 2014年5月2日
 */
public class ShowUserBasicResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = -2169328312218173542L;
	
	
	Userinfo data;


	public Userinfo getData() {
		return data;
	}


	public void setData(Userinfo data) {
		this.data = data;
	}


}
