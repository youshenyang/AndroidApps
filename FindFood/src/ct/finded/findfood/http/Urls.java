package ct.finded.findfood.http;
/**
 * 
 * @ClassName: Urls
 * @Description: URL链接类
 * @author Shy_You
 * @date 2014年5月1日
 */
public final class Urls {
	
	public static final String HOST = "192.168.1.2";
	public static final String HTTP = "http://";
	
	/**热门菜式*/
	public static final String GET_HOT_MENU = HTTP+HOST+"/findfoodApp/web/default.php/?g=Menu&m=Find&a=getHotMenu";
	/**随机获取9个菜式*/
	public static final String GET_RAND_MENU = HTTP+HOST+"/findfoodApp/web/default.php/?g=Menu&m=Find&a=getRandMenu";
	/**最新评价*/
	public static final String GET_NEW_COMMENT = HTTP+HOST+"/findfoodApp/web/default.php/?g=Menu&m=Find&a=getNewComment";
	/**菜式详情*/
	public static final String MENU_DETAIL = HTTP+HOST+"/findfoodApp/web/default.php/?g=Menu&m=Find&a=menuInfo";
	/**点评详情，用户回复的内容*/
	public static final String SHOW_GRADE_REPLY = HTTP+HOST+"/findfoodApp/web/default.php/?g=Menu&m=Find&a=showGradeReply";
	/**登陆*/
	public static final String LOGIN = HTTP+HOST+"/findfoodApp/web/default.php/?g=User&m=Login&a=login";
	/**搜索*/
	public static final String SEARCH = HTTP+HOST+"/findfoodApp/web/default.php/?g=Menu&m=Find&a=index";
	/**当前登陆用户关注的人的动态展示*/
	public static final String CUR_USER_FOUCS_TREND = HTTP+HOST+"/findfoodApp/web/default.php/?g=User&m=Usercen&a=index";
	/**菜式详情页，用户的点评*/
	public static final String MENU_DETAIL_EVALUATE = HTTP+HOST+"/findfoodApp/web/default.php/?g=Menu&m=Find&a=getMenuComment";
	/**点评菜式*/
	public static final String EVALUATE_MENU = HTTP+HOST+"/findfoodApp/web/default.php/?g=Menu&m=Find&a=commentMenu";
	/**用户中心，用户基本资料*/
	public static final String SHOW_USER_BASIC = HTTP+HOST+"/findfoodApp/web/default.php/?g=User&m=Usercen&a=showUserBasic";
	/**用户对点评 赞、取消赞 操作*/
	public static final String PRAISE_ACTION = HTTP+HOST+"/findfoodApp/web/default.php/?g=Menu&m=Find&a=praise";
	/**用户中心，用户动态*/
	public static final String SHOW_USER_TREND = HTTP+HOST+"/findfoodApp/web/default.php/?g=User&m=Usercen&a=showUserNews";
	/**用户中心，用户关注的人*/
	public static final String SHOW_USER_FOCUS = HTTP+HOST+"/findfoodApp/web/default.php/?g=User&m=Usercen&a=showUserFollow";
	/**用户中心，用户的粉丝*/
	public static final String SHOW_USER_FANS = HTTP+HOST+"/findfoodApp/web/default.php/?g=User&m=Usercen&a=showUserFans";
	/**点评详情页内，用户回复功能*/
	public static final String EVALUATE_REPLY = HTTP+HOST+"/findfoodApp/web/default.php/?g=Menu&m=Find&a=reply";
	
	
	
	
}
