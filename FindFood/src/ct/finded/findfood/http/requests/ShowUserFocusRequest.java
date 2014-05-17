package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.ShowUserFocusResult;
import ct.finded.findfood.http.results.ShowUserTrendResult;
import ct.finded.findfood.utils.JsonParser;
/**
 * 
 * @ClassName: ShowUserFocusRequest
 * @Description:用户中心，用户关注的人请求类 
 * @author Shy_You
 * @date 2014年5月3日
 */
public class ShowUserFocusRequest extends BaseRequestHandler implements
		BaseRequest {
	
	/**要查看的用户id*/
	private String tuserId;
	
	/**当前用户id*/
	private String userId;
	
	public ShowUserFocusRequest(String tuserId, String userId, int page, int length) {
		this.tuserId = tuserId;
		this.userId = userId;
		this.page = page;
		this.length = length;
	}

	@Override
	public String getPostContent() {
		// TODO Auto-generated method stub
		return ParamNames.TUSERID+"="+tuserId+"&"+ParamNames.USERID+"="+userId+"&"+ParamNames.PAGE+"="+page+"&"+ParamNames.LENGTH+"="+length;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return Urls.SHOW_USER_FOCUS;
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
			return new JsonParser<ShowUserFocusResult>().parse(new String(os.toByteArray(), "utf-8"), ShowUserFocusResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
