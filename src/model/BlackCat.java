package model;

public class BlackCat extends MainCharacter {
    private static final int CAT_MAX_HP = 5;
    
    public BlackCat(double x, double y) {
        super(x, y, 60, 60, CAT_MAX_HP);
        try {
            // Load sprite from resources
            sprite = new javafx.scene.image.Image(getClass().getResourceAsStream("/resource/blackcat.png"));
        } catch (Exception e) {
            System.out.println("Could not load black cat sprite: " + e.getMessage());
        }
    }
}