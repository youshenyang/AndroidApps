package ct.finded.findfood;

import cn.com.wo.bitmap.ImageCache.ImageCacheParams;
import cn.com.wo.bitmap.ImageFetcher;
import ct.finded.findfood.commons.AppConfigMrg;
import ct.finded.findfood.commons.TestData;
import android.app.Application;
import android.graphics.BitmapFactory;
/**
 * @ClassName: FoodApplication
 * @Description: 应用的Application类
 * @author Shy_You
 * @date 2014年4月22日
 */
public class FoodApplication extends Application {
	/**FoodApplication对象实例*/
	private static FoodApplication instance;
	
	/**图片缓存目录*/
	public static final String PIC_DIR = "pic";
	
	/**图片加载器*/
	private ImageFetcher imageFetcher;
	
	/**获取图片加载器*/
	public ImageFetcher getImageFetcher(){
		if(imageFetcher == null){
			initImageFetcher();
		}
		return imageFetcher;
	}

	/**初始化图片加载器*/
	private void initImageFetcher() {
		ImageCacheParams cacheParams = new ImageCacheParams(getInstance().getApplicationContext(), PIC_DIR);
		cacheParams.setMemCacheSizePercent(0.20f);
		imageFetcher = new ImageFetcher(this, 0);
		imageFetcher.setLoadingImage(BitmapFactory.decodeStream(
				                     getResources().openRawResource(R.drawable.head_icon_rabit)),
				                     BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.food_picture)));
		imageFetcher.addImageCache(cacheParams);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
		AppConfigMrg.load(getApplicationContext());
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}
	
	public static FoodApplication getInstance(){
		return instance;
	}

}
