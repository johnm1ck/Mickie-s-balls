package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundManager {

	private static Media kiSound;
	private static Media deathSound;
	private static Media boomSound;
	private static Media hitSound;
	private static Media bgmSound;

	private static MediaPlayer backgroundMusicPlayer;

	private static boolean boomSoundPlayed = false;

	static {
		try {
			// Load media files
			kiSound = loadMedia("resource/sound/ki.mp3");
			deathSound = loadMedia("resource/sound/death.mp3");
			boomSound = loadMedia("resource/sound/boom.mp3");
			hitSound = loadMedia("resource/sound/hit.mp3");
			bgmSound = loadMedia("resource/sound/bgm.mp3");

			// Background music setup
			backgroundMusicPlayer = new MediaPlayer(bgmSound);
			backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			backgroundMusicPlayer.setVolume(0.15);

		} catch (Exception e) {
			System.err.println("Error loading media files:");
			e.printStackTrace();
		}
	}

	private static Media loadMedia(String path) {
		return new Media(ClassLoader.getSystemResource(path).toString());
	}

	private static void playNewSound(Media sound, double volume) {
		try {
			if (sound == null) return;
			MediaPlayer player = new MediaPlayer(sound);
			player.setVolume(volume);
			player.play();
			player.setOnEndOfMedia(player::dispose);
		} catch (Exception e) {
			System.err.println("Failed to play sound.");
			e.printStackTrace();
		}
	}

	// Sound triggers with volume control
	public static void playKiSound() {
		playNewSound(kiSound, 0.1);  // Low-power blast
	}

	public static void playDeathSound() {
		playNewSound(deathSound, 0.4);  // Louder for dramatic effect
	}

	public static void playBoomSound() {
		if (!boomSoundPlayed) {
			playNewSound(boomSound, 0.3);  // Medium explosion
			boomSoundPlayed = true;
		}
	}

	public static void resetBoomSound() {
		boomSoundPlayed = false;
	}

	public static void playHitSound() {
		playNewSound(hitSound, 0.08);  // Quieter impact
	}

	// Background music controls
	public static void startBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.seek(Duration.ZERO);
			backgroundMusicPlayer.play();
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
