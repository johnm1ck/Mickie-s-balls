package application;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class BackgroundManager {

	private ImageView spacePlaceholder = Main.spacePlaceholder;
	private ImageView greenPlaceholder = Main.greenPlaceholder;
	private ImageView purplePlaceholder = Main.purplePlaceholder;
	private MediaPlayer greenSkyPlayer;
	private MediaPlayer purpleSkyPlayer;
	private MediaPlayer spacePlayer;
	private MediaView mediaView;

	public BackgroundManager(Pane container) {
		try {
			mediaView = new MediaView();

			// Add placeholder image and mediaView to container
			container.getChildren().add(0, greenPlaceholder);  // make sure image is in front
			container.getChildren().add(0, mediaView);
			container.getChildren().add(0, purplePlaceholder);

		} catch (Exception e) {
			System.out.println("Error loading video backgrounds: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public BackgroundManager(Pane container, boolean isStart) {
		try {
			mediaView = new MediaView();

			// Add placeholder image and mediaView to container
			container.getChildren().add(0, spacePlaceholder);  // make sure image is in front
			container.getChildren().add(0, mediaView);
			
			showSpace();
			
		} catch (Exception e) {
			System.out.println("Error loading video backgrounds: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void showSpace() {
		disposeAllPlayers();
		
		// Show placeholder while video is loading
		spacePlaceholder.setVisible(true);
		greenPlaceholder.setVisible(false);
		purplePlaceholder.setVisible(false);
		mediaView.setVisible(false);

		spacePlayer = new MediaPlayer(Main.spaceMedia);
		spacePlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		spacePlayer.setOnReady(() -> {
			spacePlaceholder.setVisible(false); // Hide placeholder when ready
			mediaView.setMediaPlayer(spacePlayer); // Set only after ready
			mediaView.setVisible(true);
			spacePlayer.play();
		});

		spacePlayer.setOnError(() -> {
			if(spacePlayer.getError() != null)
				showSpace();
	    });
	}

	public void showGreenSky() {
		disposeAllPlayers();
		
		// Show placeholder while video is loading
		greenPlaceholder.setVisible(true);
		purplePlaceholder.setVisible(false);
		mediaView.setVisible(false);

		greenSkyPlayer = new MediaPlayer(Main.greenSkyMedia);
		greenSkyPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		greenSkyPlayer.setOnReady(() -> {
			greenPlaceholder.setVisible(false); // Hide placeholder when ready
			mediaView.setMediaPlayer(greenSkyPlayer); // Set only after ready
			mediaView.setVisible(true);
			greenSkyPlayer.play();
		});

		greenSkyPlayer.setOnError(() -> {
			if(greenSkyPlayer.getError() != null)
				showGreenSky();
	    });
	}

	public void showPurpleSky() {
	    disposeAllPlayers();

	    // Show placeholder while video is loading
	    purplePlaceholder.setVisible(true);
	    mediaView.setVisible(false);
	    greenPlaceholder.setVisible(false);

	    purpleSkyPlayer = new MediaPlayer(Main.purpleSkyMedia);
	    purpleSkyPlayer.setCycleCount(MediaPlayer.INDEFINITE);

	    // Attach to MediaView after ready
	    purpleSkyPlayer.setOnReady(() -> {
	    	purplePlaceholder.setVisible(false); // Hide placeholder when ready
	        mediaView.setMediaPlayer(purpleSkyPlayer); // Set only after ready
	        mediaView.setVisible(true);
	        purpleSkyPlayer.play();
	    });

	    purpleSkyPlayer.setOnError(() -> {
	    	if(purpleSkyPlayer.getError() != null)
	    		showPurpleSky();
	    });
	}


	private void disposeAllPlayers() {
		if (greenSkyPlayer != null) {
			greenSkyPlayer.dispose();
		}
		if (purpleSkyPlayer != null) {
			purpleSkyPlayer.dispose();
		}
		if (spacePlayer != null) {
			spacePlayer.dispose();
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
		disposeAllPlayers();
	}
}