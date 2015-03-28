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
	private OnClickListener listener = new View.OnClickListener() {//��ť������
		public void onClick(View v) {	
			Button temp=(Button) v;
			if(temp==chuPai){//�����˳��ư�ť
				conGame.lc_start();//���ҳ���	
				conGame.sound.play(3);
			}else if(temp==buChu){//�����˲�����ť
				conGame.notOutputCount++;//���Ҳ���
				conGame.desk_manager.clearDeskLayout();// ��ձ��ҳ�����
				conGame.rg_start();
				Toast.makeText(con, "��������'����'��ť��:", Toast.LENGTH_SHORT)
				.show();
			}else{
				conGame.localPlayer.initImage();//��ѡ
			}
		}
	};	
	//���췽��
	public ChuPaiButton(DdzTestActivity con,ControlGame conGame){
		this.con=con;
		this.conGame=conGame;
		chuPai=(Button) con.findViewById(GameConstants.chupai_button);//��ȡ��ť
		buChu=(Button) con.findViewById(GameConstants.buchu_button);//��ȡ��ť
		reSet=(Button) con.findViewById(GameConstants.reset_button);//��ȡ��ť
		chuPai.setOnClickListener(listener);
		buChu.setOnClickListener(listener);
		reSet.setOnClickListener(listener);
	}

}
