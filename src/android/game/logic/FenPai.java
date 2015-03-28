package android.game.logic;

import java.util.ArrayList;
import java.util.Random;

public class FenPai {
	private Random random = null;
	private int array[];// 总数组
	private ArrayList<Integer> local_num = null, left_num = null,
			right_num = null, last_three = null;// 存放三个玩家开始时发的牌

	public FenPai() {
		local_num = new ArrayList<Integer>();
		left_num = new ArrayList<Integer>();
		right_num = new ArrayList<Integer>();
		last_three = new ArrayList<Integer>();
		random = new Random();
		array = new int[54];
		// 初始化array[]数组
		for (int i = 0; i < 54; i++) {
			array[i] = i;
		}	
		unordered();//洗牌
	}
	
	//洗牌
	public void unordered(){
		for (int i = 0; i < 54; i++) {
			exchange(random.nextInt(53), 0);// 在
							// 0（包括）和指定值（不包括）之间均匀分布的
			exchange(random.nextInt(20), 53);//换大王因为大王容易洗不开
		}
		FenGe();
	}
	//j交换
	public void exchange(int p1, int p2) {
		int temp = array[p1];
		array[p1] = array[p2];
		array[p2] = temp;
	}

	// 把牌分成三份
	public void FenGe() {
		for (int i = 0; i < array.length; i++) {
			if (i < 17) {
				local_num.add(array[i]);
			} else if (i > 16 && i < 34) {
				left_num.add(array[i]);
			} else if (i < 52 && i > 34) {
				right_num.add(array[i]);
			} else {
				last_three.add(array[i]);// 存放最后三张底牌
			}
		}
	}

	

	// 清空所有的可变长数组，并初始化array[]数组
	public void clearAll(){
		//初始化array[]数组
		for (int i = 0; i < 54; i++) {
			array[i] = i;
		}
		//清空所有的可变长数组
		local_num.clear(); 
		left_num.clear();
		right_num.clear();
		last_three.clear();
	}

	/**
	 * @return the local_num
	 */
	public ArrayList<Integer> getLocal_num() {
		return local_num;
	}

	/**
	 * @return the left_num
	 */
	public ArrayList<Integer> getLeft_num() {
		return left_num;
	}

	/**
	 * @return the right_num
	 */
	public ArrayList<Integer> getRight_num() {
		return right_num;
	}

	/**
	 * @return the last_three
	 */
	public ArrayList<Integer> getLast_three() {
		return last_three;
	}

	
}
