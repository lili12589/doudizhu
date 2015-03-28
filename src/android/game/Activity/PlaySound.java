package android.game.Activity;

import java.util.HashMap;
import android.media.SoundPool;
import android.content.Context;
import android.game.Activity.DdzTestActivity;
import android.game.Activity.GameConstants;
import android.media.AudioManager;

public class PlaySound {
	SoundPool sp;
	HashMap<Integer, Integer> map;
	DdzTestActivity con;

	public PlaySound(Context con) {// 构造方法
		this.con = (DdzTestActivity) con;
		initSoundPool();// 初始化SoundPool
	}

	public void initSoundPool() {
		sp = new SoundPool(7, AudioManager.STREAM_MUSIC, 0);
		map = new HashMap<Integer, Integer>();
		map.put(1, sp.load(con, GameConstants.bomb_sound, 1));
		map.put(2, sp.load(con, GameConstants.fapai_sound, 1));
		map.put(3, sp.load(con, GameConstants.outcard1_sound, 1));
		map.put(4, sp.load(con, GameConstants.bg_sound, 1));
		map.put(5, sp.load(con, GameConstants.start_sound, 1));
		map.put(6, sp.load(con, GameConstants.win_sound, 1));
		map.put(7, sp.load(con, GameConstants.end_sound, 1));
	}

	public void playSound(int sound, int number) {
		AudioManager am = (AudioManager) con
				.getSystemService(Context.AUDIO_SERVICE);
		float audioMaxVolumn = am
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float audioCurrentVolumn = am
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volumnRatio = audioCurrentVolumn / audioMaxVolumn;
		sp.play(map.get(sound), volumnRatio, volumnRatio, 1, number, 1);
	}

	public void play(int flag) {
		switch (flag) {
			case 1:
				playSound(1, 0);
				break;
			case 2:
				playSound(2, 0);
				break;
			case 3:
				playSound(3, 0);
				break;
			case 4:
				playSound(4, 0);
				break;
			case 5:
				playSound(5, 0);
				break;
			case 6:
				playSound(6, 0);
				break;
			case 7:
				playSound(7, 0);
				break;
		}
	}

	public void freeMusic() {
		if (sp != null) {
			sp.release();
		}
	}
}