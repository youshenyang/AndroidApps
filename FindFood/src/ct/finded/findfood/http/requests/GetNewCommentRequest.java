package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.GetNewCommentResult;
import ct.finded.findfood.http.results.GetRandMenuResult;
import ct.finded.findfood.utils.JsonParser;

public class GetNewCommentRequest extends BaseRequestHandler implements
		BaseRequest {

	@Override
	public String getPostContent() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return Urls.GET_NEW_COMMENT;
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
			return new JsonParser<GetNewCommentResult>().parse(new String(os.toByteArray(), "utf-8"), GetNewCommentResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
