/*
 * @功能:斗地主的规则
 * @备注:没有写火箭的情况
 * */
package android.game.logic;

import java.util.ArrayList;
import java.util.Iterator;

import android.game.Activity.GameConstants;

public class Rule {
	private ArrayList<Integer> cardsNum_arr = null;
	private ArrayList<Integer> cardsIndex_arr = null;
	private int cardsType = 0;

	public Rule() {
		cardsNum_arr = new ArrayList<Integer>();
		cardsIndex_arr = new ArrayList<Integer>();
	}

	// 初始化cardsNum_arr
	public void setTemp(ArrayList<Integer> cardsIndex_arr) {
		this.cardsIndex_arr = cardsIndex_arr;
		Iterator<Integer> it = cardsIndex_arr.iterator();// 遍历赋值索引-->牌号
		cardsNum_arr.clear();
		while (it.hasNext()) {
			cardsNum_arr.add(GameConstants.cards_num[it.next()]);
		}

	}

	// 获取牌型
	public int getCardsType() {
		return cardsType;
	}

	// 判断是否为单张
	public boolean isSingle() {
		boolean isSingle = false;
		if (cardsNum_arr.size() == 1) {
			isSingle = true;
		} else {
			isSingle = false;
		}
		if (isSingle) {
			cardsType = 1;
		}
		return isSingle;
	}

	// 判断是否为一对，如44
	public boolean isDou() {
		boolean isDou = false;
		if ((cardsNum_arr.get(0) == cardsNum_arr.get(1))
				&& cardsNum_arr.size() == 2) {
			isDou = true;
			cardsType = 2;
		} else {
			isDou = false;
		}
		return isDou;
	}

	// 判断是否为连子，如3456789
	public boolean isLian() {
		boolean isLian = false;
		int t1, t2;
		if (cardsNum_arr.size() > 4 && cardsNum_arr.size() < 13) {
			for (int i = 0; i < cardsNum_arr.size() - 1; i++) {
				t1 = cardsNum_arr.get(i);
				t2 = cardsNum_arr.get(i + 1);
				if (t1 != (t2 - 1)) {
					isLian = false;
					break;
				} else if (t1 == (t2 - 1)) {
					isLian = true;
				}
			}
		} else {
			isLian = false;
		}
		if (isLian) {
			cardsType = 3;
		}
		return isLian;
	}

	// 判断是否为连对，如334455
	public boolean isDouLian() {
		boolean isDoulian = false;
		ArrayList<Integer> arr_t1 = new ArrayList<Integer>(), arr_t2 = new ArrayList<Integer>();
		if (cardsNum_arr.size() % 2 != 0) {
			return isDoulian;
		}
		for (int i = 0; i < cardsNum_arr.size() - 2; i = i + 4) {
			arr_t1.add(cardsNum_arr.get(i));
			arr_t2.add(cardsNum_arr.get(i + 2));
		}
		Iterator<Integer> it1 = arr_t1.iterator();
		Iterator<Integer> it2 = arr_t2.iterator();
		for (int i = 0; i < arr_t1.size(); i = i + 2) {
			if (cardsNum_arr.get(i).equals(cardsNum_arr.get(i + 1))
					&& (it1.next() + 1 == it2.next())
					&& !(cardsNum_arr.get(i + 1)
							.equals(cardsNum_arr
									.get(i + 2)))) {
				isDoulian = true;
				cardsType = 10;
			} else {
				isDoulian = false;
				break;
			}
		}
		return isDoulian;
	}

	// 判断是否为炸弹，如5555
	public boolean isBoom() {
		boolean isBoom = false;
		if (cardsNum_arr.get(0) == 16 && cardsNum_arr.get(1) == 17
				&& cardsNum_arr.size() == 2) {// 两个鬼炸弹
			cardsType = 4;
			isBoom = true;
			return isBoom;
		}
		for (int i = 0; i < 3; i++) {
			if (cardsNum_arr.get(i).equals(cardsNum_arr.get(i + 1))
					&& cardsNum_arr.size() == 4) {
				cardsType = 4;
				isBoom = true;
			} else {
				isBoom = false;
				break;
			}
		}
		return isBoom;
	}

	// 判断是否为飞机带翅膀连，如4443355566
	public boolean isAirplaneLian() {
		boolean isAirplaneLian = false;
		ArrayList<Integer> arr_t1 = new ArrayList<Integer>(), arr_t2 = new ArrayList<Integer>();
		if (cardsNum_arr.size() % 5 != 0) {
			return isAirplaneLian;
		}
		for (int i = 0; i < cardsNum_arr.size() - 5; i++) {
			arr_t1.add(cardsNum_arr.get(i));
			arr_t2.add(cardsNum_arr.get(i + 5));
		}
		Iterator<Integer> it1 = arr_t1.iterator();
		Iterator<Integer> it2 = arr_t2.iterator();
		for (int i = 0; i < arr_t1.size();) {
			if ((cardsNum_arr.get(i)
					.equals(cardsNum_arr.get(i + 1)) && (cardsNum_arr
					.get(i + 1)).equals(cardsNum_arr
					.get(i + 2)))
					&& !cardsNum_arr.get(i + 2)
							.equals(cardsNum_arr
									.get(i + 3))
					&& (cardsNum_arr.get(i + 3)
							.equals(cardsNum_arr
									.get(i + 4)))
					&& (it1.next() + 1 == it2.next())) {
				isAirplaneLian = true;
				break;
			} else {
				isAirplaneLian = false;
				break;
			}
		}
		return isAirplaneLian;
	}

	// 判断是否为三张相同，如666
	public boolean isThree() {
		boolean isThree = false;
		if (cardsNum_arr.get(0).equals(cardsNum_arr.get(1))
				&& cardsNum_arr.get(1).equals(
						cardsNum_arr.get(2))
				&& cardsNum_arr.size() == 3) {
			isThree = true;
			cardsType = 5;
		} else {
			isThree = false;
		}
		return isThree;
	}

	// 判断是否为三张顺子，如555666
	public boolean isThreeLian() {
		boolean isThreelian = false;
		ArrayList<Integer> arr_t1 = new ArrayList<Integer>(), arr_t2 = new ArrayList<Integer>();
		if (cardsNum_arr.size() % 3 != 0) {
			return isThreelian;
		}
		for (int i = 0; i < cardsNum_arr.size() - 3; i = i + 3) {
			arr_t1.add(cardsNum_arr.get(i));
			arr_t2.add(cardsNum_arr.get(i + 3));
		}
		Iterator<Integer> it1 = arr_t1.iterator();
		Iterator<Integer> it2 = arr_t2.iterator();
		for (int i = 0; i < arr_t1.size(); i++) {
			if ((cardsNum_arr.get(i)
					.equals(cardsNum_arr.get(i + 1)) && (cardsNum_arr
					.get(i + 1)).equals(cardsNum_arr
					.get(i + 2)))
					&& (it1.next() + 1 == it2.next())) {
				isThreelian = true;
			} else {
				isThreelian = false;
				break;
			}
		}
		return isThreelian;
	}

	// 判断是否为三带一，如6665
	public boolean isThreeOne() {
		boolean isThreeOne = false;
		if (cardsNum_arr.size() == 4) {
			int t1 = cardsNum_arr.get(1), t2 = cardsNum_arr.get(0), t3 = cardsNum_arr
					.get(3);
			if (cardsNum_arr.get(1) == cardsNum_arr.get(2)) {// 第二个等于第三个
				if (((t1 == t2) || (t1 == t3)) && (t2 != t3)) {
					isThreeOne = true;
					cardsType = 6;
				}
			} else {
				isThreeOne = false;
			}
		}
		return isThreeOne;
	}

	// 判断否为三带二，如55566
	public boolean isThreeTwo() {
		boolean isThreeTwoLian = false;
		if (cardsNum_arr.size() != 5) {
			return isThreeTwoLian;
		}
		int t1 = cardsNum_arr.get(0), t2 = cardsNum_arr.get(1), t3 = cardsNum_arr
				.get(2), t4 = cardsNum_arr.get(3), t5 = cardsNum_arr
				.get(4);
		if (t1 == t2 && t4 == t5) {
			if ((t3 == t2) || (t3 == t4)) {
				isThreeTwoLian = true;
				cardsType = 7;
			}
		} else {
			isThreeTwoLian = false;
		}
		return isThreeTwoLian;
	}

	// 判断是否为三带一顺子，如55546663
	public boolean isThreeOneLian() {
		boolean isThreeOneLian = false;
		ArrayList<Integer> arr_t1 = new ArrayList<Integer>(), arr_t2 = new ArrayList<Integer>();
		int tem_len = cardsNum_arr.size();// 存放cardsNum_arr.size()
		if (tem_len % 4 != 0) {
			return isThreeOneLian;
		}
		for (int i = 2; (tem_len > 5) && i <= 5; i++) {// 添加45666777中的6667到arr_t1中
			arr_t1.add(cardsNum_arr.get(i));
		}
		if (arr_t1.get(1) == arr_t1.get(2)
				&& (arr_t1.get(1) == arr_t1.get(0) || arr_t1
						.get(2) == arr_t1.get(3))) {//arr_t1为三代一
			if ((arr_t2.get(0) == arr_t2.get(1) && arr_t2.get(1) == arr_t2
					.get(2))
					|| (arr_t2.get(6) == arr_t2.get(7) && arr_t2
							.get(5) == arr_t2
							.get(6))) {
				cardsType = 8;
				isThreeOneLian = true;
			}
		}
		return isThreeOneLian;
	}

	// 判断是否为四带二顺子，如444433555566
	public boolean isFourTwoLian() {
		boolean isFourTwoLian = false;
		ArrayList<Integer> arr_t1 = new ArrayList<Integer>(), arr_t2 = new ArrayList<Integer>();
		if (cardsNum_arr.size() % 6 != 0) {
			return isFourTwoLian;
		}
		for (int i = 0; i < cardsNum_arr.size() - 6; i = i + 6) {
			arr_t1.add(cardsNum_arr.get(i));
			arr_t2.add(cardsNum_arr.get(i + 6));
		}
		Iterator<Integer> it1 = arr_t1.iterator();
		Iterator<Integer> it2 = arr_t2.iterator();
		for (int i = 0; i < arr_t1.size(); i++) {
			if ((cardsNum_arr.get(i)
					.equals(cardsNum_arr.get(i + 1)) && (cardsNum_arr
					.get(i + 1)).equals(cardsNum_arr
					.get(i + 2)))
					&& (cardsNum_arr.get(i + 2)
							.equals(cardsNum_arr
									.get(i + 3))
							&& (cardsNum_arr.get(i + 4)
									.equals(cardsNum_arr
											.get(i + 5))) && (it1
							.next() + 1 == it2
							.next()))) {
				isFourTwoLian = true;
			} else {
				isFourTwoLian = false;
				break;
			}
		}
		return isFourTwoLian;
	}

	// 判断是否为正确
	public boolean isRight() {
		if (isSingle() || isDou() || isLian() || isBoom()
				|| isDouLian() || isAirplaneLian() || isThree()
				|| isThreeLian() || isThreeOne()
				|| isThreeTwo() || isThreeOneLian()
				|| isFourTwoLian()) {
			System.out.println("正确！");
			return true;
		} else {
			System.out.println("错误！");
			return false;
		}
	}

	// 返回cardsIndex_arr
	public ArrayList<Integer> getCardsIndex_arr() {
		return cardsIndex_arr;
	}

}