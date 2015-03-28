package android.game.ui;

import java.util.ArrayList;
import java.util.Iterator;

import android.game.Activity.DdzTestActivity;
import android.game.Activity.GameConstants;
import android.widget.LinearLayout;
public class DeskManager {	
	DdzTestActivity context;
	LinearLayout detp_layout=null;
	int PADDING =GameConstants.PADDING,first_mar=0;// ���Ƶļ�϶����һ���Ƶ�location
	ArrayList<Integer> display_arr=null;//���Ҫ��desk����ʾ���˿��Ƶ��ƺ�
	ArrayList<DdzImage> display_image=null;//���Ҫ��desk����ʾ���˿���
	public DeskManager(DdzTestActivity con){
		context=con;
		detp_layout=(LinearLayout) context.findViewById(GameConstants.detp_layout);//��ֵ
		display_arr=new ArrayList<Integer>();//�����ɱ䳤����display_arr
		display_image=new ArrayList<DdzImage>();//�����ɱ䳤����display_image
	}
	
	
	//��ʼ��detp_layout
	public void setDisplay_arr(ArrayList<Integer> display_arr) {
		this.display_arr = display_arr;
	}	
	
	// ����˿��Ƶ�DeskLayout
	public void initDeskLayout() {	
		detp_layout.removeAllViews();//������治�����еĿؼ�
		// ����Ddzimage������ɱ䳤����reamin_arr
		Iterator<Integer> it = display_arr.iterator();
		for (int i = 0; i < (display_arr.size()) && (it.hasNext()); i++) {
			int temp = (Integer) it.next();
			display_image.add(i, new DdzImage(context, detp_layout,
					GameConstants.bit_image[temp], temp));// GameConstants.bit_image���ڴ��ͼƬ����������
		}		
		// ����remain_image ����ͼƬ��λ��
		Iterator<DdzImage> iterator = display_image.iterator();
		int i = 0;		
		while (iterator.hasNext()) {
			DdzImage temp = (DdzImage) iterator.next();
			i++;
			if(i==1){
				temp.setLocation(first_mar, 20);				
			}else{
				temp.setLocation(PADDING, 20);// ����ͼƬ��λ��				
			}
		}		
	}
	public void changeBG(int flag){//���ı���ͼƬflag=0����Ӯ�����������
		if(flag==1){
			detp_layout.setBackgroundResource(GameConstants.lt_im);			
		}else{
			detp_layout.setBackgroundResource(GameConstants.wn_im);			
		}		
	}
	//������治�����еĿؼ�
	public void clearDeskLayout(){
		detp_layout.removeAllViews();//������治�����еĿؼ�
	}
}
