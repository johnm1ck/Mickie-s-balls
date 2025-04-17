package application;

import gui.GameScreen;
import gui.StartScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private Scene startScene;
    private Scene gameScene;
    private GameController gameController;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cat vs Dogs Game");
        
        // Initialize the start screen
        StartScreen startScreen = new StartScreen(this);
        startScene = new Scene(startScreen, 800, 600);
        
        // Set the initial scene to the start screen
        primaryStage.setScene(startScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public void startNewGame(String characterType) {
        // Initialize game controller with selected character
        gameController = new GameController(characterType);
        
        // Initialize game screen
        GameScreen gameScreen = new GameScreen(gameController);
        gameScene = new Scene(gameScreen, 800, 600);
        
        // Setup key handlers
        setupKeyHandlers(gameScene);
        
        // Switch to game scene
        primaryStage.setScene(gameScene);
        
        // Start the game loop
        gameController.startGame();
    }
    
    private void setupKeyHandlers(Scene scene) {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                case UP:
                    gameController.moveCharacterUp();
                    break;
                case S:
                case DOWN:
                    gameController.moveCharacterDown();
                    break;
                case SPACE:
                    gameController.shootKiBlastRight();
                    break;
                case Z:
                    gameController.shootSpecialKiBlastLeft(false); // Regular special attack
                    break;
                case X:
                    gameController.shootSpecialKiBlastLeft(true);  // Ultimate attack
                    break;
                case P:
                    gameController.togglePause();
                    break;
                case ESCAPE:
                    returnToStartScreen();
                    break;
                default:
                    break;
            }
        });
        
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W:
                case UP:
                case S:
                case DOWN:
                    gameController.stopCharacterMovement();
                    break;
                default:
                    break;
            }
        });
    }
    
    public void returnToStartScreen() {
        if (gameController != null) {
            gameController.stopGame();
        }
        primaryStage.setScene(startScene);
    }
    
    public void exitGame() {
        primaryStage.close();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}