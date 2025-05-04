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
	        Image placeholderImage = new Image(ClassLoader.getSystemResource("resource/background/greensky_firstfram.png").toString());
	        placeholderImageView = new ImageView(placeholderImage);
	        placeholderImageView.fitWidthProperty().bind(container.widthProperty());
	        placeholderImageView.fitHeightProperty().bind(container.heightProperty());

	        // Add placeholder image and mediaView to container
	        container.getChildren().add(0, mediaView);
	        container.getChildren().add(0, placeholderImageView); // make sure image is in front

	    } catch (Exception e) {
	        System.out.println("Error loading video backgrounds: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	public void showSuperDogBackground() {
	    if (mediaView != null && superDogBackgroundPlayer != null) {
	        stopAllVideos();

	        // Show placeholder
	        placeholderImageView.setVisible(true);
	        mediaView.setVisible(false);

	        // Prepare MediaPlayer
	        mediaView.setMediaPlayer(superDogBackgroundPlayer);
	        superDogBackgroundPlayer.seek(Duration.ZERO);

	        // Wait for 2-3 seconds, then switch to video
	        PauseTransition delay = new PauseTransition(Duration.seconds(5));
	        delay.setOnFinished(event -> {
	            placeholderImageView.setVisible(false);
	            mediaView.setVisible(true);
	            superDogBackgroundPlayer.play();
	        });
	        delay.play();
	    }
	}

	public void showUltraDogBackground() {
		if (mediaView != null && ultraDogBackgroundPlayer != null) {

			stopAllVideos();

			mediaView.setMediaPlayer(ultraDogBackgroundPlayer);
			ultraDogBackgroundPlayer.seek(Duration.ZERO);
			ultraDogBackgroundPlayer.play();
		}
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