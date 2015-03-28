/*���ڷ��ر��Ҵ���ϼҵ���
 * getNextCards��Ҫ���÷���
 * */
package android.game.logic;

import java.util.ArrayList;
import java.util.Iterator;

import android.game.Activity.GameConstants;

public class NextCards {
	ArrayList<Integer> preCardsNum_arr = null;// �ϼҳ����ƺ�
	ArrayList<Integer> totalCardsNum_arr = null;// ����ʣ����ƺ�
	ArrayList<Integer> nextCardsNum_arr = null;// ����Ҫ�����ƺ���,��ŵ�������
	ArrayList<Integer> preCardsIndex_arr = null;// �ϼҴ�����Ƶ�����
	ArrayList<Integer> totalCardsIndex_arr = null;// ����ʣ����Ƶ�����
	ArrayList<Integer> nextCardsIndex_arr = null;// ����Ҫ�����Ƶ�����
	public int preCardsType = 0, nextCardsType = 0;// 0Ϊ�ռ�������1Ϊ����2Ϊ��(66)��3Ϊ��(456789)��4Ϊը��(5555),5Ϊ������(999)��6Ϊ����һ(7779)��7Ϊ������(66622)��8�ɻ���45556663��9������˫�ɻ�3344555666,10Ϊ˫��(334455)��

	public NextCards() {
		// ����ArrayListNum
		preCardsNum_arr = new ArrayList<Integer>();
		nextCardsNum_arr = new ArrayList<Integer>();
		totalCardsNum_arr = new ArrayList<Integer>();
		// ����ArrayListIndex
		preCardsIndex_arr = new ArrayList<Integer>();
		nextCardsIndex_arr = new ArrayList<Integer>();
		totalCardsIndex_arr = new ArrayList<Integer>();
	}

	/*
	 * @����:�Д�totalCardsIndex_arr��nextCardsIndex_arr�Ƿ����
	 * 
	 * @ʹ�ù���:Localplayer
	 */
	public boolean nextEqualsTotal() {
		if (totalCardsNum_arr.size() == nextCardsNum_arr.size()) {// �������
			return true;
		} else {
			nextCardsNum_arr.clear();
			return false;
		}
	}

	// ��ȡNextCardsIndex
	public ArrayList<Integer> getNextCardsIndex() {
		nextCardsIndex_arr.clear();
		// ����nextCardsNum_arr��NextCardsIndex��ֵ
		Iterator<Integer> it = nextCardsNum_arr.iterator();
		while (it.hasNext()) {
			nextCardsIndex_arr.add(totalCardsIndex_arr.get(it
					.next()));// ��ֵ
		}
		return nextCardsIndex_arr;
	}

	// ��ʼ������ʣ�����Index���ƺ�
	public void setTotalCardsIndex_arr(ArrayList<Integer> totalCardsIndex) {
		totalCardsIndex_arr = totalCardsIndex;
		this.totalCardsNum_arr.clear();// ÿ�θ�ֵǰ���
		// ����totalCardsIndex_arr��totalCardsNum_arr��ֵ
		Iterator<Integer> it = totalCardsIndex_arr.iterator();
		while (it.hasNext()) {
			totalCardsNum_arr
					.add(GameConstants.cards_num[it.next()]);// ��ֵ
		}
	}

	// ��preCardsNum_arr��ֵ
	public void setPreCardsIndex_arr(ArrayList<Integer> preCardsIndex_arr) {
		this.preCardsIndex_arr = preCardsIndex_arr;
		this.preCardsNum_arr.clear();// ÿ�θ�ֵǰ���
		// ����totalCardsIndex_arr��totalCardsNum_arr��ֵ
		Iterator<Integer> it = this.preCardsIndex_arr.iterator();
		while (it.hasNext()) {
			preCardsNum_arr.add(GameConstants.cards_num[it.next()]);// ��ֵ
		}
		this.preCardsIndex_arr = preCardsIndex_arr;
	}

	/*
	 * @����:����nextCardsNum_arr��ֵ ע��:������
	 * 
	 * @����setPreCards()��setTotalCards()����
	 */
	public void transfer() {
		switch (preCardsType) {
			case 0:
				single();// ���ϼ�û�г���ʱ�����ҳ�����
				break;
			case 1: {
				single();// ���ϼҳ����ǵ���ʱ���Ѿ�����nextCardsNum_arr
				break;
			}
			case 2: {
				double2();
				if (nextCardsNum_arr.isEmpty()) {// ����nextCardsNum_arr���ǿգ�û�г���ƴ���ϼң����ը��
					boom();
				}
				break;
			}
			case 3: {
				lian();
				if (nextCardsNum_arr.isEmpty()) {// ����nextCardsNum_arr���ǿգ�û�г���ƴ���ϼң����ը��
					boom();
				}
				break;
			}
			case 4: {
				boom();
				if (nextCardsNum_arr.isEmpty()) {// ����nextCardsNum_arr���ǿգ�û�г���ƴ���ϼң����ը��
					boom();
				}
				break;
			}
			case 5: {
				Three();
				if (nextCardsNum_arr.isEmpty()) {// ����nextCardsNum_arr���ǿգ�û�г���ƴ���ϼң����ը��
					boom();
				}
				break;
			}
			case 6: {
				ThreeOne();
				if (nextCardsNum_arr.isEmpty()) {// ����nextCardsNum_arr���ǿգ�û�г���ƴ���ϼң����ը��
					boom();
				}
				break;
			}
			case 7: {
				ThreeTwo();
				if (nextCardsNum_arr.isEmpty()) {// ����nextCardsNum_arr���ǿգ�û�г���ƴ���ϼң����ը��
					boom();
				}
				break;
			}
		}

	}

	// ��ʣ�µ�����������ϼҵĵ��Ÿ�ֵ��nextCardsNum_arr
	public void single()// ����
	{
		nextCardsNum_arr.clear();
		int i = 0;
		if (totalCardsNum_arr.size() < preCardsNum_arr.size()) {// ���������С���ϼҳ�������������ʾû���ƴ���ϼ�
			System.out.println("û���ƴ���ϼ�");
		} else {
			if (preCardsType == 0) {// ����̼Ҳ���
				nextCardsNum_arr.add(0);
				nextCardsType = 1;
				return;
			}
			if (isBoom(totalCardsNum_arr) && preCardsType != 4) {// ���ҳ������ҵ�
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
					nextCardsNum_arr.add(i); // �õ������ϼ��Ƶ�������
					nextCardsType = 1; // �ѱ�־��ֵ��Ϊ1
					break;
				}
			}
			if (i > totalCardsNum_arr.size()) { // �����������Ҳ��������ϼҵ���
				nextCardsNum_arr.clear();
			}

		}
	}

	// ��ʣ�µ�����������ϼҵĶ��Ӹ�ֵ��nextCardsNum_arr
	public void double2() {
		nextCardsNum_arr.clear();
		int i;
		if (totalCardsNum_arr.size() >= preCardsNum_arr.size()) { // ���������С���ϼҳ�������������ʾû���ƴ���ϼ�
			for (i = 0; i < (totalCardsNum_arr.size() - 1); i++) {
				if (totalCardsNum_arr.get(i) == totalCardsNum_arr
						.get(i + 1)
						&& (!preCardsNum_arr.isEmpty())
						&& (totalCardsNum_arr
								.get(i + 1) > preCardsNum_arr
								.get(0))) {
					nextCardsNum_arr.add(i); // �õ������ϼ��Ƶ�������
					nextCardsNum_arr.add(i + 1);
					nextCardsType = 2; // �ѱ�־��ֵ��Ϊ2
					break;
				}
			}
			if (i > totalCardsNum_arr.size() - 1) { // �����������Ҳ��������ϼҵ���
				nextCardsNum_arr.clear();
			}
		}
	}

	// ��ʣ�µ�����������ϼҵ����Ӹ�ֵ��nextCardsNum_arr
	public void lian() // �����ӵ����
	{
		nextCardsNum_arr.clear();
		int i = 0, j = 0;
		if (totalCardsNum_arr.size() >= preCardsNum_arr.size()) { // ���������С���ϼҳ�������������ʾû���ƴ���ϼ�
			while (j < preCardsNum_arr.size()
					&& i < totalCardsNum_arr.size() - 1) {
				if (totalCardsNum_arr.get(i) > preCardsNum_arr
						.get(j)
						&& (totalCardsNum_arr
								.get(i + 1)
								- totalCardsNum_arr
										.get(i) == 1)
						&& totalCardsNum_arr.get(i + 1) <= 14) {
					nextCardsNum_arr.add(i); // �õ������ϼ��Ƶ�������
					i++;
					j++;
					if (nextCardsNum_arr.size() == preCardsNum_arr
							.size()) {
						nextCardsType = 3;
						break;// �Ƴ�
					}
				} else {
					i++;
					j = 0;
					nextCardsNum_arr.clear();
				}
			}

		}
	}

	// ��ʣ�µ�����������ϼҵ�ը����ֵ��nextCardsNum_arr
	public void boom() // ������ͬΪը��
	{
		nextCardsNum_arr.clear();
		int i = 0;
		if (totalCardsNum_arr.size() >= preCardsNum_arr.size()) { // ���������С���ϼҳ�������������ʾû���ƴ���ϼ�
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
					nextCardsNum_arr.add(i); // �õ������ϼ��Ƶ�������
					nextCardsNum_arr.add(i + 1);
					nextCardsNum_arr.add(i + 2);
					nextCardsNum_arr.add(i + 3);
					break;
				} else
					i++;

			}
			if (nextCardsNum_arr.size() - preCardsNum_arr.size() == 0) {// ����������õ��¼��Ƶĳ������ϼҳ�����ͬ��ѱ�־��ֵ��Ϊ3
				nextCardsType = 4;
			} else {
				nextCardsNum_arr.clear();
				int len = totalCardsNum_arr.size();// totalCardsNum_arr�ĳ���
				// ������ը��
				if ((len >= 2)
						&& totalCardsNum_arr
								.get(len - 2) == 16
						&& totalCardsNum_arr
								.get(len - 1) == 17) {
					nextCardsNum_arr.add(len - 2);// len -
									// 2Ϊ����ֵ
					nextCardsNum_arr.add(len - 1);
					nextCardsType = 4;
					return;
				}
			}
		}

	}

	public void Three() {// �����Ų�������� 333
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
					nextCardsNum_arr.add(i);// �õ������ϼҳ���������ͬ���Ƶ��Ƶ�������
					nextCardsNum_arr.add(i + 1);
					nextCardsNum_arr.add(i + 2);
					break;
				} else
					i++;

			}
			if (nextCardsNum_arr.size() - preCardsNum_arr.size() == 0) {// ����������õ��¼��Ƶĳ������ϼҳ�����ͬ��ѱ�־��ֵ��Ϊ5
				nextCardsType = 5;
			} else {
				nextCardsNum_arr.clear();
			}
		}
	}

	public void ThreeOne()// ������һ����� 3334
	{
		nextCardsNum_arr.clear();
		int i = 0;
		if ((totalCardsNum_arr.size() >= preCardsNum_arr.size())
				&& (preCardsNum_arr.size() >= 4)) { // ���������С���ϼҳ�������������ʾû���ƴ���ϼ�
			while (i < totalCardsNum_arr.size() - 2) {
				if ((totalCardsNum_arr.get(i) > preCardsNum_arr
						.get(1))
						&& (totalCardsNum_arr.get(i) == totalCardsNum_arr
								.get(i + 1))
						&& (totalCardsNum_arr
								.get(i + 1) == totalCardsNum_arr
								.get(i + 2))) {
					nextCardsNum_arr.add(i);// �õ������ϼҳ���������ͬ���Ƶ��Ƶ�������
					nextCardsNum_arr.add(i + 1);
					nextCardsNum_arr.add(i + 2);
					break;
				} else {
					i++;
				}
			}
			for (int k = i; (k < i + 3)
					&& (nextCardsNum_arr.size() == 3); k++) {// �ҵ���ͬ���ƺ�ɾ�����������ƣ��Ա��ڳ���������ʱ���Դ�ͷ����
				totalCardsNum_arr.remove(i);
			}
			if ((1 <= totalCardsNum_arr.size())
					&& (nextCardsNum_arr.size() == 3)) {
				nextCardsNum_arr.add(totalCardsNum_arr.get(0));// ��ȡ��������һ�д���������
			}
			if (nextCardsNum_arr.size() == 4) {// ����������õ��¼��Ƶĳ������ϼҳ�����ͬ��ѱ�־��ֵ��Ϊ6
				nextCardsType = 6;
			} else {
				nextCardsNum_arr.clear();
			}
		}
	}

	public void ThreeTwo() {// ������������� 33344
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
					nextCardsNum_arr.add(i);// �õ������ϼҳ���������ͬ���Ƶ��Ƶ�������
					nextCardsNum_arr.add(i + 1);
					nextCardsNum_arr.add(i + 2);
					break;
				} else
					i++;
			}
			for (int k = i; (k < i + 3)
					&& (nextCardsNum_arr.size() == 3); k++) {// �ҵ���ͬ���ƺ�ɾ�����������ƣ��Ա��ڳ���������ʱ���Դ�ͷ����
				totalCardsNum_arr.remove(i);
			}

			while (2 <= nextCardsNum_arr.size()
					&& j < nextCardsNum_arr.size() - 1) {
				if (totalCardsNum_arr.get(j) == totalCardsNum_arr
						.get(j + 1)) {
					nextCardsNum_arr.get(j);// ��ȡ�����������д���������
					nextCardsNum_arr.get(j + 1);
					break;
				} else {
					j++;
				}
			}
			if (nextCardsNum_arr.size() == 5) {// ����������õ��¼��Ƶĳ������ϼҳ�����ͬ��ѱ�־��ֵ��Ϊ7
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

	// �ж��Ƿ�Ϊը������5555
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
