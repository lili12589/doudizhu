package android.game.Activity;

import android.app.Activity;
import android.game.ddz.R;
import android.game.logic.ControlGame;
import android.os.Bundle;

public class DdzTestActivity extends Activity {

	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		new ControlGame(this);
	}
	//手机不在当前屏幕时退出系统
	protected void onStop(){
		super.onStop();
		System.out.println("我将要离开当前屏幕！！");
		System.exit(0);
	}
	
}