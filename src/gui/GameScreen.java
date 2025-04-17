package gui;

import application.GameController;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameScreen extends BorderPane {
    private GameController gameController;
    private Canvas gameCanvas;
    private GraphicsContext gc;
    private PauseMenu pauseMenu;
    private GameOverScreen gameOverScreen;
    private VictoryScreen victoryScreen;
    private AnimationTimer renderTimer;
    
    public GameScreen(GameController gameController) {
        this.gameController = gameController;
        
        // Setup game canvas
        gameCanvas = new Canvas(800, 600);
        gc = gameCanvas.getGraphicsContext2D();
        
        // Initialize overlay menus
        pauseMenu = new PauseMenu(gameController);
        pauseMenu.setVisible(false);
        
        gameOverScreen = new GameOverScreen();
        gameOverScreen.setVisible(false);
        
        victoryScreen = new VictoryScreen();
        victoryScreen.setVisible(false);
        
        // Create stack pane to overlay menus on top of the game canvas
        StackPane gameArea = new StackPane();
        gameArea.getChildren().addAll(gameCanvas, pauseMenu, gameOverScreen, victoryScreen);
        
        // Add game area to the center of the border pane
        this.setCenter(gameArea);
        
        // Start render loop
        startRenderLoop();
    }
    
    private void startRenderLoop() {
        renderTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Clear the canvas
                gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
                
                // Draw background
                gc.setFill(Color.LIGHTBLUE);
                gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
                
                // Render game entities
                gameController.render(gc);
                
                // Update UI overlays
                updateOverlays();
            }
        };
        
        renderTimer.start();
    }
    
    private void updateOverlays() {
        // Show/hide pause menu
        pauseMenu.setVisible(gameController.isPaused());
        
        // Check for game over or victory conditions
        if (gameController.getMainCharacter().isDefeated()) {
            gameOverScreen.setVisible(true);
            gameController.stopGame();
            renderTimer.stop();
        } else if (gameController.isGameWon()) {
            victoryScreen.setVisible(true);
            gameController.stopGame();
            renderTimer.stop();
        }
    }
    
    // Inner class for pause menu
    private class PauseMenu extends VBox {
        public PauseMenu(GameController gameController) {
            this.setAlignment(Pos.CENTER);
            this.setSpacing(20);
            this.setPadding(new Insets(20));
            this.setMaxWidth(500);
            this.setMaxHeight(400);
            this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");
            
            // Title
            Text title = new Text("GAME PAUSED");
            title.setFont(new Font(28));
            title.setFill(Color.WHITE);
            
            // Controls info
            VBox controlsBox = new VBox(10);
            controlsBox.setAlignment(Pos.CENTER_LEFT);
            
            Text controlsTitle = new Text("Controls:");
            controlsTitle.setFont(new Font(20));
            controlsTitle.setFill(Color.WHITE);
            
            Text[] controls = {
                new Text("W/Up Arrow: Move Up"),
                new Text("S/Down Arrow: Move Down"),
                new Text("Space: Shoot Ki Blast (Right)"),
                new Text("Z: Special Attack (Left, 10 points)"),
                new Text("X: Ultimate Attack (Left, requires enemy's max HP points)"),
                new Text("P: Pause/Resume Game"),
                new Text("ESC: Return to Main Menu")
            };
            
            for (Text control : controls) {
                control.setFill(Color.WHITE);
                control.setFont(new Font(16));
            }
            
            controlsBox.getChildren().add(controlsTitle);
            controlsBox.getChildren().addAll(controls);
            
            // Resume button
            Button resumeButton = new Button("Resume Game");
            resumeButton.setPrefWidth(200);
            resumeButton.setOnAction(e -> gameController.togglePause());
            
            // Add components to the pause menu
            this.getChildren().addAll(title, controlsBox, resumeButton);
        }
    }
    
    // Inner class for game over screen
    private class GameOverScreen extends VBox {
        public GameOverScreen() {
            this.setAlignment(Pos.CENTER);
            this.setSpacing(20);
            this.setPadding(new Insets(20));
            this.setMaxWidth(500);
            this.setMaxHeight(300);
            this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
            
            // Title
            Text title = new Text("GAME OVER");
            title.setFont(new Font(32));
            title.setFill(Color.RED);
            
            // Score display
            Text scoreText = new Text("Your Score: " + gameController.getScore());
            scoreText.setFont(new Font(20));
            scoreText.setFill(Color.WHITE);
            
            // Buttons
            HBox buttonBox = new HBox(20);
            buttonBox.setAlignment(Pos.CENTER);
            
            Button retryButton = new Button("Try Again");
            retryButton.setPrefWidth(150);
            
            Button mainMenuButton = new Button("Main Menu");
            mainMenuButton.setPrefWidth(150);
            
            buttonBox.getChildren().addAll(retryButton, mainMenuButton);
            
            // Add components to the game over screen
            this.getChildren().addAll(title, scoreText, buttonBox);
        }
    }
    
    // Inner class for victory screen
    private class VictoryScreen extends VBox {
        public VictoryScreen() {
            this.setAlignment(Pos.CENTER);
            this.setSpacing(20);
            this.setPadding(new Insets(20));
            this.setMaxWidth(500);
            this.setMaxHeight(300);
            this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
            
            // Title
            Text title = new Text("VICTORY!");
            title.setFont(new Font(32));
            title.setFill(Color.GOLD);
            
            // Score display
            Text scoreText = new Text("Final Score: " + gameController.getScore());
            scoreText.setFont(new Font(20));
            scoreText.setFill(Color.WHITE);
            
            // Buttons
            HBox buttonBox = new HBox(20);
            buttonBox.setAlignment(Pos.CENTER);
            
            Button playAgainButton = new Button("Play Again");
            playAgainButton.setPrefWidth(150);
            
            Button mainMenuButton = new Button("Main Menu");
            mainMenuButton.setPrefWidth(150);
            
            buttonBox.getChildren().addAll(playAgainButton, mainMenuButton);
            
            // Add components to the victory screen
            this.getChildren().addAll(title, scoreText, buttonBox);
        }
    }
}