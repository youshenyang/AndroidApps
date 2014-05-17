package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.GetHotMenuResult;
import ct.finded.findfood.utils.JsonParser;
/**
 * 
 * @ClassName: GetHotMenuRequest
 * @Description: 获取首页的热门菜式请求
 * @author Shy_You
 * @date 2014年4月28日
 */
public class GetHotMenuRequest extends BaseRequestHandler implements BaseRequest {


	@Override
	public String getPostContent() {
		return "";
	}

	@Override
	public String getUrl() {
		return Urls.GET_HOT_MENU;
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
			return new JsonParser<GetHotMenuResult>().parse(new String(os.toByteArray(), "utf-8"), GetHotMenuResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
