package model;

public class SuperDog extends Enemy {
    private static final int SUPER_DOG_MAX_HP = 69;
    
    public SuperDog(double x, double y) {
        super(x, y, 70, 70, SUPER_DOG_MAX_HP);
        try {
            // Load sprite from resources
            sprite = new javafx.scene.image.Image(getClass().getResourceAsStream("/resource/superdog.png"));
        } catch (Exception e) {
            System.out.println("Could not load super dog sprite: " + e.getMessage());
        }
    }
}