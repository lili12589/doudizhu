package android.game.ui;

import android.game.Activity.DdzTestActivity;
import android.game.Activity.GameConstants;
import android.game.logic.ControlGame;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JiaoFenButton{
	private Button buJiao, yiFen, erFen, sanFen;
	private DdzTestActivity con;
	private ControlGame conGame;
	private int pre_score = 0;// 左家叫的分
	public  int score = 0;// 叫几分？
	private OnClickListener listener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
				case GameConstants.bujiao_button: {// 不叫
					score = 0;
					conGame.rt_first();//右家叫分
					break;
				}
				case GameConstants.yifen_button: {// 一分
					score = 1;
					conGame.rt_first();//右家叫分
					break;
				}
				case GameConstants.erfen_button: {// 您单击了'erfen'按钮
					score = 2;
					conGame.rt_first();//右家叫分
					break;
				}
				case GameConstants.sanfen_button: {// 您单击了'sanfen'按钮
					score = 3;
					conGame.rt_first();//右家叫分
					break;
				}				
			}
		}
	};

	// 构造方法
	public JiaoFenButton(DdzTestActivity conn,ControlGame conGame) {
		con = conn;
		this.conGame=conGame;//为了调用ControlGame.rightplayer.qiangdizhu()
		
		buJiao = (Button) con.findViewById(GameConstants.bujiao_button);// 获取bujiao按钮
		yiFen = (Button) con.findViewById(GameConstants.yifen_button);// 获取按钮
		erFen = (Button) con.findViewById(GameConstants.erfen_button);// 获取按钮
		sanFen = (Button) con.findViewById(GameConstants.sanfen_button);// 获取按钮
		buJiao.setOnClickListener(listener);
		yiFen.setOnClickListener(listener);
		erFen.setOnClickListener(listener);
		sanFen.setOnClickListener(listener);
	}

	// 设置左家叫的分数
	public void setPre_score(int pre_score) {
		this.pre_score = pre_score;
	}

	// 设置按钮是否可单击
	public void setButton() {
		pre_score=conGame.leftPlayer.getScore();//左家叫了几分
		if(pre_score==0){
			pre_score=conGame.rightPlayer.getScore();//右家叫了几分
		}
		if (pre_score == 3) {// 左家叫了3分
			sanFen.setClickable(false);
			yiFen.setClickable(false);
			erFen.setClickable(false);
			yiFen.setBackgroundColor(Color.rgb(255, 0, 0));
			erFen.setBackgroundColor(Color.rgb(255, 0, 0));
			sanFen.setBackgroundColor(Color.rgb(255, 0, 0));
		}
		if (pre_score == 2) {// 左家叫了2分
			yiFen.setClickable(false);
			erFen.setClickable(false);
			yiFen.setBackgroundColor(Color.rgb(255, 0, 0));
			erFen.setBackgroundColor(Color.rgb(255, 0, 0));
		}
		if (pre_score == 1) {// 左家叫了2分
			yiFen.setClickable(false);
			yiFen.setBackgroundColor(Color.rgb(255, 0, 0));
		}
	}

	
	// return score抢了几分
	public int getScore() {
		return score;
	}

	
}
