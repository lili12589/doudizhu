//���ͷ�����
package android.game.ui;

import android.game.Activity.DdzTestActivity;
import android.game.Activity.GameConstants;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoManager {
	DdzTestActivity context;
	TextView lf_tv,rg_tv,lc_tv;//���textview
	ImageView lf_ph,rg_ph,lc_ph;//���ͷ��
	
	//���췽��
	public PhotoManager(DdzTestActivity con){
		context=con;
		//����textview
		lf_tv=(TextView) context.findViewById(GameConstants.lfpl_tv);
		rg_tv=(TextView) context.findViewById(GameConstants.rgpl_tv);
		lc_tv=(TextView) context.findViewById(GameConstants.lcpl_tv);
		//����ImageView
		lf_ph=(ImageView) context.findViewById(GameConstants.lfpl_im);
		rg_ph=(ImageView) context.findViewById(GameConstants.rgpl_im);
		lc_ph=(ImageView) context.findViewById(GameConstants.lcpl_im);
		
	}

	//�������TextView������
	public void setLf_tv(String str) {
		lf_tv.setText(str);
	}

	//�����Ҽ�TextView����
	public void setRg_tv(String str) {
		rg_tv.setText(str) ;
	}

	//���ñ���TextView������
	public void setLc_tv(String str) {
		lc_tv .setText(str);
	}

	//�������ͷ��
	public void setLf_ph() {
		lf_ph.setImageResource(GameConstants.dz);
	}

	//�����Ҽ�ͷ��
	public void setRg_ph() {
		rg_ph.setImageResource(GameConstants.dz);
	}

	//���ñ���ͷ��
	public void setLc_ph() {
		lc_ph.setImageResource(GameConstants.dz);
	}	
}
