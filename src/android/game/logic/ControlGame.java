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
			middle_layout = null;// 出牌不出佈局，叫分佈局
	public LeftPlayer leftPlayer;// 声明本家，右家，左家
	public LocalPlayer localPlayer;
	public RightPlayer rightPlayer;

	public PlaySound sound;//声音管理
	public PhotoManager ph_mg;// 用于头像管理
	public ChuPaiButton chuPaiButton;// 对按钮添加了监视器
	public JiaoFenButton jiaoFenButton;
	public DeskManager desk_manager = null;// 用来显示本家出的牌和最后3张牌

	public FenPai fenPai;
	public Rule rule = null;// 游戏规则
	public NextCards nextCards = null;// 自动出牌
	public int lf_sc = 0, rt_sc = 0, lc_sc = 0;// 存储玩家的分数
	public int first,dz_flag;// 谁第一个抢地主,dz_flag谁是地主，1本家，2右家，3左家
	public int notOutputCount = 0;// 有几个玩家没有出牌了
	public int preCardsType = 0;// 0为空即不出，1为单，2为对(66)，3为连(456789)，4为炸弹(5555),5为三不带(999)，6为三带一(7779)，7为三带二(66622)，8为炸弹(5555)
	public ArrayList<Integer> preCards_arr = null;// 存储上一个玩家的牌

	// 构造方法
	public ControlGame(DdzTestActivity con) {
		context = con;
		leftPlayer = new LeftPlayer(context);
		rightPlayer = new RightPlayer(context);
		localPlayer = new LocalPlayer(context);
		sound=new PlaySound(context);//创建声音管理对象	
		
		preCards_arr = new ArrayList<Integer>();
		fenPai = new FenPai();
		nextCards = new NextCards();// 创建自动出牌类
		rule = new Rule();// 创建游戏规则类
		jiaofen_layout = (LinearLayout) con
				.findViewById(GameConstants.jiaofen_layout);// 赋值四个叫分
		chupai_layout = (LinearLayout) con
				.findViewById(GameConstants.chupai_layout);// 赋值两个按钮

		middle_layout = (LinearLayout) context
				.findViewById(GameConstants.buttgroup_layout);
		chuPaiButton = new ChuPaiButton(context, this);
		jiaoFenButton = new JiaoFenButton(context, this);// JiaoFenButton中实现对单击叫分事件的处理
		desk_manager = new DeskManager(context);// 创建桌面布局
		ph_mg = new PhotoManager(context);// 创建头像管理类
		middle_layout.removeAllViews();// 清空中间布局的组件
		localPlayer.setRemain_index(fenPai.getLocal_num());// 初始化本家十17张牌，玩家根据牌好坏叫地主
		localPlayer.initImage();
		comDz();// 抢地主
	}

	// qiang地主---0本家、1右家、2左家
	public void comDz() {
		sound.play(5);//播放start声音
		first = (int) (Math.random() * 3);// 谁第一个抢地主
		if (first == 0) {// 本家第一个抢地主
			lc_first();
		} else if (first == 1) {// 右家第一个抢地主
			rt_first();
		} else {// 左家第一个抢地主
			lf_first();
		}
	}

	/*----------------------谁第一个抢地主------------------------------------*/
	// 左家第一个抢地主
	public void lf_first() {
		if (rt_sc == 3) {// 如果右家是地主
			dz_flag=2;//地主标记设为2
			ph_mg.setRg_ph();// 设置右家头像
			rightPlayer.setDz(true);
			rightPlayer.setRemain_index(fenPai.getLast_three());// 最后三张牌给右家
			initNum();// 初始化三个玩家的牌，开始出牌
			desk_manager.setDisplay_arr(fenPai.getLast_three());// 显示三张底牌
			desk_manager.initDeskLayout();
			rg_start();// 右家开始出牌
			return;
		}
		// 左家叫地主
		if (rightPlayer.getScore() == 0) {// 右家不叫，左家叫分
			lf_sc = leftPlayer.getJiaofen(jiaoFenButton.getScore());
		} else {// 本家叫分了
			lf_sc = leftPlayer.getJiaofen(rightPlayer.getScore());// 右家叫分
		}
		// 左家设置textView
		if (lf_sc == 0) {// 左家不叫设置textView
			ph_mg.setLf_tv("不叫");
		} else {
			ph_mg.setLf_tv("" + lf_sc + "分");
		}
		// 本家叫地主
		lc_first();
	}

	// 右家第一个抢地主
	public void rt_first() {
		if (jiaoFenButton.getScore() == 0) {// 本家不叫设置textView
			ph_mg.setLc_tv("不叫");
		} else {
			ph_mg.setLc_tv("" + jiaoFenButton.getScore() + "分");// textview上显示本家教的分数
		}
		if (jiaoFenButton.getScore() == 3) {// 如果本家是地主
			dz_flag=1;//地主标记设为1
			notOutputCount = 3;// 左家，右家都不出
			ph_mg.setLc_ph();// 设置本家头像为地主头像
			localPlayer.setDz(true);
			localPlayer.setRemain_index(fenPai.getLast_three());
			desk_manager.setDisplay_arr(fenPai.getLast_three());// 显示三张底牌
			desk_manager.initDeskLayout();
			initNum();
			return;
		}
		// 右家叫地主
		if (jiaoFenButton.getScore() == 0) {// 本家不叫，右家叫地主
			rt_sc = rightPlayer.getJiaofen(leftPlayer.getScore());// 右家叫分
		} else {// 本家叫分了
			rt_sc = rightPlayer
					.getJiaofen(jiaoFenButton.getScore());// 右家叫分
		}
		// 右家设置textView
		if (rt_sc == 0) {// 右家不叫设置textView
			ph_mg.setRg_tv("不叫");
		} else {
			ph_mg.setRg_tv("" + rt_sc + "分");// textview上显示右家教的分数
		}
		middle_layout.removeAllViews();// 清空中间布局的组件
		// 左家开始叫地主
		lf_first();
	}

	// 本家第一个抢地主
	public void lc_first() {
		if (lf_sc == 3) {// 左家是地主
			dz_flag=3;//地主标记设为3
			ph_mg.setLf_ph();// 左家头像更改为地主头像
			leftPlayer.setDz(true);
			leftPlayer.setRemain_index(fenPai.getLast_three());
			desk_manager.setDisplay_arr(fenPai.getLast_three());// 显示三张底牌
			desk_manager.initDeskLayout();
			initNum();
			lf_start();// 左家开始出牌
			return;
		}
		middle_layout.removeAllViews();// 清空中间布局的组件
		middle_layout.addView(jiaofen_layout);// 添加叫分布局
		jiaoFenButton.setButton();// 设置按钮是否可单击
	}

	/*------------------------------抢地主结束------------------------------------*/

	// 初始化三个玩家的牌
	public void initNum() {
		middle_layout.removeAllViews();// 清空中间布局的组件
		middle_layout.addView(chupai_layout);
		localPlayer.clear_layout();// 本家清空布局
		desk_manager.setDisplay_arr(fenPai.getLast_three());// 初始化卓敏布局
		leftPlayer.setRemain_index(fenPai.getLeft_num());
		rightPlayer.setRemain_index(fenPai.getRight_num());
		localPlayer.initImage();
		fenPai.clearAll();// 清空分牌
		sound.play(4);//播放背景音乐
	}

	/*--------------------------------------------------------游戏开始-------------------------------------------------------------*/

	public void lf_start() {// 左家开始出牌
		if (notOutputCount >= 2) {// 如果有两个玩家没有出牌
			preCards_arr.clear();
			preCardsType = 0;
			notOutputCount=0;//notOutputCount置0
		}
		/*-----------------------------使用nextCards-------------------------------*/
		nextCards.setTotalCardsIndex_arr(leftPlayer.getRemain_index());// nextCards获取左家剩余的牌
		nextCards.setPreCardsType(preCardsType);// 上一个玩家出牌的类型
		nextCards.setPreCardsIndex_arr(preCards_arr);// nextCards获取上一个玩家出的牌
		nextCards.transfer();// nextCards开始选牌
		/*----------------------------使用nextCards完毕------------------------*/

		/*-----------------------leftPlayer开始出牌-------------------------*/
		leftPlayer.setOutput_index(nextCards.getNextCardsIndex());// 左家出牌
		leftPlayer.clearImage_layout();// 清空左家出的牌
		if (!leftPlayer.getOutput_index().isEmpty()) {// 左家出牌了
			notOutputCount = 0;
			preCards_arr.clear();
			preCards_arr = nextCards.getNextCardsIndex();
			preCardsType = nextCards.getNextCardsType();// 左家出牌的类型赋值给preCardsType
			leftPlayer.initImage();// 显示左家出的牌						
		} else {			
			ph_mg.setLf_tv("不出");
			notOutputCount++;// 左家不出notOutputCount++
		}	
		leftPlayer.clearOutputindex();// 为了节省内存，清空左家出国的牌
		if(preCardsType==4){//播放炸弹声音
			sound.play(1);
		}
		ph_mg.setLf_tv("" + leftPlayer.getCardsCout());// 显示左家出剩余的牌的数量
		if(!isGameOver()){//判断游戏是否结束
			middle_layout.removeAllViews();// 防止出错
			middle_layout.addView(chupai_layout);
		}
		/*-------------------leftPlayer出牌结束----------------------------*/
	}

	/*
	 * @说明:localPlayer的getSelected_index作为nextCards的TotalCardsIndex_arr 如果
	 */
	public void lc_start() {// 本家开始出牌		
		desk_manager.clearDeskLayout();// 清空本家出的牌
		if (notOutputCount >= 2) {// 如果有两个玩家没有出牌
			Rule r = new Rule();
			r.setTemp(localPlayer.getSelected_index());
			if (r.isRight()) {// 出牌符合规则
				notOutputCount = 0;
				preCards_arr.clear();
				preCards_arr = r.getCardsIndex_arr();
				preCardsType = r.getCardsType();
				desk_manager.setDisplay_arr(localPlayer
						.getSelected_index());
				desk_manager.initDeskLayout();// 显示本家出的牌
				localPlayer.clearSelected_index();// 清空打出的牌
				localPlayer.initImage();// 显示本家剩余的牌
				middle_layout.removeAllViews();// 清空middle_layout
				localPlayer.clearSelected_index();// 为了节省内存，清空本家出国的牌
				ph_mg.setLc_tv("" + localPlayer.getCardsCout());// 显示本家剩余的牌的数量
				if(preCardsType==4){//播放炸弹声音
					sound.play(1);
				}
				if(!isGameOver()){//判断游戏是否结束
					rg_start();// 右家出牌
				}
			} else {
				Toast.makeText(context, "不符合规则请重新选牌notOutputCount >= 2!",
						Toast.LENGTH_SHORT).show();
			}
			return;// 返回，下面的代码不执行
		}
		/*-----------------------------使用nextCards-------------------------------*/
		nextCards.setTotalCardsIndex_arr(localPlayer
				.getSelected_index());// nextCards获取本家剩余的牌
		nextCards.setPreCardsType(preCardsType);// 上一个玩家出牌的类型
		nextCards.setPreCardsIndex_arr(preCards_arr);// nextCards获取上一个家出的牌
		nextCards.transfer();// nextCards开始选牌
		/*----------------------------使用nextCards完毕------------------------*/

		/*-----------------------localPlayer开始出牌-------------------------*/
		if (nextCards.nextEqualsTotal()) {// 本家出牌了
			notOutputCount = 0;
			preCards_arr.clear();
			preCardsType = nextCards.getNextCardsType();// 本家出牌的类型赋值给preCardsType
			preCards_arr = nextCards.getNextCardsIndex();
			desk_manager.setDisplay_arr(localPlayer
					.getSelected_index());
			desk_manager.initDeskLayout();// 显示本家出的牌
			localPlayer.clearSelected_index();// 清空打出的牌
			localPlayer.initImage();// 显示本家剩余的牌
			middle_layout.removeAllViews();// 清空middle_layout
			localPlayer.clearSelected_index();// 为了节省内存，清空本家出国的牌
			if(!isGameOver()){//判断游戏是否结束
				rg_start();// 右家出牌
			}
		} else {
			// 弹出提示框
			Toast.makeText(context, "不符合规则请重新选牌!",
					Toast.LENGTH_SHORT).show();
			notOutputCount++;// 本家不出notOutputCount++
		}
		if(preCardsType==4){//播放炸弹声音
			sound.play(1);
		}
		ph_mg.setLc_tv("" + localPlayer.getCardsCout());// 显示本家剩余的牌的数量
	}

	/*-------------------localPlayer出牌结束----------------------------*/
	public void rg_start() {// 右家开始出牌		
		if (notOutputCount >= 2) {// 如果有两个玩家没有出牌
			preCards_arr.clear();
			preCardsType = 0;
			notOutputCount=0;//notOutputCount置0
		}
		/*-----------------------------使用nextCards-------------------------------*/
		nextCards.setTotalCardsIndex_arr(rightPlayer.getRemain_index());// nextCards获取左家剩余的牌
		nextCards.setPreCardsType(preCardsType);// 上一个玩家出牌的类型
		nextCards.setPreCardsIndex_arr(preCards_arr);// nextCards获取上一个玩家出的牌
		nextCards.transfer();// nextCards开始选牌
		/*----------------------------使用nextCards完毕------------------------*/

		/*-----------------------leftPlayer开始出牌-------------------------*/
		rightPlayer.setOutput_index(nextCards.getNextCardsIndex());// 右家出牌
		rightPlayer.clearImage_layout();
		if (!rightPlayer.getOutput_index().isEmpty()) {// 右家出牌了			
			notOutputCount = 0;
			preCards_arr.clear();
			preCards_arr = nextCards.getNextCardsIndex();
			preCardsType = nextCards.getNextCardsType();// 右家出牌的类型赋值给preCardsType
			rightPlayer.initImage();// 显示右家出的牌
			rightPlayer.clearOutputindex();// 清除出过的牌			
		} else {
			notOutputCount++;// 右家不出notOutputCount++
		}
		ph_mg.setRg_tv("" + rightPlayer.getCardsCout());// 显示右家出剩余的牌的数量		
		if(preCardsType==4){//播放炸弹声音
			sound.play(1);
		}
		if(!isGameOver()){//判断游戏是否结束
			lf_start();// 左家出牌
		}
	}

	/*-------------------rightPlayer出牌结束----------------------------*/
	public boolean isGameOver() {
		if (leftPlayer.getRemain_index().size() <= 0) {
			if(dz_flag!=3){//左家不是地主，地主输了
				desk_manager.changeBG(1);
			}else{
				desk_manager.changeBG(0);
			}
			System.out.println("左家赢！");
			sound.play(7);//播放结束声音
			return true;
		} else if (rightPlayer.getRemain_index().size() <= 0) {
			if(dz_flag!=2){//右家不是地主，地主输了
				desk_manager.changeBG(1);
			}else{
				desk_manager.changeBG(0);
			}
			System.out.println("右家赢！");
			sound.play(7);//播放结束声音
			return true;
		} else if(localPlayer.getRemain_index().size()<=0&&dz_flag==1){
			if(dz_flag!=1){//本家不是地主，地主输了
				desk_manager.changeBG(1);
			}else{
				desk_manager.changeBG(0);
			}
			System.out.println("本家赢！");
			sound.play(7);//播放结束声音;
			return true;
		}
		return false;
	}
	/*---------------------------------------游戏结束----------------------------------------------*/
}
