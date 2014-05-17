package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.CurUserFocusTrendResult;
import ct.finded.findfood.utils.JsonParser;
/**
 * 
 * @ClassName: CurUserFocusTrend
 * @Description: 当前登陆用户关注的人的动态展示请求类
 * @author Shy_You
 * @date 2014年5月2日
 */
public class CurUserFocusTrendRequest extends BaseRequestHandler implements
		BaseRequest {
	
	/**当前的用户id*/
	private String userId;
	

	public CurUserFocusTrendRequest(String userId, int page, int length) {
		this.userId = userId;
		this.page = page;
		this.length = length;
	}

	@Override
	public String getPostContent() {
		return ParamNames.USERID+"="+userId+"&"+ParamNames.PAGE+"="+page+"&"+ParamNames.LENGTH+"="+length;
	}

	@Override
	public String getUrl() {
		return Urls.CUR_USER_FOUCS_TREND;
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
			return new JsonParser<CurUserFocusTrendResult>().parse(new String(os.toByteArray(), "utf-8"), CurUserFocusTrendResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
