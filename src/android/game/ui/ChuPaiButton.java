package android.game.ui;

import android.game.Activity.DdzTestActivity;
import android.game.Activity.GameConstants;
import android.game.logic.ControlGame;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ChuPaiButton {
	private Button chuPai,buChu,reSet;
	private DdzTestActivity con;
	private ControlGame conGame;	
	private OnClickListener listener = new View.OnClickListener() {//按钮监视器
		public void onClick(View v) {	
			Button temp=(Button) v;
			if(temp==chuPai){//单击了出牌按钮
				conGame.lc_start();//作家出牌	
				conGame.sound.play(3);
			}else if(temp==buChu){//单击了不出按钮
				conGame.notOutputCount++;//本家不出
				conGame.desk_manager.clearDeskLayout();// 清空本家出的牌
				conGame.rg_start();
				Toast.makeText(con, "您单击了'不出'按钮！:", Toast.LENGTH_SHORT)
				.show();
			}else{
				conGame.localPlayer.initImage();//重选
			}
		}
	};	
	//构造方法
	public ChuPaiButton(DdzTestActivity con,ControlGame conGame){
		this.con=con;
		this.conGame=conGame;
		chuPai=(Button) con.findViewById(GameConstants.chupai_button);//获取按钮
		buChu=(Button) con.findViewById(GameConstants.buchu_button);//获取按钮
		reSet=(Button) con.findViewById(GameConstants.reset_button);//获取按钮
		chuPai.setOnClickListener(listener);
		buChu.setOnClickListener(listener);
		reSet.setOnClickListener(listener);
	}

}
