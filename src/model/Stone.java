package model;

public class Stone extends Obstacle {
    private static final int STONE_HIT_POINTS = 1;
    private static final int STONE_POINTS = 1;
    private static final double STONE_SPEED = 3.0;
    
    public Stone(double x, double y) {
        super(x, y, 30, 30, STONE_HIT_POINTS, STONE_POINTS, STONE_SPEED);
        try {
            // Load sprite from resources
            sprite = new javafx.scene.image.Image(getClass().getResourceAsStream("/resource/stone.png"));
        } catch (Exception e) {
            System.out.println("Could not load stone sprite: " + e.getMessage());
        }
    }
}