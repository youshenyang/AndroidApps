package ct.finded.findfood.commons;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionMgr
{
	private ConnectivityManager _conMgr;
	private NetworkInfo _networkInfo;
	private static ConnectionMgr mInstance = null;
	
	public static ConnectionMgr getInstance(Context context) {
		if (null == mInstance) {
			mInstance = new ConnectionMgr(context);
		}
		return mInstance;
	}
	
	
	private ConnectionMgr(Context context)
	{
		_conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
//	public void Connect()
//	{
//		// (1, "GPRS"), (2, "EDGE") and (3, "UMTS).
//		int result = _conMgr.startUsingNetworkFeature(ConnectivityManager.TYPE_MOBILE, "GPRS");
////		if (result == -1)
////		{
//////			Logger.getLogger(Config.TAG_PLAYER).log(Level.INFO, "连接结果:" + result);
////		}
//	}
	
	public int getCurrentNetworkType()
	{
		int networkType = -1;
		NetworkInfo[] infos = _conMgr.getAllNetworkInfo();
		if (infos != null && infos.length > 0)
		{
			for (NetworkInfo info : infos)
			{
				if (info.isConnected())
				{
					if (networkType == ConnectivityManager.TYPE_WIFI)
					{
						return networkType;
					}
					networkType = info.getType();
				}
			}
		}
		
		return networkType;
	}
	
	public boolean isNetworkAvailable()
	{
		_networkInfo = _conMgr.getActiveNetworkInfo();
		if (_networkInfo != null)
		{
			return _networkInfo.isAvailable();
		}
		return false;
	}
	
	public boolean checkConnection() {
		_networkInfo = _conMgr.getActiveNetworkInfo();
        if (_networkInfo == null || !_networkInfo.isConnectedOrConnecting()) {
        	return false;
        }
        return true;
    }
	
}
