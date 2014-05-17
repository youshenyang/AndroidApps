package ct.finded.findfood.http.results;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: TrendCardListResult
 * @Description: 
 * @author Shy_You
 * @date 2014年4月24日 
 */
public class TrendCardListResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = 4282869185415780891L;

	/**动态卡片列表*/
	private List<TrendCard> list;
	
	
	
	public List<TrendCard> getList() {
		return list;
	}



	public void setList(List<TrendCard> list) {
		this.list = list;
	}



	public static class TrendCard implements Serializable{
		/**
		 *
		 */
		private static final long serialVersionUID = -3808341027946658911L;
		
		/**动态id*/
		private String trendid;
		/**头像地址*/
		private String imgurl;
		/**用户名*/
		private String username;
		/**用户id*/
		private String userid;
		/**菜式名*/
		private String foodname;
		/**菜式id*/
		private String foodid;
		/**评分*/
		private String rate;
		/**评价内容*/
		private String content;
		/**评价时间*/
		private String trendtime;
		/**评论数*/
		private String commentcount;
		/**点赞数*/
		private String goodcount;
		
		
		
		@Override
		public String toString() {
			return "TrendCard [trendid=" + trendid + ", username=" + username
					+ ", foodname=" + foodname + ", trendtime=" + trendtime
					+ "]";
		}
		
		public String getTrendid() {
			return trendid;
		}
		public void setTrendid(String trendid) {
			this.trendid = trendid;
		}
		public String getImgurl() {
			return imgurl;
		}
		public void setImgurl(String imgurl) {
			this.imgurl = imgurl;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getUserid() {
			return userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}
		public String getFoodname() {
			return foodname;
		}
		public void setFoodname(String foodname) {
			this.foodname = foodname;
		}
		public String getRate() {
			return rate;
		}
		public void setRate(String rate) {
			this.rate = rate;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getTrendtime() {
			return trendtime;
		}
		public void setTrendtime(String trendtime) {
			this.trendtime = trendtime;
		}
		public String getCommentcount() {
			return commentcount;
		}
		public void setCommentcount(String commentcount) {
			this.commentcount = commentcount;
		}
		public String getGoodcount() {
			return goodcount;
		}
		public void setGoodcount(String goodcount) {
			this.goodcount = goodcount;
		}

		public String getFoodid() {
			return foodid;
		}

		public void setFoodid(String foodid) {
			this.foodid = foodid;
		}
		
	}
	
}
