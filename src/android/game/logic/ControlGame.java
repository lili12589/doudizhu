package android.game.logic;

import java.util.ArrayList;

import android.game.Activity.DdzTestActivity;
import android.game.Activity.GameConstants;
import android.game.Activity.PlaySound;
import android.game.player.LeftPlayer;
import android.game.player.LocalPlayer;
import android.game.player.RightPlayer;
import android.game.ui.ChuPaiButton;
import android.game.ui.DeskManager;
import android.game.ui.JiaoFenButton;
import android.game.ui.PhotoManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ControlGame {
	DdzTestActivity context = null;
	LinearLayout jiaofen_layout = null, chupai_layout = null,
			middle_layout = null;// ���Ʋ����Ѿ֣��зցѾ�
	public LeftPlayer leftPlayer;// �������ң��Ҽң����
	public LocalPlayer localPlayer;
	public RightPlayer rightPlayer;

	public PlaySound sound;//��������
	public PhotoManager ph_mg;// ����ͷ�����
	public ChuPaiButton chuPaiButton;// �԰�ť����˼�����
	public JiaoFenButton jiaoFenButton;
	public DeskManager desk_manager = null;// ������ʾ���ҳ����ƺ����3����

	public FenPai fenPai;
	public Rule rule = null;// ��Ϸ����
	public NextCards nextCards = null;// �Զ�����
	public int lf_sc = 0, rt_sc = 0, lc_sc = 0;// �洢��ҵķ���
	public int first,dz_flag;// ˭��һ��������,dz_flag˭�ǵ�����1���ң�2�Ҽң�3���
	public int notOutputCount = 0;// �м������û�г�����
	public int preCardsType = 0;// 0Ϊ�ռ�������1Ϊ����2Ϊ��(66)��3Ϊ��(456789)��4Ϊը��(5555),5Ϊ������(999)��6Ϊ����һ(7779)��7Ϊ������(66622)��8Ϊը��(5555)
	public ArrayList<Integer> preCards_arr = null;// �洢��һ����ҵ���

	// ���췽��
	public ControlGame(DdzTestActivity con) {
		context = con;
		leftPlayer = new LeftPlayer(context);
		rightPlayer = new RightPlayer(context);
		localPlayer = new LocalPlayer(context);
		sound=new PlaySound(context);//���������������	
		
		preCards_arr = new ArrayList<Integer>();
		fenPai = new FenPai();
		nextCards = new NextCards();// �����Զ�������
		rule = new Rule();// ������Ϸ������
		jiaofen_layout = (LinearLayout) con
				.findViewById(GameConstants.jiaofen_layout);// ��ֵ�ĸ��з�
		chupai_layout = (LinearLayout) con
				.findViewById(GameConstants.chupai_layout);// ��ֵ������ť

		middle_layout = (LinearLayout) context
				.findViewById(GameConstants.buttgroup_layout);
		chuPaiButton = new ChuPaiButton(context, this);
		jiaoFenButton = new JiaoFenButton(context, this);// JiaoFenButton��ʵ�ֶԵ����з��¼��Ĵ���
		desk_manager = new DeskManager(context);// �������沼��
		ph_mg = new PhotoManager(context);// ����ͷ�������
		middle_layout.removeAllViews();// ����м䲼�ֵ����
		localPlayer.setRemain_index(fenPai.getLocal_num());// ��ʼ������ʮ17���ƣ���Ҹ����ƺû��е���
		localPlayer.initImage();
		comDz();// ������
	}

	// qiang����---0���ҡ�1�Ҽҡ�2���
	public void comDz() {
		sound.play(5);//����start����
		first = (int) (Math.random() * 3);// ˭��һ��������
		if (first == 0) {// ���ҵ�һ��������
			lc_first();
		} else if (first == 1) {// �Ҽҵ�һ��������
			rt_first();
		} else {// ��ҵ�һ��������
			lf_first();
		}
	}

	/*----------------------˭��һ��������------------------------------------*/
	// ��ҵ�һ��������
	public void lf_first() {
		if (rt_sc == 3) {// ����Ҽ��ǵ���
			dz_flag=2;//���������Ϊ2
			ph_mg.setRg_ph();// �����Ҽ�ͷ��
			rightPlayer.setDz(true);
			rightPlayer.setRemain_index(fenPai.getLast_three());// ��������Ƹ��Ҽ�
			initNum();// ��ʼ��������ҵ��ƣ���ʼ����
			desk_manager.setDisplay_arr(fenPai.getLast_three());// ��ʾ���ŵ���
			desk_manager.initDeskLayout();
			rg_start();// �Ҽҿ�ʼ����
			return;
		}
		// ��ҽе���
		if (rightPlayer.getScore() == 0) {// �ҼҲ��У���ҽз�
			lf_sc = leftPlayer.getJiaofen(jiaoFenButton.getScore());
		} else {// ���ҽз���
			lf_sc = leftPlayer.getJiaofen(rightPlayer.getScore());// �Ҽҽз�
		}
		// �������textView
		if (lf_sc == 0) {// ��Ҳ�������textView
			ph_mg.setLf_tv("����");
		} else {
			ph_mg.setLf_tv("" + lf_sc + "��");
		}
		// ���ҽе���
		lc_first();
	}

	// �Ҽҵ�һ��������
	public void rt_first() {
		if (jiaoFenButton.getScore() == 0) {// ���Ҳ�������textView
			ph_mg.setLc_tv("����");
		} else {
			ph_mg.setLc_tv("" + jiaoFenButton.getScore() + "��");// textview����ʾ���ҽ̵ķ���
		}
		if (jiaoFenButton.getScore() == 3) {// ��������ǵ���
			dz_flag=1;//���������Ϊ1
			notOutputCount = 3;// ��ң��ҼҶ�����
			ph_mg.setLc_ph();// ���ñ���ͷ��Ϊ����ͷ��
			localPlayer.setDz(true);
			localPlayer.setRemain_index(fenPai.getLast_three());
			desk_manager.setDisplay_arr(fenPai.getLast_three());// ��ʾ���ŵ���
			desk_manager.initDeskLayout();
			initNum();
			return;
		}
		// �Ҽҽе���
		if (jiaoFenButton.getScore() == 0) {// ���Ҳ��У��Ҽҽе���
			rt_sc = rightPlayer.getJiaofen(leftPlayer.getScore());// �Ҽҽз�
		} else {// ���ҽз���
			rt_sc = rightPlayer
					.getJiaofen(jiaoFenButton.getScore());// �Ҽҽз�
		}
		// �Ҽ�����textView
		if (rt_sc == 0) {// �ҼҲ�������textView
			ph_mg.setRg_tv("����");
		} else {
			ph_mg.setRg_tv("" + rt_sc + "��");// textview����ʾ�Ҽҽ̵ķ���
		}
		middle_layout.removeAllViews();// ����м䲼�ֵ����
		// ��ҿ�ʼ�е���
		lf_first();
	}

	// ���ҵ�һ��������
	public void lc_first() {
		if (lf_sc == 3) {// ����ǵ���
			dz_flag=3;//���������Ϊ3
			ph_mg.setLf_ph();// ���ͷ�����Ϊ����ͷ��
			leftPlayer.setDz(true);
			leftPlayer.setRemain_index(fenPai.getLast_three());
			desk_manager.setDisplay_arr(fenPai.getLast_three());// ��ʾ���ŵ���
			desk_manager.initDeskLayout();
			initNum();
			lf_start();// ��ҿ�ʼ����
			return;
		}
		middle_layout.removeAllViews();// ����м䲼�ֵ����
		middle_layout.addView(jiaofen_layout);// ��ӽзֲ���
		jiaoFenButton.setButton();// ���ð�ť�Ƿ�ɵ���
	}

	/*------------------------------����������------------------------------------*/

	// ��ʼ��������ҵ���
	public void initNum() {
		middle_layout.removeAllViews();// ����м䲼�ֵ����
		middle_layout.addView(chupai_layout);
		localPlayer.clear_layout();// ������ղ���
		desk_manager.setDisplay_arr(fenPai.getLast_three());// ��ʼ��׿������
		leftPlayer.setRemain_index(fenPai.getLeft_num());
		rightPlayer.setRemain_index(fenPai.getRight_num());
		localPlayer.initImage();
		fenPai.clearAll();// ��շ���
		sound.play(4);//���ű�������
	}

	/*--------------------------------------------------------��Ϸ��ʼ-------------------------------------------------------------*/

	public void lf_start() {// ��ҿ�ʼ����
		if (notOutputCount >= 2) {// ������������û�г���
			preCards_arr.clear();
			preCardsType = 0;
			notOutputCount=0;//notOutputCount��0
		}
		/*-----------------------------ʹ��nextCards-------------------------------*/
		nextCards.setTotalCardsIndex_arr(leftPlayer.getRemain_index());// nextCards��ȡ���ʣ�����
		nextCards.setPreCardsType(preCardsType);// ��һ����ҳ��Ƶ�����
		nextCards.setPreCardsIndex_arr(preCards_arr);// nextCards��ȡ��һ����ҳ�����
		nextCards.transfer();// nextCards��ʼѡ��
		/*----------------------------ʹ��nextCards���------------------------*/

		/*-----------------------leftPlayer��ʼ����-------------------------*/
		leftPlayer.setOutput_index(nextCards.getNextCardsIndex());// ��ҳ���
		leftPlayer.clearImage_layout();// �����ҳ�����
		if (!leftPlayer.getOutput_index().isEmpty()) {// ��ҳ�����
			notOutputCount = 0;
			preCards_arr.clear();
			preCards_arr = nextCards.getNextCardsIndex();
			preCardsType = nextCards.getNextCardsType();// ��ҳ��Ƶ����͸�ֵ��preCardsType
			leftPlayer.initImage();// ��ʾ��ҳ�����						
		} else {			
			ph_mg.setLf_tv("����");
			notOutputCount++;// ��Ҳ���notOutputCount++
		}	
		leftPlayer.clearOutputindex();// Ϊ�˽�ʡ�ڴ棬�����ҳ�������
		if(preCardsType==4){//����ը������
			sound.play(1);
		}
		ph_mg.setLf_tv("" + leftPlayer.getCardsCout());// ��ʾ��ҳ�ʣ����Ƶ�����
		if(!isGameOver()){//�ж���Ϸ�Ƿ����
			middle_layout.removeAllViews();// ��ֹ����
			middle_layout.addView(chupai_layout);
		}
		/*-------------------leftPlayer���ƽ���----------------------------*/
	}

	/*
	 * @˵��:localPlayer��getSelected_index��ΪnextCards��TotalCardsIndex_arr ���
	 */
	public void lc_start() {// ���ҿ�ʼ����		
		desk_manager.clearDeskLayout();// ��ձ��ҳ�����
		if (notOutputCount >= 2) {// ������������û�г���
			Rule r = new Rule();
			r.setTemp(localPlayer.getSelected_index());
			if (r.isRight()) {// ���Ʒ��Ϲ���
				notOutputCount = 0;
				preCards_arr.clear();
				preCards_arr = r.getCardsIndex_arr();
				preCardsType = r.getCardsType();
				desk_manager.setDisplay_arr(localPlayer
						.getSelected_index());
				desk_manager.initDeskLayout();// ��ʾ���ҳ�����
				localPlayer.clearSelected_index();// ��մ������
				localPlayer.initImage();// ��ʾ����ʣ�����
				middle_layout.removeAllViews();// ���middle_layout
				localPlayer.clearSelected_index();// Ϊ�˽�ʡ�ڴ棬��ձ��ҳ�������
				ph_mg.setLc_tv("" + localPlayer.getCardsCout());// ��ʾ����ʣ����Ƶ�����
				if(preCardsType==4){//����ը������
					sound.play(1);
				}
				if(!isGameOver()){//�ж���Ϸ�Ƿ����
					rg_start();// �Ҽҳ���
				}
			} else {
				Toast.makeText(context, "�����Ϲ���������ѡ��notOutputCount >= 2!",
						Toast.LENGTH_SHORT).show();
			}
			return;// ���أ�����Ĵ��벻ִ��
		}
		/*-----------------------------ʹ��nextCards-------------------------------*/
		nextCards.setTotalCardsIndex_arr(localPlayer
				.getSelected_index());// nextCards��ȡ����ʣ�����
		nextCards.setPreCardsType(preCardsType);// ��һ����ҳ��Ƶ�����
		nextCards.setPreCardsIndex_arr(preCards_arr);// nextCards��ȡ��һ���ҳ�����
		nextCards.transfer();// nextCards��ʼѡ��
		/*----------------------------ʹ��nextCards���------------------------*/

		/*-----------------------localPlayer��ʼ����-------------------------*/
		if (nextCards.nextEqualsTotal()) {// ���ҳ�����
			notOutputCount = 0;
			preCards_arr.clear();
			preCardsType = nextCards.getNextCardsType();// ���ҳ��Ƶ����͸�ֵ��preCardsType
			preCards_arr = nextCards.getNextCardsIndex();
			desk_manager.setDisplay_arr(localPlayer
					.getSelected_index());
			desk_manager.initDeskLayout();// ��ʾ���ҳ�����
			localPlayer.clearSelected_index();// ��մ������
			localPlayer.initImage();// ��ʾ����ʣ�����
			middle_layout.removeAllViews();// ���middle_layout
			localPlayer.clearSelected_index();// Ϊ�˽�ʡ�ڴ棬��ձ��ҳ�������
			if(!isGameOver()){//�ж���Ϸ�Ƿ����
				rg_start();// �Ҽҳ���
			}
		} else {
			// ������ʾ��
			Toast.makeText(context, "�����Ϲ���������ѡ��!",
					Toast.LENGTH_SHORT).show();
			notOutputCount++;// ���Ҳ���notOutputCount++
		}
		if(preCardsType==4){//����ը������
			sound.play(1);
		}
		ph_mg.setLc_tv("" + localPlayer.getCardsCout());// ��ʾ����ʣ����Ƶ�����
	}

	/*-------------------localPlayer���ƽ���----------------------------*/
	public void rg_start() {// �Ҽҿ�ʼ����		
		if (notOutputCount >= 2) {// ������������û�г���
			preCards_arr.clear();
			preCardsType = 0;
			notOutputCount=0;//notOutputCount��0
		}
		/*-----------------------------ʹ��nextCards-------------------------------*/
		nextCards.setTotalCardsIndex_arr(rightPlayer.getRemain_index());// nextCards��ȡ���ʣ�����
		nextCards.setPreCardsType(preCardsType);// ��һ����ҳ��Ƶ�����
		nextCards.setPreCardsIndex_arr(preCards_arr);// nextCards��ȡ��һ����ҳ�����
		nextCards.transfer();// nextCards��ʼѡ��
		/*----------------------------ʹ��nextCards���------------------------*/

		/*-----------------------leftPlayer��ʼ����-------------------------*/
		rightPlayer.setOutput_index(nextCards.getNextCardsIndex());// �Ҽҳ���
		rightPlayer.clearImage_layout();
		if (!rightPlayer.getOutput_index().isEmpty()) {// �Ҽҳ�����			
			notOutputCount = 0;
			preCards_arr.clear();
			preCards_arr = nextCards.getNextCardsIndex();
			preCardsType = nextCards.getNextCardsType();// �Ҽҳ��Ƶ����͸�ֵ��preCardsType
			rightPlayer.initImage();// ��ʾ�Ҽҳ�����
			rightPlayer.clearOutputindex();// �����������			
		} else {
			notOutputCount++;// �ҼҲ���notOutputCount++
		}
		ph_mg.setRg_tv("" + rightPlayer.getCardsCout());// ��ʾ�Ҽҳ�ʣ����Ƶ�����		
		if(preCardsType==4){//����ը������
			sound.play(1);
		}
		if(!isGameOver()){//�ж���Ϸ�Ƿ����
			lf_start();// ��ҳ���
		}
	}

	/*-------------------rightPlayer���ƽ���----------------------------*/
	public boolean isGameOver() {
		if (leftPlayer.getRemain_index().size() <= 0) {
			if(dz_flag!=3){//��Ҳ��ǵ�������������
				desk_manager.changeBG(1);
			}else{
				desk_manager.changeBG(0);
			}
			System.out.println("���Ӯ��");
			sound.play(7);//���Ž�������
			return true;
		} else if (rightPlayer.getRemain_index().size() <= 0) {
			if(dz_flag!=2){//�ҼҲ��ǵ�������������
				desk_manager.changeBG(1);
			}else{
				desk_manager.changeBG(0);
			}
			System.out.println("�Ҽ�Ӯ��");
			sound.play(7);//���Ž�������
			return true;
		} else if(localPlayer.getRemain_index().size()<=0&&dz_flag==1){
			if(dz_flag!=1){//���Ҳ��ǵ�������������
				desk_manager.changeBG(1);
			}else{
				desk_manager.changeBG(0);
			}
			System.out.println("����Ӯ��");
			sound.play(7);//���Ž�������;
			return true;
		}
		return false;
	}
	/*---------------------------------------��Ϸ����----------------------------------------------*/
}
