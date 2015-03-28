package android.game.player;

import java.util.ArrayList;
import java.util.Iterator;
import android.game.Activity.DdzTestActivity;
import android.game.Activity.GameConstants;
import android.game.logic.Sort;
import android.game.ui.DdzImage;
import android.util.Log;
import android.widget.LinearLayout;

public class LeftPlayer {
	DdzTestActivity context = null;
	private ArrayList<DdzImage> output_image = null; // ��Ŵ�����˿���
	private ArrayList<Integer> remain_index = null, output_index = null; // ���int�͵��˿���
	private LinearLayout image_layout;
	private int PADDING =GameConstants.PADDING;// ���Ƶļ�϶
	private boolean dz = false;// �Ƿ��ǵ���
	private int score = 0;// ���ν��˼���

	// ���췽��
	public LeftPlayer(DdzTestActivity con) {
		context = con;
		output_image = new ArrayList<DdzImage>();
		remain_index = new ArrayList<Integer>();
		output_index = new ArrayList<Integer>();
		// ���image_layout��DdzActivity��linearlayout
		image_layout = (LinearLayout) context
				.findViewById(GameConstants.left_layout);
	}

	// ��ʼ��ͼƬ����
	public void initImage() {
		image_layout.removeAllViews();// ���image_layout�����е�view
		sort();// ���ƽ�������
			// ����Ddzimage������ɱ䳤����reamin_arr
		Iterator<Integer> it = output_index.iterator();
		for (int i = 0; i < (output_index.size()) && (it.hasNext()); i++) {
			int temp = (Integer) it.next();
			output_image.add(i, new DdzImage(context, image_layout,
					GameConstants.bit_image[temp], temp));// GameConstants.bit_image���ڴ��ͼƬ����������
		}
		// ��������ͼƬ��λ��
		Iterator<DdzImage> iterator = output_image.iterator();
		DdzImage temp = (DdzImage) iterator.next();
		temp.setLocation(32, 0);// ���õ�һ��ͼƬ��Xλ��
		temp.setClickable(false);// ���õ�һ��ͼƬ���ܱ�����
		while (iterator.hasNext()) {
			temp = (DdzImage) iterator.next();
			temp.setClickable(false);
			temp.setLocation(PADDING, 0);// ����ͼƬ��λ��
		}
		// ˢ��linerayout
		image_layout.invalidate();
	}

	// Ϊ�˿��ƺ���������
	public void sort() {
		Sort sort = new Sort(remain_index);
		remain_index = sort.getF_list();
		sort = new Sort(output_index);
		output_index = sort.getF_list();
	}

	// �Ƴ�������Ʋ����right_index��now_index
	public void removeSelected() {
		output_index.clear();
	}

	// return ������ķ�
	public int getJiaofen(int right_score) {
		int t = (((int) (Math.random() * (3 - right_score))) + right_score);
		if (t <= right_score) {
			t++;
		}
		if (t >= 3) {
			if ((Math.random() * 10) > 5) {// ����һ��ļ��ʲ�����
				t = 3;
			} else {
				t = 0;
			}
		}
		Log.i("�еķ�", "�Ҽҽ���:" + right_score + "\t��ҽ���:" + t);
		setScore(t);
		return t;
	}

	// ��ȡ�еķ���
	public int getScore() {
		return score;
	}

	// ���ýеķ���
	public void setScore(int score) {
		this.score = score;
	}
	
	//��մ������
	public void clearOutputindex(){
		remain_index.removeAll(output_index);
		sort();// Ϊ�˿��ƺ���������
	}
	
	//��ȡʣ����Ƶ�����
	public int getCardsCout(){
		return remain_index.size();
	}
	
	//���image_layout
	public void clearImage_layout(){
		image_layout.removeAllViews();
	}

	//���ʣ�����
	public void setRemain_index(ArrayList<Integer> remain_index) {
		this.remain_index.addAll(remain_index);
		sort();// Ϊ�˿��ƺ���������
	}
	//@return remain_index
	public ArrayList<Integer> getRemain_index() {
		return remain_index;
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
		this.output_index.clear();
		this.output_index = output_index;
	}

}
