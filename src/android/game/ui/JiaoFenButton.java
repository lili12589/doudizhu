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
	private int pre_score = 0;// ��ҽеķ�
	public  int score = 0;// �м��֣�
	private OnClickListener listener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
				case GameConstants.bujiao_button: {// ����
					score = 0;
					conGame.rt_first();//�Ҽҽз�
					break;
				}
				case GameConstants.yifen_button: {// һ��
					score = 1;
					conGame.rt_first();//�Ҽҽз�
					break;
				}
				case GameConstants.erfen_button: {// ��������'erfen'��ť
					score = 2;
					conGame.rt_first();//�Ҽҽз�
					break;
				}
				case GameConstants.sanfen_button: {// ��������'sanfen'��ť
					score = 3;
					conGame.rt_first();//�Ҽҽз�
					break;
				}				
			}
		}
	};

	// ���췽��
	public JiaoFenButton(DdzTestActivity conn,ControlGame conGame) {
		con = conn;
		this.conGame=conGame;//Ϊ�˵���ControlGame.rightplayer.qiangdizhu()
		
		buJiao = (Button) con.findViewById(GameConstants.bujiao_button);// ��ȡbujiao��ť
		yiFen = (Button) con.findViewById(GameConstants.yifen_button);// ��ȡ��ť
		erFen = (Button) con.findViewById(GameConstants.erfen_button);// ��ȡ��ť
		sanFen = (Button) con.findViewById(GameConstants.sanfen_button);// ��ȡ��ť
		buJiao.setOnClickListener(listener);
		yiFen.setOnClickListener(listener);
		erFen.setOnClickListener(listener);
		sanFen.setOnClickListener(listener);
	}

	// ������ҽеķ���
	public void setPre_score(int pre_score) {
		this.pre_score = pre_score;
	}

	// ���ð�ť�Ƿ�ɵ���
	public void setButton() {
		pre_score=conGame.leftPlayer.getScore();//��ҽ��˼���
		if(pre_score==0){
			pre_score=conGame.rightPlayer.getScore();//�Ҽҽ��˼���
		}
		if (pre_score == 3) {// ��ҽ���3��
			sanFen.setClickable(false);
			yiFen.setClickable(false);
			erFen.setClickable(false);
			yiFen.setBackgroundColor(Color.rgb(255, 0, 0));
			erFen.setBackgroundColor(Color.rgb(255, 0, 0));
			sanFen.setBackgroundColor(Color.rgb(255, 0, 0));
		}
		if (pre_score == 2) {// ��ҽ���2��
			yiFen.setClickable(false);
			erFen.setClickable(false);
			yiFen.setBackgroundColor(Color.rgb(255, 0, 0));
			erFen.setBackgroundColor(Color.rgb(255, 0, 0));
		}
		if (pre_score == 1) {// ��ҽ���2��
			yiFen.setClickable(false);
			yiFen.setBackgroundColor(Color.rgb(255, 0, 0));
		}
	}

	
	// return score���˼���
	public int getScore() {
		return score;
	}

	
}
