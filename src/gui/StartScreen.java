package gui;

import application.MediaManager;
import application.BackgroundManager;
import application.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartScreen extends StackPane {
    private Main main;
    private VBox startScreen;
    
    public StartScreen(Main main) {
        this.main = main;
        this.startScreen = new VBox(20);
        startScreen.setAlignment(Pos.CENTER);
        startScreen.setPadding(new javafx.geometry.Insets(50));
        //this.setBackground(new Background(new BackgroundImage(new Image("resource/background/main1.png"), null, null, null, null)));
        //startScreen.setStyle("-fx-background-color: lightblue;");
        
        this.getChildren().add(0, startScreen);
        new BackgroundManager(this, true);
        
        // Show the main menu initially
        showMainMenu();
    }
    
    private void showMainMenu() {
        // Clear any existing content
        startScreen.getChildren().clear();
        
        // Game title
        Text title = new Text("Catto a Go Go !");
        //title.setFont(new Font("Comic Sans MS", 40));
        title.setStyle("-fx-font: 120px 'Comic Sans MS';"
        		+ "-fx-font-weight: bold;"
        		+ "-fx-fill: radial-gradient(focus-distance 79%,"
        		+ "center 25% 0%, radius 89%, reflect, goldenrod, midnightblue);"
        		+ "-fx-stroke: whitesmoke; -fx-stroke-width: 1;");
        
        // Subtitle
        Text subtitle = new Text("catto dont like da doggos");
        //subtitle.setFont(new Font("Comic Sans MS", 29));
        subtitle.setStyle("-fx-font: 23px 'Courier New';"
        		+ "-fx-fill: moccasin;");
        
        // Start game button
        Button startGameBtn = new Button("Start New Game");
        startGameBtn.setPrefWidth(200);
        startGameBtn.setStyle("-fx-font: 20px 'Impact'; -fx-padding: 10; -fx-text-fill: floralwhite;"
        		+ "-fx-background-radius: 10; -fx-background-color: cadetblue;");
        startGameBtn.setOnAction(e -> showCharacterSelection());
        
        // Exit game button
        Button exitGameBtn = new Button("Exit Game");
        exitGameBtn.setPrefWidth(200);
        exitGameBtn.setStyle("-fx-font: 20px 'Impact'; -fx-padding: 10; -fx-text-fill: floralwhite;"
        		+ "-fx-background-radius: 10; -fx-background-color: darkred;");
        exitGameBtn.setOnAction(e -> main.exitGame());
        
        // Add components to the layout
        startScreen.getChildren().addAll(title, subtitle, startGameBtn, exitGameBtn);
    }
    
    private void showCharacterSelection() {
        // Clear previous content
    	startScreen.getChildren().clear();
        
        // Character selection title
        Text title = new Text("Select Your Catto");
        //title.setFont(new Font(30));
        title.setStyle("-fx-font: 43px 'Courier New';"
        		+ "-fx-fill: moccasin;");
        
        // Character selection instructions
        Text instructions = new Text("Choose your cat to battle the evil dogs!");
        instructions.setFont(new Font(16));
        
        // Character options
        HBox characterOptions = new HBox(50);
        characterOptions.setAlignment(Pos.CENTER);
        characterOptions.setPadding(new javafx.geometry.Insets(20));
        
        // Orange cat option
        VBox orangeCatOption = createCharacterOption(
            MediaManager.ORANGECAT_URL, 
            "Orange Cat", 
            "orange",
            "Automatic ki blasting"
        );
        
        // White cat option
        VBox whiteCatOption = createCharacterOption(
        		MediaManager.WHITECAT_URL, 
            "White Cat", 
            "white",
            "Manual ki blasting"
        );
        
        characterOptions.getChildren().addAll(orangeCatOption, whiteCatOption);
        
        // Back button
        Button backButton = new Button("Back to Main Menu");
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-font: 17px 'Impact'; -fx-padding: 10; -fx-text-fill: floralwhite;"
        		+ "-fx-background-radius: 10; -fx-background-color: darkred;");
        backButton.setOnAction(e -> showMainMenu());
        
        // Add components to the layout
        startScreen.getChildren().addAll(title, instructions, characterOptions, backButton);
    }
    
    private VBox createCharacterOption(String imagePath, String characterName, String characterType, String stats) {
        VBox option = new VBox(15);
        option.setAlignment(Pos.CENTER);
        option.setPadding(new javafx.geometry.Insets(10));
        option.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 15;");
        
        // Character image
        ImageView characterImage = new ImageView();
        try {
            Image image = new Image(imagePath);
            characterImage.setImage(image);
            characterImage.setFitWidth(120);
            characterImage.setFitHeight(120);
            characterImage.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Could not load character image: " + e.getMessage());
            // Create a placeholder rectangle
            javafx.scene.shape.Rectangle placeholder = new javafx.scene.shape.Rectangle(120, 120);
            placeholder.setFill(javafx.scene.paint.Color.LIGHTGRAY);
            option.getChildren().add(placeholder);
        }
        
        // Character name
        Text name = new Text(characterName);
        //name.setFont(new Font(18));
        name.setStyle("-fx-font: 23px 'Impact';"
        		+ "-fx-fill: floralwhite;");
        
        // Character stats
        Text statsText = new Text(stats);
        //statsText.setFont(new Font(14));
        statsText.setStyle("-fx-font: 17px 'Comic Sans MS';"
        		+ "-fx-fill: floralwhite;");
        
        // Select button with improved styling
        Button selectBtn = new Button("Select " + characterName);
        selectBtn.setPrefWidth(180);
        selectBtn.setStyle("-fx-font: 17px 'Impact'; "
        		+ "-fx-background-color: darkgreen;"
        		+ "-fx-text-fill: floralwhite; -fx-background-radius: 10;");
        selectBtn.setOnAction(e -> {
            // Start the game with the selected character type
            main.startNewGame(characterType);
        });
        
        // Add components to the option
        if (characterImage.getImage() != null) {
            option.getChildren().addAll(characterImage, name, statsText, selectBtn);
        } else {
            option.getChildren().addAll(name, statsText, selectBtn);
        }
        
        return option;
    }
}