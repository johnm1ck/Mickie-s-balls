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
    private GameScreen gameScreen;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cat vs Dogs Game");
        
        // Initialize the start screen
        StartScreen startScreen = new StartScreen(this);
        startScene = new Scene(startScreen, GameController.getWidth(), GameController.getHeight());
        
        // Set the initial scene to the start screen
        primaryStage.setScene(startScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // Play background music when game starts
        SoundManager.startBackgroundMusic();
    }
    
    public void startNewGame(String characterType) {
        // Clean up previous game resources if they exist
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        
        // Initialize game controller with selected character
        gameController = new GameController(characterType);
        
        // Initialize game screen
        gameScreen = new GameScreen(gameController, this);
        gameScene = new Scene(gameScreen, GameController.getWidth(), GameController.getHeight());
        
        // Setup key handlers
        setupKeyHandlers(gameScene);
        
        // Set up cheat code listener
        CheatCodeManager cheatManager = new CheatCodeManager();
        cheatManager.setupCheatCodeListener(gameScene, gameController);
        
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
                	if(this.gameController.getCharacterType() == "white") {                		
                		gameController.shootKiBlastRight();
                	}
                    break;
                case Z:
                    gameController.shootKiBlastLeft(false); // Regular special attack
                    break;
                case X:
                    gameController.shootKiBlastLeft(true);  // Ultimate attack
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
        
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        
        primaryStage.setScene(startScene);
    }
    
    public void exitGame() {
        // Clean up resources
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        
        // Stop any playing sounds before exiting
        SoundManager.stopBackgroundMusic();
        primaryStage.close();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}