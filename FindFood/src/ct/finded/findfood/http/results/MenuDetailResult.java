package ct.finded.findfood.http.results;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: MenuDetailResult
 * @Description: 菜式详情请求结果类
 * @author Shy_You
 * @date 2014年5月1日
 */
public class MenuDetailResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = -646687573803603822L;

	private List<MenuInfo> data;
	
	
	public List<MenuInfo> getData() {
		return data;
	}


	public void setData(List<MenuInfo> data) {
		this.data = data;
	}




	/**
	 * 
	 * @ClassName: MenuInfo
	 * @Description: 菜式信息类
	 * @author Shy_You
	 * @date 2014年5月1日
	 */
	public static class MenuInfo implements Serializable{
		/**
		 *
		 */
		private static final long serialVersionUID = 2764378336897090084L;
		/**菜式Id*/
		private String menuId;
		/**菜式名称*/
		private String menuName;
		/**菜式价钱*/
		private String price;
		/**菜式所得分数*/
		private String showScore;
		/**点评人数*/
		private String commentNum;
		/**分数百分比*/
		private String scorePercent;
		/**所属店铺名称*/
		private String shopName;
		/**店铺长号*/
		private String tel;
		/**店铺短号*/
		private String stel;
		/**菜式图片路径*/
		private String imgPath;
		/**广大商业中心*/
		private String address;
		/*各点评等级人数所占比例*/
		private String grade1Percent;
		private String grade2Percent;
		private String grade3Percent;
		private String grade4Percent;
		
		
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
		public String getPrice() {
			return price != null ? price : "";
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getShowScore() {
			return showScore != null ? showScore : "";
		}
		public void setShowScore(String showScore) {
			this.showScore = showScore;
		}
		public String getCommentNum() {
			return commentNum != null ? commentNum : "";
		}
		public void setCommentNum(String commentNum) {
			this.commentNum = commentNum;
		}
		public String getScorePercent() {
			return scorePercent != null ? scorePercent : "";
		}
		public void setScorePercent(String scorePercent) {
			this.scorePercent = scorePercent;
		}
		public String getShopName() {
			return shopName != null ? shopName : "";
		}
		public void setShopName(String shopName) {
			this.shopName = shopName;
		}
		public String getTel() {
			return tel != null ? tel : "";
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getStel() {
			return stel != null ? stel : "";
		}
		public void setStel(String stel) {
			this.stel = stel;
		}
		public String getImgPath() {
			return imgPath != null ? imgPath : "";
		}
		public void setImgPath(String imgPath) {
			this.imgPath = imgPath;
		}
		public String getAddress() {
			return address != null ? address : "";
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getGrade1Percent() {
			return grade1Percent != null ? grade1Percent : "";
		}
		public void setGrade1Percent(String grade1Percent) {
			this.grade1Percent = grade1Percent;
		}
		public String getGrade2Percent() {
			return grade2Percent != null ? grade2Percent : "";
		}
		public void setGrade2Percent(String grade2Percent) {
			this.grade2Percent = grade2Percent;
		}
		public String getGrade3Percent() {
			return grade3Percent != null ? grade3Percent : "";
		}
		public void setGrade3Percent(String grade3Percent) {
			this.grade3Percent = grade3Percent;
		}
		public String getGrade4Percent() {
			return grade4Percent != null ? grade4Percent : "";
		}
		public void setGrade4Percent(String grade4Percent) {
			this.grade4Percent = grade4Percent;
		}
		
	}
}
