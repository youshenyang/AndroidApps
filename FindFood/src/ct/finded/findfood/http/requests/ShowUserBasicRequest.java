package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.GetHotMenuResult;
import ct.finded.findfood.http.results.ShowUserBasicResult;
import ct.finded.findfood.utils.JsonParser;
/**
 * 
 * @ClassName: ShowUserBasicRequest
 * @Description: 用户中心，用户基本资料请求
 * @author Shy_You
 * @date 2014年5月2日
 */
public class ShowUserBasicRequest extends BaseRequestHandler implements
		BaseRequest {
	
	/**要查看的用户id*/
	private String tuserId;
	/**当前用户id*/
	private String userId;
	
	

	public ShowUserBasicRequest(String tuserId, String userId) {
		this.tuserId = tuserId;
		this.userId = userId;
	}

	@Override
	public String getPostContent() {
		return ParamNames.TUSERID+"="+tuserId+"&"+ParamNames.USERID+"="+userId;
	}

	@Override
	public String getUrl() {
		return Urls.SHOW_USER_BASIC;
	}

	@Override
	public boolean needCache() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseRequestHandler getRequestHandler() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	protected BaseResult parseResult(ByteArrayOutputStream os) {
		try {
			return new JsonParser<ShowUserBasicResult>().parse(new String(os.toByteArray(), "utf-8"), ShowUserBasicResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
