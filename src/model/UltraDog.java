package model;

public class UltraDog extends Enemy {
    private static final int ULTRA_DOG_MAX_HP = 100;
    
    public UltraDog(double x, double y) {
        super(x, y, 80, 80, ULTRA_DOG_MAX_HP);
        try {
            // Load sprite from resources
            sprite = new javafx.scene.image.Image(getClass().getResourceAsStream("/resource/ultradog.png"));
        } catch (Exception e) {
            System.out.println("Could not load ultra dog sprite: " + e.getMessage());
        }
    }
}