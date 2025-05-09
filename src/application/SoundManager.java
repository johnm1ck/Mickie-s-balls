package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

	private static Media kiSound;
	private static Media deathSound;
	private static Media boomSound;
	private static Media hitSound;
	private static Media bgmSound;
	private static Media inGameBgmSound;
	private static Media superBgmSound;
	private static Media victorySound;
	private static Media xKiSound;
	private static Media zKiSound;
	private static Media dogCrySound;
	
	private static MediaPlayer backgroundMusicPlayer;

	private static boolean boomSoundPlayed = false;

	static {
		try {
			// Load media files
			bgmSound = new Media(MediaManager.BGM_URL);
			boomSound = new Media(MediaManager.BOOM_URL);
			deathSound = new Media(MediaManager.DEATH_URL);
			hitSound = new Media(MediaManager.HIT_URL);
			inGameBgmSound = new Media(MediaManager.IN_GAME_BGM_URL);
			kiSound = new Media(MediaManager.KI_URL);
			superBgmSound = new Media(MediaManager.SUPER_BGM_URL);
			victorySound = new Media(MediaManager.VICTORY_URL);
			xKiSound = new Media(MediaManager.X_KI_URL);
			zKiSound = new Media(MediaManager.Z_KI_URL);
			dogCrySound = new Media(MediaManager.DOG_CRY_URL);
			
			// initialize backgroundMusicPlayer
			backgroundMusicPlayer = new MediaPlayer(bgmSound);
			
		} catch (Exception e) {
			System.err.println("Error loading media files");
			e.printStackTrace();
		}
	}
	
	private static void playNewSound(Media sound, double volume) {
		try {
			if (sound == null) return;
			MediaPlayer player = new MediaPlayer(sound);
			player.setVolume(volume);
			player.play();
			player.setOnEndOfMedia(player::dispose);
		} catch (Exception e) {
			System.err.println("Failed to play sound");
			e.printStackTrace();
		}
	}

	// Sound triggers with volume control
	public static void playKiSound() {
		playNewSound(kiSound, 0.16);
	}

	public static void playDeathSound() {
		playNewSound(deathSound, 0.7);
	}
	
	public static void playDogCrySound() {
		playNewSound(dogCrySound, 0.4);
	}
	
	public static void playVictorySound() {
		playNewSound(victorySound, 0.59);
	}
	
	public static void playXKiSound() {
		playNewSound(xKiSound, 0.4);
	}
	
	public static void playZKiSound() {
		playNewSound(zKiSound, 0.4);
	}

	public static void playBoomSound() {
		if (!boomSoundPlayed) {
			playNewSound(boomSound, 0.96);
			boomSoundPlayed = true;
		}
	}

	public static void resetBoomSound() {
		boomSoundPlayed = false;
	}

	public static void playHitSound() {
		playNewSound(hitSound, 0.12);
	}

	// Background music controls
	public static void startBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.dispose();
			backgroundMusicPlayer = new MediaPlayer(bgmSound);
			backgroundMusicPlayer.setVolume(0.55);
			backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			backgroundMusicPlayer.play();
		}
	}
	
	public static void startInGameBackgroundMusic() {
		if(backgroundMusicPlayer != null) {
			backgroundMusicPlayer.dispose();
			backgroundMusicPlayer = new MediaPlayer(inGameBgmSound);
			backgroundMusicPlayer.setVolume(1);
			backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			backgroundMusicPlayer.play();
		}
	}
	
	public static void startSuperBackgroundMusic() {
		if(backgroundMusicPlayer != null) {
			backgroundMusicPlayer.dispose();
			backgroundMusicPlayer = new MediaPlayer(superBgmSound);
			backgroundMusicPlayer.setVolume(0.36);
			backgroundMusicPlayer.play();
			backgroundMusicPlayer.setOnEndOfMedia(() -> startInGameBackgroundMusic());
		}
	}

	public static void stopBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.stop();
		}
	}

	public static void pauseBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.pause();
		}
	}

	public static void resumeBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.play();
		}
	}
}
