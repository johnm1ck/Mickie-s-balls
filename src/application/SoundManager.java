package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

	//  Volume Constants 
	public static final double DEFAULT_BGM_VOLUME = 0.55;
	public static final double IN_GAME_BGM_VOLUME = 1.0;
	public static final double SUPER_BGM_VOLUME = 0.32;

	public static final double KI_SOUND_VOLUME = 0.16;
	public static final double DEATH_SOUND_VOLUME = 0.7;
	public static final double DOG_CRY_VOLUME = 0.29;
	public static final double VICTORY_SOUND_VOLUME = 0.59;
	public static final double X_KI_VOLUME = 0.4;
	public static final double Z_KI_VOLUME = 0.4;
	public static final double BOOM_VOLUME = 1.0;
	public static final double HIT_SOUND_VOLUME = 0.12;

	// Media Assets 
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

	//  Sound Effects 
	public static void playKiSound() {
		playNewSound(kiSound, KI_SOUND_VOLUME);
	}

	public static void playDeathSound() {
		playNewSound(deathSound, DEATH_SOUND_VOLUME);
	}

	public static void playDogCrySound() {
		playNewSound(dogCrySound, DOG_CRY_VOLUME);
	}

	public static void playVictorySound() {
		playNewSound(victorySound, VICTORY_SOUND_VOLUME);
	}

	public static void playXKiSound() {
		playNewSound(xKiSound, X_KI_VOLUME);
	}

	public static void playZKiSound() {
		playNewSound(zKiSound, Z_KI_VOLUME);
	}

	public static void playBoomSound() {
		if (!boomSoundPlayed) {
			playNewSound(boomSound, BOOM_VOLUME);
			boomSoundPlayed = true;
		}
	}

	public static void resetBoomSound() {
		boomSoundPlayed = false;
	}

	public static void playHitSound() {
		playNewSound(hitSound, HIT_SOUND_VOLUME);
	}

	//  Background Music 
	public static void startBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.dispose();
			backgroundMusicPlayer = new MediaPlayer(bgmSound);
			backgroundMusicPlayer.setVolume(DEFAULT_BGM_VOLUME);
			backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			backgroundMusicPlayer.play();
		}
	}

	public static void startInGameBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.dispose();
			backgroundMusicPlayer = new MediaPlayer(inGameBgmSound);
			backgroundMusicPlayer.setVolume(IN_GAME_BGM_VOLUME);
			backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			backgroundMusicPlayer.play();
		}
	}

	public static void startSuperBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.dispose();
			backgroundMusicPlayer = new MediaPlayer(superBgmSound);
			backgroundMusicPlayer.setVolume(SUPER_BGM_VOLUME);
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
