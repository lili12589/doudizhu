/*
 * ������
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
	private ArrayList<DdzImage> remain_image = null, selected_image = null;// right_arr����Ҽҳ�����,remain_arr��ŵ�ǰʣ����˿���,selected_arr��ű�ѡ�е��˿���
	private ArrayList<Integer> remain_index = null, selected_index = null;// ��int�ͷ��ƺ�

	private DdzTestActivity context;
	private LinearLayout image_layout;

	private int PADDING =GameConstants.PADDING;// ���Ƶļ�϶
	private boolean dz = false;// �Ƿ��ǵ���

	// ���췽��
	public LocalPlayer(DdzTestActivity con) {
		context = con;
		// �����ɱ䳤����
		remain_image = new ArrayList<DdzImage>();
		selected_image = new ArrayList<DdzImage>();
		remain_index = new ArrayList<Integer>();
		selected_index = new ArrayList<Integer>();
		// ���image_layout��DdzActivity��linearlayout
		image_layout = (LinearLayout) context
				.findViewById(GameConstants.local_layout);
	}

	// ��ʼ��ͼƬ����
	public void initImage() {
		image_layout.removeAllViews();
		remain_image.clear();//��ʡ�ڴ�
		Sort sort = new Sort(remain_index);
		remain_index = sort.getF_list();
		// ����Ddzimage������ɱ䳤����reamin_arr
		Iterator<Integer> it = remain_index.iterator();
		for (int i = 0; i < (remain_index.size()) && (it.hasNext()); i++) {
			int temp = (Integer) it.next();
			remain_image.add(i, new DdzImage(context, image_layout,
					GameConstants.bit_image[temp], temp));// GameConstants.bit_image���ڴ��ͼƬ����������
		}
		// ����remain_image ����ͼƬ��λ��
		Iterator<DdzImage> iterator = remain_image.iterator();
		int i = 0;
		if(iterator.hasNext()){
			((DdzImage) iterator.next()).setLocation(10, 20);			
		}
		while (iterator.hasNext()) {
			i++;
			DdzImage temp = (DdzImage) iterator.next();
			temp.setLocation(PADDING, 20);// ����ͼƬ��λ��
		}		
	}

	// ��ЩͼƬ��ѡ�У�
	public void setSelected_arr() {
		selected_index.clear();
		selected_image.clear();
		DdzImage temp = null;
		Iterator<DdzImage> iterator = remain_image.iterator();
		while (iterator.hasNext()) {
			if ((temp = ((DdzImage) iterator.next())).isUp()) {// �����ѡ��
				selected_image.add(temp);
				selected_index.add(temp.getNum());// ѡ���Ƶĺ�������selected_index
			}
		}		
	}

	// �Ƴ�������Ʋ����remain_arr
	public void removeSelected() {
		remain_image.removeAll(selected_image);
		selected_image.clear();
		remain_index.removeAll(selected_index);
		selected_index.clear();
	}

	// ��ձ���������ƣ��������ǵ���ʱ��������
	public void clear_layout() {
		image_layout.removeAllViewsInLayout();
	}

	// dzΪtrue�򱾼��ǵ���
	public void setDz(boolean dz) {
		this.dz = dz;
	}

	// ���ر����Ƿ��ǵ���
	public boolean isDz() {
		return dz;
	}

	// �������ǵ���ʱ����
	public void setRemain_index(ArrayList<Integer> arrayList) {
		this.remain_index.addAll(arrayList);
	}


	//��մ������
	public void clearSelected_index(){
		remain_index.removeAll(selected_index);
	}
	
	//��ȡʣ����Ƶ�����
	public int getCardsCout(){
		return remain_index.size();
	}

	//��ȡ��ѡ�е��ƣ�	
	public ArrayList<Integer> getSelected_index() {
		setSelected_arr();// ��ЩͼƬ��ѡ�У�
		return selected_index;
	}

	//��ȡ����ʣ�����
	public ArrayList<Integer> getRemain_index() {
		return remain_index;
	}	
}
