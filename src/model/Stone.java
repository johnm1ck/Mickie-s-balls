package model;

import application.MediaManager;
import base.Obstacle;
import javafx.scene.image.Image;

public class Stone extends Obstacle {
    private static final int STONE_HIT_POINTS = 3;
    private static final int STONE_POINTS = 3;
    private static final double STONE_SPEED = 3.1;
    private static final double STONE_BODY_SIZE = 41;
    
    public Stone(double x, double y) {
        super(x, y, STONE_BODY_SIZE, STONE_BODY_SIZE, STONE_HIT_POINTS, STONE_POINTS, STONE_SPEED);
        try {
            // Load sprite from resources
            sprite = new Image(MediaManager.STONE_URL);
        } catch (Exception e) {
            System.out.println("Could not load stone sprite: " + e.getMessage());
        }
    }
}