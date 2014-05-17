package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.LoginResult;
import ct.finded.findfood.utils.JsonParser;
/**
 * 
 * @ClassName: LoginRequest
 * @Description: 登陆请求类
 * @author Shy_You
 * @date 2014年5月2日
 */
public class LoginRequest extends BaseRequestHandler implements BaseRequest {

	/**登陆用户名*/
	private String username;
	/**登陆密码*/
	private String pwd;
	
	
	public LoginRequest(String username, String pwd) {
		this.username = username;
		this.pwd = pwd;
	}

	@Override
	public String getPostContent() {
		return ParamNames.USERNAME+"="+username+"&"+ParamNames.PWD+"="+pwd;
	}

	@Override
	public String getUrl() {
		return Urls.LOGIN;
	}

	@Override
	public boolean needCache() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseRequestHandler getRequestHandler() {
		return this;
	}

	@Override
	protected BaseResult parseResult(ByteArrayOutputStream os) {
		try {
			return new JsonParser<LoginResult>().parse(new String(os.toByteArray(), "utf-8"), LoginResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
