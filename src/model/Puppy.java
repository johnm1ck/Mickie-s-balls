package model;

public class Puppy extends Obstacle {
    private static final int PUPPY_HIT_POINTS = 3;
    private static final int PUPPY_POINTS = 3;
    private static final double PUPPY_SPEED = 2.5;
    
    public Puppy(double x, double y) {
        super(x, y, 40, 40, PUPPY_HIT_POINTS, PUPPY_POINTS, PUPPY_SPEED);
        try {
            // Load sprite from resources
            sprite = new javafx.scene.image.Image(getClass().getResourceAsStream("/resource/puppy.png"));
        } catch (Exception e) {
            System.out.println("Could not load puppy sprite: " + e.getMessage());
        }
    }
}