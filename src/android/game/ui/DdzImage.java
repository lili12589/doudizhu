package android.game.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
public class DdzImage extends ImageView {
	private LinearLayout layout;//this存在的layout
	private boolean isUp=false;//判断扑克牌位置是否在上面	
	private final int  UP_LENGTH=10;//扑克牌向上长度
	private  int  num=10;//扑克牌号
	// 私有属性
	private OnClickListener listener = new View.OnClickListener() {
		public void onClick(View v) {			
			changeLocation();
		}
	};
	//构造方法
	public DdzImage(Context context,LinearLayout layout, int image,int num) {
		super(context);
		this.layout=layout;
		this.num=num;
		initImage();//把imageview添加到layout
		setImageResource(image);
		setOnClickListener(listener);// 为该类添加监视器		
	}
	
	// 添加图片到layout
	public void initImage() {
		layout.addView(this, new LayoutParams(75, 100));// 设置大小
	}
	
	// 用来设置ImageView的位置
	public void setLocation(int x, int y) {// x,y为你想要view的坐标，可以是负数
		LayoutParams lp = (LayoutParams) this.getLayoutParams();
		lp.leftMargin = x;
		lp.topMargin = y;
		this.setLayoutParams(lp);
	}
	
	// 扑克牌被单击时执行的方法
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
	
	//返回扑克牌号
	public int getNum() {
		return num;
	}

	//返回扑克牌状态
	public boolean isUp() {
		return isUp;
	}
}
