package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.GetHotMenuResult;
import ct.finded.findfood.http.results.GetRandMenuResult;
import ct.finded.findfood.utils.JsonParser;

public class GetRandMenuRequest extends BaseRequestHandler implements
		BaseRequest {

	@Override
	public String getPostContent() {
		return "";
	}

	@Override
	public String getUrl() {
		return Urls.GET_RAND_MENU;
	}

	@Override
	public boolean needCache() {
		return false;
	}

	@Override
	public BaseRequestHandler getRequestHandler() {
		return this;
	}

	@Override
	protected BaseResult parseResult(ByteArrayOutputStream os) {
		try {
			return new JsonParser<GetRandMenuResult>().parse(new String(os.toByteArray(), "utf-8"), GetRandMenuResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
