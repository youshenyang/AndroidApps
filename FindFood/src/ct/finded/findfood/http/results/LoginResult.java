package ct.finded.findfood.http.results;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: LoginResult
 * @Description: 登陆请求结果类
 * @author Shy_You
 * @date 2014年5月2日
 */
public class LoginResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = -5926796844684682533L;
	
	private List<Userinfo> data;
	
	

	public LoginResult() {
	}

	public List<Userinfo> getData() {
		return data;
	}

	public void setData(List<Userinfo> data) {
		this.data = data;
	}



	/**
	 * 
	 * @ClassName: Userinfo
	 * @Description: 用户信息
	 * @author Shy_You
	 * @date 2014年5月2日
	 */
	public static class Userinfo implements Serializable{
	
		/**
		 *
		 */
		private static final long serialVersionUID = 5352458117107583702L;
		/**用户Id*/
		private String userId;
		/**用户头像路径*/
		private String face;
		/**用户姓名*/
		private String realname;
		/**用户签名*/
		private String signature;
		
		/**用户Email*/
		private String email;
		/**用户性别*/
		private String sex;
		/**当前登陆用户与该用户的关注关系
         *（注意：1表示已经关注，2表示未关注，3表示是自己，不能关注）
         */
		private String isFollow;
		/**动态数目*/
		private String activeNum;
		/**关注数目*/
		private String followNum;
		/**粉丝数目*/
		private String fansNum;
		
		
		public Userinfo() {
		}
		
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getFace() {
			return face;
		}
		public void setFace(String face) {
			this.face = face;
		}
		public String getRealname() {
			return realname;
		}
		public void setRealname(String realname) {
			this.realname = realname;
		}
		public String getSignature() {
			return signature;
		}
		public void setSignature(String signature) {
			this.signature = signature;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getIsFollow() {
			return isFollow;
		}

		public void setIsFollow(String isFollow) {
			this.isFollow = isFollow;
		}

		public String getActiveNum() {
			return activeNum;
		}

		public void setActiveNum(String activeNum) {
			this.activeNum = activeNum;
		}

		public String getFollowNum() {
			return followNum;
		}

		public void setFollowNum(String followNum) {
			this.followNum = followNum;
		}

		public String getFansNum() {
			return fansNum;
		}

		public void setFansNum(String fansNum) {
			this.fansNum = fansNum;
		}
		
	}

}
