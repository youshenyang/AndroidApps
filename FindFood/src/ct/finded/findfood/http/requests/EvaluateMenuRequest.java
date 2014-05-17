package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.EvaluateMenuResult;
import ct.finded.findfood.http.results.GetHotMenuResult;
import ct.finded.findfood.utils.JsonParser;
/**
 * 
 * @ClassName: EvaluateMenuRequest
 * @Description: 用户点评菜式请求类
 * @author Shy_You
 * @date 2014年5月2日
 */
public class EvaluateMenuRequest extends BaseRequestHandler implements
		BaseRequest {
	/**菜式Id*/
	private String menuId;
	/**点评等级（只能为1/2/3/4其中之一）*/
	private String grade;
	/**点评内容*/
	private String content;
	/**当前用户Id*/
	private String userId;
	
	public EvaluateMenuRequest(String menuId, String grade, String content, String userId) {
		this.menuId = menuId;
		this.grade = grade;
		this.content = content;
		this.userId = userId;
	}

	@Override
	public String getPostContent() {
		return ParamNames.MENU_ID+"="+menuId+"&"+ParamNames.GRADE+"="+grade+"&"+ParamNames.CONTENT+"="+content+"&"+ParamNames.USERID+"="+userId;
	}

	@Override
	public String getUrl() {
		return Urls.EVALUATE_MENU;
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
			return new JsonParser<EvaluateMenuResult>().parse(new String(os.toByteArray(), "utf-8"), EvaluateMenuResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
