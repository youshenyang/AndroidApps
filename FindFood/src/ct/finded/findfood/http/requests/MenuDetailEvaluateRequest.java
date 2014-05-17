package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.MenuDetailEvaluateResult;
import ct.finded.findfood.http.results.MenuDetailResult;
import ct.finded.findfood.utils.JsonParser;
/**
 * 
 * @ClassName: MenuDetailEvaluateRequest
 * @Description: 菜式详情页，用户的点评请求类
 * @author Shy_You
 * @date 2014年5月2日
 */
public class MenuDetailEvaluateRequest extends BaseRequestHandler implements
		BaseRequest {
	
	/**菜式id*/
	private String menuId;
	/**当前用户id*/
	private String userId;
	

	public MenuDetailEvaluateRequest(String menuId, String userId, int page, int length) {
		this.menuId = menuId;
		this.userId = userId;
		this.page = page;
		this.length = length;
	}

	@Override
	public String getPostContent() {
		return ParamNames.MENU_ID+"="+menuId+"&"+ParamNames.USERID+"="+userId+"&"+ParamNames.PAGE+"="+page+"&"+ParamNames.LENGTH+"="+length;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return Urls.MENU_DETAIL_EVALUATE;
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
			return new JsonParser<MenuDetailEvaluateResult>().parse(new String(os.toByteArray(), "UTF-8"), MenuDetailEvaluateResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
