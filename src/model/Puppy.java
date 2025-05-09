package model;

import application.MediaManager;
import base.Obstacle;
import javafx.scene.image.Image;

public class Puppy extends Obstacle {
    private static final int PUPPY_HIT_POINTS = 5;
    private static final int PUPPY_POINTS = 5;
    private static final double PUPPY_SPEED = 3;
    private static final double PUPPY_BODY_SIZE = 64;
    
    public Puppy(double x, double y) {
        super(x, y, PUPPY_BODY_SIZE, PUPPY_BODY_SIZE, PUPPY_HIT_POINTS, PUPPY_POINTS, PUPPY_SPEED);
        try {
            // Load sprite from resources
            sprite = new Image(MediaManager.PUPPY_URL);
        } catch (Exception e) {
            System.out.println("Could not load puppy sprite: " + e.getMessage());
        }
    }
}