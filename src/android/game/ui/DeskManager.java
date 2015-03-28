package android.game.ui;

import java.util.ArrayList;
import java.util.Iterator;

import android.game.Activity.DdzTestActivity;
import android.game.Activity.GameConstants;
import android.widget.LinearLayout;
public class DeskManager {	
	DdzTestActivity context;
	LinearLayout detp_layout=null;
	int PADDING =GameConstants.PADDING,first_mar=0;// 扑牌的间隙，第一张牌的location
	ArrayList<Integer> display_arr=null;//存放要在desk上显示的扑克牌的牌号
	ArrayList<DdzImage> display_image=null;//存放要在desk上显示的扑克牌
	public DeskManager(DdzTestActivity con){
		context=con;
		detp_layout=(LinearLayout) context.findViewById(GameConstants.detp_layout);//赋值
		display_arr=new ArrayList<Integer>();//创建可变长数组display_arr
		display_image=new ArrayList<DdzImage>();//创建可变长数组display_image
	}
	
	
	//初始化detp_layout
	public void setDisplay_arr(ArrayList<Integer> display_arr) {
		this.display_arr = display_arr;
	}	
	
	// 添加扑克牌到DeskLayout
	public void initDeskLayout() {	
		detp_layout.removeAllViews();//清空桌面不上所有的控件
		// 创建Ddzimage并放入可变长数组reamin_arr
		Iterator<Integer> it = display_arr.iterator();
		for (int i = 0; i < (display_arr.size()) && (it.hasNext()); i++) {
			int temp = (Integer) it.next();
			display_image.add(i, new DdzImage(context, detp_layout,
					GameConstants.bit_image[temp], temp));// GameConstants.bit_image用于存放图片常量的数组
		}		
		// 遍历remain_image 设置图片的位置
		Iterator<DdzImage> iterator = display_image.iterator();
		int i = 0;		
		while (iterator.hasNext()) {
			DdzImage temp = (DdzImage) iterator.next();
			i++;
			if(i==1){
				temp.setLocation(first_mar, 20);				
			}else{
				temp.setLocation(PADDING, 20);// 设置图片的位置				
			}
		}		
	}
	public void changeBG(int flag){//更改背景图片flag=0地主赢，否则地主输
		if(flag==1){
			detp_layout.setBackgroundResource(GameConstants.lt_im);			
		}else{
			detp_layout.setBackgroundResource(GameConstants.wn_im);			
		}		
	}
	//清空桌面不上所有的控件
	public void clearDeskLayout(){
		detp_layout.removeAllViews();//清空桌面不上所有的控件
	}
}
