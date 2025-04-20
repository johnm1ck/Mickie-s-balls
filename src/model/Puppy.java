package model;

import base.Obstacle;
import javafx.scene.image.Image;

public class Puppy extends Obstacle {
    private static final int PUPPY_HIT_POINTS = 4;
    private static final int PUPPY_POINTS = 4;
    private static final double PUPPY_SPEED = 2;
    
    public Puppy(double x, double y) {
        super(x, y, 64, 64, PUPPY_HIT_POINTS, PUPPY_POINTS, PUPPY_SPEED);
        try {
            // Load sprite from resources
            sprite = new Image("file:resource/puppy.png");
        } catch (Exception e) {
            System.out.println("Could not load puppy sprite: " + e.getMessage());
        }
    }
}