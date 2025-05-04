package application;

import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;

public class BackgroundManager {
	private MediaPlayer superDogBackgroundPlayer;
	private MediaPlayer ultraDogBackgroundPlayer;
	private MediaView mediaView;

	public BackgroundManager(Pane container) {

		try {

			// Media greenSkyVideo = new Media(new
			// File("resource/greensky.mp4").toURI().toString());
			Media greenSkyVideo = new Media(
					ClassLoader.getSystemResource("resource/background/greensky.mp4").toString());
			Media purpleSkyVideo = new Media(
					ClassLoader.getSystemResource("resource/background/purplesky.mp4").toString());

			superDogBackgroundPlayer = new MediaPlayer(greenSkyVideo);
			ultraDogBackgroundPlayer = new MediaPlayer(purpleSkyVideo);

			superDogBackgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			ultraDogBackgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);

//            
			mediaView = new MediaView();

//         
			mediaView.fitWidthProperty().bind(container.widthProperty());
			mediaView.fitHeightProperty().bind(container.heightProperty());

			container.getChildren().add(0, mediaView);

		} catch (Exception e) {
			System.out.println("Error loading video backgrounds: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void showSuperDogBackground() {
		if (mediaView != null && superDogBackgroundPlayer != null) {

			stopAllVideos();

			mediaView.setMediaPlayer(superDogBackgroundPlayer);
			superDogBackgroundPlayer.seek(Duration.ZERO);
			superDogBackgroundPlayer.play();
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