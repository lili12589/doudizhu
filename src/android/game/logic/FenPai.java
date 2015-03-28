package android.game.logic;

import java.util.ArrayList;
import java.util.Random;

public class FenPai {
	private Random random = null;
	private int array[];// ������
	private ArrayList<Integer> local_num = null, left_num = null,
			right_num = null, last_three = null;// ���������ҿ�ʼʱ������

	public FenPai() {
		local_num = new ArrayList<Integer>();
		left_num = new ArrayList<Integer>();
		right_num = new ArrayList<Integer>();
		last_three = new ArrayList<Integer>();
		random = new Random();
		array = new int[54];
		// ��ʼ��array[]����
		for (int i = 0; i < 54; i++) {
			array[i] = i;
		}	
		unordered();//ϴ��
	}
	
	//ϴ��
	public void unordered(){
		for (int i = 0; i < 54; i++) {
			exchange(random.nextInt(53), 0);// ��
							// 0����������ָ��ֵ����������֮����ȷֲ���
			exchange(random.nextInt(20), 53);//��������Ϊ��������ϴ����
		}
		FenGe();
	}
	//j����
	public void exchange(int p1, int p2) {
		int temp = array[p1];
		array[p1] = array[p2];
		array[p2] = temp;
	}

	// ���Ʒֳ�����
	public void FenGe() {
		for (int i = 0; i < array.length; i++) {
			if (i < 17) {
				local_num.add(array[i]);
			} else if (i > 16 && i < 34) {
				left_num.add(array[i]);
			} else if (i < 52 && i > 34) {
				right_num.add(array[i]);
			} else {
				last_three.add(array[i]);// ���������ŵ���
			}
		}
	}

	

	// ������еĿɱ䳤���飬����ʼ��array[]����
	public void clearAll(){
		//��ʼ��array[]����
		for (int i = 0; i < 54; i++) {
			array[i] = i;
		}
		//������еĿɱ䳤����
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
