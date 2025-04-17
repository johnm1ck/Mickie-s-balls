package model;

public class BigDog extends Obstacle {
    private static final int BIG_DOG_HIT_POINTS = 6;
    private static final int BIG_DOG_POINTS = 6;
    private static final double BIG_DOG_SPEED = 2.0;
    
    public BigDog(double x, double y) {
        super(x, y, 50, 50, BIG_DOG_HIT_POINTS, BIG_DOG_POINTS, BIG_DOG_SPEED);
        try {
            // Load sprite from resources
            sprite = new javafx.scene.image.Image(getClass().getResourceAsStream("/resource/bigdog.png"));
        } catch (Exception e) {
            System.out.println("Could not load big dog sprite: " + e.getMessage());
        }
    }
}