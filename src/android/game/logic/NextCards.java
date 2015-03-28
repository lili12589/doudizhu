/*用于返回本家大过上家的牌
 * getNextCards主要调用方法
 * */
package android.game.logic;

import java.util.ArrayList;
import java.util.Iterator;

import android.game.Activity.GameConstants;

public class NextCards {
	ArrayList<Integer> preCardsNum_arr = null;// 上家出的牌号
	ArrayList<Integer> totalCardsNum_arr = null;// 本家剩余的牌号
	ArrayList<Integer> nextCardsNum_arr = null;// 本家要出的牌号牌,存放的是索引
	ArrayList<Integer> preCardsIndex_arr = null;// 上家打出的牌的索引
	ArrayList<Integer> totalCardsIndex_arr = null;// 本家剩余的牌的索引
	ArrayList<Integer> nextCardsIndex_arr = null;// 本家要出的牌的索引
	public int preCardsType = 0, nextCardsType = 0;// 0为空即不出，1为单，2为对(66)，3为连(456789)，4为炸弹(5555),5为三不带(999)，6为三带一(7779)，7为三带二(66622)，8飞机（45556663）9，特殊双飞机3344555666,10为双连(334455)，

	public NextCards() {
		// 创建ArrayListNum
		preCardsNum_arr = new ArrayList<Integer>();
		nextCardsNum_arr = new ArrayList<Integer>();
		totalCardsNum_arr = new ArrayList<Integer>();
		// 创建ArrayListIndex
		preCardsIndex_arr = new ArrayList<Integer>();
		nextCardsIndex_arr = new ArrayList<Integer>();
		totalCardsIndex_arr = new ArrayList<Integer>();
	}

	/*
	 * @作用:判斷totalCardsIndex_arr和nextCardsIndex_arr是否相等
	 * 
	 * @使用範圍:Localplayer
	 */
	public boolean nextEqualsTotal() {
		if (totalCardsNum_arr.size() == nextCardsNum_arr.size()) {// 长度相等
			return true;
		} else {
			nextCardsNum_arr.clear();
			return false;
		}
	}

	// 获取NextCardsIndex
	public ArrayList<Integer> getNextCardsIndex() {
		nextCardsIndex_arr.clear();
		// 遍历nextCardsNum_arr给NextCardsIndex赋值
		Iterator<Integer> it = nextCardsNum_arr.iterator();
		while (it.hasNext()) {
			nextCardsIndex_arr.add(totalCardsIndex_arr.get(it
					.next()));// 赋值
		}
		return nextCardsIndex_arr;
	}

	// 初始化本家剩余的牌Index和牌号
	public void setTotalCardsIndex_arr(ArrayList<Integer> totalCardsIndex) {
		totalCardsIndex_arr = totalCardsIndex;
		this.totalCardsNum_arr.clear();// 每次赋值前清空
		// 遍历totalCardsIndex_arr给totalCardsNum_arr赋值
		Iterator<Integer> it = totalCardsIndex_arr.iterator();
		while (it.hasNext()) {
			totalCardsNum_arr
					.add(GameConstants.cards_num[it.next()]);// 赋值
		}
	}

	// 给preCardsNum_arr赋值
	public void setPreCardsIndex_arr(ArrayList<Integer> preCardsIndex_arr) {
		this.preCardsIndex_arr = preCardsIndex_arr;
		this.preCardsNum_arr.clear();// 每次赋值前清空
		// 遍历totalCardsIndex_arr给totalCardsNum_arr赋值
		Iterator<Integer> it = this.preCardsIndex_arr.iterator();
		while (it.hasNext()) {
			preCardsNum_arr.add(GameConstants.cards_num[it.next()]);// 赋值
		}
		this.preCardsIndex_arr = preCardsIndex_arr;
	}

	/*
	 * @功能:设置nextCardsNum_arr的值 注意:必须先
	 * 
	 * @调用setPreCards()和setTotalCards()方法
	 */
	public void transfer() {
		switch (preCardsType) {
			case 0:
				single();// 当上家没有出牌时，本家出单张
				break;
			case 1: {
				single();// 当上家出牌是单张时，已经设置nextCardsNum_arr
				break;
			}
			case 2: {
				double2();
				if (nextCardsNum_arr.isEmpty()) {// 若果nextCardsNum_arr还是空，没有抽出牌大过上家，则出炸弹
					boom();
				}
				break;
			}
			case 3: {
				lian();
				if (nextCardsNum_arr.isEmpty()) {// 若果nextCardsNum_arr还是空，没有抽出牌大过上家，则出炸弹
					boom();
				}
				break;
			}
			case 4: {
				boom();
				if (nextCardsNum_arr.isEmpty()) {// 若果nextCardsNum_arr还是空，没有抽出牌大过上家，则出炸弹
					boom();
				}
				break;
			}
			case 5: {
				Three();
				if (nextCardsNum_arr.isEmpty()) {// 若果nextCardsNum_arr还是空，没有抽出牌大过上家，则出炸弹
					boom();
				}
				break;
			}
			case 6: {
				ThreeOne();
				if (nextCardsNum_arr.isEmpty()) {// 若果nextCardsNum_arr还是空，没有抽出牌大过上家，则出炸弹
					boom();
				}
				break;
			}
			case 7: {
				ThreeTwo();
				if (nextCardsNum_arr.isEmpty()) {// 若果nextCardsNum_arr还是空，没有抽出牌大过上家，则出炸弹
					boom();
				}
				break;
			}
		}

	}

	// 从剩下的牌里抽出大过上家的单张赋值给nextCardsNum_arr
	public void single()// 单张
	{
		nextCardsNum_arr.clear();
		int i = 0;
		if (totalCardsNum_arr.size() < preCardsNum_arr.size()) {// 如果总牌数小于上家出的牌数，则提示没有牌大过上家
			System.out.println("没有牌大过上家");
		} else {
			if (preCardsType == 0) {// 如果商家不出
				nextCardsNum_arr.add(0);
				nextCardsType = 1;
				return;
			}
			if (isBoom(totalCardsNum_arr) && preCardsType != 4) {// 本家出的是砸蛋
				nextCardsNum_arr.add(0);
				nextCardsNum_arr.add(1);
				nextCardsNum_arr.add(2);
				nextCardsNum_arr.add(3);
				return;
			}
			for (i = 0; i < (totalCardsNum_arr.size())
					&& (!preCardsNum_arr.isEmpty()); i++) {
				if (totalCardsNum_arr.get(i) > preCardsNum_arr
						.get(0)) {
					nextCardsNum_arr.add(i); // 得到大于上家牌的索引号
					nextCardsType = 1; // 把标志数值赋为1
					break;
				}
			}
			if (i > totalCardsNum_arr.size()) { // 遍历结束后找不到大于上家的牌
				nextCardsNum_arr.clear();
			}

		}
	}

	// 从剩下的牌里抽出大过上家的对子赋值给nextCardsNum_arr
	public void double2() {
		nextCardsNum_arr.clear();
		int i;
		if (totalCardsNum_arr.size() >= preCardsNum_arr.size()) { // 如果总牌数小于上家出的牌数，则提示没有牌大过上家
			for (i = 0; i < (totalCardsNum_arr.size() - 1); i++) {
				if (totalCardsNum_arr.get(i) == totalCardsNum_arr
						.get(i + 1)
						&& (!preCardsNum_arr.isEmpty())
						&& (totalCardsNum_arr
								.get(i + 1) > preCardsNum_arr
								.get(0))) {
					nextCardsNum_arr.add(i); // 得到大于上家牌的索引号
					nextCardsNum_arr.add(i + 1);
					nextCardsType = 2; // 把标志数值赋为2
					break;
				}
			}
			if (i > totalCardsNum_arr.size() - 1) { // 遍历结束后找不到大于上家的牌
				nextCardsNum_arr.clear();
			}
		}
	}

	// 从剩下的牌里抽出大过上家的连子赋值给nextCardsNum_arr
	public void lian() // 出连子的情况
	{
		nextCardsNum_arr.clear();
		int i = 0, j = 0;
		if (totalCardsNum_arr.size() >= preCardsNum_arr.size()) { // 如果总牌数小于上家出的牌数，则提示没有牌大过上家
			while (j < preCardsNum_arr.size()
					&& i < totalCardsNum_arr.size() - 1) {
				if (totalCardsNum_arr.get(i) > preCardsNum_arr
						.get(j)
						&& (totalCardsNum_arr
								.get(i + 1)
								- totalCardsNum_arr
										.get(i) == 1)
						&& totalCardsNum_arr.get(i + 1) <= 14) {
					nextCardsNum_arr.add(i); // 得到大于上家牌的索引号
					i++;
					j++;
					if (nextCardsNum_arr.size() == preCardsNum_arr
							.size()) {
						nextCardsType = 3;
						break;// 推出
					}
				} else {
					i++;
					j = 0;
					nextCardsNum_arr.clear();
				}
			}

		}
	}

	// 从剩下的牌里抽出大过上家的炸弹赋值给nextCardsNum_arr
	public void boom() // 四张相同为炸弹
	{
		nextCardsNum_arr.clear();
		int i = 0;
		if (totalCardsNum_arr.size() >= preCardsNum_arr.size()) { // 如果总牌数小于上家出的牌数，则提示没有牌大过上家
			while (i < (totalCardsNum_arr.size() - 3)) {
				if ((!preCardsNum_arr.isEmpty())
						&& totalCardsNum_arr.get(i) > preCardsNum_arr
								.get(0)
						&& totalCardsNum_arr.get(i) == totalCardsNum_arr
								.get(i + 1)
						&& totalCardsNum_arr.get(i) == totalCardsNum_arr
								.get(i + 2)
						&& totalCardsNum_arr.get(i) == totalCardsNum_arr
								.get(i + 3)) {
					nextCardsNum_arr.add(i); // 得到大于上家牌的索引号
					nextCardsNum_arr.add(i + 1);
					nextCardsNum_arr.add(i + 2);
					nextCardsNum_arr.add(i + 3);
					break;
				} else
					i++;

			}
			if (nextCardsNum_arr.size() - preCardsNum_arr.size() == 0) {// 遍历后如果得到下家牌的长度与上家出的相同则把标志数值赋为3
				nextCardsType = 4;
			} else {
				nextCardsNum_arr.clear();
				int len = totalCardsNum_arr.size();// totalCardsNum_arr的长度
				// 两个鬼炸弹
				if ((len >= 2)
						&& totalCardsNum_arr
								.get(len - 2) == 16
						&& totalCardsNum_arr
								.get(len - 1) == 17) {
					nextCardsNum_arr.add(len - 2);// len -
									// 2为索引值
					nextCardsNum_arr.add(len - 1);
					nextCardsType = 4;
					return;
				}
			}
		}

	}

	public void Three() {// 出三张不带的情况 333
		nextCardsNum_arr.clear();
		int i = 0;
		if (totalCardsNum_arr.size() >= preCardsNum_arr.size()) {
			while (i < totalCardsNum_arr.size() - 2) {
				if (totalCardsNum_arr.get(i) > preCardsNum_arr
						.get(1)
						&& totalCardsNum_arr.get(i) == totalCardsNum_arr
								.get(i + 1)
						&& totalCardsNum_arr.get(i + 1) == totalCardsNum_arr
								.get(i + 2)) {
					nextCardsNum_arr.add(i);// 得到大于上家出的三张相同的牌的牌的索引号
					nextCardsNum_arr.add(i + 1);
					nextCardsNum_arr.add(i + 2);
					break;
				} else
					i++;

			}
			if (nextCardsNum_arr.size() - preCardsNum_arr.size() == 0) {// 遍历后如果得到下家牌的长度与上家出的相同则把标志数值赋为5
				nextCardsType = 5;
			} else {
				nextCardsNum_arr.clear();
			}
		}
	}

	public void ThreeOne()// 出三带一的情况 3334
	{
		nextCardsNum_arr.clear();
		int i = 0;
		if ((totalCardsNum_arr.size() >= preCardsNum_arr.size())
				&& (preCardsNum_arr.size() >= 4)) { // 如果总牌数小于上家出的牌数，则提示没有牌大过上家
			while (i < totalCardsNum_arr.size() - 2) {
				if ((totalCardsNum_arr.get(i) > preCardsNum_arr
						.get(1))
						&& (totalCardsNum_arr.get(i) == totalCardsNum_arr
								.get(i + 1))
						&& (totalCardsNum_arr
								.get(i + 1) == totalCardsNum_arr
								.get(i + 2))) {
					nextCardsNum_arr.add(i);// 得到大于上家出的三张相同的牌的牌的索引号
					nextCardsNum_arr.add(i + 1);
					nextCardsNum_arr.add(i + 2);
					break;
				} else {
					i++;
				}
			}
			for (int k = i; (k < i + 3)
					&& (nextCardsNum_arr.size() == 3); k++) {// 找到相同的牌后删除掉这三张牌，以便于出第四张牌时可以从头遍历
				totalCardsNum_arr.remove(i);
			}
			if ((1 <= totalCardsNum_arr.size())
					&& (nextCardsNum_arr.size() == 3)) {
				nextCardsNum_arr.add(totalCardsNum_arr.get(0));// 获取出的三带一中带的那张牌
			}
			if (nextCardsNum_arr.size() == 4) {// 遍历后如果得到下家牌的长度与上家出的相同则把标志数值赋为6
				nextCardsType = 6;
			} else {
				nextCardsNum_arr.clear();
			}
		}
	}

	public void ThreeTwo() {// 出三代二的情况 33344
		nextCardsNum_arr.clear();

		int i = 0, j = 0;
		if (totalCardsNum_arr.size() >= preCardsNum_arr.size()) {
			while (i < totalCardsNum_arr.size() - 2) {
				if (totalCardsNum_arr.get(i) > preCardsNum_arr
						.get(2)
						&& totalCardsNum_arr.get(i) == totalCardsNum_arr
								.get(i + 1)
						&& totalCardsNum_arr.get(i + 1) == totalCardsNum_arr
								.get(i + 2)) {
					nextCardsNum_arr.add(i);// 得到大于上家出的三张相同的牌的牌的索引号
					nextCardsNum_arr.add(i + 1);
					nextCardsNum_arr.add(i + 2);
					break;
				} else
					i++;
			}
			for (int k = i; (k < i + 3)
					&& (nextCardsNum_arr.size() == 3); k++) {// 找到相同的牌后删除掉这三张牌，以便于出第四张牌时可以从头遍历
				totalCardsNum_arr.remove(i);
			}

			while (2 <= nextCardsNum_arr.size()
					&& j < nextCardsNum_arr.size() - 1) {
				if (totalCardsNum_arr.get(j) == totalCardsNum_arr
						.get(j + 1)) {
					nextCardsNum_arr.get(j);// 获取出的三带二中带的那张牌
					nextCardsNum_arr.get(j + 1);
					break;
				} else {
					j++;
				}
			}
			if (nextCardsNum_arr.size() == 5) {// 遍历后如果得到下家牌的长度与上家出的相同则把标志数值赋为7
				nextCardsType = 7;
			} else {
				nextCardsNum_arr.clear();
			}
		}

	}

	/**
	 * @return the preCardsType
	 */
	public int getPreCardsType() {
		return preCardsType;
	}

	/**
	 * @param preCardsType
	 *                the preCardsType to set
	 */
	public void setPreCardsType(int preCardsType) {
		this.preCardsType = preCardsType;
	}

	/**
	 * @return the nextCardsType
	 */
	public int getNextCardsType() {
		return nextCardsType;
	}

	/**
	 * @return the totalCardsIndex_arr
	 */
	public ArrayList<Integer> getTotalCardsIndex_arr() {
		return totalCardsIndex_arr;
	}

	/**
	 * @return the preCardsIndex_arr
	 */
	public ArrayList<Integer> getPreCardsIndex_arr() {
		return preCardsIndex_arr;
	}

	// 判断是否为炸弹，如5555
	public boolean isBoom(ArrayList<Integer> cardsNum_arr) {
		boolean isBoom = false;
		for (int i = 0; i < 3 && cardsNum_arr.size() == 4; i++) {
			if (cardsNum_arr.get(i).equals(cardsNum_arr.get(i + 1))) {
				isBoom = true;
			} else {
				isBoom = false;
				break;
			}
		}
		if (isBoom) {
			nextCardsType = 4;
		}
		return isBoom;
	}
}
