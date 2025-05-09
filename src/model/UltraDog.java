package model;

import application.MediaManager;
import base.BlasterState;
import base.Enemy;
import javafx.scene.image.Image;

public class UltraDog extends Enemy {
    private static final int ULTRA_DOG_MAX_HP = 100;
    private static final double ULTRA_DOG_BODY_SIZE = 108;
    private static final double ULTRA_DOG_MOVE_SPEED = 6.9;
    
    public UltraDog(double x, double y) {
        super(x, y, ULTRA_DOG_BODY_SIZE, ULTRA_DOG_BODY_SIZE, ULTRA_DOG_MAX_HP, ULTRA_DOG_MOVE_SPEED);
        try {
            // Load sprite from resources
        	this.sprite = new Image(MediaManager.ULTRADOG_URL);
        } catch (Exception e) {
            System.out.println("Could not load ultra dog sprite: " + e.getMessage());
        }
    }
    
    @Override
    public KiBlast shootKiBlast() {
        return new KiBlast(x + width, y + height/2, 1, 2, 13, BlasterState.ULTRA_ENEMY); // Right direction toward player
    }
}