package application;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundManager {
    private static MediaPlayer kiBlastPlayer;
    private static MediaPlayer deathPlayer;
    private static MediaPlayer boomPlayer;
    private static MediaPlayer hitPlayer;
    private static MediaPlayer backgroundMusicPlayer;
    
    // Track if boom sound has been played for super saiyan transformation
    private static boolean boomSoundPlayed = false;

    // Initialize the media players
    static {
        try {
            // Load all sound effects
            Media kiSound = new Media(new File("resource/ki.mp3").toURI().toString());
            Media deathSound = new Media(new File("resource/death.mp3").toURI().toString());
            Media boomSound = new Media(new File("resource/boom.mp3").toURI().toString());
            Media hitSound = new Media(new File("resource/hit.mp3").toURI().toString());
            Media bgmSound = new Media(new File("resource/bgm.mp3").toURI().toString());

            // Create players
            kiBlastPlayer = new MediaPlayer(kiSound);
            deathPlayer = new MediaPlayer(deathSound);
            boomPlayer = new MediaPlayer(boomSound);
            hitPlayer = new MediaPlayer(hitSound);
            backgroundMusicPlayer = new MediaPlayer(bgmSound);
            backgroundMusicPlayer.setVolume(0.2);

            // Set players to reset when they finish playing
            kiBlastPlayer.setOnEndOfMedia(() -> kiBlastPlayer.seek(Duration.ZERO));
            deathPlayer.setOnEndOfMedia(() -> deathPlayer.seek(Duration.ZERO));
            boomPlayer.setOnEndOfMedia(() -> boomPlayer.seek(Duration.ZERO));
            hitPlayer.setOnEndOfMedia(() -> hitPlayer.seek(Duration.ZERO));
            
            // Set background music to loop
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            
        } catch (Exception e) {
            System.out.println("Error loading sound files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Play the ki blast sound
    public static void playKiSound() {
//        if (kiBlastPlayer != null) {
//            kiBlastPlayer.stop();
//            kiBlastPlayer.seek(Duration.ZERO);
//            kiBlastPlayer.play();
//        }
    	 playNewSound("resource/ki.mp3");
    }

    // Play the death sound
    public static void playDeathSound() {
//        if (deathPlayer != null && !isDeathSoundPlaying()) {
//            deathPlayer.stop();
//            deathPlayer.seek(Duration.ZERO);
//            deathPlayer.play();
//        }
    	 playNewSound("resource/death.mp3");
    }

    // Check if death sound is already playing
    private static boolean isDeathSoundPlaying() {
        return deathPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    // Play the transformation sound (only once per transformation)
    public static void playBoomSound() {
//        if (boomPlayer != null && !boomSoundPlayed) {
//            boomPlayer.stop();
//            boomPlayer.seek(Duration.ZERO);
//            boomPlayer.play();
//            boomSoundPlayed = true;
//        }
    	 playNewSound("resource/boom.mp3");
    }
    
    // Reset boom sound flag (for new game)
    public static void resetBoomSound() {
        boomSoundPlayed = false;
    }
    
    // Play hit sound when player takes damage
    public static void playHitSound() {
//        if (hitPlayer != null) {
//            hitPlayer.stop();
//            hitPlayer.seek(Duration.ZERO);
//            hitPlayer.play();
//        }
    	 playNewSound("resource/hit.mp3");
    }
    
    // Start background music
    public static void startBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.seek(Duration.ZERO);
            backgroundMusicPlayer.play();
        }
    }
    
    // Stop background music
    public static void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }
    
    // Pause background music
    public static void pauseBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.pause();
        }
    }
    
    // Resume background music
    public static void resumeBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }
    }
    private static void playNewSound(String filePath) {
        try {
            Media sound = new Media(new File(filePath).toURI().toString());
            MediaPlayer player = new MediaPlayer(sound);
            player.play();

            // Dispose when done to free memory
            player.setOnEndOfMedia(() -> {
                player.dispose();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}