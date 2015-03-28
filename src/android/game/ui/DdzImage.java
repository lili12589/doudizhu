package android.game.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
public class DdzImage extends ImageView {
	private LinearLayout layout;//this���ڵ�layout
	private boolean isUp=false;//�ж��˿���λ���Ƿ�������	
	private final int  UP_LENGTH=10;//�˿������ϳ���
	private  int  num=10;//�˿��ƺ�
	// ˽������
	private OnClickListener listener = new View.OnClickListener() {
		public void onClick(View v) {			
			changeLocation();
		}
	};
	//���췽��
	public DdzImage(Context context,LinearLayout layout, int image,int num) {
		super(context);
		this.layout=layout;
		this.num=num;
		initImage();//��imageview��ӵ�layout
		setImageResource(image);
		setOnClickListener(listener);// Ϊ������Ӽ�����		
	}
	
	// ���ͼƬ��layout
	public void initImage() {
		layout.addView(this, new LayoutParams(75, 100));// ���ô�С
	}
	
	// ��������ImageView��λ��
	public void setLocation(int x, int y) {// x,yΪ����Ҫview�����꣬�����Ǹ���
		LayoutParams lp = (LayoutParams) this.getLayoutParams();
		lp.leftMargin = x;
		lp.topMargin = y;
		this.setLayoutParams(lp);
	}
	
	// �˿��Ʊ�����ʱִ�еķ���
	public void changeLocation(){
		LayoutParams lp = (LayoutParams) getLayoutParams();
		if(isUp){
			setLocation(lp.leftMargin, (lp.topMargin+UP_LENGTH));
			isUp=false;
		}else{
			setLocation(lp.leftMargin, (lp.topMargin-UP_LENGTH));
			isUp=true;
		}		
	}
	
	//�����˿��ƺ�
	public int getNum() {
		return num;
	}

	//�����˿���״̬
	public boolean isUp() {
		return isUp;
	}
}
