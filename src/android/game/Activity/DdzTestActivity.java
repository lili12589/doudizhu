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
	//�ֻ����ڵ�ǰ��Ļʱ�˳�ϵͳ
	protected void onStop(){
		super.onStop();
		System.out.println("�ҽ�Ҫ�뿪��ǰ��Ļ����");
		System.exit(0);
	}
	
}