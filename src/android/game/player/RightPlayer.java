package android.game.player;

import java.util.ArrayList;
import java.util.Iterator;
import android.game.Activity.DdzTestActivity;
import android.game.Activity.GameConstants;
import android.game.logic.Sort;
import android.game.ui.DdzImage;
import android.util.Log;
import android.widget.LinearLayout;

public class RightPlayer {
	DdzTestActivity context = null;
	private ArrayList<DdzImage> output_image = null; // 存放打出的扑克牌
	private ArrayList<Integer> remain_index = null, output_index = null; // 存放int型扑克牌号
	private LinearLayout image_layout;
	private int PADDING = GameConstants.PADDING;// 扑牌的间隙
	private boolean dz = false;// 是否是地主
	private int score = 0;// 本次叫了几分

	// 构造方法
	public RightPlayer(DdzTestActivity con) {
		context = con;
		output_image = new ArrayList<DdzImage>();
		remain_index = new ArrayList<Integer>();
		output_index = new ArrayList<Integer>();
		image_layout = (LinearLayout) context
				.findViewById(GameConstants.right_layout);
	}

	// 初始化图片数组
	public void initImage() {
		image_layout.removeAllViews();// 清空image_layout上所有的view
		sort();// 对牌进行排序
		Iterator<Integer> it = output_index.iterator();
		for (int i = 0; i < (output_index.size()) && (it.hasNext()); i++) {
			int temp = (Integer) it.next();
			output_image.add(i, new DdzImage(context, image_layout,
					GameConstants.bit_image[temp], temp));// GameConstants.bit_image用于存放图片常量的数组
		}		
		// 遍历设置图片的位置
		Iterator<DdzImage> iterator = output_image.iterator();
		DdzImage temp = (DdzImage) iterator.next();
		temp.setClickable(false);// 设置第一张图片不能被单击
		temp.setLocation(50, 0);
		while (iterator.hasNext()) {
			temp.setClickable(false);// 设置图片不能被单击
			temp = (DdzImage) iterator.next();
			temp.setClickable(false);// 设置图片不能被单击
			temp.setLocation(PADDING, 0);// 设置图片的位置
		}
		// 刷新linerayout
		image_layout.invalidate();
	}

	// 为扑克牌号数组排序
	public void sort() {
		Sort sort = new Sort(remain_index);
		remain_index = sort.getF_list();
		sort = new Sort(output_index);
		output_index = sort.getF_list();
	}

	// 移除打出的牌并清空local_index和now_index
	public void removeSelected() {
		output_index.clear();
	}

	// 获取右家抢的分
	public int getJiaofen(int local_score) {
		int t = (((int) (Math.random() * (3 - local_score))) + local_score);
		if (t <= local_score) {
			t++;
		}
		if (t >= 3) {
			if ((Math.random() * 10) > 5) {// 还有一般的几率不叫呢
				t = 3;
			} else {
				t = 0;
			}
		}
		Log.i("叫的分", "本家叫了:" + local_score + "\t右家叫了:" + t);
		setScore(t);
		return t;
	}

	// 获取叫的分数
	public int getScore() {
		return score;
	}

	// 设置叫的分数
	public void setScore(int score) {
		this.score = score;
	}

	// 清空打出的牌
	public void clearOutputindex() {
		remain_index.removeAll(output_index);
		sort();// 为扑克牌号数组排序
	}

	// 获取剩余的牌的数量
	public int getCardsCout() {
		return remain_index.size();
	}

	// 清空image_layout
	public void clearImage_layout() {
		image_layout.removeAllViews();
	}

	/**
	 * @return the remain_index
	 */
	public ArrayList<Integer> getRemain_index() {
		return remain_index;
	}

	// 初始化剩余cards
	public void setRemain_index(ArrayList<Integer> remain_index) {
		this.remain_index.addAll(remain_index);
		sort();// 为扑克牌号数组排序
	}

	/**
	 * @return the dz
	 */
	public boolean isDz() {
		return dz;
	}

	/**
	 * @param dz
	 *                the dz to set
	 */
	public void setDz(boolean dz) {
		this.dz = dz;
	}

	/**
	 * @return the output_index
	 */
	public ArrayList<Integer> getOutput_index() {
		return output_index;
	}

	/**
	 * @param output_index
	 *                the output_index to set
	 */
	public void setOutput_index(ArrayList<Integer> output_index) {
		this.output_index = output_index;
	}

}
