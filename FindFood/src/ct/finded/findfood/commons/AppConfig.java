package ct.finded.findfood.commons;

import java.io.Serializable;

/**
 * 
 * @ClassName: AppConfig
 * @Description: 应用相关的一些配置信息类
 * @author Shy_You
 * @date 2014年4月22日
 */
public class AppConfig implements Serializable{

	private static final long serialVersionUID = -7998244875871403191L;
	
	/** 用户ID */
	private String userid;
	/** 用户昵称 */
	private String username;
	/**用户头像地址*/
	private String face;
	/**用户签名*/
	private String signature;
	
	
	
	
	/**是否登录*/
	private Boolean isLogin;
	
	
	
	public String getUserid() {
		return userid == null ? "" : userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username == null ? "" : username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getIsLogin() {
		return isLogin == null ? false:isLogin;
	}
	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}
	public String getFace() {
		return face == null ? "" : face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getSignature() {
		return signature == null ? "" : signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
