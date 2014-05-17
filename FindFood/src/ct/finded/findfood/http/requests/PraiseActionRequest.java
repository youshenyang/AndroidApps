package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.MenuDetailResult;
import ct.finded.findfood.http.results.PraiseActionResult;
import ct.finded.findfood.utils.JsonParser;

public class PraiseActionRequest extends BaseRequestHandler implements
		BaseRequest {
	/**点评Id*/
	private String gradeId;
	/**发该点评的用户Id*/
	private String tuserId;
	/**该点评点评的菜式的Id*/
	private String menuId;
	/**当前用户id*/
	private String userId;
	
	

	public PraiseActionRequest(String gradeId, String tuserId, String menuId, String userId) {
		this.gradeId = gradeId;
		this.tuserId = tuserId;
		this.menuId = menuId;
		this.userId = userId;
	}

	@Override
	public String getPostContent() {
		// TODO Auto-generated method stub
		return ParamNames.GRADE_ID+"="+gradeId+"&"+ParamNames.TUSERID+"="+tuserId+"&"+ParamNames.MENU_ID+"="+menuId+"&"+ParamNames.USERID+"="+userId;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return Urls.PRAISE_ACTION;
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
			return new JsonParser<PraiseActionResult>().parse(new String(os.toByteArray(), "UTF-8"), PraiseActionResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
