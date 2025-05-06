package application;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class BackgroundManager {

	private ImageView placeholderImageView;
	private ImageView placeholderPurpleImageView;
	private MediaPlayer superDogBackgroundPlayer;
	private MediaPlayer ultraDogBackgroundPlayer;
	private MediaView mediaView;

	public BackgroundManager(Pane container) {
		try {
			Media greenSkyVideo = new Media(
					ClassLoader.getSystemResource("resource/background/greensky.mp4").toString());
			Media purpleSkyVideo = new Media(
					ClassLoader.getSystemResource("resource/background/purplesky.mp4").toString());

			superDogBackgroundPlayer = new MediaPlayer(greenSkyVideo);
			ultraDogBackgroundPlayer = new MediaPlayer(purpleSkyVideo);

			superDogBackgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			ultraDogBackgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);

			mediaView = new MediaView();
			mediaView.fitWidthProperty().bind(container.widthProperty());
			mediaView.fitHeightProperty().bind(container.heightProperty());

			// Load placeholder image
			Image placeholderImage = new Image(
					ClassLoader.getSystemResource("resource/background/greensky_firstframe.png").toString());
			placeholderImageView = new ImageView(placeholderImage);
			placeholderImageView.fitWidthProperty().bind(container.widthProperty());
			placeholderImageView.fitHeightProperty().bind(container.heightProperty());
			
			Image placeholderPurpleImage = new Image(
					ClassLoader.getSystemResource("resource/background/purplesky_firstframe.png").toString());
			placeholderPurpleImageView = new ImageView(placeholderPurpleImage);
			placeholderPurpleImageView.fitWidthProperty().bind(container.widthProperty());
			placeholderPurpleImageView.fitHeightProperty().bind(container.heightProperty());

			// Add placeholder image and mediaView to container
			container.getChildren().add(0, mediaView);
			container.getChildren().add(0, placeholderImageView); // make sure image is in front
			container.getChildren().add(0, placeholderPurpleImageView); // this is needed!

		} catch (Exception e) {
			System.out.println("Error loading video backgrounds: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void showSuperDogBackground() {
		if (mediaView != null && superDogBackgroundPlayer != null) {
			stopAllVideos();

			// Show placeholder while video is loading
			placeholderImageView.setVisible(true);
			mediaView.setVisible(false);

			mediaView.setMediaPlayer(superDogBackgroundPlayer);
			superDogBackgroundPlayer.seek(Duration.ZERO);

			superDogBackgroundPlayer.setOnReady(() -> {
				mediaView.setVisible(true);
				placeholderImageView.setVisible(false); // Hide placeholder when ready
				superDogBackgroundPlayer.play();
			});

			// Optional: Also ensure placeholder hides once playback actually starts
			superDogBackgroundPlayer.setOnPlaying(() -> {
				placeholderImageView.setVisible(false);
				mediaView.setVisible(true);
			});
		}
	}

	public void showUltraDogBackground() {
	    stopAllVideos();

	    placeholderPurpleImageView.setVisible(true);
	    mediaView.setVisible(false);

	    // Recreate MediaPlayer to avoid caching bugs
	    Media purpleSkyMedia = new Media(ClassLoader.getSystemResource("resource/background/purplesky.mp4").toString());
	    ultraDogBackgroundPlayer = new MediaPlayer(purpleSkyMedia);

	    // Attach to MediaView after ready
	    ultraDogBackgroundPlayer.setOnReady(() -> {
	        mediaView.setMediaPlayer(ultraDogBackgroundPlayer); // <-- Set only after ready!
	        mediaView.setVisible(true);
	        placeholderPurpleImageView.setVisible(false);
	        ultraDogBackgroundPlayer.play();
	    });

	    ultraDogBackgroundPlayer.setOnError(() -> {
	        System.out.println("UltraDog video load error: " + ultraDogBackgroundPlayer.getError());
	    });
	}


	private void stopAllVideos() {
		if (superDogBackgroundPlayer != null) {
			superDogBackgroundPlayer.stop();
		}
		if (ultraDogBackgroundPlayer != null) {
			ultraDogBackgroundPlayer.stop();
		}
	}

	public void pauseVideo() {
		MediaPlayer currentPlayer = mediaView.getMediaPlayer();
		if (currentPlayer != null) {
			currentPlayer.pause();
		}
	}

	public void resumeVideo() {
		MediaPlayer currentPlayer = mediaView.getMediaPlayer();
		if (currentPlayer != null) {
			currentPlayer.play();
		}
	}

	public void dispose() {
		stopAllVideos();

	}
}