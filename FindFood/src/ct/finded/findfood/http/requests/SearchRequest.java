package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.MenuDetailResult;
import ct.finded.findfood.http.results.SearchResult;
import ct.finded.findfood.utils.JsonParser;
/**
 * 
 * @ClassName: SearchRequest
 * @Description: 搜索请求类
 * @author Shy_You
 * @date 2014年5月2日
 */
public class SearchRequest extends BaseRequestHandler implements BaseRequest {

	private String key;
	
	
	public SearchRequest(String key,int page,int length) {
		this.key = key;
		this.page = page;
		this.length = length;
	}

	
	@Override
	public String getPostContent() {
		return ParamNames.KEY+"="+key+"&"+ParamNames.PAGE+"="+page+"&"+ParamNames.LENGTH+"="+length;
	}

	@Override
	public String getUrl() {
		return Urls.SEARCH;
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
			return new JsonParser<SearchResult>().parse(new String(os.toByteArray(), "UTF-8"), SearchResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
