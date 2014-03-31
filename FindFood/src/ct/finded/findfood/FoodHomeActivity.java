package ct.finded.findfood;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
/**
 * 首页Activity,用户未登陆时
 * @author SHY_YOU  2014-03-19
 *
 */
public class FoodHomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_home, menu);
		return true;
	}

}
