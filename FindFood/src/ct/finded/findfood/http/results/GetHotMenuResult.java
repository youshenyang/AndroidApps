package ct.finded.findfood.http.results;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: GetHotMenuResult
 * @Description: 热门菜式的请求结果
 * @author Shy_You
 * @date 2014年4月28日
 */
public class GetHotMenuResult extends BaseResult {

	/**
	 *
	 */
	private static final long serialVersionUID = 4246406618609145281L;
	
	private List<BaseMenuMsg> data;
	
	
	public List<BaseMenuMsg> getData() {
		return data;
	}


	public void setData(List<BaseMenuMsg> data) {
		this.data = data;
	}


	public static class BaseMenuMsg implements Serializable{

		/**
		 *
		 */
		private static final long serialVersionUID = -3728310471639244117L;
		/**菜式id*/
		private String menuId;
		/**菜式名*/
		private String menuName;
		/**菜式图片路径*/
		private String imgPath;
		/**菜式所得分数(10分制)*/
		private String showScore;
		/**菜式所得分数(百分制)*/
		private String showScorePercent;
		/**菜式价钱*/
		private String price;
		/**点评人数*/
		private String commentNum;
		/**所属店铺名称*/
		private String shopName;
		/**店铺长号*/
		private String tel;
		/**店铺短号*/
		private String stel;
		
		
		
		public BaseMenuMsg() {
			super();
		}
		
		public String getMenuId() {
			return menuId;
		}
		public void setMenuId(String menuId) {
			this.menuId = menuId;
		}
		public String getMenuName() {
			return menuName;
		}
		public void setMenuName(String menuName) {
			this.menuName = menuName;
		}

		public String getImgPath() {
			return imgPath;
		}

		public void setImgPath(String imgPath) {
			this.imgPath = imgPath;
		}

		public String getShowScore() {
			return showScore;
		}

		public void setShowScore(String showScore) {
			this.showScore = showScore;
		}

		public String getShowScorePercent() {
			return showScorePercent;
		}

		public void setShowScorePercent(String showScorePercent) {
			this.showScorePercent = showScorePercent;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getCommentNum() {
			return commentNum;
		}

		public void setCommentNum(String commentNum) {
			this.commentNum = commentNum;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getStel() {
			return stel;
		}

		public void setStel(String stel) {
			this.stel = stel;
		}
		
		
	}

}
