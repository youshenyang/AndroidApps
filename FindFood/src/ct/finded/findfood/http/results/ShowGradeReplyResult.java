package ct.finded.findfood.http.results;

import java.io.Serializable;
import java.util.List;
/**
 * 点评详情下的评论请求结果
 * @ClassName: ShowGradeReplyResult
 * @Description: 
 * @author Shy_You
 * @date 2014年5月1日
 */
public class ShowGradeReplyResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = -1554201926104369342L;
	
	private List<GradeReply> data;
	
	
	
	public List<GradeReply> getData() {
		return data;
	}



	public void setData(List<GradeReply> data) {
		this.data = data;
	}



	/**
	 * 
	 * @ClassName: GradeReply
	 * @Description: 点评回复信息
	 * @author Shy_You
	 * @date 2014年5月1日
	 */
	public static class GradeReply implements Serializable{
		/**
		 *
		 */
		private static final long serialVersionUID = -5977592932724318746L;
		/**用户头像路径*/
		private String face;
		/**用户姓名*/
		private String realname;
		/**回复Id*/
		private String replyId;
		/**发送回复的用户Id*/
		private String fromId;
		/**接收回复的用户Id*/
		private String toId;
		/**点评内容*/
		private String content;
		/**发送时间*/
		private String addTime;
		public String getFace() {
			return face;
		}
		public void setFace(String face) {
			this.face = face;
		}
		public String getRealname() {
			return realname;
		}
		public void setRealname(String realname) {
			this.realname = realname;
		}
		public String getReplyId() {
			return replyId;
		}
		public void setReplyId(String replyId) {
			this.replyId = replyId;
		}
		public String getFromId() {
			return fromId;
		}
		public void setFromId(String fromId) {
			this.fromId = fromId;
		}
		public String getToId() {
			return toId;
		}
		public void setToId(String toId) {
			this.toId = toId;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getAddTime() {
			return addTime;
		}
		public void setAddTime(String addTime) {
			this.addTime = addTime;
		}
		
		
	}

}
