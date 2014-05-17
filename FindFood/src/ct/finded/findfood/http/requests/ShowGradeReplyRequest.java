package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.MenuDetailResult;
import ct.finded.findfood.http.results.ShowGradeReplyResult;
import ct.finded.findfood.utils.JsonParser;
/**
 * 
 * @ClassName: ShowGradeReplyRequest
 * @Description: 点评详情下的评论请求类
 * @author Shy_You
 * @date 2014年5月1日
 */
public class ShowGradeReplyRequest extends BaseRequestHandler implements
		BaseRequest {
	
	private String gradeId ;

	
	public ShowGradeReplyRequest(String gradeId, int page, int length) {
		this.gradeId = gradeId;
		this.page = page;
		this.length = length;
	}

	@Override
	public String getPostContent() {
		return ParamNames.GRADE_ID + "=" + gradeId + "&" + ParamNames.PAGE + "=" + page + "&" + ParamNames.LENGTH + "=" + length;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return Urls.SHOW_GRADE_REPLY;
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
			return new JsonParser<ShowGradeReplyResult>().parse(new String(os.toByteArray(), "UTF-8"), ShowGradeReplyResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
