//玩家头像管理
package android.game.ui;

import android.game.Activity.DdzTestActivity;
import android.game.Activity.GameConstants;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoManager {
	DdzTestActivity context;
	TextView lf_tv,rg_tv,lc_tv;//玩家textview
	ImageView lf_ph,rg_ph,lc_ph;//玩家头像
	
	//构造方法
	public PhotoManager(DdzTestActivity con){
		context=con;
		//创建textview
		lf_tv=(TextView) context.findViewById(GameConstants.lfpl_tv);
		rg_tv=(TextView) context.findViewById(GameConstants.rgpl_tv);
		lc_tv=(TextView) context.findViewById(GameConstants.lcpl_tv);
		//创建ImageView
		lf_ph=(ImageView) context.findViewById(GameConstants.lfpl_im);
		rg_ph=(ImageView) context.findViewById(GameConstants.rgpl_im);
		lc_ph=(ImageView) context.findViewById(GameConstants.lcpl_im);
		
	}

	//设置左家TextView的内容
	public void setLf_tv(String str) {
		lf_tv.setText(str);
	}

	//设置右家TextView内容
	public void setRg_tv(String str) {
		rg_tv.setText(str) ;
	}

	//设置本家TextView的内容
	public void setLc_tv(String str) {
		lc_tv .setText(str);
	}

	//设置左家头像
	public void setLf_ph() {
		lf_ph.setImageResource(GameConstants.dz);
	}

	//设置右家头像
	public void setRg_ph() {
		rg_ph.setImageResource(GameConstants.dz);
	}

	//设置本家头像
	public void setLc_ph() {
		lc_ph.setImageResource(GameConstants.dz);
	}	
}
