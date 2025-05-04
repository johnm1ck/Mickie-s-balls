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

	private static MediaPlayer kiBlastPlayer;
	private static MediaPlayer deathPlayer;
	private static MediaPlayer boomPlayer;
	private static MediaPlayer hitPlayer;
	private static MediaPlayer backgroundMusicPlayer;

	private static boolean boomSoundPlayed = false;

	static {
		try {
			// Assign directly to class variables (no shadowing)
			
			kiSound = loadMedia("resource/sound/ki.mp3");
			deathSound = loadMedia("resource/sound/death.mp3");
			boomSound = loadMedia("resource/sound/boom.mp3");
			hitSound = loadMedia("resource/sound/hit.mp3");
			bgmSound = loadMedia("resource/sound/bgm.mp3");
			bgmSound = new Media(ClassLoader.getSystemResource("resource/sound/bgm.mp3").toString());

			kiBlastPlayer = new MediaPlayer(kiSound);
			kiBlastPlayer.setVolume(0.1); 

			deathPlayer = new MediaPlayer(deathSound);
			deathPlayer.setVolume(0.4);  

			boomPlayer = new MediaPlayer(boomSound);
			boomPlayer.setVolume(0.3);  

			hitPlayer = new MediaPlayer(hitSound);
			hitPlayer.setVolume(0.2);

	

			backgroundMusicPlayer = new MediaPlayer(bgmSound);

			backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			backgroundMusicPlayer.setVolume(0.2);

			// Reset players when sound ends
			kiBlastPlayer.setOnEndOfMedia(() -> kiBlastPlayer.seek(Duration.ZERO));
			deathPlayer.setOnEndOfMedia(() -> deathPlayer.seek(Duration.ZERO));
			boomPlayer.setOnEndOfMedia(() -> boomPlayer.seek(Duration.ZERO));
			hitPlayer.setOnEndOfMedia(() -> hitPlayer.seek(Duration.ZERO));

		} catch (Exception e) {
			System.err.println("Error loading media files:");
			e.printStackTrace();
		}
	}
	private static Media loadMedia(String path) {
		return new Media(ClassLoader.getSystemResource(path).toString());
	}
	private static void playNewSound(Media sound) {
		try {
			if (sound == null) return;
			MediaPlayer player = new MediaPlayer(sound);
			player.play();
			player.setOnEndOfMedia(player::dispose);
		} catch (Exception e) {
			System.err.println("Failed to play sound.");
			e.printStackTrace();
		}
	}

	public static void playKiSound() {
		playNewSound(kiSound);
	}

	public static void playDeathSound() {
		playNewSound(deathSound);
	}

	public static void playBoomSound() {
		if (!boomSoundPlayed) {
			playNewSound(boomSound);
			boomSoundPlayed = true;
		}
	}

	public static void resetBoomSound() {
		boomSoundPlayed = false;
	}

	public static void playHitSound() {
		playNewSound(hitSound);
	}

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
