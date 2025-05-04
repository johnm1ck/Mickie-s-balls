package model;

import base.Obstacle;
import application.ImageManager;
import javafx.scene.image.Image;

public class BigDog extends Obstacle {
    private static final int BIG_DOG_HIT_POINTS = 6;
    private static final int BIG_DOG_POINTS = 6;
    private static final double BIG_DOG_SPEED = 3;
    
    public BigDog(double x, double y) {
        super(x, y, 79, 79, BIG_DOG_HIT_POINTS, BIG_DOG_POINTS, BIG_DOG_SPEED);
        try {
            // Load sprite from resources\
        	
           //sprite = new Image("file:resource/bigdog.png".toString());
        	sprite = new Image(ImageManager.BIGDOG_URL);
        } catch (Exception e) {
            System.out.println("Could not load big dog sprite: " + e.getMessage());
        }
    }
}