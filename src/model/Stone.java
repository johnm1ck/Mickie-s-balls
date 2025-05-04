package model;

import application.ImageManager;
import base.Obstacle;
import javafx.scene.image.Image;

public class Stone extends Obstacle {
    private static final int STONE_HIT_POINTS = 2;
    private static final int STONE_POINTS = 2;
    private static final double STONE_SPEED = 3.0;
    
    public Stone(double x, double y) {
        super(x, y, 41, 41, STONE_HIT_POINTS, STONE_POINTS, STONE_SPEED);
        try {
            // Load sprite from resources
            sprite = new Image(ImageManager.STONE_URL);
        } catch (Exception e) {
            System.out.println("Could not load stone sprite: " + e.getMessage());
        }
    }
}