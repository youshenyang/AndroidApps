package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.MenuDetailResult;
import ct.finded.findfood.utils.JsonParser;

public class MenuDetailRequest extends BaseRequestHandler implements
		BaseRequest {
	
	private String menuId;
	
	
	public MenuDetailRequest(String menuId) {
		this.menuId = menuId;
	}

	@Override
	public String getPostContent() {
		return ParamNames.MENU_ID+"="+menuId;
	}

	@Override
	public String getUrl() {
		return Urls.MENU_DETAIL;
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
			return new JsonParser<MenuDetailResult>().parse(new String(os.toByteArray(), "UTF-8"), MenuDetailResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
