package ct.finded.findfood.http.results;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: GetNewCommentResult
 * @Description: 最新点评请求结果类
 * @author Shy_You
 * @date 2014年4月29日
 */
public class GetNewCommentResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = -1545988055833619826L;

	private List<Evaluate> data;
	
	
	
	public List<Evaluate> getData() {
		return data;
	}



	public void setData(List<Evaluate> data) {
		this.data = data;
	}




	/**
	 * 
	 * @ClassName: Evaluate
	 * @Description: 点评类
	 * @author Shy_You
	 * @date 2014年4月29日
	 */
	public static class Evaluate implements Serializable{
		
		/**
		 *
		 */
		private static final long serialVersionUID = -23164473587631332L;
		/**点评id*/
		private String gradeId;
		/**用户id*/
		private String userId;
		/**用户头像路径*/
		private String face;
		/**用户姓名*/
		private String realname;
		/**评论等级*/
		private Long grade;
		/**评论内容*/
		private String content;
		/**点赞数量*/
		private String praiseNum;
		/**回复数量*/
		private String replyNum;
		/**发布时间*/
		private String time;
		/**菜式Id*/
		private String menuId;
		/**菜式名称*/
		private String menuName;
		/**点赞标志,1表示已赞，2表示未赞*/
		private String hasPraise;
		
		
		public String getGradeId() {
			return gradeId != null ? gradeId : "";
		}
		public void setGradeId(String gradeId) {
			this.gradeId = gradeId;
		}
		public String getUserId() {
			return userId != null ? userId : "";
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getFace() {
			return face  != null ? face : "";
		}
		public void setFace(String face) {
			this.face = face;
		}
		public String getRealname() {
			return realname != null ? realname : "";
		}
		public void setRealname(String realname) {
			this.realname = realname;
		}
		public Long getGrade() {
			return grade != null ? grade : 0;
		}
		public void setGrade(Long grade) {
			this.grade = grade;
		}
		public String getContent() {
			return content != null ? content : "";
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getPraiseNum() {
			return praiseNum != null ? praiseNum : "";
		}
		public void setPraiseNum(String praiseNum) {
			this.praiseNum = praiseNum;
		}
		public String getReplyNum() {
			return replyNum != null ? replyNum : "";
		}
		public void setReplyNum(String replyNum) {
			this.replyNum = replyNum;
		}
		public String getTime() {
			return time != null ? time : "";
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getMenuId() {
			return menuId != null ? menuId : "";
		}
		public void setMenuId(String menuId) {
			this.menuId = menuId;
		}
		public String getMenuName() {
			return menuName != null ? menuName : "";
		}
		public void setMenuName(String menuName) {
			this.menuName = menuName;
		}
		public String getHasPraise() {
			return hasPraise  != null ? hasPraise : "";
		}
		public void setHasPraise(String hasPraise) {
			this.hasPraise = hasPraise;
		}
		
	}
	
}
