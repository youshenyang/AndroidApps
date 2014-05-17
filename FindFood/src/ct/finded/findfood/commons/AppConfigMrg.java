package ct.finded.findfood.commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @ClassName: AppConfigMrg
 * @Description: 应用相关配置信息的管理
 * @author Shy_You
 * @date 2014年4月22日
 */
public class AppConfigMrg {
	/**配置信息的单例对象*/
	private static AppConfig instance;
	
	/**配置文件名*/
	private static String CONFIG_FILE = "appconfig";
	
	/**
	 * 获取配置信息的单例对象
	 * @return
	 */
	public static AppConfig getAppConfig(){
		if(instance == null){
			instance = new AppConfig();
		}
		return instance;
	}

	public static void save(Context c){
		AppConfig configInfo = instance;
		if(configInfo == null){
			return ;
		}
		SharedPreferences perf = c.getSharedPreferences(CONFIG_FILE, 0);
		Editor edit = perf.edit();
		edit.putString(ParamNames.USERID, configInfo.getUserid());
		edit.putString(ParamNames.USERNAME, configInfo.getUsername());
		edit.putBoolean(ParamNames.ISLOGIN, configInfo.getIsLogin());
		edit.putString(ParamNames.FACE, configInfo.getFace());
		edit.putString(ParamNames.SIGNATURE, configInfo.getSignature());
		edit.commit();
	}
	
	/**
	 * 加载应用程序配置信息
	 * @param c
	 */
	public static void load(Context c) {
		AppConfig configInfo = getAppConfig();
		SharedPreferences perf = c.getSharedPreferences(CONFIG_FILE, 0);
		configInfo.setUserid(perf.getString(ParamNames.USERID, ""));
		configInfo.setUsername(perf.getString(ParamNames.USERNAME, ""));
		configInfo.setFace(perf.getString(ParamNames.FACE, ""));
		configInfo.setSignature(perf.getString(ParamNames.SIGNATURE, ""));
		configInfo.setIsLogin(perf.getBoolean(ParamNames.ISLOGIN, false));
	}
	
	

}
