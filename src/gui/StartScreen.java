package gui;

import application.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartScreen extends VBox {
    private Main main;
    
    public StartScreen(Main main) {
        this.main = main;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPadding(new javafx.geometry.Insets(50));
        this.setStyle("-fx-background-color: lightblue;");
        
        // Show the main menu initially
        showMainMenu();
    }
    
    private void showMainMenu() {
        // Clear any existing content
        this.getChildren().clear();
        
        // Game title
        Text title = new Text("Cat vs Dogs Game");
        title.setFont(new Font(40));
        title.setStyle("-fx-fill: darkblue;");
        
        // Subtitle
        Text subtitle = new Text("A JavaFX Battle Game");
        subtitle.setFont(new Font(20));
        subtitle.setStyle("-fx-fill: #444;");
        
        // Start game button
        Button startGameBtn = new Button("Start New Game");
        startGameBtn.setPrefWidth(200);
        startGameBtn.setStyle("-fx-font-size: 16px; -fx-background-radius: 10;");
        startGameBtn.setOnAction(e -> showCharacterSelection());
        
        // Exit game button
        Button exitGameBtn = new Button("Exit Game");
        exitGameBtn.setPrefWidth(200);
        exitGameBtn.setStyle("-fx-font-size: 16px; -fx-background-radius: 10;");
        exitGameBtn.setOnAction(e -> main.exitGame());
        
        // Add components to the layout
        this.getChildren().addAll(title, subtitle, new VBox(10), startGameBtn, exitGameBtn);
    }
    
    private void showCharacterSelection() {
        // Clear previous content
        this.getChildren().clear();
        
        // Character selection title
        Text title = new Text("Select Your Character");
        title.setFont(new Font(30));
        title.setStyle("-fx-fill: darkblue;");
        
        // Character selection instructions
        Text instructions = new Text("Choose your cat to battle the evil dogs!");
        instructions.setFont(new Font(16));
        
        // Character options
        HBox characterOptions = new HBox(50);
        characterOptions.setAlignment(Pos.CENTER);
        characterOptions.setPadding(new javafx.geometry.Insets(20));
        
        // Orange cat option
        VBox orangeCatOption = createCharacterOption(
            "file:resource/orangecat.png", 
            "Orange Cat", 
            "orange",
            "Automatic ki blasting"
        );
        
        // White cat option
        VBox whiteCatOption = createCharacterOption(
            "file:resource/whitecat.png", 
            "White Cat", 
            "white",
            "Manual ki blasting"
        );
        
        characterOptions.getChildren().addAll(orangeCatOption, whiteCatOption);
        
        // Back button
        Button backButton = new Button("Back to Main Menu");
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 10;");
        backButton.setOnAction(e -> showMainMenu());
        
        // Add components to the layout
        this.getChildren().addAll(title, instructions, characterOptions, backButton);
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
        name.setFont(new Font(18));
        name.setStyle("-fx-font-weight: bold;");
        
        // Character stats
        Text statsText = new Text(stats);
        statsText.setFont(new Font(14));
        
        // Select button with improved styling
        Button selectBtn = new Button("Select " + characterName);
        selectBtn.setPrefWidth(180);
        selectBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5;");
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