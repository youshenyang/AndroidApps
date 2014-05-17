package ct.finded.findfood.http.requests;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import ct.finded.findfood.commons.ParamNames;
import ct.finded.findfood.http.BaseRequest;
import ct.finded.findfood.http.BaseRequestHandler;
import ct.finded.findfood.http.Urls;
import ct.finded.findfood.http.results.BaseResult;
import ct.finded.findfood.http.results.EvaluateReplyResult;
import ct.finded.findfood.http.results.GetHotMenuResult;
import ct.finded.findfood.utils.JsonParser;
/**
 * 
 * @ClassName: EvaluateReplyRequest
 * @Description: 点评详情页内，用户回复功能请求类
 * @author Shy_You
 * @date 2014年5月2日
 */
public class EvaluateReplyRequest extends BaseRequestHandler implements
		BaseRequest {
	/**点评Id*/
	private String gradeId;
	/**要发给的用户的Id*/
	private String toId;
	/**发送内容*/
	private String content;
	/**hasMore=>对应情况一为‘f’,对应情况二为‘t’*/
	private String hasMore;
	/**当前用户的id*/
	private String userId;
	
	

	public EvaluateReplyRequest(String gradeId, String toId, String content,
			String hasMore, String userId) {
		this.gradeId = gradeId;
		this.toId = toId;
		this.content = content;
		this.hasMore = hasMore;
		this.userId = userId;
	}

	@Override
	public String getPostContent() {
		return ParamNames.GRADE_ID+"="+gradeId+"&"+ParamNames.TO_ID+"="+toId+"&"+ParamNames.CONTENT+"="+content+"&"+ParamNames.HAS_MORE+"="+hasMore+"&"+ParamNames.USERID+"="+userId;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return Urls.EVALUATE_REPLY;
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
			return new JsonParser<EvaluateReplyResult>().parse(new String(os.toByteArray(), "utf-8"), EvaluateReplyResult.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
