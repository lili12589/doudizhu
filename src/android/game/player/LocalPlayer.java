/*
 * 本家类
 * */
package android.game.player;

import java.util.ArrayList;
import java.util.Iterator;
import android.game.Activity.DdzTestActivity;
import android.game.Activity.GameConstants;
import android.game.logic.Sort;
import android.game.ui.DdzImage;
import android.widget.LinearLayout;

public class LocalPlayer {
	private ArrayList<DdzImage> remain_image = null, selected_image = null;// right_arr存放右家出的牌,remain_arr存放当前剩余的扑克牌,selected_arr存放被选中的扑克牌
	private ArrayList<Integer> remain_index = null, selected_index = null;// 存int型放牌号

	private DdzTestActivity context;
	private LinearLayout image_layout;

	private int PADDING =GameConstants.PADDING;// 扑牌的间隙
	private boolean dz = false;// 是否是地主

	// 构造方法
	public LocalPlayer(DdzTestActivity con) {
		context = con;
		// 创建可变长数组
		remain_image = new ArrayList<DdzImage>();
		selected_image = new ArrayList<DdzImage>();
		remain_index = new ArrayList<Integer>();
		selected_index = new ArrayList<Integer>();
		// 添加image_layout到DdzActivity的linearlayout
		image_layout = (LinearLayout) context
				.findViewById(GameConstants.local_layout);
	}

	// 初始化图片数组
	public void initImage() {
		image_layout.removeAllViews();
		remain_image.clear();//节省内存
		Sort sort = new Sort(remain_index);
		remain_index = sort.getF_list();
		// 创建Ddzimage并放入可变长数组reamin_arr
		Iterator<Integer> it = remain_index.iterator();
		for (int i = 0; i < (remain_index.size()) && (it.hasNext()); i++) {
			int temp = (Integer) it.next();
			remain_image.add(i, new DdzImage(context, image_layout,
					GameConstants.bit_image[temp], temp));// GameConstants.bit_image用于存放图片常量的数组
		}
		// 遍历remain_image 设置图片的位置
		Iterator<DdzImage> iterator = remain_image.iterator();
		int i = 0;
		if(iterator.hasNext()){
			((DdzImage) iterator.next()).setLocation(10, 20);			
		}
		while (iterator.hasNext()) {
			i++;
			DdzImage temp = (DdzImage) iterator.next();
			temp.setLocation(PADDING, 20);// 设置图片的位置
		}		
	}

	// 那些图片被选中？
	public void setSelected_arr() {
		selected_index.clear();
		selected_image.clear();
		DdzImage temp = null;
		Iterator<DdzImage> iterator = remain_image.iterator();
		while (iterator.hasNext()) {
			if ((temp = ((DdzImage) iterator.next())).isUp()) {// 如果被选中
				selected_image.add(temp);
				selected_index.add(temp.getNum());// 选中牌的号码存放在selected_index
			}
		}		
	}

	// 移除打出的牌并清空remain_arr
	public void removeSelected() {
		remain_image.removeAll(selected_image);
		selected_image.clear();
		remain_index.removeAll(selected_index);
		selected_index.clear();
	}

	// 清空本家桌面的牌，当本家是地主时将被调用
	public void clear_layout() {
		image_layout.removeAllViewsInLayout();
	}

	// dz为true则本家是地主
	public void setDz(boolean dz) {
		this.dz = dz;
	}

	// 返回本家是否是地主
	public boolean isDz() {
		return dz;
	}

	// 当本家是地主时加牌
	public void setRemain_index(ArrayList<Integer> arrayList) {
		this.remain_index.addAll(arrayList);
	}


	//清空打出的牌
	public void clearSelected_index(){
		remain_index.removeAll(selected_index);
	}
	
	//获取剩余的牌的数量
	public int getCardsCout(){
		return remain_index.size();
	}

	//获取被选中的牌，	
	public ArrayList<Integer> getSelected_index() {
		setSelected_arr();// 那些图片被选中？
		return selected_index;
	}

	//获取本家剩余的牌
	public ArrayList<Integer> getRemain_index() {
		return remain_index;
	}	
}
